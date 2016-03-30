import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import dao.ElExpressionService;
import dao.MongoDAOUtil;
import org.apache.commons.lang.StringUtils;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cvertiz on 3/29/16.
 */
public class dbTreePerformanceTest {

    public static void init(){
        try {
            MongoDAOUtil.setupMongodb("localhost",27017, "riot_main", null , null, "admin", "control123!");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static void main(String [] args){
        init();

        // test get all things
        //getAllThings();

        String serialNumber = "RFID02";
        String thingPath = getPath(serialNumber);
        // test get thing by serial in tree view
        // getThingBySerialTreeView(serialNumber, thingPath);
        // test get thing by serial no tree view
        getThingBySerialNoTreeView(serialNumber, thingPath);
    }

    public static String getPath(String serialNumber){
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

    public static void getAllThings(){
        List<DBObject> result = new ArrayList<>();
        DBCursor cursor = MongoDAOUtil.getInstance().getCollection("tree_things").find(new BasicDBObject("_id", 1));
        while (cursor.hasNext()){
            result.add(cursor.next());
        }
        System.out.println(result);
    }

    public static void getThingBySerialTreeView(String serialNumber,String thingPath){
        List<DBObject> result = new ArrayList<>();
        DBCursor cursor = MongoDAOUtil.getInstance().getCollection("tree_things").find(new BasicDBObject(thingPath + "serialNumber", serialNumber));
        while (cursor.hasNext()){
            result.add(cursor.next());
        }
        System.out.println(result);
    }

    public static void getThingBySerialNoTreeView(String serialNumber,String thingPath){
        List<DBObject> things = new ArrayList<>();
        String expression = "${" + StringUtils.substring(thingPath,0,thingPath.length()-1) + "}";
        Object thing;
        DBCursor cursor = MongoDAOUtil.getInstance().getCollection("tree_things").find(new BasicDBObject(thingPath + "serialNumber", serialNumber));
        while (cursor.hasNext()){
            thing = getFormulaValue(null,cursor.next(),expression);
            if (thing instanceof BasicDBList){
                for(int i = 0; i < ((BasicDBList)thing).size(); i++)
                    if( ((BasicDBObject)((BasicDBList)thing).get(i)).get("serialNumber").equals(serialNumber) ){
                        removeChildren(((BasicDBObject)((BasicDBList)thing).get(i)));
                        things.add(((BasicDBObject)((BasicDBList)thing).get(i)));
                    }
            } else {
                removeChildren((BasicDBObject)thing);
                things.add((BasicDBObject)thing);
            }
        }
        System.out.println(things.toString());
    }
    public static Object getFormulaValue(Map<String, Object> udf, Object thing, String formula){
        Object result;
        ElExpressionService ees = new ElExpressionService();
        ees.initialize(udf,thing);
        result = ees.evaluate(formula,false);
        return result;
    }

    public static void removeChildren(BasicDBObject thing){
        Map<String,Object> thingAsMap = thing.toMap();
        for (Map.Entry<String, Object> property : thingAsMap.entrySet()){
            if (property.getValue() instanceof BasicDBObject){
                if (((BasicDBObject)property.getValue()).get("isChild").equals("true")){
                    thing.remove(property.getKey());
                }
            }
        }
    }

}
