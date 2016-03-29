db.ref_things.insert(
    {
        "_id": NumberLong(1),
        "name": "PALLETE01",
        "groupTypeId": NumberLong(3),
        "groupTypeName": "Facility",
        "groupTypeCode": "",
        "groupId": NumberLong(3),
        "groupCode": "DF",
        "groupName": "Default Facility",
        "serialNumber": "PALLETE01",
        "children": [
            {
                "$ref": "ref_things",
                "$id": NumberLong(2),
                "$db": "riot_main"
            },
            {
                "$ref": "ref_things",
                "$id": NumberLong(3),
                "$db": "riot_main"
            }
        ]
    }
);

db.ref_things.insert(
    {
        "_id": NumberLong(2),
        "name": "RFID01",
        "groupTypeId": NumberLong(3),
        "groupTypeName": "Facility",
        "groupTypeCode": "",
        "groupId": NumberLong(3),
        "groupCode": "DF",
        "groupName": "Default Facility",
        "serialNumber": "RFID01",
        "parent": {
            "$ref": "ref_things",
            "$id": NumberLong(1),
            "$db": "riot_main"
        }
    });

db.ref_things.insert(
    {
        "_id": NumberLong(3),
        "name": "CARTON01",
        "groupTypeId": NumberLong(3),
        "groupTypeName": "Facility",
        "groupTypeCode": "",
        "groupId": NumberLong(3),
        "groupCode": "DF",
        "groupName": "Default Facility",
        "serialNumber": "CARTON01",
        "parent": {
            "$ref": "ref_things",
            "$id": NumberLong(1),
            "$db": "riot_main"
        },
        "children": [
            {
                "$ref": "ref_things",
                "$id": NumberLong(4),
                "$db": "riot_main"
            },
            {
                "$ref": "ref_things",
                "$id": NumberLong(5),
                "$db": "riot_main"
            }
        ]
    }
);

db.ref_things.insert(
    {
        "_id": NumberLong(4),
        "name": "RFID02",
        "groupTypeId": NumberLong(3),
        "groupTypeName": "Facility",
        "groupTypeCode": "",
        "groupId": NumberLong(3),
        "groupCode": "DF",
        "groupName": "Default Facility",
        "serialNumber": "RFID02",
        "parent": {
            "$ref": "ref_things",
            "$id": NumberLong(3),
            "$db": "riot_main"
        }
    });

db.ref_things.insert(
    {
        "_id": NumberLong(5),
        "name": "BOX01",
        "groupTypeId": NumberLong(3),
        "groupTypeName": "Facility",
        "groupTypeCode": "",
        "groupId": NumberLong(3),
        "groupCode": "DF",
        "groupName": "Default Facility",
        "serialNumber": "BOX01",
        "parent": {
            "$ref": "ref_things",
            "$id": NumberLong(3),
            "$db": "riot_main"
        },
        "children": [
            {
                "$ref": "ref_things",
                "$id": NumberLong(6),
                "$db": "riot_main"
            },
            {
                "$ref": "ref_things",
                "$id": NumberLong(7),
                "$db": "riot_main"
            }
        ]
    }
);

db.ref_things.insert(
    {
        "_id": NumberLong(6),
        "name": "RFID03",
        "groupTypeId": NumberLong(3),
        "groupTypeName": "Facility",
        "groupTypeCode": "",
        "groupId": NumberLong(3),
        "groupCode": "DF",
        "groupName": "Default Facility",
        "serialNumber": "RFID03",
        "parent": {
            "$ref": "ref_things",
            "$id": NumberLong(5),
            "$db": "riot_main"
        }
    });

db.ref_things.insert(
    {
        "_id": NumberLong(7),
        "name": "ITEM01",
        "groupTypeId": NumberLong(3),
        "groupTypeName": "Facility",
        "groupTypeCode": "",
        "groupId": NumberLong(3),
        "groupCode": "DF",
        "groupName": "Default Facility",
        "serialNumber": "ITEM01",
        "parent": {
            "$ref": "ref_things",
            "$id": NumberLong(5),
            "$db": "riot_main"
        },
        "children": [
            {
                "$ref": "ref_things",
                "$id": NumberLong(8),
                "$db": "riot_main"
            }
        ]
    }
);

db.ref_things.insert(
    {
        "_id": NumberLong(8),
        "name": "RFID03",
        "groupTypeId": NumberLong(3),
        "groupTypeName": "Facility",
        "groupTypeCode": "",
        "groupId": NumberLong(3),
        "groupCode": "DF",
        "groupName": "Default Facility",
        "serialNumber": "RFID03",
        "parent": {
            "$ref": "ref_things",
            "$id": NumberLong(7),
            "$db": "riot_main"
        }
    });