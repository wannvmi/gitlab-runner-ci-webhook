package com.wan.gitlabrunnerciwebhook.model;

public class DingTalkText {
    public DingTalkText(String title, String text) {
        this.title = title;
        this.text = text;
    }

    private String title;
    private String text;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
