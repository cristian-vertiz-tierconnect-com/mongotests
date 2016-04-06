package dummydata;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import dao.MongoDAOUtil;

import java.util.*;

/**
 * Created by rsejas on 3/31/16.
 */
public class DummyDataPath {
    private static int MAX_THINGS = 10000;
    private static int MAX_BLINKS_PER_THING = 500;
    private static int BLINKS_PER_THING_LIMIT_MIN = 150;
    private static int BLINKS_PER_THING_LIMIT_MAX = 500;
    private static int MAX_THINGS_BY_DOC = 20;
    private static Long START_THING_ID = 1L;//0
    private static int MAX_LEVELS = 6;
    private static String COLLECTION_NAME = "path_things";
    private static String COLLECTION_SNAPSHOTS = "path_thingSnapshots";
    private static String COLLECTION_SNAPSHOTS_IDS = "path_thingSnapshotIds";
    private static String[] THING_TYPE_CODES = {
            "pallete_code", "cartoon_code", "box_code", "item_code", "default_rfid_thingtype"
    };
    private static String[] THING_TYPE_NAMES = {
            "pallete", "cartoon", "box", "item", "rfid"
    };
    private static Long id;

    public static void main(String[] args) {
        final long startTime = System.currentTimeMillis();
        Boolean mongoInitialized = initMongo();
        id = START_THING_ID;
        if (id == 1L) {
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
        while (id < START_THING_ID + MAX_THINGS) {
            BasicDBList docs = new BasicDBList();
            int thingsByDoc = (int)(Math.random()*MAX_THINGS_BY_DOC)+5;
            while ((thingsByDoc-- > 0) && (id < START_THING_ID + MAX_THINGS)) {
                docs.add(newChild(docs, (int)(Math.random()*THING_TYPE_NAMES.length)));
            }
//             Saving docs to mongo
            for (Object doc:docs) {
                MongoDAOUtil.getInstance().getCollection(COLLECTION_NAME).save((BasicDBObject)doc);
                // Creating snapshots
                DummyDataUtils.createSnapshot((BasicDBObject) doc, COLLECTION_SNAPSHOTS, COLLECTION_SNAPSHOTS_IDS, BLINKS_PER_THING_LIMIT_MIN, BLINKS_PER_THING_LIMIT_MAX);
            }
        }
//        List<Map<String, Object>> thingTypeList = fillThingTypeList();
//        DummyDataUtils dummyDataUtils = new DummyDataUtils();
//        Long id = Long.parseLong(START_THING_ID+"");
//        // N things
//        //System.out.println(thingTypeList);
//        for (int i = 0; i < MAX_THINGS; i++) {
//            int level = (int)(Math.random()*thingTypeList.size());
//            String[] levels = thingTypeList.get(level).get("thingType").toString().split(",");
//            String[] levelsCodes = thingTypeList.get(level).get("thingTypeCode").toString().split(",");
//            //System.out.println(levels[0]);
//            String pathThingType = "";
//            String path = ",";
//            for (int k = 0; k < levels.length; k++) {
//                if (k > 0) {
//                    path += (id)+",";
//                }
//                Map<String, Object> thingMap = dummyDataUtils.newThing(Long.valueOf(String.valueOf(++id)), levels[k]);
//                BasicDBObject thingObject = new BasicDBObject();
//                for (Map.Entry<String, Object> entry : thingMap.entrySet()) {
//                    thingObject.put(entry.getKey(), entry.getValue());
//                }
//                thingObject.put("thingType", levels[k]);
//                thingObject.put("thingTypeCode", levelsCodes[k]);
//                if (k > 0) {
//                    pathThingType += (pathThingType.isEmpty()) ? levelsCodes[k-1] : "," + levelsCodes[k-1];
//                }
//                thingObject.put("pathThingType", (k == 0)?null:pathThingType);
//                thingObject.put("path", (k == 0)?null:path);
//                MongoDAOUtil.getInstance().getCollection(COLLECTION_NAME).save(thingObject);
//                //Create Snapshot
//                dummyDataUtils.createSnapshot(thingObject, COLLECTION_SNAPSHOTS, COLLECTION_SNAPSHOTS_IDS, MAX_BLINKS_PER_THING);
//                int total = MAX_THINGS + START_THING_ID;
//                if(id>=total)
//                {
//                    i=MAX_THINGS+1;
//                    break;
//                }
//            }
//        }
    }

    private static BasicDBObject newChild(BasicDBList docs, int level) {
        BasicDBObject parent = null;
        String pathThingType = null;
        String path = null;
        if (docs.size() == 0) {
            level = 0;
        } else {
            if (level == 0) {
                level = (int)(Math.random()*(THING_TYPE_NAMES.length-1))+1;
            }
            parent = (BasicDBObject) docs.get((int)(Math.random()*docs.size()));
            path = (parent.get("pathThingType")==null)?"":parent.get("path").toString();
            pathThingType = (parent.get("pathThingType")==null)?"":parent.get("pathThingType").toString();
            if (Math.random() < 0.15) {
                level = THING_TYPE_NAMES.length-1;
            } else {
                int auxLevel = (parent.get("path")==null)?0:levelFrom(parent.get("path").toString());
                if (level > auxLevel)
                    level = (auxLevel < THING_TYPE_NAMES.length-1)?auxLevel+1:auxLevel;
                else {
                    level = auxLevel;
                }
            }
            if (!parent.get("name").toString().toLowerCase().contains(THING_TYPE_NAMES[THING_TYPE_NAMES.length-1].toLowerCase())
                    && !parent.get("name").toString().toLowerCase().contains(THING_TYPE_NAMES[level].toLowerCase())) {
                if (path.isEmpty()) {
                    path = ",";
                }
                path += parent.get("_id").toString() + ",";
                pathThingType += parent.get("thingTypeCode") + ",";
            } else {
                level = THING_TYPE_NAMES.length-1;
            }
        }
        BasicDBObject thingObject = DummyDataUtils.newThing(id++, THING_TYPE_NAMES[level]);
        thingObject.put("thingType", THING_TYPE_NAMES[level]);
        thingObject.put("thingTypeCode", THING_TYPE_CODES[level]);
        thingObject.put("pathThingType", pathThingType);
        thingObject.put("path", path);
        return thingObject;
    }

    private static int levelFrom(String path) {
        return path.split(",").length-1;
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
