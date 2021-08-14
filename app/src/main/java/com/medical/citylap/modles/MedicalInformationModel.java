package com.medical.citylap.modles;

import java.io.Serializable;

public class MedicalInformationModel implements Serializable
{
String Image;
String hide;
String content;

    public MedicalInformationModel() {
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getHide() {
        return hide;
    }

    public void setHide(String hide) {
        this.hide = hide;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MedicalInformationModel(String image, String hide, String content) {
        Image = image;
        this.hide = hide;
        this.content = content;
    }
}
