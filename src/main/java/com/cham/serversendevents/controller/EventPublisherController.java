package com.cham.serversendevents.controller;

import com.cham.serversendevents.domain.Employee;
import com.cham.serversendevents.domain.EmployeeChanges;
import com.cham.serversendevents.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
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
    public Flux<EmployeeChanges> getEmployeeEventsAsStream() {

        System.out.println("Inside EventPublisherController.getEmployeeEventsAsStream()..");

        List<EmployeeChanges> employeeChanges = employeeService.getChangeStream();
        //print the events
        Iterator itr = employeeChanges.iterator();
        while (itr.hasNext()){
            System.out.println(itr.next());
        }

        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
        interval.subscribe((i) -> employeeChanges.forEach(changes -> changes.setKey(changes.getKey())));
        Flux<EmployeeChanges> changesFlux = Flux.fromStream(employeeChanges.stream());
        return Flux.zip(interval, changesFlux).map(Tuple2::getT2);
    }
}
