package com.example.mahmoud.healthtag;



import java.util.HashMap;

/**
 * Created by Mahmoud on 8/22/2016.
 */
public class Rojet {
    private String specialty, doctorName, signs, diagnosis, investigations, prescription;
    String timestampCreated;


    public Rojet(String specialty, String doctorName, String signs, String diagnosis, String investigations, String prescription, String timestampCreated) {
        this.specialty = specialty;
        this.doctorName = doctorName;
        this.signs = signs;
        this.diagnosis = diagnosis;
        this.investigations = investigations;
        this.prescription = prescription;
        this.timestampCreated = timestampCreated;
    }

    public Rojet() {
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getSigns() {
        return signs;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getInvestigations() {
        return investigations;
    }

    public String getPrescription() {
        return prescription;
    }

    public String getTimestampCreated() {
        return timestampCreated;
    }
}
