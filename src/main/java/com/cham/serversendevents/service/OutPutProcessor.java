package com.cham.serversendevents.service;

import com.cham.serversendevents.domain.EmployeeChanges;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OutPutProcessor {

    private List<EmployeeChanges> changeList = new ArrayList<>();

    public List<EmployeeChanges> getChangeList() {
        return changeList;
    }

    public void process(EmployeeChanges event){
        System.out.println("OutPutProcessor.process() .." + event);
        changeList.add(event);
    }

}
