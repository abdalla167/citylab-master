package com.medical.citylap.modles;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Resultss {
    @SerializedName("files")
    private List<String> mFiles;
    @SerializedName("mediaType")
    private Long mMediaType;
    @SerializedName("notes")
    private String mNotes;
    @SerializedName("phoneNumber")
    private String mPhoneNumber;
    @SerializedName("resultId")
    private Long mResultId;





    public List<String> getFiles() {
        return mFiles;
    }

    public void setFiles(List<String> files) {
        mFiles = files;
    }

    public Long getMediaType() {
        return mMediaType;
    }

    public void setMediaType(Long mediaType) {
        mMediaType = mediaType;
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String notes) {
        mNotes = notes;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public Long getResultId() {
        return mResultId;
    }

    public void setResultId(Long resultId) {
        mResultId = resultId;
    }
}
