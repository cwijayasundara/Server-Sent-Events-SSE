package com.cham.serversendevents.service;


import com.cham.serversendevents.domain.EmployeeChanges;
import com.cham.serversendevents.stream.EventStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventConsumer {

    @Autowired
    private OutPutProcessor outPutProcessor;

    @StreamListener(EventStream.INPUT)
    public void consumeTweets(@Payload EmployeeChanges event) {
        System.out.println("Received Server Event .." + event);
        outPutProcessor.process(event);
    }
}
