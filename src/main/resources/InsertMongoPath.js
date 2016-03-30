/* 1 */
db.path_things.insert({
    "_id" : NumberLong(1),
    "thingTypeCode" : "pallete_code",
    "serialNumber" : "P10000",
    "path" : null
});

/* 2 */
db.path_things.insert({
    "_id" : NumberLong(2),
    "thingTypeCode" : "default_rfid_thingtype",
    "serialNumber" : "RFID1000",
    "path" : "1"
});

/* 3 */
db.path_things.insert({
    "_id" : NumberLong(3),
    "thingTypeCode" : "cartoon_code",
    "serialNumber" : "CARTOON1000",
    "path" : "1"
});

/* 4 */
db.path_things.insert({
    "_id" : NumberLong(4),
    "thingTypeCode" : "default_rfid_thingtype",
    "serialNumber" : "RFID1001",
    "path" : "1,3"
});

/* 5 */
db.path_things.insert({
    "_id" : NumberLong(5),
    "thingTypeCode" : "box_code",
    "serialNumber" : "BOX1000",
    "path" : "1,3"
});

/* 6 */
db.path_things.insert({
    "_id" : NumberLong(6),
    "thingTypeCode" : "default_rfid_thingtype",
    "serialNumber" : "RFID1002",
    "path" : "1,3,5"
});

/* 7 */
db.path_things.insert({
    "_id" : NumberLong(7),
    "thingTypeCode" : "item_code",
    "serialNumber" : "ITEM1000",
    "path" : "1,3,5"
});

/* 8 */
db.path_things.insert({
    "_id" : NumberLong(8),
    "thingTypeCode" : "default_rfid_thingtype",
    "serialNumber" : "RFID1003",
    "path" : "1,3,5,7"
});

/* 9 */
db.path_things.insert({
    "_id" : NumberLong(9),
    "thingTypeCode" : "pallete_code",
    "serialNumber" : "P10001",
    "path" : null
});

/* 10 */
db.path_things.insert({
    "_id" : NumberLong(10),
    "serialNumber" : "RFID1004",
    "thingTypeCode" : "default_rfid_thingtype",
    "path" : "9"
});

/* 11 */
db.path_things.insert({
    "_id" : NumberLong(11),
    "serialNumber" : "CARTOON1001",
    "thingTypeCode" : "cartoon_code",
    "path" : "9"
});

/* 12 */
db.path_things.insert({
    "_id" : NumberLong(12),
    "serialNumber" : "RFID1005",
    "thingTypeCode" : "default_rfid_thingtype",
    "path" : "9,11"
});

/* 13 */
db.path_things.insert({
    "_id" : NumberLong(13),
    "serialNumber" : "BOX1001",
    "thingTypeCode" : "box_code",
    "path" : "9,11"
});

/* 14 */
db.path_things.insert({
    "_id" : NumberLong(14),
    "serialNumber" : "RFID1006",
    "thingTypeCode" : "default_rfid_thingtype",
    "path" : "9,11,13"
});

/* 15 */
db.path_things.insert({
    "_id" : NumberLong(15),
    "serialNumber" : "ITEM1001",
    "thingTypeCode" : "item_code",
    "path" : "9,11,13"
});

/* 16 */
db.path_things.insert({
    "_id" : NumberLong(16),
    "serialNumber" : "RFID1007",
    "thingTypeCode" : "default_rfid_thingtype",
    "path" : "9,11,13,15"
});
