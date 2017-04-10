package com.PocketMoodle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

import java.util.*;

import com.PocketMoodle.Services.GradesServices;
import com.PocketMoodle.Services.GetAllClass;
import com.amazonaws.AmazonClientException;


/**
 * Created by John on 2017-04-07.
 */
public class AddGradeFragment extends Fragment {

    private List<GetAllClass.User> studentList = new ArrayList<>(); // List of all students in the class
    private ArrayList<String> studentNameList = new ArrayList<>(); // Array containing just the names of the students
    private ArrayList<String> studentIdList = new ArrayList<>();
    public ArrayAdapter<String> studentListAdapter; // ArrayAdapter to link list of students to spinner
    private String studentChoice; // Student being graded
    private EditText titleChoiceField; // Field storing value of the grade item title
    private String titleChoice; // Title of grade item
    private EditText gradeValueField; // Field storing the value of the grade being assigned
    private double gradeValue; // Grade being assigned to student
    private String userID; // userid used to insert grade
    private String className; // class name passed on from TAGradeFragment
    private static final String TAG = "AddGradeFragment";

    // UI elements
    Spinner studentNameSpinner;
    Button submitGrade;

    public AddGradeFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_grade, container, false);

        // Link the submit grade button
        submitGrade = (Button) view.findViewById(R.id.submitGrade);

        // Retrieve class name from TAGradeFragment
        Bundle bundle = getArguments();
        className =  bundle.getString("className");


        // Link variables to XML fields
        titleChoiceField = (EditText) view.findViewById(R.id.gradeItemTitle);
        gradeValueField = (EditText) view.findViewById(R.id.gradeValue);

        // Add spinner to select student
        studentNameSpinner = (Spinner) view.findViewById(R.id.studentNameSpinner);
        studentListAdapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_spinner_dropdown_item, studentNameList);
        studentNameSpinner.setAdapter(studentListAdapter);

        // Add functionality to submit grade button
        submitGrade.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                titleChoice = titleChoiceField.getText().toString(); // Retrieve title of assignment from UI
                studentChoice = studentNameSpinner.getSelectedItem().toString(); // Retrieve name of student being graded from UI
                gradeValue = Double.parseDouble(gradeValueField.getText().toString()); // Retrieve value of grade being assigned from UI

                // Scan through student list to find student whose username was selected, then get their userid
                for(int usersCount=0; usersCount<studentNameList.size(); usersCount++){
                    if(studentNameList.get(usersCount).equals(studentChoice)){
                        userID = studentIdList.get(usersCount);
                    }
                }
                submitGrade(); // Call helper method to submit the grade
            }

        });
        // Populate student name list
        updateStudentList();
        return view;
    }

    // Helper method to retrieve list of students from database
    private void updateStudentList(){

        Runnable runnable = new Runnable() {
            public void run() {
                GetAllClass GetClassList = new GetAllClass();
                studentList = GetClassList.GetAllUsersInAClass(className);
            }
        };

        Thread getStudentsThread = new Thread(runnable);
        getStudentsThread.start();


        while(getStudentsThread.isAlive()){
            // Wait for thread to get all student User objects in the class
        }

        // Set to get and store usernames of students
        ArrayList<String> setOfStudentNames = new ArrayList<>();
        ArrayList<String> setOfStudentIds = new ArrayList<>();

        // Loop through and add students' usernames into the sets
        for(int usersCount=0; usersCount<studentList.size(); usersCount++){

            // Check that only students, and not TAs, are added
            if(studentList.get(usersCount).getTaOrStu() != 1.0){
                setOfStudentNames.add(studentList.get(usersCount).getUsername());
                setOfStudentIds.add(studentList.get(usersCount).getUserId());
            }
        }

        // Clear old data and fill in new
        studentNameList.clear();
        studentNameList.addAll(setOfStudentNames);
        studentListAdapter.notifyDataSetChanged();
        studentIdList.clear();
        studentIdList.addAll(setOfStudentIds);
    }

    // Helper method that creates thread to submit grade
    private void submitGrade(){

        Runnable runnable2 = new Runnable() {

            public void run() {
                GradesServices tempGrade = new GradesServices();
                tempGrade.InsertGrade(studentChoice, userID, className, titleChoice, gradeValue);
            }};

        try{
            Thread submitGradeThread = new Thread(runnable2);
            submitGradeThread.start();

            while(submitGradeThread.isAlive()){

            }
        } catch(AmazonClientException e){
            Toast failToast = Toast.makeText(getActivity(), "Error submitting grade", Toast.LENGTH_LONG);
            failToast.show();
            Log.e(TAG, e.getMessage());
        }
        Toast successToast = Toast.makeText(getActivity(), "Grade submitted", Toast.LENGTH_LONG);
        successToast.show();
    }

}
