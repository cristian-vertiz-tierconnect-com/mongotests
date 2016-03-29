import com.mongodb.*;
import org.jose4j.json.internal.json_simple.JSONObject;

import java.io.IOException;
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
        Mongo mongo = new Mongo("localhost", 27017);
        DB db = mongo.getDB("riot_main");

        pathMongo(db);
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
    public static void pathMongo(DB db) {
        //get father
        BasicDBObject query = new BasicDBObject("serialNumber", "BOX1000");
        DBObject pivote = db.getCollection("path_things").findOne(query);
        String pathPivote = pivote.get("path").toString();
        Map<String,Object> dataFinal = null;

        dataFinal = getParents(db, pathPivote,pivote);

        BasicDBObject query2 = new BasicDBObject("path", Pattern.compile(pathPivote));
        DBCursor pivoteData2 = db.getCollection("path_things").find(query2);
        List<Object> data = new ArrayList<>();

        while( pivoteData2.hasNext() )
        {
            DBObject record = pivoteData2.next();
            data.add(record);
        }

       // dataFinal = getChildren(dataFinal,data, 0);

        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll( dataFinal );
        System.out.printf( "JSON: %s", jsonObject );

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
    public static Map<String,Object> getParents(DB db, String path, DBObject pivote)
    {
        Map<String, Object> dataMap = new HashMap<>();
        String newPath = "";
        String[] data = path.split(",");
        BasicDBObject query = new BasicDBObject("_id", Integer.parseInt(data[0]));
        DBObject dbObject= db.getCollection("path_things").findOne(query);
        dataMap.putAll((Map) dbObject);

        for(int i=1;i<data.length;i++)
        {
            newPath = newPath+data[i]+",";
        }

        if(newPath!=null && !newPath.trim().isEmpty())
        {
            newPath = newPath.substring(0,newPath.length()-1);
            dataMap.put("children",getParents(db,newPath,pivote));
        }else
        {
            dataMap.put("children",pivote);
        }
        return dataMap;
    }


}
