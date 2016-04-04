/**
 * Created by rchirinos on 3/29/16.
 */
import com.mongodb.*;
import dao.MongoDAOUtil;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;


/**
 * Created by rchirinos on 3/29/16.
 */
public class DbMaterializedPathTest {

    //public static DB db = null;

    static public void main(String[] args) throws IOException {

        init();
        //Mongo mongo = new Mongo("localhost", 27017);
        //db = mongo.getDB("riot_main");

        getTreeExamples();
        getReports();
//        System.out.println();
//        System.out.println();
//        //getPlainExamples();
//        System.out.println();
//        System.out.println();
//        //getThingTypeTreeExamples();
//        System.out.println();
//        System.out.println();
//        //getThingTypePlainExamples();
//        System.out.println();
//        System.out.println();
//        //createNewThing();
//        //updateNewThing();



    }

    public static void getTreeExamples()
    {
        System.out.println("*******TREE DATA");
        String serial = "P10000";//P10000 CARTOON1000 RFID1002 BOX1000 ITEM1000 RFID1003  DATA1000-17
        lstThingListTree(serial, null);
    }

    public static void getPlainExamples()
    {
        System.out.println("*******PLAIN DATA");
        String serialPlain = "P10000"; //P10000 CARTOON1000 RFID1002 BOX1000 ITEM1000 RFID1003  DATA1000-17
        lstThingPlain(serialPlain,null);
        System.out.println();
        serialPlain = "ITEM1000";
        lstThingPlain(serialPlain,null);
        System.out.println();
        serialPlain = "RFID1003";
        lstThingPlain(serialPlain,null);
        System.out.println();
        serialPlain = "DATA22";
        lstThingPlain(serialPlain,null);
        System.out.println();
        lstThingPlain(null,null);
    }

    public static void getThingTypeTreeExamples()
    {
        /*
        * pallete_code
        * cartoon_code
        * box_code
        * item_code
        * default_rfid_thingtype
        * */
        System.out.println("*******TREE DATA");
        String thingTypeCode = "default_rfid_thingtype";
        lstThingListTree(null,thingTypeCode);

    }

    public static void getThingTypePlainExamples()
    {
        /*
        * pallete_code
        * cartoon_code
        * box_code
        * item_code
        * default_rfid_thingtype
        * */
        System.out.println("*******TREE DATA");
        String thingTypeCode = "default_rfid_thingtype";
        lstThingPlain(null,thingTypeCode);

    }

    /*****Plain*/
    public static void lstThingPlain(String serial, String thingTypeCode)
    {
        final long startTime = System.currentTimeMillis();
        DBCursor pivotCursor = null;
        BasicDBList result   = new BasicDBList();
        BasicDBObject query    = new BasicDBObject();
        if(serial==null && thingTypeCode==null)
        {
            query.append("path", null);
        }
        if(serial!=null)
        {
            query.append("serialNumber", serial);
        }
        if(thingTypeCode!=null)
        {
            query.append("thingTypeCode", thingTypeCode);
        }
        pivotCursor = MongoDAOUtil.getInstance().getCollection("path_things").find(query);
        while( pivotCursor.hasNext() )
        {
            result.add(pivotCursor.next());
        }
        System.out.println(result);

        final long endTime = System.currentTimeMillis();
        final long totalTime = endTime-startTime;
        System.out.println("TOTAL TIME (milliseconds): "+(totalTime));
    }

    //paths
    public static void lstThingListTree(String serial, String thingTypeCode) {

        final long startTime = System.currentTimeMillis();
        DBCursor pivotCursor   = null;
        BasicDBList result     = new BasicDBList();
        BasicDBObject query    = new BasicDBObject();
        Set<String> container = new HashSet<>();

        if(serial==null && thingTypeCode==null)
        {
            query.append("path", null);
        }
        if(serial!=null)
        {
            query.append("serialNumber", serial);
        }
        if(thingTypeCode!=null)
        {
            query.append("thingTypeCode", thingTypeCode);
        }

        pivotCursor = MongoDAOUtil.getInstance().getCollection("path_things").find(query);
        while( pivotCursor.hasNext() )
        {
            long startTimeOne = System.currentTimeMillis();
            Object resultData   = null;
            DBCursor pivotData2 = null;

            DBObject pivot = pivotCursor.next();
            String pathPivot = pivot.get("path")!=null?pivot.get("path").toString():null;

            BasicDBList lstDbObject = new BasicDBList();
            if(pathPivot!=null)
            {
                /********************/
                //getFather
                if(container!=null && container.contains(pathPivot.substring(1,2))){
                    continue;
                }else{
                    container.add(pathPivot.substring(1,2));
                }
                lstDbObject = getFather( pathPivot);

                /*******************/
                //get pivot
                lstDbObject.add(pivot);

                /******************/
                //getChildren
                BasicDBObject query2 = new BasicDBObject("path", Pattern.compile("^"+pathPivot+pivot.get("_id")+","));
                pivotData2 = MongoDAOUtil.getInstance().getCollection("path_things").find(query2);
            }else
            {
                /*******************/
                //get pivot
                lstDbObject.add(pivot);

                /******************/
                //getChildren
                BasicDBObject query2 = new BasicDBObject("path", Pattern.compile("^,"+pivot.get("_id").toString()+","));
                query2.append("pathThingType",Pattern.compile("^"+pivot.get("thingTypeCode")) );
                pivotData2 = MongoDAOUtil.getInstance().getCollection("path_things").find(query2);
            }

            BasicDBList lstDBObjectChild = new BasicDBList();
            while( pivotData2.hasNext() )
            {
                DBObject record = pivotData2.next();
                lstDBObjectChild.add(record);
            }
            if(lstDBObjectChild!=null && lstDBObjectChild.size()>0)
            {
                lstDbObject.addAll(lstDBObjectChild);
            }
            String path = ((BasicDBObject)lstDbObject.get(0)).get("path")!=null?((BasicDBObject)lstDbObject.get(0)).get("path").toString():null;
            BasicDBList basicTreeList = getTreeList(lstDbObject, path);
            if(lstDBObjectChild!=null && lstDBObjectChild.size()>0)
            {
                resultData = basicTreeList;
            }
            else
            {
                resultData = lstDbObject.get(0);
            }
            result.add(resultData);

//            long endTimeOne = System.currentTimeMillis();
//            long total1 = endTimeOne-startTimeOne;
//            System.out.println("Iteration ["+pivot.get("serialNumber")+"] :"+total1);

        }

        final long endTime = System.currentTimeMillis();
        final long totalTime = endTime-startTime;
        System.out.println(result);
        System.out.println("TOTAL TIME (milliseconds): "+(totalTime));
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

    /*Get Parents*/
    public static BasicDBList getFather(String path)
    {
        BasicDBList result = new BasicDBList();
        if(path!=null)
        {
//            path = path.substring(1,path.length()-1);
//            String[] data = path.split(",");
//            Integer[] dataInt = new Integer[data.length];
//            for (int i = 0; i < data.length; i++) {
//                dataInt[i] = Integer.parseInt(data[i]);
//            }
//
//            BasicDBObject query    = new BasicDBObject("_id", new BasicDBObject("$in", Arrays.asList(dataInt) ));
//            DBCursor dbObjectCursor= db.getCollection("path_things").find(query);
//            while( dbObjectCursor.hasNext() )
//            {
//                result.add(dbObjectCursor.next());
//            }

            path = path.substring(1,path.length()-1);
            String[] data = path.split(",");
            for(int i =0;i<data.length;i++)
            {
                BasicDBObject query = new BasicDBObject("_id", Integer.parseInt(data[i]));
                DBObject dbObject= MongoDAOUtil.getInstance().getCollection("path_things").findOne(query);
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

    public static void createNewThing()
    {

    }

    public static void init(){
        try {
            MongoDAOUtil.setupMongodb("localhost",27017, "path_riot_main", null , null, "admin", "control123!");
//            MongoDAOUtil.setupMongodb("10.100.1.140",27017, "riot_main", null , null, "vizix", "m0j1xInc!");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    /**
    * Get report
    * */
    public static void getReports()
    {
        // pallete_code.color = Multicolor
        // box_code.color = Brown
        String code = null;
        Map<String, Object> options = null;
        Map<String, Object> mapResult = null;

        try {
            mapResult = execute(code, options);
        }catch (Exception e )
        {
            e.printStackTrace();
        }


    }
    private static Map<String, Object> execute(String code, Map<String, Object> options ) throws Exception
    {
        Object o = null;
        try{
            o = MongoDAOUtil.getInstance().db.eval( code, options );
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        BasicDBObject bdb = (BasicDBObject) o;
        return bdb;
    }
}
