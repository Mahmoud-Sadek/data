package com.example.mahmoud.healthtag;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DoctorInfoActivity extends AppCompatActivity {

    FirebaseDatabase database;
    TextView doctorName, address, specialty, identificationNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_info);
        doctorName = (TextView) findViewById(R.id.doctorName);
        address = (TextView) findViewById(R.id.address);
        specialty = (TextView) findViewById(R.id.specialty);
        identificationNumber = (TextView) findViewById(R.id.identificationNumber);
        database = FirebaseDatabase.getInstance();
        DatabaseReference patient = database.getReference(Constants.FIREBASE_LOCATION_DOCTOR);
        patient.child(Constants.user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Doctor doctor = snapshot.getValue(Doctor.class);
                if (doctor != null) {
                    Toast.makeText(DoctorInfoActivity.this, doctor.getDoctorName()+"\n"+
                            doctor.getIdentificationNumber()+"\n"+
                            doctor.getAddress()+"\n"+
                            doctor.getSpecialty(), Toast.LENGTH_SHORT).show();
                    doctorName.setText("Doctor Name:- "+doctor.getDoctorName());
                    address.setText("Address:- "+doctor.getAddress());
                    specialty.setText("Specialty:- "+doctor.getSpecialty());
                    identificationNumber.setText("Identification Number:- "+doctor.getIdentificationNumber());
                }
                else {
                    Toast.makeText(DoctorInfoActivity.this, "Error Loading data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
