package dummydata;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.sun.xml.internal.ws.util.StringUtils;
import dao.MongoDAOUtil;
import org.bson.types.ObjectId;

import java.util.*;

/**
 * Created by rsejas on 3/31/16.
 */
public class DummyDataTree {
    // INITIAL_ID = 1: Collection will be deleted
    // INITIAL_ID > 1: Collection will continue from this id
    private static int INITIAL_ID = 1;
    private static int MAX_THINGS = 100;
    private static int MAX_THINGS_BY_DOC = 5;
    private static int MAX_LEVELS = 6;
    private static int MAX_BLINKS_PER_THING = 500;
    private static String COLLECTION_NAME = "dummy_data_tree";
    private static String COLLECTION_SNAPSHOTS_IDS = "dummy_data_tree_snapshots_ids";
    private static String COLLECTION_SNAPSHOTS = "dummy_data_tree_snapshots";
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
    private static String[] LEVELS = {"palette", "carton", "box", "item", "rfid"};
    private static Long id;

    public static void main(String[] args) {
        Boolean mongoInitialized = initMongo();
        if (mongoInitialized) {
            fillDummyData();
        }
    }

    private static void fillDummyData() {
        if (INITIAL_ID == 1) {
            MongoDAOUtil.getInstance().getCollection(COLLECTION_NAME).drop();
            MongoDAOUtil.getInstance().getCollection(COLLECTION_SNAPSHOTS_IDS).drop();
            MongoDAOUtil.getInstance().getCollection(COLLECTION_SNAPSHOTS).drop();
        }
        List<Map<String, Object>> thingTypeList = fillThingTypeList();
        DummyDataUtils dummyDataUtils = new DummyDataUtils();
        id = INITIAL_ID - 1L;
        // N things
        while (id < MAX_THINGS) {
            BasicDBObject doc = createThingDoc((int)(Math.random()*MAX_THINGS_BY_DOC+1));
            doc = removePathFrom(doc);
            MongoDAOUtil.getInstance().getCollection(COLLECTION_NAME).save(doc);
            // Creating snapshots

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
        createSnapshot(thing);
        return thing;
    }

    public static void createSnapshot(BasicDBObject thingObject)
    {
        BasicDBList blinks = new BasicDBList();
        Date date = new Date();
        Long delta = 100000*1000L;
        Long timeMili = date.getTime() - (MAX_BLINKS_PER_THING+1)*delta;
        for(int i = 0; i< MAX_BLINKS_PER_THING; i++)
        {

            BasicDBObject snapshot = new BasicDBObject();

            if(i>0)
            {
                thingObject.put("color", DummyDataUtils.getRandomValueFrom(DummyDataUtils.colorsList));
                thingObject.put("size", DummyDataUtils.getRandomValueFrom(DummyDataUtils.sizeList));
            }

            snapshot.put("value",thingObject);
            snapshot.put("time",new Date(timeMili));
            MongoDAOUtil.getInstance().getCollection(COLLECTION_SNAPSHOTS).save(snapshot);
            ObjectId objId = (ObjectId)snapshot.get( "_id" );

            BasicDBObject blink = new BasicDBObject();
            blink.put("time", timeMili);
            blink.put("blink_id", objId);
            blinks.add(0,blink);
            timeMili+=delta;
        }

        BasicDBObject snapshotId = new BasicDBObject();
        snapshotId.put("_id",thingObject.get("_id"));
        snapshotId.put("blinks",blinks);
        MongoDAOUtil.getInstance().getCollection(COLLECTION_SNAPSHOTS_IDS).save(snapshotId);
    }

    private static BasicDBObject createThingDoc(int nThings) {
        DummyDataUtils dummyDataUtils = new DummyDataUtils();
        BasicDBObject parent = dummyDataUtils.newThingTree(++id, LEVELS[0], "");
        while ((--nThings > 0) && (id < MAX_THINGS)){
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
                String pathAux = (thingBase.get("path").toString().isEmpty())?LEVELS[0]+",":thingBase.get("path").toString()+",";
                things.add(dummyDataUtils.newThingTree(++id, LEVELS[4], pathAux + LEVELS[4] + "[0]"));
                thingBase.put(LEVELS[4], things);
            } else {
                String pathAux = (thingBase.get("path").toString().isEmpty())?LEVELS[0]+",":thingBase.get("path").toString()+",";
                things.add(dummyDataUtils.newThingTree(++id, LEVELS[4], pathAux + LEVELS[4] + "["+things.size()+"]"));
                thingBase.put(LEVELS[4], things);
            }
            return thingBase;
        }
        BasicDBList things = (BasicDBList) thingBase.get(LEVELS[initialLevel]);
        DummyDataUtils dummyDataUtils = new DummyDataUtils();
        if (things == null) {
            things = new BasicDBList();
            String pathAux = (thingBase.get("path").toString().isEmpty())?LEVELS[0]+",":thingBase.get("path").toString()+",";
            things.add(dummyDataUtils.newThingTree(++id, LEVELS[initialLevel], pathAux+LEVELS[initialLevel]+"[0]"));
            thingBase.put(LEVELS[initialLevel], things);
        } else {
            if ((things.size() == 1) || (initialLevel == 5)) {
                String pathAux = (thingBase.get("path").toString().isEmpty())?LEVELS[0]+",":thingBase.get("path").toString()+",";
                things.add(dummyDataUtils.newThingTree(++id, LEVELS[initialLevel], pathAux+LEVELS[initialLevel]+"["+things.size()+"]"));
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
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
