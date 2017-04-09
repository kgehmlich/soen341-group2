package com.PocketMoodle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ArrayAdapter;




import java.util.*;

import com.PocketMoodle.Services.GradesServices;
import com.PocketMoodle.Services.GetAllClass;


/**
 * Created by John on 2017-04-07.
 */
public class AddGradeFragment extends Fragment {

    private List<GetAllClass.User> studentList = new ArrayList<GetAllClass.User>(); // List of all students in the class
    private ListView studentListView; // ListView to display students in the class
    private ArrayList<String> studentNameList; // Array containing just the names of the students
    private ArrayAdapter studentListAdapter; // Adapter for displaying list of students in ListView
    private String className;



    public AddGradeFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_grade, container, false);

        // Link ListView to XML
        studentListView = (ListView) view.findViewById(R.id.studentList);

        // Initialize student and student name ArrayLists and get class name from TA grade fragment
        studentNameList = new ArrayList<String>();
        Bundle tempBundle = getArguments();
        className = tempBundle.getString("className");


        // Initialize and attach adapter to ListView
        studentListAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, studentNameList);
        studentListView.setAdapter(studentListAdapter);

        // Populate student name list
        updateStudentList();
        return view;
    }


    private void updateStudentList(){

        Runnable runnable = new Runnable() {
            public void run() {
                GetAllClass GetClassList = new GetAllClass();
                studentList = GetClassList.GetAllUsersInAClass(className);
            }
        };

        Thread getStudentsThread = new Thread(runnable);
        getStudentsThread.start();

        // Wait for thread to get all student User objects in the class
        while(getStudentsThread.isAlive()){

        }

        // Set to get and store usernames of students
        Set<String> setOfStudentNames = new HashSet<>();
        for(int i=0; i<studentList.size(); i++){
            setOfStudentNames.add(studentList.get(i).getUsername());
        }

        // Clear old data and fill in new
        studentNameList.clear();
        studentNameList.addAll(setOfStudentNames);
        studentListAdapter.notifyDataSetChanged();
    }

}
