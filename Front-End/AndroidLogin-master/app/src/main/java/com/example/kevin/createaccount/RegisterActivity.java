package com.example.kevin.createaccount;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText rAge = (EditText) findViewById(R.id.rAge);
        final EditText rName = (EditText) findViewById(R.id.rName);
        final EditText rUsername = (EditText) findViewById(R.id.rUsername);
        final EditText rpassword = (EditText) findViewById(R.id.rPassword);
        final EditText rDegree = (EditText) findViewById(R.id.rDegree);
        final Button rbRegister = (Button) findViewById(R.id.rbRegister);

        rbRegister.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              final String name = rName.getText().toString();
                                              final String username = rUsername.getText().toString();
                                              final int age = Integer.parseInt(rAge.getText().toString());
                                              final String degree = rDegree.getText().toString();
                                              final String password = rpassword.getText().toString();

                                              Response.Listener<String> responseListener = new Response.Listener<String>() {

                                                  @Override
                                                  public void onResponse(String response) {
                                                      try {
                                                          JSONObject jsonResponse = new JSONObject(response);
                                                          boolean success = jsonResponse.getBoolean("success");

                                                          if (success) {
                                                              Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                              RegisterActivity.this.startActivity(intent);
                                                          }else{
                                                              AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                                              builder.setMessage("Registration Failed")
                                                                      .setNegativeButton("Retry",null)
                                                                      .create()
                                                                      .show();
                                                          }
                                                      } catch (JSONException e) {
                                                          e.printStackTrace();
                                                      }
                                                  }
                                              };

                                              RegisterRequest registerRequest = new RegisterRequest(name, username, age, degree, password, responseListener);
                                              RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                                          }

                                      }
        );
    }
}