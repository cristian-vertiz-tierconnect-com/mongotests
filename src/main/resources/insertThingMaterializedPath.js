/* 1 */
db.path_things.insert({
    "_id" : NumberLong(1),
    "thingTypeCode" : "pallete_code",
    "serialNumber" : "P10000",
    "pathThingType" : null,
    "path" : null,
    "color" : {
    "thingTypeFieldId" : NumberLong(24),
        "time" : ISODate("2016-01-10T21:07:49.241Z"),
        "value" : "Multicolor"
    }
});

/* 2 */
db.path_things.insert({
    "_id" : NumberLong(2),
    "thingTypeCode" : "default_rfid_thingtype",
    "serialNumber" : "RFID1000",
    "size" : {
        "thingTypeFieldId" : NumberLong(23),
        "time" : ISODate("2016-01-10T21:07:49.241Z"),
        "value" : "X-Large"
    },
    "color" : {
        "thingTypeFieldId" : NumberLong(24),
        "time" : ISODate("2016-01-10T21:07:49.241Z"),
        "value" : "Gray"
    },
    "pathThingType" : "pallete_code",
    "path" : ",1,"
});

/* 3 */
db.path_things.insert({
    "_id" : NumberLong(3),
    "thingTypeCode" : "cartoon_code",
    "serialNumber" : "CARTOON1000",
    "pathThingType" : "pallete_code",
    "path" : ",1,"
});

/* 4 */
db.path_things.insert({
    "_id" : NumberLong(4),
    "thingTypeCode" : "default_rfid_thingtype",
    "serialNumber" : "RFID1001",
    "size" : {
        "thingTypeFieldId" : NumberLong(23),
        "time" : ISODate("2016-01-10T21:07:49.241Z"),
        "value" : "XXXL"
    },
    "color" : {
        "thingTypeFieldId" : NumberLong(24),
        "time" : ISODate("2016-01-10T21:07:49.241Z"),
        "value" : "Pink"
    },
    "pathThingType" : "pallete_code,cartoon_code",
    "path" : ",1,3,"
});

/* 5 */
db.path_things.insert({
    "_id" : NumberLong(5),
    "thingTypeCode" : "box_code",
    "serialNumber" : "BOX1000",
    "pathThingType" : "pallete_code,cartoon_code",
    "path" : ",1,3,",
    "color" : {
        "thingTypeFieldId" : NumberLong(24),
        "time" : ISODate("2016-01-10T21:07:49.241Z"),
        "value" : "Brown"
    }
});

/* 6 */
db.path_things.insert({
    "_id" : NumberLong(6),
    "thingTypeCode" : "default_rfid_thingtype",
    "serialNumber" : "RFID1002",
    "size" : {
        "thingTypeFieldId" : NumberLong(23),
        "time" : ISODate("2016-01-10T21:07:49.241Z"),
        "value" : "Small"
    },
    "color" : {
        "thingTypeFieldId" : NumberLong(24),
        "time" : ISODate("2016-01-10T21:07:49.241Z"),
        "value" : "Black"
    },
    "pathThingType" : "pallete_code,cartoon_code,box_code",
    "path" : ",1,3,5,"
});

/* 7 */
db.path_things.insert({
    "_id" : NumberLong(7),
    "thingTypeCode" : "item_code",
    "serialNumber" : "ITEM1000",
    "pathThingType" : "pallete_code,cartoon_code,box_code",
    "path" : ",1,3,5,"
});

/* 8 */
db.path_things.insert({
    "_id" : NumberLong(8),
    "thingTypeCode" : "default_rfid_thingtype",
    "serialNumber" : "RFID1003",
    "size" : {
        "thingTypeFieldId" : NumberLong(23),
        "time" : ISODate("2016-01-10T21:07:49.241Z"),
        "value" : "X-Large"
    },
    "color" : {
        "thingTypeFieldId" : NumberLong(24),
        "time" : ISODate("2016-01-10T21:07:49.241Z"),
        "value" : "Gray"
    },
    "pathThingType" : "pallete_code,cartoon_code,box_code,item_code",
    "path" : ",1,3,5,7,"
});

/* 9 */
db.path_things.insert({
    "_id" : NumberLong(9),
    "thingTypeCode" : "pallete_code",
    "serialNumber" : "P10001",
    "pathThingType" : null,
    "path" : null,
    "color" : {
        "thingTypeFieldId" : NumberLong(24),
        "time" : ISODate("2016-01-10T21:07:49.241Z"),
        "value" : "Multicolor"
    }
});

/* 10 */
db.path_things.insert({
    "_id" : NumberLong(10),
    "serialNumber" : "RFID1004",
    "thingTypeCode" : "default_rfid_thingtype",
    "size" : {
        "thingTypeFieldId" : NumberLong(23),
        "time" : ISODate("2016-01-10T21:07:49.241Z"),
        "value" : "Medium"
    },
    "color" : {
        "thingTypeFieldId" : NumberLong(24),
        "time" : ISODate("2016-01-10T21:07:49.241Z"),
        "value" : "Pink"
    },
    "pathThingType" : "pallete_code",
    "path" : ",9,"
});

/* 11 */
db.path_things.insert({
    "_id" : NumberLong(11),
    "serialNumber" : "CARTOON1001",
    "thingTypeCode" : "cartoon_code",
    "pathThingType" : "pallete_code",
    "path" : ",9,"
});

/* 12 */
db.path_things.insert({
    "_id" : NumberLong(12),
    "serialNumber" : "RFID1005",
    "thingTypeCode" : "default_rfid_thingtype",
    "size" : {
        "thingTypeFieldId" : NumberLong(23),
        "time" : ISODate("2016-01-10T21:07:49.241Z"),
        "value" : "X-Large"
    },
    "color" : {
        "thingTypeFieldId" : NumberLong(24),
        "time" : ISODate("2016-01-10T21:07:49.241Z"),
        "value" : "Black"
    },
    "pathThingType" : "pallete_code,cartoon_code",
    "path" : ",9,11,"
});

/* 13 */
db.path_things.insert({
    "_id" : NumberLong(13),
    "serialNumber" : "BOX1001",
    "thingTypeCode" : "box_code",
    "pathThingType" : "pallete_code,cartoon_code",
    "path" : ",9,11,"
});

/* 14 */
db.path_things.insert({
    "_id" : NumberLong(14),
    "serialNumber" : "RFID1006",
    "thingTypeCode" : "default_rfid_thingtype",
    "size" : {
        "thingTypeFieldId" : NumberLong(23),
        "time" : ISODate("2016-01-10T21:07:49.241Z"),
        "value" : "XXXL"
    },
    "color" : {
        "thingTypeFieldId" : NumberLong(24),
        "time" : ISODate("2016-01-10T21:07:49.241Z"),
        "value" : "Green"
    },
    "pathThingType" : "pallete_code,cartoon_code,box_code",
    "path" : ",9,11,13,"
});

/* 15 */
db.path_things.insert({
    "_id" : NumberLong(15),
    "serialNumber" : "ITEM1001",
    "thingTypeCode" : "item_code",
    "pathThingType" : "pallete_code,cartoon_code,box_code",
    "path" : ",9,11,13,"
});

/* 16 */
db.path_things.insert({
    "_id" : NumberLong(16),
    "serialNumber" : "RFID1007",
    "thingTypeCode" : "default_rfid_thingtype",
    "size" : {
        "thingTypeFieldId" : NumberLong(23),
        "time" : ISODate("2016-01-10T21:07:49.241Z"),
        "value" : "Medium"
    },
    "color" : {
        "thingTypeFieldId" : NumberLong(24),
        "time" : ISODate("2016-01-10T21:07:49.241Z"),
        "value" : "Green"
    },
    "pathThingType" : "pallete_code,cartoon_code,box_code,item_code",
    "path" : ",9,11,13,15,"
});

db.getCollection('path_things').insert({_id:NumberLong(17),thingTypeCode:"TEST",serialNumber:"TEST1000",pathThingType:null,path:null})
db.getCollection('path_things').insert({_id:NumberLong(18),thingTypeCode:"TESTL1",serialNumber:"TESTL11000",pathThingType:"TEST",path:",17,"})
db.getCollection('path_things').insert({_id:NumberLong(19),thingTypeCode:"default_rfid_thingtype",serialNumber:"RFID1008",pathThingType:"TEST,TESTL1",path:",17,18,"})

db.getCollection('path_things').insert({_id:NumberLong(20),thingTypeCode:"default_rfid_thingtype",serialNumber:"RFID1009",pathThingType:null,path:null})
db.getCollection('path_things').insert({_id:NumberLong(21),thingTypeCode:"default_rfid_thingtype",serialNumber:"RFID1010",pathThingType:null,path:null})

/************************************/
//Queries

//All Tree
db.getCollection('path_things').find().sort( { path: 1 }  );
//Just one branch
db.getCollection('path_things').find({path: /^,1,/});
//Just one subranch
db.getCollection('path_things').find({path: /^,1,3,/}, {_id:true,"serialNumber":true, "path":true});


//How to search a thing by serialNumber
db.getCollection('path_things').find({serialNumber:"BOX1000"}, {_id:true,"serialNumber":true, "path":true});
db.getCollection('path_things').find({_id: {$in:[1,3]}}, {_id:true,"serialNumber":true, "path":true});
db.getCollection('path_things').find({path: /^,1,3,5,/}, {_id:true,"serialNumber":true, "path":true});

//How to get a specific value (ELExpression)
db.getCollection('path_things').findOne(
    {
        pathThingType:"pallete_code,cartoon_code,box_code,item_code"
        ,"size.value":"X-Large"
    }
    ,{"size.value":true,_id:false});


/***********Expressions***********/
//How to get a specific value (ELExpression)
db.getCollection('path_things').findOne(
    {
        pathThingType:"pallete_code,cartoon_code,box_code,item_code"
        ,"size.value":{$regex:/.*X.*/}
    }
    ,{serialNumber:true,"size.value":true,_id:false});


//Color Value: PALLETE > CARTOON-> ${default_rfid_thingtype.color.value}
db.getCollection('path_things').find(
    {
        "pathThingType":"pallete_code,cartoon_code"
        ,"thingTypeCode":"default_rfid_thingtype"
    }
    ,{serialNumber:true,"color.value":true,_id:false});

//Color Object: PALLETE > CARTOON-> ${default_rfid_thingtype.color}
db.getCollection('path_things').find(
    {
        "pathThingType":"pallete_code,cartoon_code"
        ,"thingTypeCode":"default_rfid_thingtype"
    }
    ,{serialNumber:true,"color":true,_id:false});

//Count: PALLETE > CARTOON-> ${count{"default_rfid_thingtype","color=Pink"}}
db.getCollection('path_things').find(
    {
        "pathThingType":"pallete_code,cartoon_code"
        ,"thingTypeCode":"default_rfid_thingtype"
        ,"color.value":"Pink"
    }
    ,{serialNumber:true,"color":true,_id:false}).count();

//Count: PALLETE -> ${count{"CARTOON"}}
db.getCollection('path_things').find(
    {
        "pathThingType":/^pallete_code/
        ,"thingTypeCode":"cartoon_code"
    }
    ,{serialNumber:true,"color":true,_id:false}).count();

db.getCollection('path_things').drop();
db.getCollection('path_thingSnapshotIds').drop();
db.getCollection('path_thingSnapshots').drop();


db.getCollection('path_things').find().count();
db.getCollection('path_thingSnapshotIds').find().count();
db.getCollection('path_thingSnapshots').find().count();

//Snapshot
//QuerySnapshot

db.getCollection('path_thingSnapshotIds').find(
    { "blinks" : {"$elemMatch": { "time": { "$lte": 1459518142754 } } } },
    { "blinks" : {"$elemMatch": { "time": { "$lte": 1459518142754 } } } }
);
db.getCollection('path_thingSnapshots').find({_id:ObjectId("56fe7abeba7e4e5453fd8a64")},{value:true});

/*********************************************/

/**
 *
 * Example RAW Mongo DB query showing the expected properties in the response javascript object
 *
 */
function( options )
{
    var table = new Object();

    // required
    table.options = options;

    // optional, but recommended
    table.title = "Example #1a";

    // optional
    table.labelX = "Words";

    // optional
    table.labelY = "Letters";

    // optional
    table.labelZ = "Count";

    // optional
    table.columnNames = ["Id", "Pallete.Color", "Cartoon.SerialNumber", "Box.Color" ];

    // optional
    //table.rowNames = [ "Alpha", "Bravo", "Charlie", "Delta" ];

    // pallete_code.color = Multicolor
    // search all cartoon_code with
    // box_code.color = Brown
    var result = [];
    var myCursor = db.getCollection('path_things').find(
        {
            thingTypeCode:"pallete_code"
            //,"color.value":"Multicolor"
        }
    );

//      while (myCursor.hasNext()) {
//             print(tojson(myCursor.next()));
//          }

    while (myCursor.hasNext()) {
        var cursor = myCursor.next();
        var id =  "^,"+cursor._id+",";
        var myCursorPath2 = db.getCollection('path_things').find(
            {
                path:{$regex:id}
            },{_id:true, serialNumber:true,color:true,thingTypeCode:true}).sort({ color: -1 });

//         while (myCursorPath2.hasNext()) {
//             print(tojson(myCursorPath2.next()));
//          }
        //Verify the filters


        if(cursor.color!=null)
        {
            var count = 0;
            var subResult=[];
            subResult.push(cursor._id);
            subResult.push(cursor.color.value);
            var serialNumber = null;
            var colorBox = null;
            //print(cursor.color.value);
            while (myCursorPath2.hasNext()) {
                var subElement = myCursorPath2.next();
                //            for(var key in subElement){
                //                print(key+"-"+subElement._id)
                //            }
                if(subElement.thingTypeCode == "cartoon_code")
                {
                    //subResult.push(subElement.serialNumber);
                    serialNumber = subElement.serialNumber;
                    count++;
                }
                if(subElement.thingTypeCode == "box_code" && subElement.color!=null)
                {
                    if(subElement.color.value == "Brown" || subElement.color.value == "Yellow" || subElement.color.value == "Red"  )
                    {
                        //subResult.push(subElement.color.value);
                        colorBox = subElement.color.value;
                        count++;
                    }
                }
            };
            //Create Object
            if(count==2){
                subResult.push(serialNumber);
                subResult.push(colorBox);
                result.push(subResult);
            }

        }
    }
    table.data = result;

    return table;
}


