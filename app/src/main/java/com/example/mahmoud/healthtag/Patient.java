package com.example.mahmoud.healthtag;

/**
 * Created by Mahmoud on 8/21/2016.
 */
public class Patient {
    private String patientName;
    private String address;
    private String patientNID;
    private String phoneNum;
    private String relativePhoneNum;
    private String bloodType;
    private String serialNum;

    public Patient(String patientName, String address, String patientNID, String phoneNum, String relativePhoneNum, String bloodType, String serialNum) {
        this.patientName = patientName;
        this.address = address;
        this.patientNID = patientNID;
        this.phoneNum = phoneNum;
        this.relativePhoneNum = relativePhoneNum;
        this.bloodType = bloodType;
        this.serialNum = serialNum;
    }

    public Patient() {
    }

    public String getPatientName() {
        return patientName;
    }

    public String getAddress() {
        return address;
    }

    public String getPatientNID() {
        return patientNID;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getRelativePhoneNum() {
        return relativePhoneNum;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getSerialNum() {
        return serialNum;
    }
}
