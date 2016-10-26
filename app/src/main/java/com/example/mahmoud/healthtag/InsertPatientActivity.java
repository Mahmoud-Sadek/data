package com.example.mahmoud.healthtag;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InsertPatientActivity extends AppCompatActivity {

    private EditText inputName, inputAddress, inputNID, inputTelephone, inputRelativePhoneNum, inputBloodType;
    private Button btnAddPatient;
    private ProgressBar progressBar;
    private String name, address, NID, telephone, relativePhoneNum, bloodType;
    private DatabaseReference mDatabase;
    static String serialNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_patient);

        inputName = (EditText) findViewById(R.id.patientName);
        inputAddress = (EditText) findViewById(R.id.address);
        inputNID = (EditText) findViewById(R.id.patientNID);
        inputTelephone = (EditText) findViewById(R.id.phoneNum);
        inputRelativePhoneNum = (EditText) findViewById(R.id.relativePhoneNum);
        inputBloodType = (EditText) findViewById(R.id.bloodType);
        btnAddPatient = (Button) findViewById(R.id.addPatient_button);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_LOCATION_PATIENT);

        btnAddPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = inputName.getText().toString().trim();
                address = inputAddress.getText().toString().trim();
                NID = inputNID.getText().toString().trim();
                telephone = inputTelephone.getText().toString().trim();
                relativePhoneNum = inputRelativePhoneNum.getText().toString().trim();
                bloodType = inputBloodType.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Enter name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(NID)) {
                    Toast.makeText(getApplicationContext(), "Enter NID!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(telephone)) {
                    Toast.makeText(getApplicationContext(), "Enter telephone!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(address)) {
                    Toast.makeText(getApplicationContext(), "Enter address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(relativePhoneNum)) {
                    Toast.makeText(getApplicationContext(), "Enter relativePhoneNum!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(bloodType)) {
                    Toast.makeText(getApplicationContext(), "Enter bloodType !", Toast.LENGTH_SHORT).show();
                    return;
                }
                newUser();

            }
        });
    }

    private void newUser() {
        progressBar.setVisibility(View.VISIBLE);
        serialNum = Constants.PATIENT_ID;
        Patient patient = new Patient(name, address, NID, telephone, relativePhoneNum, bloodType,serialNum);
        mDatabase.child(Constants.PATIENT_ID).setValue(patient)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(InsertPatientActivity.this, MainActivity.class));
                            finish();
                            Toast.makeText(InsertPatientActivity.this, "New Patient Add Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(InsertPatientActivity.this, "Error : post not add 🙁 ", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }
}
