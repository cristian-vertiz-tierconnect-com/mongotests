import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import dao.MongoDAOUtil;

import java.net.UnknownHostException;
import java.util.Date;

/**
 * Created by cvertiz on 3/29/16.
 */
public class DbMatPathPerformanceTest {

    public static int MAX_THINGS = 27;
    public static void init(){
        try {
            MongoDAOUtil.setupMongodb("localhost",27017, "path_riot_main", null , null, "admin", "control123!");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static void main(String [] args){

        init();
        fillNestedTest("path_things");
        fillOneLevelTest("path_things");
        System.out.println(MAX_THINGS + "created");
    }

    private static void fillNestedTest(String collectionName) {
        
        BasicDBObject doc = new BasicDBObject("_id", 17);
        doc.append("serialNumber","DATA1000-17");
        doc.append("thingTypeCode","DATA_CODE");
        doc.append("pathThingType",null);
        doc.append("path",null);


        MongoDAOUtil.getInstance().getCollection(collectionName).save(doc);

    }

    private static void fillOneLevelTest(String collectionName) {

        for(int i = MAX_THINGS ; i > 17 ; i--){

            BasicDBObject thing = new BasicDBObject();

            thing.append("_id",i);
            thing.append("thingTypeCode","DATA_TAG");
            thing.append("thingTypeName","thingTypeName"+i);
            thing.append("name","name"+i);
            thing.append("serialNumber","DATA"+i);
            thing.append("pathThingType","DATA_CODE");
            thing.append("path",",17,");

            /*BasicDBObject udf = new BasicDBObject();
            udf.append("thingTypeFieldId",0);
            udf.append("time",new Date());
            udf.append("value", "ufdValue");

            thing.append("AlertFlag",udf);
            thing.append("S_Discharge",udf);
            thing.append("VisitActive",udf);
            thing.append("S_Waiting",udf);
            thing.append("VisitDate",udf);
            thing.append("S_Exam",udf);
            thing.append("S_Treatment",udf);
            thing.append("S_Registration_DateTime",udf);
            thing.append("S_Registration",udf);
            thing.append("S_Discharge_DateTime",udf);*/

            MongoDAOUtil.getInstance().getCollection(collectionName).save(thing);

        }

    }




}
