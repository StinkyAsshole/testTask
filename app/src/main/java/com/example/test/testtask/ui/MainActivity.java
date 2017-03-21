package com.example.test.testtask.ui;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.test.testtask.R;

public class MainActivity extends AppCompatActivity {
    public static final String BACKSTACK_NAME= "users_back_stack";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, UserListFragment.getInstance(), UserListFragment.FRAGMENT_TAG)
                .addToBackStack(BACKSTACK_NAME)
                .commit();
    }

    @Override
    public void onBackPressed(){
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 1){
            finish();
        } else if (count > 1){
            getSupportFragmentManager().popBackStack();
        }
    }
}
