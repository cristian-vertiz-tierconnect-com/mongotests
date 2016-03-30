/**
 * Created by rchirinos on 3/29/16.
 */
import com.mongodb.*;
import com.sun.org.apache.xpath.internal.SourceTree;
import dao.MongoDAOUtil;
import org.jose4j.json.internal.json_simple.JSONObject;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        Map<String,Object> dataFinal = new HashMap<>();
        String path = lstDbObject.get(0).get("path")!=null?lstDbObject.get(0).get("path").toString():null;
        Map<String, Object> mapChildren = getTree(lstDbObject,path,0);
        JSONObject jsonChild = new JSONObject();
        jsonChild.putAll( mapChildren );
        System.out.printf( "JSON : %s", jsonChild );
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

}
