package com.PocketMoodle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.support.v4.app.FragmentTransaction;
import android.widget.ListView;
import android.widget.TextView;
import com.PocketMoodle.Services.GradesServices;
import com.PocketMoodle.Services.GetAllClass;
import java.util.*;


/**
 * Created by John on 2017-04-03.
 */

public class TAGradeFragment extends Fragment {

    private static final String TAG = "TAGradeFragment";
    Button openAddGrade;

    public TAGradeFragment() {
        // Required empty public constructor
    }

    private ListView studentNames; // ListView to display the all the students in the class
    private ArrayAdapter<String> studentNamesAdapter; // Adapter to display list of student names on ListView
    private ArrayList<String> studentNamesListFromDatabase; // List of student names items for the class
    private ArrayList<String> userIdsListFromDatabase; // List of user id for the class
    private String className; // Holds the class name retrieved from the ClassPageFragment
    private List<GetAllClass.User> studentList = new ArrayList<GetAllClass.User>(); // List of User Object which holds several information
    // Data that would be pass to another fragment
    private String userID;
    private String studentName;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grades_ta, container, false);

        studentNamesListFromDatabase = new ArrayList<String>(); // Initialize the list of students to hold data from database
        userIdsListFromDatabase = new ArrayList<String>(); // Initialize the list of user ID to hold data from database
        studentNames = (ListView) view.findViewById(R.id.listOfStudents); // Link to the xml page
        studentNamesAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, studentNamesListFromDatabase); // Set up the adapter
        studentNames.setAdapter(studentNamesAdapter);
        // Retrieve the class name from the ClassPageFragment
        Bundle bundle = getArguments();
        className =  bundle.getString("className");
        updateStudentsInfo();

        studentNames.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            // When a student from the list is clicked jump to the selected student grades fragment
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                SelectedStudentGradesFragment selectedStudentGradesFragment = new SelectedStudentGradesFragment();
                //String currentName = (String) parent.getItemAtPosition(position);
                userID = userIdsListFromDatabase.get(position);
                studentName = studentNamesListFromDatabase.get(position);
                // Bundle to add arguments the fragment will need to function(like what a constructor does)
                Bundle bundle = new Bundle();
                bundle.putString("className", className);
                bundle.putString("userID", userID);
                bundle.putString("studentName", studentName);
                selectedStudentGradesFragment.setArguments(bundle);

                // Start the new fragment and replace the current fragment with the new one
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, selectedStudentGradesFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        // Button to open the AddGrade fragment
        openAddGrade = (Button) view.findViewById(R.id.addGrades);
        openAddGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    // Fragment that will display the messages in the group
                    AddGradeFragment tempFragment = new AddGradeFragment();

                    // Pass on arguments that the add grade fragment will need
                    Bundle bundle = new Bundle();
                    bundle.putString("className", className);
                    tempFragment.setArguments(bundle);

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

    // Helper method that retrieves and updates the lists students in the database
    private void updateStudentsInfo(){

        // Retrieve the list of Grade objects from the database by calling the specific API method
        Runnable runnable = new Runnable() {
            public void run() {
                GetAllClass classService = new GetAllClass();
                studentList = classService.GetAllUsersInAClass(className);
            }
        };

        Thread threadGetStudents = new Thread(runnable);

        threadGetStudents.start();
        while (threadGetStudents.isAlive()){
            // Give the method some time to get all the user in the class
        }

        // Sets used to store temporal data
        ArrayList<String> setOfStudentNames = new ArrayList<>();
        ArrayList<String> setOfUserIds = new ArrayList<>();

        // Loop which will put all student names into the sets created above
        for(int usersCount = 0; usersCount < studentList.size(); usersCount++ ) {

            // Make sure TAs are not in the list
            if(studentList.get(usersCount).getTaOrStu() != 1.0){
                setOfStudentNames.add(studentList.get(usersCount).getUsername());
                setOfUserIds.add(studentList.get(usersCount).getUserId());
            }
        }

        // Clear the old list and update with new data
        studentNamesListFromDatabase.clear();
        studentNamesListFromDatabase.addAll(setOfStudentNames);
        studentNamesAdapter.notifyDataSetChanged();

        userIdsListFromDatabase.clear();
        userIdsListFromDatabase.addAll(setOfUserIds);
    }
}
