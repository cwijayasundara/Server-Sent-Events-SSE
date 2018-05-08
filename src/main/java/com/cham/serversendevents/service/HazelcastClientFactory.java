package com.cham.serversendevents.service;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.QueryCacheConfig;
import com.hazelcast.config.SerializationConfig;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.stereotype.Service;

@Service
public class HazelcastClientFactory {

    private final static String mapName="employee-map";
    private final static String portableClassName="com.cham.serversendevents.domain.PortableFactoryImpl";
    private final static int factoryId=1;
    private final static String cacheName="employee-map-cache";

    private ClientConfig clientConfig;
    private SerializationConfig srzConfig;
    private QueryCacheConfig queryCacheConfig;

    private HazelcastInstance client;

    public HazelcastClientFactory(){
        clientConfig = new ClientConfig();
        srzConfig = clientConfig.getSerializationConfig();
        srzConfig.addPortableFactoryClass(factoryId, portableClassName);
        queryCacheConfig = new QueryCacheConfig(cacheName);
        clientConfig.addQueryCacheConfig(mapName, queryCacheConfig);
        client=HazelcastClient.newHazelcastClient(clientConfig);
    }

    public HazelcastInstance getClient() {
        return client;
    }

}
