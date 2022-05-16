package com.project.memorybuzz.models;

public class MemoClass {
    public String topic,decription;

    public MemoClass(){

    }
    public MemoClass(String topic, String decription) {
        this.topic = topic;
        this.decription = decription;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }
}
