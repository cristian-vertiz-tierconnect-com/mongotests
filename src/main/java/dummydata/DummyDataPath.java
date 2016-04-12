package dummydata;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import dao.MongoDAOUtil;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by rsejas on 3/31/16.
 */
public class DummyDataPath {
    private static int nThreads = 20;
    private static int MAX_THINGS = 20000;
    private static int MAX_BLINKS_PER_THING = 500;
    private static int BLINKS_PER_THING_LIMIT_MIN = 150;
    private static int BLINKS_PER_THING_LIMIT_MAX = 500;
    private static int MAX_THINGS_BY_DOC = 20;
    private static long START_THING_ID = 1L;//0
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
    private static long id;

    public static void main(String[] args) {
        System.out.println("Starting at "+new Date());
        final long startTime = System.currentTimeMillis();
        Boolean mongoInitialized = initMongo();
        id = START_THING_ID;
        if (id == 1L) {
            MongoDAOUtil.getInstance().getCollection(COLLECTION_NAME).drop();
            MongoDAOUtil.getInstance().getCollection(COLLECTION_SNAPSHOTS).drop();
            MongoDAOUtil.getInstance().getCollection(COLLECTION_SNAPSHOTS_IDS).drop();
        }
        DummyDataPath dummyDataPath = new DummyDataPath();
        if (mongoInitialized) {
            dummyDataPath.fillDummyData();
        }
        final long endTime = System.currentTimeMillis();
        final long total = endTime-startTime;
        System.out.println("THINGS CREATED: "+MAX_THINGS);
        System.out.println("TIME: "+total);
        System.out.println("Finished at: "+new Date());
    }

    public class RunnableNewMongoDoc implements Runnable {
//        BasicDBList docs;
        int thingsByDoc;
        long initialId;

        public RunnableNewMongoDoc(int thingsByDoc, long initialId) {
            this.thingsByDoc = thingsByDoc;
            this.initialId = initialId;
        }
        @Override
        public void run() {
            BasicDBList docs = new BasicDBList();
//            int thingsByDoc = (int) (Math.random() * MAX_THINGS_BY_DOC) + 5;
            while ((thingsByDoc-- > 0) && (initialId < (START_THING_ID + MAX_THINGS))) {
//            while ((thingsByDoc-- > 0) && (id < START_THING_ID + MAX_THINGS)) {
                docs.add(newChild(docs, (int) (Math.random() * THING_TYPE_NAMES.length), initialId++));
            }
//             Saving docs to mongo
            for (Object doc : docs) {
                MongoDAOUtil.getInstance().getCollection(COLLECTION_NAME).save((BasicDBObject) doc);
                // Creating snapshots
                DummyDataUtils.createSnapshot((BasicDBObject) doc, COLLECTION_SNAPSHOTS, COLLECTION_SNAPSHOTS_IDS, BLINKS_PER_THING_LIMIT_MIN, BLINKS_PER_THING_LIMIT_MAX);
            }
        }

        private  BasicDBObject newChild(BasicDBList docs, int level, long auxId) {
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
            BasicDBObject thingObject = DummyDataUtils.newThing(auxId, THING_TYPE_NAMES[level]);
            thingObject.put("thingType", THING_TYPE_NAMES[level]);
            thingObject.put("thingTypeCode", THING_TYPE_CODES[level]);
            thingObject.put("pathThingType", pathThingType);
            thingObject.put("path", path);
            return thingObject;
        }
    }

    public void fillDummyData() {
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);

        while (id < START_THING_ID + MAX_THINGS) {
            int nThings = (int) (Math.random() * MAX_THINGS_BY_DOC) + 5;
            RunnableNewMongoDoc runnable = new RunnableNewMongoDoc(nThings, id);
            executor.execute(runnable);
            id += nThings;
        }
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
