package com.cham.serversendevents.service;

import com.cham.serversendevents.domain.Employee;
import com.cham.serversendevents.listener.EmployeeEntryListener;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.QueryCacheConfig;
import com.hazelcast.config.SerializationConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.map.QueryCache;
import com.hazelcast.query.SqlPredicate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;

@Service
public class EmployeeService {

    private final static String cacheName="employee-map-cache";
    //private final static String sqlPredicate="name like A% AND age>15 AND salary<600";

    private final static String sqlPredicate="name like A%";

    private final static String mapName="employee-map";
    private final static String portableClassName="com.cham.serversendevents.domain.PortableFactoryImpl";
    private final static int factoryId=1;

    public Collection<Employee> getEmployeeCache() {
        // sql predicates
        ClientConfig clientConfig = new ClientConfig();
        SerializationConfig srzConfig = clientConfig.getSerializationConfig();
        srzConfig.addPortableFactoryClass(factoryId, portableClassName);

        // create the query cache
        QueryCacheConfig queryCacheConfig = new QueryCacheConfig(cacheName);
        clientConfig.addQueryCacheConfig(mapName, queryCacheConfig);

        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);

        IMap<String, Employee> clientEmpMap = (IMap) client.getMap(mapName);
        // continous query cache
        QueryCache<String, Employee> queryCache = clientEmpMap.getQueryCache(cacheName, new SqlPredicate(sqlPredicate),true);

        queryCache.addEntryListener(new EmployeeEntryListener(),
                                    new SqlPredicate("name like A%"), true);

        System.out.println("Registered the listener ..");

        Collection<Employee> employeeCollection = queryCache.values();

        while (true) {
        try {
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }

        int size = queryCache.size();
        System.out.println("Continuous query cache size = " + size);
        Iterator itr = employeeCollection.iterator();
        while(itr.hasNext()){
            System.out.println(itr.next());
        }
        }
    }
}
