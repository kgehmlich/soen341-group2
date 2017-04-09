package com.PocketMoodle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v4.app.FragmentTransaction;

import java.util.*;


import com.PocketMoodle.Services.GradesServices;

/**
 * Created by John on 2017-04-03.
 */

public class TAGradeFragment extends Fragment {

    private static final String TAG = "TAGradeFragment";
    Button openAddGrade;
    
    public TAGradeFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grades_ta, container, false);

        // Button to open the AddGrade fragment
        openAddGrade = (Button) view.findViewById(R.id.addGrades);
        openAddGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    // Fragment that will display the messages in the group
                    AddGradeFragment tempFragment = new AddGradeFragment();

                    // Start the new fragment and replace the current fragment with the new one
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_container, tempFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                } catch (Exception ex) {
                    // In case the open fails display error message in log
                    Log.d(TAG, "Error accessing Add Grades");
                }
            }
        });
        return view;
    }
}
