package com.cham.serversendevents;

import com.cham.serversendevents.stream.EventStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(EventStream.class)
public class ServerSendEventsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerSendEventsApplication.class, args);
	}
}
