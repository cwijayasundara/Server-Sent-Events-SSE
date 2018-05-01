package com.cham.serversendevents.controller;

import com.cham.serversendevents.domain.Employee;
import com.cham.serversendevents.service.EmployeeService;
import com.hazelcast.core.EntryEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class EventPublisherController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(value = "/stream/numbers", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<Integer>> getAllNumbers() {

        System.out.println("Inside EventPublisherController.getAllNumbers()..");

        return Flux.interval(Duration.ofSeconds(1))
                .map(seq -> Tuples.of(seq, ThreadLocalRandom.current().nextInt()))
                .map(data -> ServerSentEvent.<Integer>builder()
                        .event("random")
                        .id(Long.toString(data.getT1()))
                        .data(data.getT2())
                        .build());
    }

    @GetMapping(value = "/stream/employee-events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Employee> getEmployeeChangeEvents() {
        System.out.println("Inside EventPublisherController.getEmployeeChangeEvents()..");
        return Flux.fromStream(employeeService.getEmployeeCache().stream());
    }

    @GetMapping(value = "/stream/employee-change-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EntryEvent<String, Employee>> getEmployeeEventsAsStream(){
        System.out.println("Inside EventPublisherController.getEmployeeEventsAsStream()..");
        return null;
    }
}
