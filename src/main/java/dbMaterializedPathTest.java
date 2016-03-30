/**
 * Created by rchirinos on 3/29/16.
 */
import com.mongodb.*;
import com.sun.org.apache.xpath.internal.SourceTree;
import dao.MongoDAOUtil;
import org.jose4j.json.internal.json_simple.JSONObject;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.*;
import java.util.regex.Pattern;


/**
 * Created by rchirinos on 3/29/16.
 */
public class DbMaterializedPathTest {


    static public void main(String[] args) throws IOException {
        Mongo mongo = new Mongo("localhost", 27017);
        DB db = mongo.getDB("riot_main");

        pathMongoReloaded(db);
    }


    //paths
    public static void pathMongoReloaded(DB db) {
        //get father
        final long startTime = System.currentTimeMillis();
        BasicDBObject query = new BasicDBObject("serialNumber", "P10000");
//        BasicDBObject query = new BasicDBObject("serialNumber", "BOX1000");
        DBObject pivote = db.getCollection("path_things").findOne(query);
        String pathPivote = pivote.get("path")!=null?pivote.get("path").toString():null;

        List<DBObject> lstDbObject = new ArrayList<>();
        DBCursor pivoteData2 = null;
        if(pathPivote!=null)
        {
            /********************/
            //getFather
            lstDbObject = getFather( pathPivote, db);

            /*******************/
            //get pivote
            lstDbObject.add(pivote);

            /******************/
            //getChildren
            BasicDBObject query2 = new BasicDBObject("path", Pattern.compile(pathPivote+","+pivote.get("_id")));
            pivoteData2 = db.getCollection("path_things").find(query2);
        }else
        {
            BasicDBObject query2 = new BasicDBObject("path", 1);
            pivoteData2 = db.getCollection("path_things").find().sort(query2);
        }

        while( pivoteData2.hasNext() )
        {
            DBObject record = pivoteData2.next();
            lstDbObject.add(record);
        }

        final long endTime = System.currentTimeMillis();
        long total1 = endTime-startTime;
        System.out.println("Total Execution Parent & Pivote (miliseconds): "+total1);

        final long startTimeChildren = System.currentTimeMillis();
        String path = lstDbObject.get(0).get("path")!=null?lstDbObject.get(0).get("path").toString():null;
        List<Map<String, Object>> mapChildren = getTreeList(lstDbObject, path);
        final long endTimeChildren = System.currentTimeMillis();
        long total2 = endTimeChildren-startTimeChildren;
        System.out.println("Total Execution Children (miliseconds): "+total2);
        long totalMiliseconds = (total1+total2);


        int proyeccion = 1000000;
        int totalMinutes = (int) (((totalMiliseconds*proyeccion)/ (1000*60)) % 60);
        System.out.println("TOTAL (miliseconds): "+ totalMiliseconds);
        System.out.println("TOTAL (minutes)    : "+ totalMinutes);
        System.out.println("TOTAL Proyeccion hasta 1000 (minutes): "+(totalMinutes));
        System.out.println(mapChildren);
    }

    /*Get Parents*/
    public static List<DBObject> getFather(String path, DB db)
    {
        List<DBObject> result = new ArrayList<>();
        if(path!=null)
        {
            String[] data = path.split(",");
            for(int i =0;i<data.length;i++)
            {
                BasicDBObject query = new BasicDBObject("_id", Integer.parseInt(data[i]));
                DBObject dbObject= db.getCollection("path_things").findOne(query);
                result.add(dbObject);
            }
        }
        return result;
    }

    //Get Children
    public static Map<String,Object> getTree(List<DBObject> data, String path, int count)
    {
        Map<String, Object> result = new HashMap<>();
        String value = path;

        for(DBObject obj:data)
        {
            if((value==null && obj.get("path")==null)||
                    obj.get("path")!=null && obj.get("path").toString().equals(value))
            {
                result.put(obj.get("_id").toString(),(Map) obj);
            }
        }

        for (String key : result.keySet()) {
            Map<String, Object> dataRes =(Map<String, Object>) result.get(key);
            String path2 = null;
            if(value==null)
            {
                path2= dataRes.get("_id").toString();
            }else
            {
                path2 = value+","+dataRes.get("_id").toString();
            }
            count ++;
            dataRes.put("children", getTree(data, path2,count));
        }

        return result;
    }

    //Get Children
    public static List<Map<String, Object>> getTreeList(List<DBObject> data, String path)
    {
        List<Map<String, Object>> result = new ArrayList<>();
        String value = path;

        for(DBObject obj:data)
        {
            if((value==null && obj.get("path")==null)||
                    obj.get("path")!=null && obj.get("path").toString().equals(value))
            {
                result.add((Map) obj);
            }
        }

        for (Map<String,Object> dataRes: result) {
            String path2 = null;
            if(value==null)
            {
                path2= dataRes.get("_id").toString();
            }else
            {
                path2 = value+","+dataRes.get("_id").toString();
            }
            List<Map<String,Object>> lstChildren = getTreeList(data, path2);
            if(lstChildren!=null && !lstChildren.isEmpty())
            {
                dataRes.put("children", getTreeList(data, path2));
            }
        }

        return result;
    }

}
