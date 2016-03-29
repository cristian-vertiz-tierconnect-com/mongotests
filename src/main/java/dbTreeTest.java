import com.mongodb.*;
import dao.MongoDAOUtil;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cvertiz on 3/29/16.
 */
public class dbTreeTest {

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
        getAllThings();

        // test get thing by serial
        String serialNumber = "RFID02";
        String thingPath = getPath(serialNumber);
    }

    public static String getPath(String serialNumber){
        String result = null;
        Map<String,Object> paths = new HashMap<>();
        paths.put("PALLETE01","");
        paths.put("RFID01","rfid");
        paths.put("CARTON01","carton");
        paths.put("RFID02","carton.rfid");
        paths.put("BOX01","carton.box");
        paths.put("RFID03","carton.box.rfid");
        paths.put("ITEM01","carton.box.item");
        paths.put("RFID03","");
        paths.put("RFID04","");
        return result;
    }

    public static void getAllThings(){
        List<DBObject> result1 = new ArrayList<>();
        DBCursor cursor = MongoDAOUtil.getInstance().getCollection("tree_things").find(new BasicDBObject("_id", 1));
        while (cursor.hasNext()){
            result1.add(cursor.next());
        }
        System.out.println(result1);
    }

    public static void getThingBySerial(String serialNumber){
        List<DBObject> result1 = new ArrayList<>();
        DBCursor cursor = MongoDAOUtil.getInstance().getCollection("tree_things").find(new BasicDBObject("_id", 1));
        while (cursor.hasNext()){
            result1.add(cursor.next());
        }
        System.out.println(result1);
    }


}
