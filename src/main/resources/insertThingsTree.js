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
                    "_id": NumberLong(5),
                    "name": "RFID02",
                    "serialNumber": "RFID02",
                    "isChild":"true",
                    "groupTypeId": NumberLong(3),
                    "groupId": NumberLong(3),
                    "thingTypeId":NumberLong(3)
                    }],
            "box":{"_id": NumberLong(6),
                "name": "BOX01",
                "serialNumber": "BOX01",
                "isChild":"true",
                "groupTypeId": NumberLong(3),
                "groupId": NumberLong(3),
                "thingTypeId":NumberLong(4),
                "rfid":{"_id": NumberLong(7),
                    "name": "RFID03",
                    "serialNumber": "RFID03",
                    "isChild":"true",
                    "groupTypeId": NumberLong(3),
                    "groupId": NumberLong(3),
                    "thingTypeId":NumberLong(3)
                },
                "item":{"_id": NumberLong(8),
                    "name": "ITEM01",
                    "serialNumber": "ITEM01",
                    "isChild":"true",
                    "groupTypeId": NumberLong(3),
                    "groupId": NumberLong(3),
                    "thingTypeId":NumberLong(5),
                    "rfid":[{"_id": NumberLong(9),
                        "name": "RFID04",
                        "serialNumber": "RFID04",
                        "isChild":"true",
                        "groupTypeId": NumberLong(3),
                        "groupId": NumberLong(3),
                        "thingTypeId":NumberLong(3)
                    },
                        {"_id": NumberLong(10),
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
db.tree_things.insert(
    {
        "_id": NumberLong(10),
        "name": "PALLETE10",
        "serialNumber": "PALLETE10",
        "groupTypeId": NumberLong(3),
        "groupId": NumberLong(3),
        "thingTypeId":NumberLong(1),
        "rfid":{"_id": NumberLong(20),
            "name": "RFID10",
            "serialNumber": "RFID10",
            "isChild":"true",
            "groupTypeId": NumberLong(3),
            "groupId": NumberLong(3),
            "thingTypeId":NumberLong(3)
        },
        "carton":{"_id": NumberLong(30),
            "name": "CARTON10",
            "serialNumber": "CARTON10",
            "isChild":"true",
            "groupTypeId": NumberLong(3),
            "groupId": NumberLong(3),
            "thingTypeId":NumberLong(2),
            "rfid":[{
                "_id": NumberLong(40),
                "name": "RFID11",
                "serialNumber": "RFID11",
                "isChild":"true",
                "groupTypeId": NumberLong(3),
                "groupId": NumberLong(3),
                "thingTypeId":NumberLong(3)
            },
                {
                    "_id": NumberLong(50),
                    "name": "RFID12",
                    "serialNumber": "RFID12",
                    "isChild":"true",
                    "groupTypeId": NumberLong(3),
                    "groupId": NumberLong(3),
                    "thingTypeId":NumberLong(3)
                }],
            "box":{"_id": NumberLong(60),
                "name": "BOX10",
                "serialNumber": "BOX10",
                "isChild":"true",
                "groupTypeId": NumberLong(3),
                "groupId": NumberLong(3),
                "thingTypeId":NumberLong(4),
                "rfid":{"_id": NumberLong(70),
                    "name": "RFID13",
                    "serialNumber": "RFID13",
                    "isChild":"true",
                    "groupTypeId": NumberLong(3),
                    "groupId": NumberLong(3),
                    "thingTypeId":NumberLong(3)
                },
                "item":{"_id": NumberLong(80),
                    "name": "ITEM10",
                    "serialNumber": "ITEM10",
                    "isChild":"true",
                    "groupTypeId": NumberLong(3),
                    "groupId": NumberLong(3),
                    "thingTypeId":NumberLong(5),
                    "rfid":[{"_id": NumberLong(90),
                        "name": "RFID14",
                        "serialNumber": "RFID14",
                        "isChild":"true",
                        "groupTypeId": NumberLong(3),
                        "groupId": NumberLong(3),
                        "thingTypeId":NumberLong(3)
                    },
                        {"_id": NumberLong(100),
                            "name": "RFID15",
                            "serialNumber": "RFID15",
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