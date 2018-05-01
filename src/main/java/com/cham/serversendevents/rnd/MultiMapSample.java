package com.cham.serversendevents.rnd;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MultiMap;

import java.util.Iterator;

public class MultiMapSample {

    private static final String multiMapName="person-multi-map";
    private static final ClientConfig clientConfig = new ClientConfig();
    private static final HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);

    public static void main(String args[]){

        // create the IMap
        MultiMap <String, Person> serverMultiMap = client.getMultiMap(multiMapName);
        // populate data to the server Map
        serverMultiMap.put("Chaminda", new Person("Chaminda", "UK", "v1"));
        serverMultiMap.put("Chaminda", new Person("Chaminda", "SL", "v2"));
        serverMultiMap.put("Chaminda", new Person("Chaminda", "US", "v3"));
        serverMultiMap.put("Chaminda", new Person("Chaminda", "AUS", "v4"));

        Iterator itr = serverMultiMap.values().iterator();
        while(itr.hasNext()){
            System.out.println(itr.next());
        }
    }
}
