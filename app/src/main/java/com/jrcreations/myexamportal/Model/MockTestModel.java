package com.jrcreations.myexamportal.Model;

public class MockTestModel {

    String name;
    int marks,time,questions;

    public MockTestModel(String name, int marks, int time, int questions) {
        this.name = name;
        this.marks = marks;
        this.time = time;
        this.questions = questions;
    }

    public MockTestModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getQuestions() {
        return questions;
    }

    public void setQuestions(int questions) {
        this.questions = questions;
    }
}
