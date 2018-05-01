package com.cham.serversendevents.service;

import com.cham.serversendevents.domain.EmployeeChanges;
import com.cham.serversendevents.stream.EventStream;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Service
public class KafkaEventProducer {

    private final EventStream eventStream;

    public KafkaEventProducer(EventStream eventStream) {
        this.eventStream = eventStream;
    }

    public void publishEvents(EmployeeChanges events) {
        System.out.println("Sending events " + events);
        MessageChannel messageChannel = eventStream.outboundChanges();
        messageChannel.send(MessageBuilder
                .withPayload(events)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build());
    }
}