package com.shyam.movietovoyage.core;

import java.util.HashMap;
import java.util.Map;

public class Database {

    private static Map<String, ExtractRequest> map = new HashMap<>();

    public static void insert(String uuid, ExtractRequest extractRequest){
        map.put(uuid, extractRequest);
    }

    public static ExtractRequest read(String uuid) {
        return map.get(uuid);
    }
}
