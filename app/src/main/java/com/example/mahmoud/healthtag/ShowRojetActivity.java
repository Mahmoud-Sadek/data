package com.example.mahmoud.healthtag;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowRojetActivity extends AppCompatActivity {

    static Rojet rojet;
    TextView doctorName, specialty, sign, diagnosis, investigations, prescription;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_rojet);

        doctorName = (TextView) findViewById(R.id.doctorName);
        specialty = (TextView) findViewById(R.id.specialty);
        sign = (TextView) findViewById(R.id.sign);
        diagnosis = (TextView) findViewById(R.id.diagnosis);
        investigations = (TextView) findViewById(R.id.investigations);
        prescription = (TextView) findViewById(R.id.prescription);

        doctorName.setText("Doctor Name:- "+rojet.getDoctorName());
        specialty.setText("specialty:- "+rojet.getSpecialty());
        sign.setText("sign:- "+rojet.getSigns());
        diagnosis.setText("diagnosis:- "+rojet.getDiagnosis());
        investigations.setText("investigations:- "+rojet.getInvestigations());
        prescription.setText("prescription:- "+rojet.getPrescription());

        back = (Button) findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }
}
