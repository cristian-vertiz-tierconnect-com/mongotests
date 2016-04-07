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
public class DummyDataTree {
    // INITIAL_ID = 1: Collection will be deleted
    // INITIAL_ID > 1: Collection will continue from this id
    private static int nThreads = 18;
    private static int INITIAL_ID = 150420;
    private static int MAX_THINGS = 1000000;
    private static int MAX_THINGS_BY_DOC = 20;
    private static int MAX_LEVELS = 6;
    private static int MAX_BLINKS_PER_THING = 500;
    private static int BLINKS_PER_THING_LIMIT_MIN = 150;
    private static int BLINKS_PER_THING_LIMIT_MAX = 500;
    private static String COLLECTION_NAME = "tree_things";
    private static String COLLECTION_SNAPSHOTS_IDS = "tree_things_snapshots_ids";
    private static String COLLECTION_SNAPSHOTS = "tree_things_snapshots";
    private static String[] THING_TYPE_CODES = {
            "pallete_code",
            "pallete_code,rfid_code",
//            "pallete_code,carton_code",
            "pallete_code,carton_code,rfid_code",
//            "pallete_code,carton_code,box_code",
            "pallete_code,carton_code,box_code,rfid_code",
//            "pallete_code,carton_code,box_code,item_code",
            "pallete_code,carton_code,box_code,item_code,rfid_code",
    };
    private static String[] THING_TYPE_NAMES = {
            "pallete",
            "pallete,rfid",
//            "pallete,carton",
            "pallete,carton,rfid",
//            "pallete,carton,box",
            "pallete,carton,box,rfid",
//            "pallete,carton,box,item",
            "pallete,carton,box,item,rfid",
    };
    private static String[] LEVELS = {"pallete", "carton", "box", "item", "rfid"};
    private static Long id;

    public static void main(String[] args) {
        System.out.println("Starting tree path things");
        Long initialTime = new Date().getTime();
        Boolean mongoInitialized = initMongo();
        if (mongoInitialized) {
            fillDummyData();
        }
        System.out.println("Finishing... "+(System.currentTimeMillis()-initialTime));
    }

    private static class RunnableTree implements Runnable {
        private static int thingsByDoc;
        private static Long initialId;
        public RunnableTree(int thingsByDoc, Long initialId) {
            this.thingsByDoc = thingsByDoc;
            this.initialId = initialId;
        }

        @Override
        public void run() {
            BasicDBObject doc = createThingDoc(thingsByDoc);
            doc = removePathFrom(doc);
            MongoDAOUtil.getInstance().getCollection(COLLECTION_NAME).save(doc);
        }
        private static BasicDBObject createThingDoc(int nThings) {
            DummyDataUtils dummyDataUtils = new DummyDataUtils();
            BasicDBObject parent = dummyDataUtils.newThingTree(++id, LEVELS[0], "");
            while ((--nThings > 0) && (initialId < MAX_THINGS)){
                parent = addChild(parent, LEVELS[0], 1, (int)(Math.random()*5));
            }
            return parent;
        }

        private static BasicDBObject addChild(BasicDBObject thingBase, String path, int initialLevel, int level) {
            // Creating rfid
            if ((int)(Math.random()*10) < 3) {
                BasicDBList things = (BasicDBList) thingBase.get(LEVELS[4]);
                DummyDataUtils dummyDataUtils = new DummyDataUtils();
                if (things == null) {
                    things = new BasicDBList();
                    String pathAux = thingBase.get("path").toString()+".";
                    things.add(dummyDataUtils.newThingTree(++initialId, LEVELS[4], pathAux + LEVELS[4] + "[0]"));
                    thingBase.put(LEVELS[4], things);
                } else {
                    String pathAux = thingBase.get("path").toString()+".";
                    things.add(dummyDataUtils.newThingTree(++initialId, LEVELS[4], pathAux + LEVELS[4] + "["+things.size()+"]"));
                    thingBase.put(LEVELS[4], things);
                }
                return thingBase;
            }
            if (initialLevel >= LEVELS.length) {
                initialLevel = LEVELS.length - 1;
            }
            BasicDBList things = (BasicDBList) thingBase.get(LEVELS[initialLevel]);
            DummyDataUtils dummyDataUtils = new DummyDataUtils();
            if (things == null) {
                things = new BasicDBList();
                String pathAux = thingBase.get("path").toString()+".";
                things.add(dummyDataUtils.newThingTree(++initialId, LEVELS[initialLevel], pathAux+LEVELS[initialLevel]+"[0]"));
                thingBase.put(LEVELS[initialLevel], things);
            } else {
                if ((things.size() == 1) || (initialLevel == 5)) {
                    String pathAux = thingBase.get("path").toString()+".";
                    things.add(dummyDataUtils.newThingTree(++initialId, LEVELS[initialLevel], pathAux+LEVELS[initialLevel]+"["+things.size()+"]"));
                    thingBase.put(LEVELS[initialLevel], things);
                } else {
                    int pos = (int)(Math.random()*things.size());
                    BasicDBObject auxThing = (BasicDBObject) things.get(pos);
                    //path + auxThing.get("path").toString()+","+LEVELS[initialLevel]
                    auxThing = addChild(auxThing, "", initialLevel+1, level);
                    things.remove(pos);
                    things.add(pos, auxThing);
                    thingBase.put(LEVELS[initialLevel], things);
                }
            }
            return thingBase;
        }
    }

    private static void fillDummyData() {
        if (INITIAL_ID == 1) {
            MongoDAOUtil.getInstance().getCollection(COLLECTION_NAME).drop();
            MongoDAOUtil.getInstance().getCollection(COLLECTION_SNAPSHOTS_IDS).drop();
            MongoDAOUtil.getInstance().getCollection(COLLECTION_SNAPSHOTS).drop();
        }
        id = INITIAL_ID - 1L;
        // N things
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        while (id < MAX_THINGS) {
            int nThings = (int) (Math.random() * THING_TYPE_NAMES.length)+5;
            RunnableTree runnableTree = new RunnableTree(nThings, id);
            executorService.execute(runnableTree);
            id += nThings;
        }
    }

    private static BasicDBObject removePathFrom(BasicDBObject thing) {
        for (String level:LEVELS) {
            BasicDBList things = (BasicDBList) thing.get(level);
            if (things != null) {
                for (int i = 0; i < things.size(); i++) {
                    BasicDBObject auxThing = (BasicDBObject) things.get(i);
                    things.remove(i);
                    auxThing = removePathFrom(auxThing);
                    things.add(i, auxThing);
                }
            }
        }
        thing.remove("path");
        // Creating snapshots
        DummyDataUtils.createSnapshot(thing, COLLECTION_SNAPSHOTS, COLLECTION_SNAPSHOTS_IDS, BLINKS_PER_THING_LIMIT_MIN, BLINKS_PER_THING_LIMIT_MAX);
        return thing;
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
//            MongoDAOUtil.setupMongodb("10.100.0.140",27017, "riot_main", null , null, "admin", "control123!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
