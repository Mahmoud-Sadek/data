package com.example.mahmoud.healthtag;

import java.text.SimpleDateFormat;

/**
 * Created by Mahmoud on 6/22/2016.
 */
public class Constants {

    public static final String FIREBASE_LOCATION_ROJET = "rojet";
    public static final String FIREBASE_LOCATION_PATIENT = "patient";
    public static final String FIREBASE_LOCATION_DOCTOR = "doctor";
    public static final String FIREBASE_PROPERTY_TIMESTAMP = "timestamp";

    public static String user="";
    public static String userType="";
    public static String PATIENT_ID = "";
    public static String SPECIALTY = "";
    public static String DOCTOR_SPECIALTY = "";
    public static String DOCTOR_NAME ="";
    public static final String KEY_USER_ID = "user_id";

    public static final String[] MONTHS = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    }
