package com.example.mahmoud.healthtag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class PatientRojestsFragment extends Fragment {
    private static ListView mListView;
    static Activity context;
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static String timeStampCreated = "";
    Spinner spinMonth, spinYear;
    static List<Rojet> rojetList;

    /**
     * Create fragment and pass bundle with data as its' arguments
     */
    public static PatientRojestsFragment newInstance() {
        PatientRojestsFragment fragment = new PatientRojestsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public PatientRojestsFragment() {
        /* Required empty public constructor*/
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /* Inflate the layout for this fragment */
        View rootView = inflater.inflate(R.layout.fragment_patient_activities, container, false);
        context = getActivity();
        /**
         * Link layout elements from XML and setup the toolbar
         */

        initializeScreen(rootView);
        spinYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                timeStampCreated = (adapterView.getItemAtPosition(i)+","+spinMonth.getSelectedItem());
                complete();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                timeStampCreated = (spinYear.getSelectedItem()+","+adapterView.getItemAtPosition(i));
                complete();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ShowRojetActivity.rojet = rojetList.get(i);
                startActivity(new Intent(getActivity(), ShowRojetActivity.class));
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void initializeScreen(View rootView) {
        mListView = (ListView) rootView.findViewById(R.id.list_view_meals_list);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constants.DOCTOR_SPECIALTY.equals(Constants.SPECIALTY)) {
                    startActivity(new Intent(getActivity(), AddRojectActivity.class));
                }else {
                    Toast.makeText(getActivity(), "NOT You Specialty!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (Constants.userType.equals(Constants.FIREBASE_LOCATION_PATIENT)){
            fab.setVisibility(View.GONE);
        }
        int i;
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (i = 2014; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,
                years);
        spinYear = (Spinner)rootView.findViewById(R.id.year_spiner);
        spinYear.setAdapter(adapter);
        ArrayAdapter<String> adapterMonth = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                Constants.MONTHS);

        spinMonth = (Spinner)rootView.findViewById(R.id.month_spiner);
        spinMonth.setAdapter(adapterMonth);
        spinYear.setSelection(years.size()-1);
        spinMonth.setSelection(Calendar.getInstance().get(Calendar.MONTH));
    }

    public static void complete(){if (timeStampCreated.equals("")){
            timeStampCreated = (Calendar.getInstance().get(Calendar.YEAR)+","+Calendar.getInstance().get(Calendar.MONTH));
        }
        DatabaseReference posts = database.getReference(Constants.FIREBASE_LOCATION_ROJET).child(Constants.PATIENT_ID).child(Constants.SPECIALTY);
        posts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                HashMap<String, Rojet> results = dataSnapshot.getValue(new GenericTypeIndicator<HashMap<String, Rojet>>() {});
                 if (results != null) {
                     rojetList = new ArrayList<Rojet>();
                     List<Rojet> rojets = new ArrayList<>(results.values());
                     if (rojets.get(0).getTimestampCreated() != null) {
                         for (int r = (rojets.size()-1); r>=0  ; r--) {
                             if (rojets.get(r).getTimestampCreated().equals(timeStampCreated)) {
                                 rojetList.add(rojets.get(r));
                             }
                         }
                     }
                    ActiveListAdapter mActiveListAdapter = new ActiveListAdapter(context, rojetList);
                    mListView.setAdapter(mActiveListAdapter);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}