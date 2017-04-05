package com.PocketMoodle;

import java.util.*;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.PocketMoodle.Services.GradesServices;
import com.amazonaws.mobile.AWSMobileClient;

import java.util.ArrayList;

/**
 * Created by John on 2017-04-03.
 */

public class StudentGradeFragment extends Fragment {

    public StudentGradeFragment() {
        // Required empty public constructor
    }

    private ListView gradesStudents;
    private ListView gradesItemsStudents;
    private ArrayAdapter<Double> gradesAdapter;
    private ArrayAdapter<String> gradesItemsAdapter;
    private ArrayList<Double> gradesListFromDatabase;
    private ArrayList<String> gradesItemsListFromDatabase;
    private List<GradesServices.Grade> gradesList = new ArrayList<GradesServices.Grade>();
    final String userId = AWSMobileClient.defaultMobileClient().getIdentityManager().getCachedUserID().toString();
    private String className;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grades_student, container, false);

        gradesListFromDatabase = new ArrayList<Double>();
        gradesItemsListFromDatabase = new ArrayList<String>();

        gradesStudents = (ListView) view.findViewById(R.id.listOfGrades);
        gradesItemsStudents = (ListView) view.findViewById(R.id.listOfGradesItems);
        Bundle bundle = getArguments();
        className =  bundle.getString("className");

        // Set adapter with our list and layout
        gradesAdapter = new ArrayAdapter<Double>(getActivity(), android.R.layout.simple_list_item_1, gradesListFromDatabase);
        gradesStudents.setAdapter(gradesAdapter);
        gradesItemsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, gradesItemsListFromDatabase);
        gradesItemsStudents.setAdapter(gradesItemsAdapter);

        updateGradesInfo();

        return view;

    }

    private void updateGradesInfo(){

        Runnable runnable = new Runnable() {
            public void run() {
                GradesServices gS = new GradesServices();
                gradesList = gS.GetAllGradesInClassForOneUser(className,userId);
            }
        };

        Thread threadGetGrades = new Thread(runnable);

        threadGetGrades.start();
        while (threadGetGrades.isAlive()){
            // Give the method some time to get all the announcements for class
        }

        Set<Double> setOfGrades = new HashSet<>();
        Set<String> setOfGradesItems = new HashSet<>();

        for(int gradesCount = 0; gradesCount < gradesList.size(); gradesCount++ ) {
            setOfGrades.add(gradesList.get(gradesCount).getGradeForUser());
            setOfGradesItems.add(gradesList.get(gradesCount).getTitleForGrade());
        }

        gradesListFromDatabase.clear();
        gradesListFromDatabase.addAll(setOfGrades);
        gradesAdapter.notifyDataSetChanged();

        gradesItemsListFromDatabase.clear();
        gradesItemsListFromDatabase.addAll(setOfGradesItems);
        gradesItemsAdapter.notifyDataSetChanged();
    }

}
