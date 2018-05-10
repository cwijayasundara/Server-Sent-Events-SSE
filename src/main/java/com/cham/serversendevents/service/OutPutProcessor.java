package com.cham.serversendevents.service;

import com.cham.serversendevents.domain.EmployeeChanges;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OutPutProcessor {

    private List<EmployeeChanges> changeList = new ArrayList<>();

    @Autowired
    private SimpMessagingTemplate template;

    private final static Gson gson = new GsonBuilder().create();

    public List<EmployeeChanges> getChangeList() {
        return changeList;
    }

    public EmployeeChanges process(EmployeeChanges event){
        System.out.println("OutPutProcessor.process() .." + event);
        changeList.add(event);
        String eventStr = gson.toJson(event);
        System.out.println("The JSON str is " + eventStr);
        template.convertAndSend("/topic/greetings",eventStr);
        return event;
    }
}
