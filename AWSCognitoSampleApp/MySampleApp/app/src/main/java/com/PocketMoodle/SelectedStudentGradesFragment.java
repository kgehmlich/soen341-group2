package com.PocketMoodle;

import java.util.*;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.PocketMoodle.Services.GradesServices;
import java.util.ArrayList;

/**
 * Created by Yufeng on 2017-04-09.
 */
public class SelectedStudentGradesFragment extends Fragment {


    public SelectedStudentGradesFragment() {
        // Required empty public constructor
    }
    private ListView gradesStudents; // ListView to display the grades of the student in the class
    private ListView gradesItemsStudents; // ListView to display the type of grades for each corresponding grades
    private TextView classTitle; // TextView to display the class title and student name
    private ArrayAdapter<Double> gradesAdapter; // Adapter to display list of grades on ListView
    private ArrayAdapter<String> gradesItemsAdapter; // Adapter to display list of grades Items on ListView
    private ArrayList<Double> gradesListFromDatabase; // List of grades for the class
    private ArrayList<String> gradesItemsListFromDatabase; // List of grades items for the class
    private List<GradesServices.Grade> gradesList = new ArrayList<GradesServices.Grade>(); // List of Grades Object which holds several information
    private String userID; // Holds the userID retrieved from the TAGradeFragment
    private String className; // Holds the class name retrieved from the TAGradeFragment
    private String studentName; // Holds the student's name retrieved from the TAGradeFragment

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_selected_student_grades, container, false);

        classTitle = (TextView) view.findViewById(R.id.selectedStudentGradesTitle);
        gradesListFromDatabase = new ArrayList<Double>(); // Initialize the list of grades to hold data from database
        gradesItemsListFromDatabase = new ArrayList<String>(); // Initialize the list of grades items to hold data from database

        // Find the ListView on xml file to display data
        gradesStudents = (ListView) view.findViewById(R.id.listOfSelectedStudentGrades);
        gradesItemsStudents = (ListView) view.findViewById(R.id.listOfSelectedStudentGradesItems);
        // Retrieve the class name from the ClassPageFragment
        Bundle bundle = getArguments();
        className =  bundle.getString("className");
        userID = bundle.getString("userID");
        studentName = bundle.getString("studentName");

        classTitle.setText("Grades for " + studentName);


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
                gradesList = gS.GetAllGradesInClassForOneUser(className,userID);
            }
        };

        Thread threadGetGrades = new Thread(runnable);

        threadGetGrades.start();
        while (threadGetGrades.isAlive()){
            // Give the method some time to get all the grades for the class
        }

        // Sets used to store temporal data
        ArrayList<Double> setOfGrades = new ArrayList<>();
        ArrayList<String> setOfGradesItems = new ArrayList<>();

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
