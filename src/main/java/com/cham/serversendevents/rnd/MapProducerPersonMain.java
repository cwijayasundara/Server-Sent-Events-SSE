package com.cham.serversendevents.rnd;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

// This will produce values to a
public class MapProducerPersonMain {

    private final static String mapName="person-map-cache";

    private final static Gson gson = new GsonBuilder().create();


    public static void main(String args[]){


        ClientConfig clientConfig = new ClientConfig();
        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);

        // create the IMap
        IMap<String, String> serverEmpMap = client.getMap(mapName);

        // populate data to the server Map
        Person person1 = new Person("Alice", "UK", "v1" );
        Person person2 = new Person("Alicey", "US", "v1");
        Person person3 = new Person("Ben", "AUS", "v2");

        String person1Str = gson.toJson(person1);
        String person2Str = gson.toJson(person2);
        String person3Str = gson.toJson(person3);

        String key1 = person1.getName() + person1.getAddress();
        String key2 = person2.getName() + person2.getAddress();
        String key3 = person3.getName() + person3.getAddress();


        serverEmpMap.put(key1, person1Str);
        serverEmpMap.put(key2, person2Str);
        serverEmpMap.put(key3, person3Str);

    }
}
