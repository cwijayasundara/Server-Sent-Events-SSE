package com.cham.serversendevents.listener;

import com.cham.serversendevents.domain.Employee;
import com.cham.serversendevents.domain.EmployeeChanges;
import com.cham.serversendevents.service.KafkaEventProducer;
import com.cham.serversendevents.springUtil.CustomStaticApplicationContext;
import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.MapEvent;
import com.hazelcast.map.listener.*;

public class EmployeeEntryListener implements EntryAddedListener<String, Employee>,
                                    EntryUpdatedListener<String, Employee>,
                                    EntryRemovedListener<String, Employee>,
                                    EntryEvictedListener<String, Employee>,
                                    MapEvictedListener, MapClearedListener {

    @Override
    public void entryAdded(EntryEvent<String, Employee> event) {
        System.out.println("entryAdded:" + event);
        setValuesFromEvent(event);
    }

    @Override
    public void entryRemoved(EntryEvent<String, Employee> event) {
        System.out.println("entryRemoved:" + event);
        setValuesFromEvent(event);
    }

    @Override
    public void entryUpdated(EntryEvent<String, Employee> event) {
        System.out.println("entryUpdated:" + event);
        setValuesFromEvent(event);
    }

    @Override
    public void entryEvicted(EntryEvent<String, Employee> event) {
        System.out.println("entryEvicted:" + event);
        setValuesFromEvent(event);
    }

    private void setValuesFromEvent(EntryEvent<String, Employee> event){
        //get the Kafka producer bean
        KafkaEventProducer kafkaEventProducer = (KafkaEventProducer)(CustomStaticApplicationContext.getContext()
                .getBean("kafkaEventProducer"));

        EmployeeChanges employeeChanges = new EmployeeChanges();

        if(null !=event) {
            if (null != event.getMember()) employeeChanges.setMember(event.getMember().toString());
            employeeChanges.setName(event.getName());
            employeeChanges.setKey(event.getKey());
            employeeChanges.setOldValue(event.getOldValue());
            employeeChanges.setSentValue(event.getValue());
            employeeChanges.setMergingValue(event.getMergingValue());
            // publish the event to Kafka
            System.out.println("Sending event" + employeeChanges.getKey());
            kafkaEventProducer.publishEvents(employeeChanges);
        }
    }

    @Override
    public void mapEvicted(MapEvent event) {
        System.out.println("mapEvicted:" + event);
    }

    @Override
    public void mapCleared(MapEvent event) {
        System.out.println("mapCleared:" + event);
    }

}
