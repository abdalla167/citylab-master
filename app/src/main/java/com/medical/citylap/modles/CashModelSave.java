package com.medical.citylap.modles;

import java.util.List;

public class CashModelSave {
    int type;
    String path;
    int id;
    String nametest;

    public List<String> getFile() {
        return file;
    }

    public void setFile(List<String> file) {
        this.file = file;
    }

    List<String>file;

    public String getNametest() {
        return nametest;
    }

    public void setNametest(String nametest) {
        this.nametest = nametest;
    }

    public CashModelSave(int type, int id, String nametest, List<String> file) {
        this.type = type;
        this.id = id;
        this.nametest = nametest;
        this.file = file;
    }

    public CashModelSave(int type, String path, int id, String nametest) {
        this.type = type;
        this.path = path;
        this.id = id;
        this.nametest = nametest;
    }

    public CashModelSave(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CashModelSave(int type, String path, int id) {
        this.type = type;
        this.path = path;
        this.id=id;

    }
}
