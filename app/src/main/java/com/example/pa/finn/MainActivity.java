package com.example.pa.finn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.pa.finn.fragments.HomeFragment;
import com.example.pa.finn.fragments.LocateFragment;
import com.example.pa.finn.fragments.ProfileFragment;
import com.example.pa.finn.fragments.SettingsFragment;
import com.example.pa.finn.fragments.ShopFragment;
import com.example.pa.finn.fragments.subfragments.DoctorFragment;
import com.example.pa.finn.fragments.subfragments.HospitalFragment;
import com.example.pa.finn.fragments.subfragments.MedicineFragment;
import com.example.pa.finn.models.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("user");
    Users user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomeFragment()).commit();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(Users.class);
                Log.d("TAG", "onDataChange: " + user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onMainActivityClickListener(View view) {
        switch (view.getId()) {
            case R.id.ivSettings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new SettingsFragment()).commit();
                break;
            case R.id.llProfile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ProfileFragment()).commit();
                break;
            case R.id.llLocate:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new LocateFragment()).commit();
                break;
            case R.id.llHome:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomeFragment()).commit();
                break;
            case R.id.llShop:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ShopFragment()).commit();
                break;
        }
    }

    public void onHomeFragmentClickListener(View view) {
        switch (view.getId()) {
            case R.id.llDoctors:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new DoctorFragment()).commit();
                break;
            case R.id.llMedicines:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new MedicineFragment()).commit();
                break;
            case R.id.llHospitals:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HospitalFragment()).commit();
                break;
            case R.id.llGallery:
                startActivity(new Intent(this,MlAi.class));
                //Intent intent = new Intent(Intent.ACTION_VIEW);
                //intent.setType("image/*");
                //startActivity(intent);
                break;
        }
    }
}
