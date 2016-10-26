package com.example.mahmoud.healthtag;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Mahmoud on 8/26/2016.
 */
public class SpecialtyFragment extends Fragment {
    static Activity context;
    Button heartBtn, surgeonBtn, eyesBtn, kidsBtn, dentistBtn;

    public SpecialtyFragment() {
        /* Required empty public constructor */
    }

    /**
     * Create fragment and pass bundle with data as it's arguments
     * Right now there are not arguments...but eventually there will be.
     */
    public static SpecialtyFragment newInstance() {
        SpecialtyFragment fragment = new SpecialtyFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_specialty, container, false);
        initializeScreen(rootView);
        context = getActivity();

        heartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.SPECIALTY = "Heart";
                MainActivity.switchTab(0);
            }
        });
        surgeonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.SPECIALTY = "Surgeon";
                MainActivity.switchTab(0);
            }
        });
        eyesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.SPECIALTY = "Eyes";
                MainActivity.switchTab(0);
            }
        });
        kidsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.SPECIALTY = "Kids";
                MainActivity.switchTab(0);
            }
        });
        dentistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.SPECIALTY = "Dentist";
                MainActivity.switchTab(0);
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
        heartBtn = (Button) rootView.findViewById(R.id.btn_heart);
        surgeonBtn = (Button) rootView.findViewById(R.id.btn_surgeon);
        eyesBtn = (Button) rootView.findViewById(R.id.btn_eyes);
        kidsBtn = (Button) rootView.findViewById(R.id.btn_kids);
        dentistBtn = (Button) rootView.findViewById(R.id.btn_dentist);
    }

}
