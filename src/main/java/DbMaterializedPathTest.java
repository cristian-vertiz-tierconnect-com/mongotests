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

        pathMongoReloaded2(db);
    }

    //paths
    public static void pathMongoReloaded2(DB db) {
        //get father
        final long startTime = System.currentTimeMillis();
        String serial = "RFID1002";
        BasicDBObject query = new BasicDBObject("serialNumber", serial);//P10000
//        BasicDBObject query = new BasicDBObject("serialNumber", "DATA1000-17");
//        BasicDBObject query = new BasicDBObject("serialNumber", "BOX1000");
        DBCursor pivoteCursor = db.getCollection("path_things").find(query);
//        DBCursor pivoteCursor = db.getCollection("path_things").find();

        BasicDBList result = new BasicDBList();
        while( pivoteCursor.hasNext() )
        {
            DBObject pivote = pivoteCursor.next();
            String pathPivote = pivote.get("path")!=null?pivote.get("path").toString():null;

            BasicDBList lstDbObject = new BasicDBList();
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
                BasicDBObject query2 = new BasicDBObject("path", Pattern.compile("^"+pathPivote+pivote.get("_id")+","));
                pivoteData2 = db.getCollection("path_things").find(query2);
            }else
            {
                /*******************/
//                //get pivote
//                lstDbObject.add(pivote);

                /******************/
                //getChildren
                BasicDBObject query2 = new BasicDBObject("path", Pattern.compile("^,"+pivote.get("_id").toString()+","));
                query2.append("pathThingType",Pattern.compile("^"+pivote.get("thingTypeCode")) );
                //pivoteData2 = db.getCollection("path_things").find().sort(query2);
                pivoteData2 = db.getCollection("path_things").find(query2);
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
            String path = ((BasicDBObject)lstDbObject.get(0)).get("path")!=null?((BasicDBObject)lstDbObject.get(0)).get("path").toString():null;
            BasicDBList mapChildren = getTreeList(lstDbObject, path);
            BasicDBObject pivoteCopy =  getPivoteCopy(pivote);
            if(mapChildren!=null && mapChildren.size()>0)
            {
                pivoteCopy.append("children",mapChildren);
            }
            final long endTimeChildren = System.currentTimeMillis();
            long total2 = endTimeChildren-startTimeChildren;
            System.out.println("Total Execution Children (miliseconds): "+total2);
            long totalMiliseconds = (total1+total2);


            int proyeccion = 1000000;
            int totalMinutes = (int) (((totalMiliseconds*proyeccion)/ (1000*60)) % 60);
            System.out.println("TOTAL (miliseconds): "+ totalMiliseconds);
//            System.out.println("TOTAL (minutes)    : "+ totalMinutes);
//            System.out.println("TOTAL Proyeccion hasta "+proyeccion+" things: "+(totalMinutes*proyeccion));

            result.add(pivoteCopy);
//            System.out.println(mapChildren);
        }
        System.out.println(result);
    }


    public static BasicDBObject getPivoteCopy(DBObject data)
    {
        BasicDBObject response = new BasicDBObject();
        for(String key:data.keySet())
        {
            response.append(key,data.get(key));
        }

        return response;
    }
//    //paths
//    public static void pathMongoReloaded(DB db) {
//        //get father
//        final long startTime = System.currentTimeMillis();
////        BasicDBObject query = new BasicDBObject("serialNumber", "P10000");
//        BasicDBObject query = new BasicDBObject("serialNumber", "DATA1000");
////        BasicDBObject query = new BasicDBObject("serialNumber", "BOX1000");
//        DBObject pivote = db.getCollection("path_things").findOne(query);
//        String pathPivote = pivote.get("path")!=null?pivote.get("path").toString():null;
//
//        BasicDBList lstDbObject = new BasicDBList();
//        DBCursor pivoteData2 = null;
//        if(pathPivote!=null)
//        {
//            /********************/
//            //getFather
//            lstDbObject = getFather( pathPivote, db);
//
//            /*******************/
//            //get pivote
//            lstDbObject.add(pivote);
//
//            /******************/
//            //getChildren
//            BasicDBObject query2 = new BasicDBObject("path", Pattern.compile(","+pathPivote+","+pivote.get("_id")+","));
//            pivoteData2 = db.getCollection("path_things").find(query2);
//        }else
//        {
//            BasicDBObject query2 = new BasicDBObject("path", 1);
//            pivoteData2 = db.getCollection("path_things").find().sort(query2);
//        }
//
//        while( pivoteData2.hasNext() )
//        {
//            DBObject record = pivoteData2.next();
//            lstDbObject.add(record);
//        }
//
//        final long endTime = System.currentTimeMillis();
//        long total1 = endTime-startTime;
//        System.out.println("Total Execution Parent & Pivote (miliseconds): "+total1);
//
//        final long startTimeChildren = System.currentTimeMillis();
//        String path = ((BasicDBObject)lstDbObject.get(0)).get("path")!=null?((BasicDBObject)lstDbObject.get(0)).get("path").toString():null;
//        BasicDBList mapChildren = getTreeList(lstDbObject, path);
//        final long endTimeChildren = System.currentTimeMillis();
//        long total2 = endTimeChildren-startTimeChildren;
//        System.out.println("Total Execution Children (miliseconds): "+total2);
//        long totalMiliseconds = (total1+total2);
//
//
//        int proyeccion = 1000000;
//        int totalMinutes = (int) (((totalMiliseconds*proyeccion)/ (1000*60)) % 60);
//        System.out.println("TOTAL (miliseconds): "+ totalMiliseconds);
//        System.out.println("TOTAL (minutes)    : "+ totalMinutes);
//        System.out.println("TOTAL Proyeccion hasta 1000 (minutes): "+(totalMinutes));
//        System.out.println(mapChildren);
//    }

    /*Get Parents*/
    public static BasicDBList getFather(String path, DB db)
    {
        BasicDBList result = new BasicDBList();
        if(path!=null)
        {
            path = path.substring(1,path.length()-1);
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
    public static BasicDBList getTreeList(BasicDBList data, String path)
    {
        BasicDBList result = new BasicDBList();
        String value = path;

        for(Object objData:data)
        {
            BasicDBObject obj = (BasicDBObject) objData;
            if((value==null && obj.get("path")==null)||
                    obj.get("path")!=null && obj.get("path").toString().equals(value))
            {
                result.add( obj);
            }
        }

        for (Object dataResObj: result)
        {
            BasicDBObject dataRes = (BasicDBObject) dataResObj;
            String path2 = null;
            if(value==null)
            {
                path2= ","+dataRes.get("_id").toString()+",";
            }else
            {
                path2 = value+dataRes.get("_id").toString()+",";
            }
            BasicDBList lstChildren = getTreeList(data, path2);
            if(lstChildren!=null && !lstChildren.isEmpty())
            {
                dataRes.put("children", getTreeList(data, path2));
            }
        }

        return result;
    }

}
