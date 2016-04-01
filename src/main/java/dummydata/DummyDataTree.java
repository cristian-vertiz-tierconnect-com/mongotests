package dummydata;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.sun.xml.internal.ws.util.StringUtils;
import dao.MongoDAOUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rsejas on 3/31/16.
 */
public class DummyDataTree {
    private static int MAX_THINGS = 10;
    private static int MAX_THINGS_BY_DOC = 8;
    private static int MAX_LEVELS = 6;
    private static String COLLECTION_NAME = "dummy_data_tree";
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
        MongoDAOUtil.getInstance().getCollection(COLLECTION_NAME).drop();
        List<Map<String, Object>> thingTypeList = fillThingTypeList();
        DummyDataUtils dummyDataUtils = new DummyDataUtils();
        id = 0L;
        // N things
        while (id < MAX_THINGS) {
            BasicDBObject doc = createThingDoc((int)(Math.random()*MAX_THINGS_BY_DOC+1));
            MongoDAOUtil.getInstance().getCollection(COLLECTION_NAME).save(doc);
        }
    }

    private static BasicDBObject createThingDoc(int nThings) {
        DummyDataUtils dummyDataUtils = new DummyDataUtils();
        BasicDBObject parent = dummyDataUtils.newThingTree(++id, LEVELS[0], "");
        while ((--nThings > 0) && (id < MAX_THINGS)){
            System.out.println(nThings);
            parent = addChild(parent, parent.get("path").toString(), 1, (int)(Math.random()*5));
        }
        return parent;
    }

    private static BasicDBObject addChild(BasicDBObject thingBase, String path, int initialLevel, int level) {
        // Creating rfid
        if ((int)(Math.random()*10) < 4) {
            BasicDBList things = (BasicDBList) thingBase.get(LEVELS[4]);
            DummyDataUtils dummyDataUtils = new DummyDataUtils();
            if (things == null) {
                things = new BasicDBList();
                things.add(dummyDataUtils.newThingTree(++id, LEVELS[4], thingBase.get("path").toString()+","+LEVELS[initialLevel-1]));
                thingBase.put(LEVELS[4], things);
            } else {
                things.add(dummyDataUtils.newThingTree(++id, LEVELS[4], path + thingBase.get("path").toString() + LEVELS[initialLevel - 1]));
                thingBase.put(LEVELS[initialLevel], things);
            }
            return thingBase;
        }
        BasicDBList things = (BasicDBList) thingBase.get(LEVELS[initialLevel]);
        DummyDataUtils dummyDataUtils = new DummyDataUtils();
        if (things == null) {
            things = new BasicDBList();
            things.add(dummyDataUtils.newThingTree(++id, LEVELS[initialLevel], path + thingBase.get("path").toString()+LEVELS[initialLevel-1]));
            thingBase.put(LEVELS[initialLevel], things);
        } else {
            if ((things.size() == 1) || (initialLevel == 5)) {
                things.add(dummyDataUtils.newThingTree(++id, LEVELS[initialLevel], path + thingBase.get("path").toString() + LEVELS[initialLevel - 1]));
                thingBase.put(LEVELS[initialLevel], things);
            } else {
                int pos = (int)(Math.random()*things.size());
                BasicDBObject auxThing = (BasicDBObject) things.get(pos);
                auxThing = addChild(auxThing, path + auxThing.get("path").toString(), initialLevel+1, level);
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
