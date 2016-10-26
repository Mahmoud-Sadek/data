package com.example.mahmoud.healthtag;

/**
 * Created by Mahmoud on 8/21/2016.
 */
public class Doctor {
    private String email, password, doctorName, address, specialty, identificationNumber;

    public Doctor(String email, String password, String doctorName, String address, String specialty, String identificationNumber) {
        this.email = email;
        this.password = password;
        this.doctorName = doctorName;
        this.address = address;
        this.specialty = specialty;
        this.identificationNumber = identificationNumber;
    }

    public Doctor() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getAddress() {
        return address;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }
}
