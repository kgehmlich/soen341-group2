package com.example.kevin.createaccount;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText lUsername = (EditText) findViewById(R.id.lUsername);
        final EditText lPasword = (EditText) findViewById(R.id.lPassword);

        final Button lbLogin = (Button) findViewById(R.id.lbLogin);
        final Button lbRegister = (Button) findViewById(R.id.lbRegister);

    }

    public void startRegister(View view){

        startActivity(new Intent(this,RegisterActivity.class));

    }
}
