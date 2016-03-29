import com.mongodb.*;
import dao.MongoDAOUtil;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by cvertiz on 3/29/16.
 */
public class dbRefTest {

    public static void testRefThings(){

        List<DBObject> result1 = new ArrayList<>();
        List<DBObject> result2 = new ArrayList<>();
        List<DBObject> result3 = new ArrayList<>();
        DBCursor cursor = MongoDAOUtil.getInstance().getCollection("ref_things").find(new BasicDBObject("_id", 1));

        while (cursor.hasNext()){
            DBObject pallete = cursor.next();

            DBObject result = new BasicDBObject();

            result1.add(getTree(pallete, result));
        }

        System.out.println(result1);

    }

    private static DBObject getTree(DBObject doc, DBObject result2) {

        DBObject result = new BasicDBObject();
        for(Map.Entry<String, Object> entry : ((Map<String, Object>)doc).entrySet()){
            if(entry.getKey().equals("children")){
                BasicDBList children = new BasicDBList();

                for(Object ref : (List<Object>)doc.get(entry.getKey())){
                    children.add(getTree(fetch(ref),result));
                }

                result.put(entry.getKey(), children);
            }else if(!entry.getKey().equals("parent")){
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }


    private static DBObject fetch (Object subDoc){
        System.out.println("Fetching " + subDoc);
        return ((DBRef)subDoc).fetch();
    }


    public static void init(){
        try {
            MongoDAOUtil.setupMongodb("localhost",27017, "riot_main", null , null, "admin", "control123!");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    public static void main(String [] args){
        init();
        testRefThings();
    }
}
