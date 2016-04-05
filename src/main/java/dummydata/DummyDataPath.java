package dummydata;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import dao.MongoDAOUtil;
import org.bson.types.ObjectId;

import java.util.*;

/**
 * Created by rsejas on 3/31/16.
 */
public class DummyDataPath {
    private static Boolean removeCollections = true;
    private static int MAX_THINGS = 10000;
    private static int MAX_BLINKS_PER_THING = 500;
    private static int START_THING_ID = 22;//0
    private static int MAX_LEVELS = 6;
    private static String COLLECTION_NAME = "path_things";
    private static String COLLECTION_SNAPSHOTS = "path_thingSnapshots";
    private static String COLLECTION_SNAPSHOTS_IDS = "path_thingSnapshotIds";
    private static String[] THING_TYPE_CODES = {
/*            "pallete_code",
            "pallete_code,default_rfid_thingtype",
//            "pallete_code,cartoon_code",
            "pallete_code,cartoon_code,default_rfid_thingtype",
//            "pallete_code,cartoon_code,box_code",
            "pallete_code,cartoon_code,box_code,default_rfid_thingtype",
//            "pallete_code,cartoon_code,box_code,item_code",*/
            "pallete_code,cartoon_code,box_code,item_code,default_rfid_thingtype",
    };
    private static String[] THING_TYPE_NAMES = {
          /*  "pallete",
            "pallete,default_rfid_thingtype",
//            "pallete,cartoon",
            "pallete,cartoon,default_rfid_thingtype",
//            "pallete,cartoon,box",
            "pallete,cartoon,box,default_rfid_thingtype",
//            "pallete,cartoon,box,item",*/
            "pallete,cartoon,box,item,rfid",
    };

    public static void main(String[] args) {
        final long startTime = System.currentTimeMillis();
        Boolean mongoInitialized = initMongo();
        if (removeCollections) {
            MongoDAOUtil.getInstance().getCollection(COLLECTION_NAME).drop();
            MongoDAOUtil.getInstance().getCollection(COLLECTION_SNAPSHOTS).drop();
            MongoDAOUtil.getInstance().getCollection(COLLECTION_SNAPSHOTS_IDS).drop();
        }
        if (mongoInitialized) {
            fillDummyData();
        }
        final long endTime = System.currentTimeMillis();
        final long total = endTime-startTime;
        System.out.println("THINGS CREATED: "+MAX_THINGS);
        System.out.println("TIME: "+total);
    }

    private static void fillDummyData() {
        List<Map<String, Object>> thingTypeList = fillThingTypeList();
        DummyDataUtils dummyDataUtils = new DummyDataUtils();
        Long id = Long.parseLong(START_THING_ID+"");
        // N things
        //System.out.println(thingTypeList);
        for (int i = 0; i < MAX_THINGS; i++) {
            int level = (int)(Math.random()*thingTypeList.size());
            String[] levels = thingTypeList.get(level).get("thingType").toString().split(",");
            String[] levelsCodes = thingTypeList.get(level).get("thingTypeCode").toString().split(",");
            //System.out.println(levels[0]);
            String pathThingType = "";
            String path = ",";
            for (int k = 0; k < levels.length; k++) {
                if (k > 0) {
                    path += (id)+",";
                }
                Map<String, Object> thingMap = dummyDataUtils.newThing(Long.valueOf(String.valueOf(++id)), levels[k]);
                BasicDBObject thingObject = new BasicDBObject();
                for (Map.Entry<String, Object> entry : thingMap.entrySet()) {
                    thingObject.put(entry.getKey(), entry.getValue());
                }
                thingObject.put("thingType", levels[k]);
                thingObject.put("thingTypeCode", levelsCodes[k]);
                if (k > 0) {
                    pathThingType += (pathThingType.isEmpty()) ? levelsCodes[k-1] : "," + levelsCodes[k-1];
                }
                thingObject.put("pathThingType", (k == 0)?null:pathThingType);
                thingObject.put("path", (k == 0)?null:path);
                MongoDAOUtil.getInstance().getCollection(COLLECTION_NAME).save(thingObject);
                //Create Snapshot
                dummyDataUtils.createSnapshot(thingObject, COLLECTION_SNAPSHOTS, COLLECTION_SNAPSHOTS_IDS, MAX_BLINKS_PER_THING);
                int total = MAX_THINGS + START_THING_ID;
                if(id>=total)
                {
                    i=MAX_THINGS+1;
                    break;
                }
            }
        }
    }

    private static List<Map<String, Object>> fillThingTypeList() {
        List<Map<String, Object>> result = new ArrayList<>();
        for (int i = 0; i < THING_TYPE_CODES.length; i++) {
            Map<String, Object> thingType = new HashMap<>();
            thingType.put("thingType", THING_TYPE_NAMES[i]);
            thingType.put("thingTypeCode", THING_TYPE_CODES[i]);
            result.add(thingType);
        }
        return result;
    }

    private static Boolean initMongo() {
        try {
            MongoDAOUtil.setupMongodb("localhost", 27017, "riot_main", null , null, "admin", "control123!");
//            MongoDAOUtil.setupMongodb("10.100.0.140",27017, "path_riot_main", null , null, "admin", "control123!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
