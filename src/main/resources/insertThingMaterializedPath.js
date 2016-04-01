/* 1 */
db.path_things.insert({
    "_id" : NumberLong(1),
    "thingTypeCode" : "pallete_code",
    "serialNumber" : "P10000",
    "pathThingType" : null,
    "path" : null
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
    "path" : ",1,3,"
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
    "path" : null
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
db.getCollection('path_things').find({path: /^1/});
//Just one subranch
db.getCollection('path_things').find({path: /^1,3/}, {_id:true,"serialNumber":true, "path":true});


//How to search a thing by serialNumber
db.getCollection('path_things').find({serialNumber:"BOX1000"}, {_id:true,"serialNumber":true, "path":true});
db.getCollection('path_things').find({_id: {$in:[1,3]}}, {_id:true,"serialNumber":true, "path":true});
db.getCollection('path_things').find({path: /^1,3,5/}, {_id:true,"serialNumber":true, "path":true});

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