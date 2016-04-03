package dummydata;

import com.mongodb.BasicDBObject;
import org.apache.commons.lang.StringUtils;

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

    public BasicDBObject newThingTree(Long id, String thingTypeCode, String path) {
        String serialNumber = thingTypeCode+String.format("%010d", Integer.parseInt(id.toString()));
        BasicDBObject result = new BasicDBObject();
        String finalPath;
        result.put("_id", id);
        result.put("name", serialNumber);
        result.put("serialNumber", serialNumber);
        result.put("groupTypeId",id);
        result.put("groupId",id);
        result.put("thingTypeCode",thingTypeCode);
        result.put("color", getRandomValueFrom(colorsList));
        result.put("size", getRandomValueFrom(sizeList));
        // path is not a UDF, we put path to generate thing and then we deleted this.
        if (!path.isEmpty() && (",").equals(StringUtils.substring(path,0,1))){
            finalPath = StringUtils.substring(path,1,path.length());
            result.put("path", finalPath);
        } else {
            finalPath = path;
            result.put("path",finalPath);
        }
        System.out.println("INSERT INTO thing_test (name,serial,path,thingType_code) " +
                "values ('" +serialNumber+ "','" +serialNumber+ "','" +finalPath+"','" +thingTypeCode+ "');");
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
