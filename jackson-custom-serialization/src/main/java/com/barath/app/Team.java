package com.barath.app;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Team {

    @JsonProperty(value = "teamName")
    private String name;

    public Team() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                '}';
    }
}
