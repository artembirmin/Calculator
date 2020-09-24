package com.example.models;

public class Calculator {
    String name;
    String content;
    String answer;

    public Calculator(String name, String content, String answer) {
        this.name = name;
        this.content = content;
        this.answer = answer;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getAnswer() {
        return answer;
    }
}
