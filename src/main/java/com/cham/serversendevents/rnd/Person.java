package com.cham.serversendevents.rnd;

import java.io.Serializable;

public class Person implements Serializable {

    private String name;
    private String address;
    private String version;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", version='" + version + '\'' +
                '}';
    }

    public Person(String name, String address, String version) {
        this.name = name;
        this.address = address;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


}
