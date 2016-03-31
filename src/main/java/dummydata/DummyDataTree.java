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
    private static int MAX_THINGS = 100;
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
            "pallete,carton",
            "pallete,carton,rfid",
            "pallete,carton,box",
            "pallete,carton,box,rfid",
            "pallete,carton,box,item",
            "pallete,carton,box,item,rfid",
    };

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
        Long id = 0L;
        // N things
        System.out.println(thingTypeList);
        for (int i = 0; i < MAX_THINGS; i++) {
            int level = (int)(Math.random()*thingTypeList.size());
            String[] levels = thingTypeList.get(level).get("thingType").toString().split(",");
            String[] levelsCodes = thingTypeList.get(level).get("thingTypeCode").toString().split(",");
            BasicDBObject doc = new BasicDBObject();
            BasicDBList arrayChildren = new BasicDBList();
            for (int k = 0; k < levels.length; k++) {
                Map<String, Object> thingMap = dummyDataUtils.newThingTree(Long.valueOf(String.valueOf(++id)), levels[k].toUpperCase());
                BasicDBObject thingObject = new BasicDBObject();
                for (Map.Entry<String, Object> entry : thingMap.entrySet()) {
                    if (k == 0) {
                        doc.append(entry.getKey(), entry.getValue());
                    } else {
                        thingObject.append(entry.getKey(), entry.getValue());
                    }
                }
                if (k > 0) {
                    arrayChildren.add(thingObject);
                }
            }
            doc.append("children", arrayChildren);
            MongoDAOUtil.getInstance().getCollection(COLLECTION_NAME).save(doc);
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
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
