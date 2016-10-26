package com.example.mahmoud.healthtag;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.piotrek.customspinner.CustomSpinner;

public class SignupActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword, inputConfPassword ,inputDoctorName, inputAddress, inputIdentificationNumber;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private CustomSpinner inputSpecialty;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    String email, password, confPassword, doctorName, address, specialty, identificationNumber;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        inputConfPassword = (EditText) findViewById(R.id.confirm_password);
        inputDoctorName = (EditText) findViewById(R.id.doctorName);
        inputAddress = (EditText) findViewById(R.id.address);
        inputSpecialty = (CustomSpinner) findViewById(R.id.specialty_spinner);
        inputIdentificationNumber = (EditText) findViewById(R.id.identificationNumber);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        String[] types = getResources().getStringArray(R.array.specialty_array);
        inputSpecialty.initializeStringValues(types, "your Specialty");
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = inputEmail.getText().toString().trim();
                password = inputPassword.getText().toString().trim();
                confPassword = inputConfPassword.getText().toString().trim();
                doctorName = inputDoctorName.getText().toString().trim();
                address = inputAddress.getText().toString().trim();
                specialty = String.valueOf(inputSpecialty.getSelectedItem());
                identificationNumber = inputIdentificationNumber.getText().toString().trim();

                if (TextUtils.isEmpty(doctorName)) {
                    Toast.makeText(getApplicationContext(), "Enter Doctor Name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(address)) {
                    Toast.makeText(getApplicationContext(), "Enter address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(specialty)) {
                    Toast.makeText(getApplicationContext(), "Enter specialty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(identificationNumber)) {
                    Toast.makeText(getApplicationContext(), "Enter Identification Number!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(confPassword)) {
                    Toast.makeText(getApplicationContext(), "Enter Confirm password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!confPassword.equals(password)){
                    Toast.makeText(getApplicationContext(), "Not Match password!", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                newUser();
            }
        });
    }

    private void newUser() {
        progressBar.setVisibility(View.VISIBLE);
        //create user
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(SignupActivity.this, "createUserWithEmail:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        try {
                            createUserInFirebaseHelper(auth.getCurrentUser().getUid());
                        } catch (Exception e) {
                            Log.d("Error", "Errroror On cancel" + e);
                            Toast.makeText(SignupActivity.this, "Error" + e, Toast.LENGTH_SHORT).show();
                        }
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Constants.userType = Constants.FIREBASE_LOCATION_DOCTOR;
                            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor spe = sp.edit();
                            spe.putString(Constants.KEY_USER_ID, auth.getCurrentUser().getUid()).apply();
                            startActivity(new Intent(SignupActivity.this, ScanPatientActivity.class));
                            finish();
                        }
                    }
                });


    }

    /**
     * Creates a new user in Firebase from the Java POJO
     */
    private void createUserInFirebaseHelper(final String uid) {

        Doctor doctor = new Doctor(email, password, doctorName, address, specialty, identificationNumber);
        mDatabase.child(Constants.FIREBASE_LOCATION_DOCTOR).child(uid).setValue(doctor)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "New Doctor Add Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignupActivity.this, "Error : post not add 🙁 ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}

