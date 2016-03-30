import com.mongodb.*;
import dao.ElExpressionService;
import dao.MongoDAOUtil;
import org.apache.commons.lang.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cvertiz on 3/29/16.
 */
public class DbTreeTest {

    public static void init(){
        try {
            MongoDAOUtil.setupMongodb("localhost",27017, "riot_main", null , null, "admin", "control123!");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static String getThingPath(String serialNumber){
        Map<String,Object> paths = new HashMap<>();
        paths.put("PALLETE01",".");
        paths.put("RFID00","rfid.");
        paths.put("CARTON01","carton.");
        paths.put("RFID01","carton.rfid.");
        paths.put("RFID02","carton.rfid.");
        paths.put("BOX01","carton.box.");
        paths.put("RFID03","carton.box.rfid.");
        paths.put("ITEM01","carton.box.item.");
        paths.put("RFID04","carton.box.item.rfid.");
        paths.put("RFID05","carton.box.item.rfid.");
        return (String) paths.get(serialNumber);
    }

    public static List<String> getPaths(){
        List<String> paths = new ArrayList<>();
        paths.add("carton");
        paths.add("rfid");
        paths.add("carton.box");
        paths.add("carton.rfid");
        paths.add("carton.box.item");
        paths.add("carton.box.rfid");
        paths.add("carton.box.item.rfid");
        return paths;
    }

    public static void getAllThingsTreeView(){
        List<DBObject> result = new ArrayList<>();
        DBCursor cursor = MongoDAOUtil.getInstance().getCollection("tree_things").find();
        while (cursor.hasNext()){
            result.add(cursor.next());
        }
        System.out.println(result);
    }

    public static void getThingBySerial(String serialNumber,String thingPath,Boolean treeView){
        List<DBObject> result = new ArrayList<>();
        if (thingPath == null){
            return;
        }
        String expression = "${" + StringUtils.substring(thingPath,0,thingPath.length()-1) + "}";
        DBCursor cursor = MongoDAOUtil.getInstance().getCollection("tree_things").find(new BasicDBObject(thingPath + "serialNumber", serialNumber));
        while (cursor.hasNext()){
            if (treeView == Boolean.TRUE){
                result.add(cursor.next());
            } else {
                Object thing;
                thing = getFormulaValue(null,cursor.next(),expression);
                if (thing instanceof BasicDBList){
                    for(int i = 0; i < ((BasicDBList)thing).size(); i++)
                        if( ((BasicDBObject)((BasicDBList)thing).get(i)).get("serialNumber").equals(serialNumber) ){
                            result.add(removeChildren(((BasicDBObject)((BasicDBList)thing).get(i))));
                        }
                } else {
                    result.add(removeChildren((BasicDBObject)thing));
                }
            }
        }
        System.out.println(result);
    }


    public static void getThingsBySerial(String serialNumber,Boolean treeView){
        List<DBObject> result = new ArrayList<>();
        BasicDBObject query = new BasicDBObject();
        BasicDBList orDB = new BasicDBList();
        for (String thingPath : getPaths()){
            DBObject orQuery = new BasicDBObject(thingPath + ".serialNumber",new BasicDBObject("$regex", java.util.regex.Pattern.compile(serialNumber)).append("$options", "i"));
            orDB.add(orQuery);
        }
        query.append("$or", orDB);
        DBCursor cursor = MongoDAOUtil.getInstance().getCollection("tree_things").find(query);
        while (cursor.hasNext()){
            if (treeView == Boolean.TRUE){
                result.add(cursor.next());
            } else {
                DBObject dbObject = cursor.next();
                String serial = (String) dbObject.get("serialNumber");
                if (serial != null && !StringUtils.isEmpty(serial) && StringUtils.contains(serial,serialNumber)) {
                    result.add(removeChildren(dbObject));
                }
                for (String expression : getPaths()){
                    Object thing = null;
                    thing = getFormulaValue(null,dbObject,"${" + expression + "}");
                    if (thing instanceof BasicDBList){
                        for(int i = 0; i < ((BasicDBList)thing).size(); i++)
                            if( StringUtils.contains((String)((BasicDBObject)((BasicDBList)thing).get(i)).get("serialNumber"),serialNumber) ){
                                result.add(removeChildren(((BasicDBObject)((BasicDBList)thing).get(i))));
                            }
                    } else {
                        String sn = (String) ((BasicDBObject)thing).get("serialNumber");
                        if (sn != null && !StringUtils.isEmpty(sn) && StringUtils.contains(sn,serialNumber)){
                            result.add(removeChildren((BasicDBObject)thing));
                        }
                    }
                }
            }
        }
        System.out.println(result);
    }

    public static Object getFormulaValue(Map<String, Object> udf, Object thing, String formula){
        Object result;
        ElExpressionService ees = new ElExpressionService();
        ees.initialize(udf,thing);
        result = ees.evaluate(formula,false);
        return result;
    }

    public static BasicDBObject removeChildren(DBObject thing){
        Map<String,Object> thingAsMap = thing.toMap();

        BasicDBObject result = new BasicDBObject();
        for (Map.Entry<String, Object> property : thingAsMap.entrySet()){
            if (property.getValue() instanceof BasicDBObject){
                if (!((BasicDBObject)property.getValue()).get("isChild").equals("true")){
                    result.append(property.getKey(), property.getValue());
                }
            } else if (property.getValue() instanceof BasicDBList){
            } else {
                result.append(property.getKey(), property.getValue());
            }
        }
        return result;
    }

    public static void main(String [] args){
        init();
        String serialNumber = "0";
        String thingPath = getThingPath(serialNumber);

//        test get all things
//        getAllThingsTreeView();

//        test get thing by serial in tree view
//        getThingBySerial(serialNumber,thingPath,true);

//        test get thing by serial no tree view
//        getThingBySerial(serialNumber,thingPath,false);

//        test get things by serial in tree view (like clause)
        getThingsBySerial(serialNumber,false);
    }

}
