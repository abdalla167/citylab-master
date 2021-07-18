
package com.medical.citylap.modles;

import java.util.List;

import com.google.gson.annotations.SerializedName;



public class ResultApi {

    @SerializedName("data")
    private List<Resultss> mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private Boolean mStatus;

    public List<Resultss> getData() {
        return mData;
    }

    public void setData(List<Resultss> data) {
        mData = data;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
