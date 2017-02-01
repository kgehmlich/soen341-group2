package com.example.kevin.createaccount;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class UserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        final EditText upAge = (android.widget.EditText) findViewById(R.id.upAge);
        final EditText upName = (EditText) findViewById(R.id.upName);
        final EditText upUsername = (EditText) findViewById(R.id.upUsername);
        final EditText upDegree = (EditText) findViewById(R.id.upDegree);
        final TextView UserProfile = (TextView) findViewById(R.id.upTextview);
    }
}
