import com.mongodb.*;
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
public class dbPathTest {


    static public void main(String[] args) throws IOException {
        init();
        pathMongo();
    }


    public static void init(){
        try {
            MongoDAOUtil.setupMongodb("localhost",27017, "riot_main", null , null, "admin", "control123!");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    //Materialized paths
    public static void ancestorsMongo(DB db) {
        BasicDBObject query = new BasicDBObject("path", "\\/^,1,3,5,7,\\/");

        DBCursor cursorpath = db.getCollection("path_things").find(query);

        // parse results
        while (cursorpath.hasNext()) {
            DBObject record = cursorpath.next();
            System.out.println(record);
        }
    }

    //Ancestors paths
    public static void pathMongo() {
        //get father
        BasicDBObject query = new BasicDBObject("serialNumber", "BOX1000");
        DBObject pivote = MongoDAOUtil.getInstance().getCollection("path_things").findOne(query);
        String pathPivote = pivote.get("path").toString();
        DBObject dataFinal = null;

        dataFinal = getParents(pathPivote,pivote);

        BasicDBObject query2 = new BasicDBObject("path", Pattern.compile(pathPivote));
        DBCursor pivoteData2 = MongoDAOUtil.getInstance().getCollection("path_things").find(query2);
        BasicDBList data = new BasicDBList();

        while( pivoteData2.hasNext() )
        {
            DBObject record = pivoteData2.next();
            data.add(record);
        }

        System.out.println( dataFinal );

    }

    //Children
    public static Map<String,Object> getChildren(Map<String,Object> dataFinal, List<Object> data, int a)
    {
        Map<String, Object> dataMap = new HashMap<>();
        if(a <= data.size())
        {
            a = a+1;
            dataFinal.put("children",getChildren((Map)data.get(a),data,a));
        }

        return dataMap;
    }

    //Parents
    public static DBObject getParents(String path, DBObject pivote)
    {
        DBObject dataMap = new BasicDBObject();
        String newPath = "";
        String[] data = path.split(",");
        BasicDBObject query = new BasicDBObject("_id", Integer.parseInt(data[0]));
        DBObject dbObject= MongoDAOUtil.getInstance().getCollection("path_things").findOne(query);
        dataMap.putAll((Map) dbObject);

        for(int i=1;i<data.length;i++)
        {
            newPath = newPath+data[i]+",";
        }

        if(newPath!=null && !newPath.trim().isEmpty())
        {
            newPath = newPath.substring(0,newPath.length()-1);
            dataMap.put("children",getParents(newPath,pivote));
        }else
        {
            dataMap.put("children",pivote);
        }
        return dataMap;
    }


}
