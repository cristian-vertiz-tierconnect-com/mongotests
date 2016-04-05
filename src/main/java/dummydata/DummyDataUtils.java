package dummydata;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import dao.MongoDAOUtil;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
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
    public static String[] udfNames = {"status", "store", "zone", "location", "locationXYZ", "quantity", "bookstore", "owner",
    "abc", "xyz", "other", "other1", "phrase", "asdf"};
    public static String[] randomStrings = {"DgvCeIlUCT", "YAaRausNWa", "kSPoJeXtnj", "unxxHlIyJa",
            "oRghFbIhMO", "EGrUihyOXH", "GejPwSefBE", "HXeBAOEnDV", "bzggHXmVyL", "kWqsVCjZgW",
            "eMqmhHLkaH", "mXVTOhCifz", "QcmuhdZQYe", "YdztbcAScl", "AExFWtZCIB", "AYRRJPcENW",
            "TXmHDINqJU",
            "xmaoCkgzja"};

    public Map<String, Object> newThing(Long id, String prefix) {
        String serialNumber = prefix.toUpperCase()+String.format("%010d", Integer.parseInt(id.toString()));
        Map<String, Object> result = new HashMap<>();
        result.put("_id", id);
        result.put("name", serialNumber);
        result.put("serialNumber", serialNumber);
        result.put("color", getRandomValueFrom(colorsList));
        result.put("size", getRandomValueFrom(sizeList));
        // Creating random UDFs
        for (int i=0; i < (int)(Math.random()*10)+5; i++) {
            result.put(getRandomName(udfNames), getRandomUdf());
        }
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
        // Creating random UDFs
        for (int i=0; i < (int)(Math.random()*10)+5; i++) {
            result.put(getRandomName(udfNames), getRandomUdf());
        }
        // path is not a UDF, we put path to generate thing and then we deleted this.
        if (!path.isEmpty() && (".").equals(StringUtils.substring(path,0,1))){
            finalPath = StringUtils.substring(path,1,path.length());
            result.put("path", finalPath);
        } else {
            finalPath = path;
            result.put("path",finalPath);
        }
        File file = new File("test.txt");
        try {
            Boolean isNewFile = !(id == 1L);
            FileOutputStream fis = new FileOutputStream(file, isNewFile);
            PrintStream out = new PrintStream(fis);
            System.setOut(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("INSERT INTO thing_test (name,serial,path,thingType_code) " +
                "values ('" +serialNumber+ "','" +serialNumber+ "','" +finalPath+"','" +thingTypeCode+ "');");
        return result;
    }

    private Map<String, Object> getRandomUdf() {
        Map<String, Object> result = new HashMap<>();
        int total = (int)(Math.random()*udfNames.length)+2;
        for (int i = 0; i < total; i++) {
            result.put(getRandomName(udfNames), getRandomName(randomStrings));
        }
        return result;
    }

    private String getRandomName(String[] list) {
        return list[(int)(Math.random()*list.length)];
    }

    public static Object getRandomValueFrom(String[] list) {
        Map<String, Object> object = new HashMap<>();
        object.put("value", list[(int)(Math.random()*list.length)]);
        object.put("thingTypeFieldId", Long.valueOf(String.valueOf((int)(Math.random()*100))));
        object.put("time", new Date());
        return object;
    }

    public static void createSnapshot(BasicDBObject thingObject, String collSnapshots, String collSnapshotsIds, int maxBlinks) {
        BasicDBList blinks = new BasicDBList();
        Date date = new Date();
        Long delta = 100000*1000L;
        Long timeMili = date.getTime() - (maxBlinks+1)*delta;
        for(int i = 0; i < maxBlinks; i++) {
            BasicDBObject snapshot = new BasicDBObject();

            if(i > 0) {
                thingObject.put("color", DummyDataUtils.getRandomValueFrom(DummyDataUtils.colorsList));
                thingObject.put("size", DummyDataUtils.getRandomValueFrom(DummyDataUtils.sizeList));
            }

            snapshot.put("value",thingObject);
            snapshot.put("time", new Date(timeMili));
            MongoDAOUtil.getInstance().getCollection(collSnapshots).save(snapshot);
            ObjectId objId = (ObjectId)snapshot.get( "_id" );

            BasicDBObject blink = new BasicDBObject();
            blink.put("time", timeMili);
            blink.put("blink_id", objId);
            blinks.add(0, blink);
            timeMili += delta;
        }

        BasicDBObject snapshotId = new BasicDBObject();
        snapshotId.put("_id",thingObject.get("_id"));
        snapshotId.put("blinks",blinks);
        MongoDAOUtil.getInstance().getCollection(collSnapshotsIds).save(snapshotId);
    }
}
