package com.wan.gitlabrunnerciwebhook.model;

public class DingTalkRequest {
    public DingTalkRequest(String content) {
        this.msgtype = "text";
        this.text = new DingTalkText(content);
    }

    private String msgtype;

    private DingTalkText text;


    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }
}

