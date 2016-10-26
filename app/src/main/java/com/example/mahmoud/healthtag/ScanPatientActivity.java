package com.example.mahmoud.healthtag;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ScanPatientActivity extends AppCompatActivity {

    private EditText inputPatientId;

    private ProgressBar progressBar;
    private Button btnScanCode;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_patient);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        inputPatientId = (EditText) findViewById(R.id.patientId);
        btnScanCode = (Button) findViewById(R.id.btn_scanCode);

        btnScanCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                Constants.userType = Constants.FIREBASE_LOCATION_DOCTOR;
                String id = inputPatientId.getText().toString().trim();
                if (TextUtils.isEmpty(id)) {
                    Toast.makeText(getApplicationContext(), "Enter ID !", Toast.LENGTH_SHORT).show();
                    return;
                }
                Constants.PATIENT_ID = id;
                database = FirebaseDatabase.getInstance();
                DatabaseReference patienturl = database.getReference(Constants.FIREBASE_LOCATION_PATIENT).child(Constants.PATIENT_ID);
                patienturl.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        Patient patient1 = dataSnapshot.getValue(Patient.class);
                        if (patient1 != null) {
                            startActivity(new Intent(ScanPatientActivity.this, MainActivity.class));
                            finish();
                        } else {
                            startActivity(new Intent(ScanPatientActivity.this, InsertPatientActivity.class));
                            finish();
                            Toast.makeText(ScanPatientActivity.this, "This ID Not in Data\nPlease Insert It", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Toast.makeText(ScanPatientActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
