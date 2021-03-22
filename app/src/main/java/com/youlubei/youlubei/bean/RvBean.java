package com.youlubei.youlubei.bean;

public class RvBean {
    private String title;
    private String content;

    public RvBean( String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
