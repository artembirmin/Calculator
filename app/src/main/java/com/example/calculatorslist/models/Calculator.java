package com.example.calculatorslist.models;

public class Calculator {
    String name;
    String content;

    public Calculator(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }
}
