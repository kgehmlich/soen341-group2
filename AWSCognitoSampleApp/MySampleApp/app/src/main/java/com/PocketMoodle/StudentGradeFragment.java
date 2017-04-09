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

    // Grades Items means the title of the grades ---> e.g (Assignment,Exam, Quiz, etc )

    private ListView gradesStudents; // ListView to display the grades of the student in the class
    private ListView gradesItemsStudents; // ListView to display the type of grades for each corresponding grades
    private ArrayAdapter<Double> gradesAdapter; // Adapter to display list of grades on ListView
    private ArrayAdapter<String> gradesItemsAdapter; // Adapter to display list of grades Items on ListView
    private ArrayList<Double> gradesListFromDatabase; // List of grades for the class
    private ArrayList<String> gradesItemsListFromDatabase; // List of grades items for the class
    private List<GradesServices.Grade> gradesList = new ArrayList<GradesServices.Grade>(); // List of Grades Object which holds several information
    // User ID for the current user, needed for the GetAllGradesInClassForOneUser() method
    final String userId = AWSMobileClient.defaultMobileClient().getIdentityManager().getCachedUserID().toString();
    // Holds the class name retrieved from the ClassPageFragment
    private String className;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grades_student, container, false);

        gradesListFromDatabase = new ArrayList<Double>(); // Initialize the list of grades to hold data from database
        gradesItemsListFromDatabase = new ArrayList<String>(); // Initialize the list of grades items to hold data from database

        // Find the ListView on xml file to display data
        gradesStudents = (ListView) view.findViewById(R.id.listOfGrades);
        gradesItemsStudents = (ListView) view.findViewById(R.id.listOfGradesItems);
        // Retrieve the class name from the ClassPageFragment
        Bundle bundle = getArguments();
        className =  bundle.getString("className");

        // Set adapter with our list and layout
        gradesAdapter = new ArrayAdapter<Double>(getActivity(), android.R.layout.simple_list_item_1, gradesListFromDatabase);
        gradesStudents.setAdapter(gradesAdapter);
        gradesItemsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, gradesItemsListFromDatabase);
        gradesItemsStudents.setAdapter(gradesItemsAdapter);
        // Get the newest data using the helper method
        updateGradesInfo();

        return view;

    }

    // Helper method that retrieves and updates our lists with all the grades and grades titles in the database
    private void updateGradesInfo(){

        // Retrieve the list of Grade objects from the database by calling the specific API method
        Runnable runnable = new Runnable() {
            public void run() {
                GradesServices gS = new GradesServices();
                gradesList = gS.GetAllGradesInClassForOneUser(className,userId);
            }
        };

        Thread threadGetGrades = new Thread(runnable);

        threadGetGrades.start();
        while (threadGetGrades.isAlive()){
            // Give the method some time to get all the grades for the class
        }

        // Sets used to store temporal data
        Set<Double> setOfGrades = new HashSet<>();
        Set<String> setOfGradesItems = new HashSet<>();

        // Loop which will put all grades and titles into the sets created above
        for(int gradesCount = 0; gradesCount < gradesList.size(); gradesCount++ ) {
            setOfGrades.add(gradesList.get(gradesCount).getGradeForUser());
            setOfGradesItems.add(gradesList.get(gradesCount).getTitleForGrade());
        }

        // Clear the old list and update with new data
        gradesListFromDatabase.clear();
        gradesListFromDatabase.addAll(setOfGrades);
        gradesAdapter.notifyDataSetChanged();

        gradesItemsListFromDatabase.clear();
        gradesItemsListFromDatabase.addAll(setOfGradesItems);
        gradesItemsAdapter.notifyDataSetChanged();
    }

}
