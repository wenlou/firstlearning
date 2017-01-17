package com.example.eastnews;

/**
 * Created by sxj52 on 2017/1/16.
 */

public class News {
    private String title;
    private String cont;
public News(){

}
    public News(String title, String cont) {
        this.title = title;
        this.cont = cont;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }
}
