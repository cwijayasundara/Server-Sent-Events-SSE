package com.cham.serversendevents.service;


import com.cham.serversendevents.domain.EmployeeChanges;
import com.cham.serversendevents.stream.EventStream;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class KafkaEventConsumer {

    private List<EmployeeChanges> employeeChangesList = new ArrayList<>();

    public List<EmployeeChanges> getEmployeeChangesList() {
        return employeeChangesList;
    }

    @StreamListener(EventStream.INPUT)
    public void consumeTweets(@Payload EmployeeChanges event) {
        System.out.println("Received Server Event .." + event);
        employeeChangesList.add(event);
    }

}
