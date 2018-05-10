package com.cham.serversendevents.service;

import com.cham.serversendevents.domain.Employee;
import com.cham.serversendevents.listener.EmployeeEntryListener;
import com.hazelcast.core.IMap;
import com.hazelcast.map.QueryCache;
import com.hazelcast.query.SqlPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class EmployeeService {

    @Autowired
    private HazelcastClientFactory hazelcastClientFactory;

    //private final static String sqlPredicate="name like A% AND age>15 AND salary<600";
    private final static String sqlPredicate="name like A%";

    private final static String mapName="employee-map";
    private final static String cacheName="employee-map-cache";

    public Collection<Employee> getEmployeeCache() {
        // create the query cache
        IMap<String, Employee> clientEmpMap = (IMap) hazelcastClientFactory.getClient().getMap(mapName);
        // continous query cache
        QueryCache<String, Employee> queryCache = clientEmpMap.getQueryCache(cacheName, new SqlPredicate(sqlPredicate), true);
        queryCache.addEntryListener(new EmployeeEntryListener(),
                new SqlPredicate(sqlPredicate), true);

        System.out.println("Registered the listener ..");

        Collection<Employee> employeeCollection = queryCache.values();

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            int size = queryCache.size();
            System.out.println("Continuous query cache size = " + size);
            employeeCollection.stream().forEach(System.out::println);
            return employeeCollection;
        }
    }
}
