package com.wan.gitlabrunnerciwebhook.model;

public class DingTalkRequest {
    public DingTalkRequest(String title, String text) {
        this.msgtype = "markdown";
        this.markdown = new DingTalkText(title, text);
    }

    private String msgtype;

    private DingTalkText markdown;

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public DingTalkText getMarkdown() {
        return markdown;
    }

    public void setMarkdown(DingTalkText markdown) {
        this.markdown = markdown;
    }
}

