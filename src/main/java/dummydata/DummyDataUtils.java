package dummydata;

import com.mongodb.BasicDBObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rsejas on 3/31/16.
 */
public class DummyDataUtils {
    public String prefixName = "Thing";
    public String prefixSerialNumber = "ThingSerial";
    public static String[] colorsList = {"White", "Gray", "Pink", "Red", "Brown", "Orange", "Yellow", "Green", "Cyan", "Blue", "Violet", "Navy"};
    public static String[] sizeList = {"Small", "Medium", "Large"};

    public Map<String, Object> newThing(Long id) {
        Map<String, Object> result = new HashMap<>();
        result.put("_id", id);
        result.put("name", prefixName+id);
        result.put("serialNumber", prefixSerialNumber+id);
        result.put("color", getRandomValueFrom(colorsList));
        result.put("size", getRandomValueFrom(sizeList));
        return result;
    }

    public BasicDBObject newThingTree(Long id, String prefix, String path) {
        String serialNumber = prefix+String.format("%010d", Integer.parseInt(id.toString()));
        BasicDBObject result = new BasicDBObject();
        result.put("_id", id);
        result.put("name", serialNumber);
        result.put("serialNumber", prefix+String.format("%010d", Integer.parseInt(id.toString())));
        result.put("color", getRandomValueFrom(colorsList));
        result.put("size", getRandomValueFrom(sizeList));
        result.put("groupTypeId",id);
        result.put("groupId",id);
        // path is not a UDF, we put path to generate thing and then we deleted this.
        result.put("path", path);
        System.out.println("thingMap.put(\"" + serialNumber + "\", \"" + path + "\");");
        return result;
    }

    public static Object getRandomValueFrom(String[] list) {
        Map<String, Object> object = new HashMap<>();
        object.put("value", list[(int)(Math.random()*list.length)]);
        object.put("thingTypeFieldId", Long.valueOf(String.valueOf((int)(Math.random()*100))));
        object.put("time", new Date());
        return object;
    }
}
