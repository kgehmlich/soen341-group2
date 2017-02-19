package com.PocketMoodle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.PocketMoodle.Services.GetAllClass;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private static final String TAG = "FromHomeFragment";
    public static List<String> ListOfClassUserIsIn = new ArrayList<String>();
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /**
         * ListOfClassIsIn is a list of all class the user is registered in...
         */
        Runnable runnable = new Runnable() {
            public void run() {
                GetAllClass GetClassForUser = new GetAllClass();
                ListOfClassUserIsIn = GetClassForUser.GetAllClassRegisteredIn();


            }
        };
        Thread ThreadGetClassForAUser = new Thread(runnable);
        try{
            ThreadGetClassForAUser.start();
        }
        catch (Exception exp){
            Log.e(TAG, exp.getMessage().toString());
        }


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

}
