package com.medical.citylap.modles;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Reservation {
    @SerializedName("name")
    String name;
    @SerializedName("phoneNumber")
    String phoneNumber;
    @SerializedName("age")
    String age;
    @SerializedName("reservationDate")
    String reservationDate;
    @SerializedName("type")
    int type;
    @SerializedName("address")
    String address;
    @SerializedName("buildingNo")
    String buildingNo;
    @SerializedName("floorNo")
    String floorNo;
    @SerializedName("file")
    String file;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }

    public String getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(String floorNo) {
        this.floorNo = floorNo;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @SerializedName("day")
    int day;
    @SerializedName("appartementNo")
     String appartementNo;

    public String getAppartementNo() {
        return appartementNo;
    }

    public void setAppartementNo(String appartementNo) {
        this.appartementNo = appartementNo;
    }


}
