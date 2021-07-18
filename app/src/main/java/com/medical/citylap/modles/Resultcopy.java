package com.medical.citylap.modles;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Resultcopy {

    private boolean expand;

    public Resultcopy() {

        this.expand=false;
    }


    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }



}
