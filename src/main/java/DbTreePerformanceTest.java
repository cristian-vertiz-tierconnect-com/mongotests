import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import dao.ElExpressionService;
import dao.MongoDAOUtil;
import org.apache.commons.lang.StringUtils;

import java.net.UnknownHostException;
import java.util.*;

/**
 * Created by cvertiz on 3/29/16.
 */
public class DbTreePerformanceTest {

    public static void init(){
        try {
            MongoDAOUtil.setupMongodb("localhost",27017, "riot_main", null , null, "admin", "control123!");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private static void fillNestedTest() {

//        BasicDBObject doc = new BasicDBObject("_id" , 100).append("level", 100);
        BasicDBObject doc = new BasicDBObject("_id", 1);
        MongoDAOUtil.getInstance().getCollection("tree_test").remove(doc);

        for(int i = 1000 ; i > 0 ; i--){

            BasicDBObject level = new BasicDBObject("_id", i).append("level", i);

            level.append("NEXT", doc);

            doc = level;

        }

        MongoDAOUtil.getInstance().getCollection("tree_test").save(doc);

    }

    private static void fillOneLevelTest() {

//        BasicDBObject doc = new BasicDBObject("_id" , 100).append("level", 100);
        BasicDBObject doc = new BasicDBObject("_id" , 0);
        doc.append("_id",0);
        doc.append("groupTypeId",0);
        doc.append("groupTypeName","groupTypeName"+0);
        doc.append("groupTypeCode","groupTypeCode"+0);
        doc.append("groupId",0);
        doc.append("groupCode","groupCode"+0);
        doc.append("groupName","groupName"+0);
        doc.append("thingTypeId",0);
        doc.append("thingTypeCode","thingTypeCode"+0);
        doc.append("thingTypeName","thingTypeName"+0);
        doc.append("name","name"+0);
        doc.append("serialNumber","serialNumber"+0);

        BasicDBObject udf = new BasicDBObject();
        udf.append("thingTypeFieldId",0);
        udf.append("time",new Date());
        udf.append("value", "ufdValue");

        doc.append("AlertFlag",udf);
        doc.append("S_Discharge",udf);
        doc.append("VisitActive",udf);
        doc.append("S_Waiting",udf);
        doc.append("VisitDate",udf);
        doc.append("S_Exam",udf);
        doc.append("S_Treatment",udf);
        doc.append("S_Registration_DateTime",udf);
        doc.append("S_Registration",udf);
        doc.append("S_Discharge_DateTime",udf);


        MongoDAOUtil.getInstance().getCollection("tree_test").remove(doc);

        BasicDBList arrayChildren = new BasicDBList();
        for(int i = 1 ; i <= 15000 ; i++){

            BasicDBObject thing = new BasicDBObject();

            thing.append("_id",i);
            thing.append("groupTypeId",i);
            thing.append("groupTypeName","groupTypeName"+i);
            thing.append("groupTypeCode","groupTypeCode"+i);
            thing.append("groupId",i);
            thing.append("groupCode","groupCode"+i);
            thing.append("groupName","groupName"+i);
            thing.append("thingTypeId",i);
            thing.append("thingTypeCode","thingTypeCode"+i);
            thing.append("thingTypeName","thingTypeName"+i);
            thing.append("name","name"+i);
            thing.append("serialNumber","serialNumber"+i);

            udf.append("time",new Date());

            thing.append("AlertFlag",udf);
            thing.append("S_Discharge",udf);
            thing.append("VisitActive",udf);
            thing.append("S_Waiting",udf);
            thing.append("VisitDate",udf);
            thing.append("S_Exam",udf);
            thing.append("S_Treatment",udf);
            thing.append("S_Registration_DateTime",udf);
            thing.append("S_Registration",udf);
            thing.append("S_Discharge_DateTime",udf);

            arrayChildren.add(thing);

        }

        doc.append("children", arrayChildren);

        MongoDAOUtil.getInstance().getCollection("tree_test").save(doc);

    }

    public static void main(String [] args){
        init();

        fillNestedTest();

        fillOneLevelTest();

    }


}
