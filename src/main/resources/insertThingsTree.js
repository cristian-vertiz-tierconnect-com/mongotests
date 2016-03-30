db.tree_things.insert(
    {
        "_id": NumberLong(1),
        "name": "PALLETE01",
        "serialNumber": "PALLETE01",
        "groupTypeId": NumberLong(3),
        "groupId": NumberLong(3),
        "thingTypeId":NumberLong(1),
        "rfid":{"_id": NumberLong(2),
            "name": "RFID00",
            "serialNumber": "RFID00",
            "isChild":"true",
            "groupTypeId": NumberLong(3),
            "groupId": NumberLong(3),
            "thingTypeId":NumberLong(3)
        },
        "carton":{"_id": NumberLong(3),
            "name": "CARTON01",
            "serialNumber": "CARTON01",
            "isChild":"true",
            "groupTypeId": NumberLong(3),
            "groupId": NumberLong(3),
            "thingTypeId":NumberLong(2),
            "rfid":[{
                    "_id": NumberLong(4),
                    "name": "RFID01",
                    "serialNumber": "RFID01",
                    "isChild":"true",
                    "groupTypeId": NumberLong(3),
                    "groupId": NumberLong(3),
                    "thingTypeId":NumberLong(3)
                    },
                    {
                    "_id": NumberLong(4),
                    "name": "RFID02",
                    "serialNumber": "RFID02",
                    "isChild":"true",
                    "groupTypeId": NumberLong(3),
                    "groupId": NumberLong(3),
                    "thingTypeId":NumberLong(3)
                    }],
            "box":{"_id": NumberLong(5),
                "name": "BOX01",
                "serialNumber": "BOX01",
                "isChild":"true",
                "groupTypeId": NumberLong(3),
                "groupId": NumberLong(3),
                "thingTypeId":NumberLong(4),
                "rfid":{"_id": NumberLong(6),
                    "name": "RFID03",
                    "serialNumber": "RFID03",
                    "isChild":"true",
                    "groupTypeId": NumberLong(3),
                    "groupId": NumberLong(3),
                    "thingTypeId":NumberLong(3)
                },
                "item":{"_id": NumberLong(7),
                    "name": "ITEM01",
                    "serialNumber": "ITEM01",
                    "isChild":"true",
                    "groupTypeId": NumberLong(3),
                    "groupId": NumberLong(3),
                    "thingTypeId":NumberLong(5),
                    "rfid":[{"_id": NumberLong(8),
                        "name": "RFID04",
                        "serialNumber": "RFID04",
                        "isChild":"true",
                        "groupTypeId": NumberLong(3),
                        "groupId": NumberLong(3),
                        "thingTypeId":NumberLong(3)
                    },
                        {"_id": NumberLong(9),
                            "name": "RFID05",
                            "serialNumber": "RFID05",
                            "isChild":"true",
                            "groupTypeId": NumberLong(3),
                            "groupId": NumberLong(3),
                            "thingTypeId":NumberLong(3)
                        }
                    ]
                }
            }
        }
    }

)