package com.example.sxj52.firstlearing;

/**
 * Created by sxj52 on 2017/1/15.
 */

public class Msg {
    public static final int TYPE_RE=0;
    public static final int TYPE_CO=1;
    private String context;
    private int type;

    public Msg(String context, int type) {
        this.context = context;
        this.type = type;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
