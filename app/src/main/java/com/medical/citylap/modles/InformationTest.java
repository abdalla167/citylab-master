package com.medical.citylap.modles;

public class InformationTest {
    private  boolean expand=false;
    public String title;
    public String content;

    public InformationTest() {
        this.expand = false;

    }

    public boolean isExpand() {
        return expand;
    }

    public InformationTest(boolean expand, String title, String content) {
        this.expand = expand;
        this.title = title;
        this.content = content;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
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
