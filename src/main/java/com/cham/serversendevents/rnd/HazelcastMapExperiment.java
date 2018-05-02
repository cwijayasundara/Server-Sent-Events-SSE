package com.cham.serversendevents.rnd;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.QueryCacheConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.map.QueryCache;
import com.hazelcast.query.SqlPredicate;

import java.util.Collection;
import java.util.Iterator;

public class HazelcastMapExperiment {

    private final static String cacheName="person-map-cache";
    private final static String sqlPredicate="__key like %ic%";

    public static void main(String args[]){

        // sql predicates
        ClientConfig clientConfig = new ClientConfig();
        // create the query cache
        QueryCacheConfig queryCacheConfig = new QueryCacheConfig(cacheName);
        clientConfig.addQueryCacheConfig(cacheName, queryCacheConfig);

        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);

        IMap<String, Person> clientEmpMap = (IMap) client.getMap(cacheName);
        // continous query cache
        QueryCache<String, Person> queryCache = clientEmpMap.getQueryCache(cacheName, new SqlPredicate(sqlPredicate),true);

        Collection<Person> employeeCollection = queryCache.values();

        int size = queryCache.size();
        System.out.println("Continuous query cache size = " + size);

        Iterator itr = employeeCollection.iterator();
        while(itr.hasNext()){
            System.out.println(itr.next());
        }
    }
}
