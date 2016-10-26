package com.example.mahmoud.healthtag;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mahmoud.healthtag.manageUser.manageUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class MainActivity extends AppCompatActivity implements MaterialTabListener {

    private MaterialTabHost tabHost;
    private static ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                /* Get mEncodedEmail and mProvider from SharedPreferences, use null as default value */
        Constants.user = sp.getString(Constants.KEY_USER_ID, null);
        tabHost = (MaterialTabHost) this.findViewById(R.id.materialTabHost);
        viewPager = (ViewPager) this.findViewById(R.id.pager);
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // when user do a swipe the selected tab change
                tabHost.setSelectedNavigationItem(position);
            }
        });

        // insert all tabs from pagerAdapter data
        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
//                            .setText(pagerAdapter.getPageTitle(i))
                            .setIcon(pagerAdapter.getIcon(i))
                            .setTabListener(this)
            );
        }
        if (Constants.userType.equals(Constants.FIREBASE_LOCATION_DOCTOR)){
            database = FirebaseDatabase.getInstance();
            DatabaseReference patient = database.getReference(Constants.FIREBASE_LOCATION_DOCTOR);
            patient.child(Constants.user).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    Doctor doctor = snapshot.getValue(Doctor.class);
                    if (doctor != null) {
                        Constants.DOCTOR_NAME = doctor.getDoctorName();
                        Constants.DOCTOR_SPECIALTY = doctor.getSpecialty();
                        Constants.SPECIALTY = doctor.getSpecialty();
                        PatientRojestsFragment.complete();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Error Loading Doctor data", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }else {
            PatientRojestsFragment.complete();
        }

    }

    public static void switchTab(int tab){
        viewPager.setCurrentItem(tab);
    }
    @Override
    public void onTabSelected(MaterialTab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {
        int icons[] = {R.drawable.ic_action_home, R.drawable.ic_action_personal, R.drawable.specialty};

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            /**
             * Set fragment to different fragments depending on position in ViewPager
             */
            switch (position) {
                case 0:
                    fragment = PatientRojestsFragment.newInstance();
                    break;
                case 1:
                    fragment = PatientInfoFragment.newInstance();
                    break;
                case 2:
                    fragment = SpecialtyFragment.newInstance();
                    break;
                default:
                    fragment = PatientRojestsFragment.newInstance();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        private Drawable getIcon(int position) {
            return getResources().getDrawable(icons[position]);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_scanNewPatient) {
            startActivity(new Intent(MainActivity.this, ScanPatientActivity.class));
            finish();
            return true;
        }
        if (id == R.id.action_settings){
            startActivity(new Intent(MainActivity.this, manageUser.class));
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

}