package com.example.predragmiljic.footballquiz.activities;


import android.os.Bundle;

import com.example.predragmiljic.footballquiz.R;
import com.example.predragmiljic.footballquiz.fragments.RegisterFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            RegisterFragment registerFragment = new RegisterFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.mainLayout, registerFragment, registerFragment.getTag()).commit();
        }
    }

    @Override
    public void onBackPressed() {

    }
}
