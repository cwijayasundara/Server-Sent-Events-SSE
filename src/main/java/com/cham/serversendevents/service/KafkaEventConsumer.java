package com.cham.serversendevents.service;


import com.cham.serversendevents.domain.EmployeeChanges;
import com.cham.serversendevents.stream.EventStream;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventConsumer {

    @StreamListener(EventStream.INPUT)
    public void consumeTweets(@Payload EmployeeChanges event) {
        System.out.println("Received Server Event .." + event);
    }
}
