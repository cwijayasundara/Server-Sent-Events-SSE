package com.cham.serversendevents.stream;


import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

@Component
public interface EventStream {

    String OUTPUT = "changes-out";

    @Output(OUTPUT)
    MessageChannel outboundChanges();

    String INPUT = "changes-in";

    @Input(INPUT)
    SubscribableChannel inboundMessages();

}
