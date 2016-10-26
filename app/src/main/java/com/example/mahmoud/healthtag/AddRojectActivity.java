package com.example.mahmoud.healthtag;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;

public class AddRojectActivity extends AppCompatActivity {

    Button addBtn;
    EditText signsInput, diagnosisInput, investigationsInput, prescriptionInput;
    String signs, diagnosis, investigations, prescription, timeStampCreated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_roject);

        signsInput = (EditText) findViewById(R.id.signs_editText);
        diagnosisInput = (EditText) findViewById(R.id.diagnosis_editText);
        investigationsInput = (EditText) findViewById(R.id.investigations_editText);
        prescriptionInput = (EditText) findViewById(R.id.prescription_editText);
        addBtn = (Button) findViewById(R.id.addRojet_button);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constants.DOCTOR_SPECIALTY.equals(Constants.SPECIALTY)) {
                    signs = signsInput.getText().toString().trim();
                    diagnosis = diagnosisInput.getText().toString().trim();
                    investigations = investigationsInput.getText().toString().trim();
                    prescription = prescriptionInput.getText().toString().trim();

                    if (TextUtils.isEmpty(signs)) {
                        Toast.makeText(getApplicationContext(), "Enter signs!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(diagnosis)) {
                        Toast.makeText(getApplicationContext(), "Enter diagnosis!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(investigations)) {
                        Toast.makeText(getApplicationContext(), "Enter investigations!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(prescription)) {
                        Toast.makeText(getApplicationContext(), "Enter prescription!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    timeStampCreated = (Calendar.getInstance().get(Calendar.YEAR) + "," + Constants.MONTHS[Calendar.getInstance().get(Calendar.MONTH)]);
                    Rojet rojet = new Rojet(Constants.DOCTOR_SPECIALTY, Constants.DOCTOR_NAME, signs, diagnosis, investigations, prescription, timeStampCreated);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference rojetUrl = database.getReference(Constants.FIREBASE_LOCATION_ROJET).child(Constants.PATIENT_ID).child(Constants.DOCTOR_SPECIALTY);
                    rojetUrl.push().setValue(rojet)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(AddRojectActivity.this, "New Rojet Add Successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(AddRojectActivity.this, "Error : Rojet not add 🙁 ", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(AddRojectActivity.this, "Cant Add", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
