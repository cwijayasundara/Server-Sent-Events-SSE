package com.cham.serversendevents.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class EmployeeChanges implements Serializable {

    @JsonProperty
    private String member;

    @JsonProperty
    private String name;

    @JsonProperty
    private String key;

    @JsonProperty
    private Employee oldValue;

    @JsonProperty
    private Employee sentValue;

    @JsonProperty
    private Employee mergingValue;

    public String getEventEntryType() {
        return eventEntryType;
    }

    public void setEventEntryType(String eventEntryType) {
        this.eventEntryType = eventEntryType;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Employee getOldValue() {
        return oldValue;
    }

    public void setOldValue(Employee oldValue) {
        this.oldValue = oldValue;
    }

    public Employee getSentValue() {
        return sentValue;
    }

    public void setSentValue(Employee sentValue) {
        this.sentValue = sentValue;
    }

    public Employee getMergingValue() {
        return mergingValue;
    }

    public void setMergingValue(Employee mergingValue) {
        this.mergingValue = mergingValue;
    }

    private String eventEntryType;

    @Override
    public String toString() {
        return "EmployeeChanges{" +
                "eventEntryType='" + eventEntryType + '\'' +
                ", member='" + member + '\'' +
                ", name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", oldValue=" + oldValue +
                ", sentValue=" + sentValue +
                ", mergingValue=" + mergingValue +
                '}';
    }
}
