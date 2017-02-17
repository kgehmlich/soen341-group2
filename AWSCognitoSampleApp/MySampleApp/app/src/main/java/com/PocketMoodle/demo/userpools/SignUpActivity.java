//
// Copyright 2017 Amazon.com, Inc. or its affiliates (Amazon). All Rights Reserved.
//
// Code generated by AWS Mobile Hub. Amazon gives unlimited permission to 
// copy, distribute and modify it.
//
// Source code generated from template: aws-my-sample-app-android v0.14
//
package com.PocketMoodle.demo.userpools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.amazonaws.mobile.user.signin.CognitoUserPoolsSignInProvider;
import com.PocketMoodle.R;
import com.PocketMoodle.util.ViewHelper;

/**
 * Activity to prompt for account sign up information.
 */
public class SignUpActivity extends Activity {
    /** Log tag. */
    private static final String LOG_TAG = SignUpActivity.class.getSimpleName();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    /**
     * Retrieve input and return to caller.
     * @param view the Android View
     */
    public void signUp(final View view) {
        final String username = ViewHelper.getStringValue(this, R.id.signup_username);
        final String password = ViewHelper.getStringValue(this, R.id.signup_password);
        final String givenName = ViewHelper.getStringValue(this, R.id.signup_given_name);
        final String email = ViewHelper.getStringValue(this, R.id.signup_email);
        final String phone = ViewHelper.getStringValue(this, R.id.signup_phone);

        Log.d(LOG_TAG, "username = " + username);
        Log.d(LOG_TAG, "given_name = " + givenName);
        Log.d(LOG_TAG, "email = " + email);
        Log.d(LOG_TAG, "phone = " + phone);

        final Intent intent = new Intent();
        intent.putExtra(CognitoUserPoolsSignInProvider.AttributeKeys.USERNAME, username);
        intent.putExtra(CognitoUserPoolsSignInProvider.AttributeKeys.PASSWORD, password);
        intent.putExtra(CognitoUserPoolsSignInProvider.AttributeKeys.GIVEN_NAME, givenName);
        intent.putExtra(CognitoUserPoolsSignInProvider.AttributeKeys.EMAIL_ADDRESS, email);
        intent.putExtra(CognitoUserPoolsSignInProvider.AttributeKeys.PHONE_NUMBER, phone);

        setResult(RESULT_OK, intent);

        finish();
    }
}
