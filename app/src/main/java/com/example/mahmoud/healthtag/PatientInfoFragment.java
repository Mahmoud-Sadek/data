package com.example.mahmoud.healthtag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class PatientInfoFragment extends Fragment {
    static Activity context;
    FirebaseDatabase database;
    TextView patientName, address, NID, telephone, relativePhone, bloodType;
    EditText patientNameInput, addressInput, NIDInput, telephoneInput, relativePhoneInput, bloodTypeInput;
    private String nameStr, addressStr, NIDStr, telephoneStr, relativePhoneNumStr, bloodTypeStr;

    Button updateBtn, updateDoneBtn;
    View insertView, infoView;
    DatabaseReference patientURL;

    public PatientInfoFragment() {
        /* Required empty public constructor */
    }

    /**
     * Create fragment and pass bundle with data as it's arguments
     * Right now there are not arguments...but eventually there will be.
     */
    public static PatientInfoFragment newInstance() {
        PatientInfoFragment fragment = new PatientInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Initialize instance variables with data from bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /**
         * Initialize UI elements
         */
        View rootView = inflater.inflate(R.layout.fragment_patient_info, container, false);
        initializeScreen(rootView);
        context = getActivity();

        database = FirebaseDatabase.getInstance();
        patientURL = database.getReference(Constants.FIREBASE_LOCATION_PATIENT);
        patientURL.child(Constants.PATIENT_ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Patient patient1 = snapshot.getValue(Patient.class);
                if (patient1 != null) {
                    patientName.setText("Patient Name:- "+patient1.getPatientName());
                    address.setText("Address:- "+patient1.getAddress());
                    NID.setText("NID:- "+patient1.getPatientNID());
                    telephone.setText("telephone:- "+patient1.getPhoneNum());
                    relativePhone.setText("relative Phone:- "+patient1.getRelativePhoneNum());
                    bloodType.setText("Blood Type:- "+patient1.getBloodType());

                    patientNameInput.setText(patient1.getPatientName()+"");
                    addressInput.setText(patient1.getAddress()+"");
                    NIDInput.setText(patient1.getPatientNID()+"");
                    telephoneInput.setText(patient1.getPhoneNum()+"");
                    relativePhoneInput.setText(patient1.getRelativePhoneNum()+"");
                    bloodTypeInput.setText(patient1.getBloodType()+"");

                }
                else {
                    Toast.makeText(context, "Error Loading data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertView.setVisibility(View.VISIBLE);
                infoView.setVisibility(View.GONE);
            }
        });

        updateDoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameStr =patientNameInput .getText().toString().trim();
                addressStr = addressInput.getText().toString().trim();
                NIDStr = NIDInput.getText().toString().trim();
                telephoneStr = telephoneInput.getText().toString().trim();
                relativePhoneNumStr = relativePhoneInput.getText().toString().trim();
                bloodTypeStr = bloodTypeInput.getText().toString().trim();

                if (TextUtils.isEmpty(nameStr)) {
                    Toast.makeText(getActivity(), "Enter name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(NIDStr)) {
                    Toast.makeText(getActivity(), "Enter NID!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(telephoneStr)) {
                    Toast.makeText(getActivity(), "Enter telephone!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(addressStr)) {
                    Toast.makeText(getActivity(), "Enter address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(relativePhoneNumStr)) {
                    Toast.makeText(getActivity(), "Enter relativePhoneNum!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(bloodTypeStr)) {
                    Toast.makeText(getActivity(), "Enter bloodType !", Toast.LENGTH_SHORT).show();
                    return;
                }
                Patient patient = new Patient(nameStr, addressStr, NIDStr, telephoneStr, relativePhoneNumStr, bloodTypeStr, Constants.PATIENT_ID);
                patientURL.child(Constants.PATIENT_ID).setValue(patient)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    insertView.setVisibility(View.GONE);
                                    infoView.setVisibility(View.VISIBLE);
                                    Toast.makeText(getActivity(), "Patient Updated Successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "Error 🙁 ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        return rootView;
    }

    /**
     * Cleanup the adapter when activity is destroyed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    /**
     * Link list view from XML
     */
    private void initializeScreen(View rootView) {
        patientName = (TextView) rootView.findViewById(R.id.patientName);
        address = (TextView) rootView.findViewById(R.id.address);
        NID = (TextView) rootView.findViewById(R.id.NID);
        telephone = (TextView) rootView.findViewById(R.id.telephone);
        relativePhone = (TextView) rootView.findViewById(R.id.relativePhone);
        bloodType = (TextView) rootView.findViewById(R.id.bloodType);

        patientNameInput = (EditText) rootView.findViewById(R.id.input_patientName);
        addressInput = (EditText) rootView.findViewById(R.id.input_address);
        NIDInput = (EditText) rootView.findViewById(R.id.input_patientNID);
        telephoneInput = (EditText) rootView.findViewById(R.id.input_phoneNum);
        relativePhoneInput = (EditText) rootView.findViewById(R.id.relativePhoneNum);
        bloodTypeInput = (EditText) rootView.findViewById(R.id.input_bloodType);

        updateBtn = (Button) rootView.findViewById(R.id.btn_update);
        updateDoneBtn = (Button) rootView.findViewById(R.id.btn_updateDone);

        insertView = rootView.findViewById(R.id.insert_layout);
        infoView = rootView.findViewById(R.id.info_layout);
    }

}