package com.PocketMoodle;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.PocketMoodle.Services.AnnounServices;
import com.PocketMoodle.Services.GetAllClass;
import com.amazonaws.mobile.AWSMobileClient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassPageFragment extends Fragment{

    private final AWSMobileClient awsMobileClient = AWSMobileClient.defaultMobileClient(); // To get user name of user

    private String className;
    private String TAOrStudent; // Will hold "TA" or "Student"
    private String userName;
    private TextView classTitle;
    private static final String TAG = "ClassPageFragment";

    // ****************************************************************************************
    // Document Variables
    // ****************************************************************************************

    Button openUploadDutton;
    Button openViewDocsDutton;

    // ****************************************************************************************
    // Discussion Group Variables
    // ****************************************************************************************

    Button openDiscussionGroup;

    // ****************************************************************************************
    // Announcement Variables
    // ****************************************************************************************

    // Announcement object for each announcement that holds the author, date, title, and description for an announcement
    private List<AnnounServices.Announcement> announcementList = new ArrayList<AnnounServices.Announcement>();

    private Button addAnnouncementTopic;    // Button to add an announcement topic for a class
    private Button removeAnnouncementTopic; // Button to remove an announcement topic for a class

    private ListView fragmentAnnouncementList; // ListView to display the list of announcement for a class
    private ArrayList<String> listOfAnnouncementsFromDatabase; // List of announcements for a class
    private ArrayAdapter<String> announcementListAdapter; // Adapter to display list of announcements on ListView
    private Spinner removeAnnouncementSpinner; // Spinner to display all the announcement for that class

    private Spinner changeClassSpinner; // Spinner to display registered classes
    private static List<String> registeredClasses = new ArrayList<String>(); // Array of classes that will contain the registered classes
    private static List<String> taClasses = new ArrayList<String>();

    public ClassPageFragment() {
        Runnable runnable = new Runnable() {
            public void run(){
                GetAllClass registered = new GetAllClass();
                registeredClasses = registered.GetAllClassRegisteredIn();

                taClasses = registered.GetAllClassYouAreTA();
            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();
        while (mythread.isAlive()){

        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class_page, container, false);

        // Retrieve the arguments passed by the calling class that is needed to display correct messages
        Bundle tempBundle = getArguments();
        className = tempBundle.getString("className");
        TAOrStudent = tempBundle.getString("TAOrStudent");

        // Getting the user's username
        userName = awsMobileClient.getIdentityManager().getUserName();

        classTitle = (TextView) view.findViewById(R.id.setclassTitle);
        classTitle.setText(className);

        // ****************************************************************************************
        // Beginning of Upload Document Code
        // ****************************************************************************************

        // Button to display file explorer beginning at root
        openUploadDutton = (Button)view.findViewById(R.id.openUploadDocument);
        openUploadDutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    // Fragment that will display the messages in the group
                    DocumentsFragment documentsFragment = new DocumentsFragment();

                    // Bundle to add arguments the fragment will need to function(like what a constructor does)
                    Bundle bundle = new Bundle();
                    bundle.putString("className", className);
                    bundle.putString("TAOrStudent", TAOrStudent);
                    documentsFragment.setArguments(bundle);

                    // Start the new fragment and replace the current fragment with the new one
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_container, documentsFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                } catch (Exception ex) {
                    // In case the open fails display error message in log
                    Log.d(TAG, "Error accessing Upload Document");
                }
            }
        });
        // Ending of Upload Document code

        // Beginning of drop down menu to change class
        //Creating selectOption array to store the available options from dropdown
        ArrayList<String> selectOption = new ArrayList<String>();
        //Adds classes to array list
        if(registeredClasses.size() > 0){
            selectOption.add("Change class");
            for(String r: registeredClasses){
                selectOption.add(r);
            }
        }

        //Spinner declaration
        changeClassSpinner = (Spinner) view.findViewById(R.id.change_class_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, selectOption);
        changeClassSpinner.setAdapter(adapter);

        //Behaviour of the changeClassSpinners' upon item selection
        changeClassSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ClassPageFragment classPageFragment = new ClassPageFragment();
                String className = parent.getItemAtPosition(position).toString();
                if (className.equals("Change class")) {

                } else {if (taClasses.contains(className)) { //Checks list of TA classes of users and compares to bundle className to determine if TA class selected
                    // Bundle to add arguments the fragment will need to function(like what a constructor does)
                    Bundle bundle = new Bundle();
                    bundle.putString("className", className);
                    bundle.putString("TAOrStudent", "TA"); // Sets TA user true, and gives TA priviledge to page
                    classPageFragment.setArguments(bundle);

//                 Start the new fragment and replace the current fragment with the new one
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_container, classPageFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    ((MainActivity) getActivity()).setActionBarTitle(className);
                } else {
                    // Bundle to add arguments the fragment will need to function(like what a constructor does)
                    Bundle bundle = new Bundle();
                    bundle.putString("className", className);
                    bundle.putString("TAOrStudent", "Student"); // Gives studen priviledge
                    classPageFragment.setArguments(bundle);

//                 Start the new fragment and replace the current fragment with the new one
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_container, classPageFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    ((MainActivity) getActivity()).setActionBarTitle(className);
                }
            }
        }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // ****************************************************************************************
        // Beginning of View Documents Code
        // ****************************************************************************************

        // Button to display file explorer beginning at root
        openViewDocsDutton = (Button)view.findViewById(R.id.openViewDocuments);
        openViewDocsDutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    // Fragment that will display the messages in the group
                    ViewDocumentsFragment viewDocumentsFragment = new ViewDocumentsFragment();

                    // Bundle to add arguments the fragment will need to function(like what a constructor does)
                    Bundle bundle = new Bundle();
                    bundle.putString("className", className);
                    bundle.putString("TAOrStudent", TAOrStudent);
                    viewDocumentsFragment.setArguments(bundle);

                    // Start the new fragment and replace the current fragment with the new one
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_container, viewDocumentsFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                } catch (Exception ex) {
                    // In case the open fails display error message in log
                    Log.d(TAG, "Error accessing View Documents");
                }
            }
        });
        // Ending of View Documents code


        // ****************************************************************************************
        // Beginning of Discussion Board Code
        // ****************************************************************************************

        // Button to display discussion board
        openDiscussionGroup = (Button)view.findViewById(R.id.openDiscussionBoard);
        openDiscussionGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // Fragment that will display the messages in the group
                    DiscussionGroupsFragment tempFragment = new DiscussionGroupsFragment();

                    // Bundle to add arguments the fragment will need to function(like what a constructor does)
                    Bundle bundle = new Bundle();
                    bundle.putString("className", className);
                    bundle.putString("TAOrStudent", TAOrStudent);
                    tempFragment.setArguments(bundle);

                    // Start the new fragment and replace the current fragment with the new one
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_container, tempFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                } catch (Exception ex) {
                    // In case the open fails display error message in log
                    Log.d(TAG, "Error accessing discussionBoard");
                }
            }
        });
        // Ending of Discussion Board Code

        // ****************************************************************************************
        // Beginning of Announcement Code
        // ****************************************************************************************

        listOfAnnouncementsFromDatabase = new ArrayList<String>(); // Create a new ArrayList to hold announcements

        // Find the buttons to add and remove an announcement
        addAnnouncementTopic    = (Button) view.findViewById(R.id.add_discussion_group);
        removeAnnouncementTopic = (Button) view.findViewById(R.id.remove_discussion_group);

        if(TAOrStudent.equals("Student")) {
            addAnnouncementTopic.setVisibility(View.INVISIBLE);
            removeAnnouncementTopic.setVisibility(View.INVISIBLE);
        }

        // Find the ListView to add the list of announcements
        fragmentAnnouncementList = (ListView) view.findViewById(R.id.list_of_announcements); // ListView to add announcement

        // Set adapter with our list and layout
        announcementListAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listOfAnnouncementsFromDatabase); // adapter for list of messages and layout
        fragmentAnnouncementList.setAdapter(announcementListAdapter);
        updateAnnouncementList(); // Retrieve the announcements for that class from the database

        // Create listener for add announcement button
        addAnnouncementTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Fragment that will display the messages in the groupAnnouncementFragment
                AddAnnouncementFragment tempFragment = new AddAnnouncementFragment();

                // Bundle to add arguments the fragment will need to function(like what a constructor does)
                Bundle bundle = new Bundle();
                bundle.putString("className", className);
                tempFragment.setArguments(bundle);

                // Start the new fragment and replace the current fragment with the new one
                // This is where the user can add the details for the announcement they want to add
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, tempFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        // Create listener for remove Announcement button
        removeAnnouncementTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create dialog to show a spinner with the list of announcements to remove
                AlertDialog.Builder removeDiscusionGroupDialog = new AlertDialog.Builder(getActivity());

                // Initialize the spinner that will hold the list of all announcements
                removeAnnouncementSpinner = new Spinner(getActivity());

                // Set adapter to inflate spinner with list of announcements
                removeAnnouncementSpinner.setAdapter(announcementListAdapter);

                // Set the title, message, and spinner to display on dialog message
                removeDiscusionGroupDialog.setTitle("Remove Discussion Group");
                removeDiscusionGroupDialog.setMessage("Select the discussion group you want to remove!");
                removeDiscusionGroupDialog.setView(removeAnnouncementSpinner);

                // Create a listener to close dialog if cancel is chosen
                removeDiscusionGroupDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });

                // Create a listener to remove the announcement chosen in spinner from the database
                //  through API call.
                removeDiscusionGroupDialog.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Make sure the announcement is in the spinner list.
                        if(removeAnnouncementSpinner.getSelectedItem()!= null) {

                            String removeAnnouncement = removeAnnouncementSpinner.getSelectedItem().toString();

                            // Find the chosen announcement by searching through all the announcements.
                            for(AnnounServices.Announcement singleAnnouncement : announcementList) {

                                if (singleAnnouncement.getAnnouTitle().equals(removeAnnouncement)) {

                                    // Get the announcement id of the annoucement to revove
                                    final String AnnouncementToRemoveId = singleAnnouncement.getAnnouID();

                                    Runnable runnable = new Runnable() {
                                        public void run() {

                                            AnnounServices aS = new AnnounServices();
                                            aS.GetAllAnnouncementsForClass(className);
                                            aS.RemoveAnAnnouncement(AnnouncementToRemoveId, className);
                                        }
                                    };

                                    Thread removeAnnouncementThread = new Thread(runnable);
                                    removeAnnouncementThread.start();
                                    while (removeAnnouncementThread.isAlive()){

                                    }
                                    updateAnnouncementList();
                                    Toast removeAnnouncementToast = Toast.makeText(getActivity(), "Announcement has been removed", Toast.LENGTH_LONG);
                                    removeAnnouncementToast.show();
                                }
                            }
                        }
                    }
                });

                removeDiscusionGroupDialog.show(); // Display dialog
            }
        });

        // Create a listener for the ListView on the ClassPageFragment to open the chosen announcement
        fragmentAnnouncementList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String title;       // Hold the title of the announcement
                String description; // Hold the description of the announce
                String date;        // Hold the date of the announcement
                String author;      // Hold the author of the announcement

                // Find the chosen announcement by searching through all the announcements.
                for(AnnounServices.Announcement eachAnnouncement : announcementList) {

                    // If we find the announcement object in the database display its values on
                    //  a new fragment.
                    if(eachAnnouncement.getAnnouTitle().equals(((TextView)view).getText().toString())) {

                        title       = eachAnnouncement.getAnnouTitle();   // Get the title of the announcement
                        description = eachAnnouncement.getAnnouMainObj(); // Get the description of the announcement
                        date        = eachAnnouncement.getAnnouDate();    // Get the date of the announcement
                        author      = eachAnnouncement.getAnnouAuthor();  // Get the author of the announcement

                        // Fragment that will display the messages in the group
                        DisplayAnnouncementFragment tempFragment = new DisplayAnnouncementFragment();

                        // Bundle to add arguments the fragment will need to function(like what a constructor does)
                        // It will be used to display the values of an announcement on a new fragment.
                        Bundle bundle = new Bundle();
                        bundle.putString("AnnouncementTitle", title);
                        bundle.putString("AnnouncementDescription", description);
                        bundle.putString("AnnouncementDate", date);
                        bundle.putString("AnnouncementAuthor", author);
                        tempFragment.setArguments(bundle);

                        // Start the new fragment and replace the current fragment with the new one
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, tempFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                }
            }
        });
        // Ending of Announcement Code

        return view;
    }

    public void onStart(){
        super.onStart();

    }

    // Helper method that retrieves and updates our list with all the announcements in the database
    private void updateAnnouncementList()
    {
        // Retrieve the list of announcement from the database by calling API
        Runnable runnable = new Runnable() {
            public void run() {
                AnnounServices aS = new AnnounServices();
                announcementList = aS.GetAllAnnouncementsForClass(className);
            }
        };

        Thread threadGetAnnouncement = new Thread(runnable);

        threadGetAnnouncement.start();
        while (threadGetAnnouncement.isAlive()){
            // Give the method some time to get all the announcements for class
        }

        Set<String> setOfAnnoucements = new HashSet<>(); // Store the list of announcements

        // Get the title of eah of the announcements for that class
        for(int announcementCount = 0; announcementCount < announcementList.size();++announcementCount ) {
            setOfAnnoucements.add(announcementList.get(announcementCount).getAnnouTitle());
        }

        // Clear the old list and update with new list of announcements
        listOfAnnouncementsFromDatabase.clear();
        listOfAnnouncementsFromDatabase.addAll(setOfAnnoucements);

        announcementListAdapter.notifyDataSetChanged(); // update the adapter of the spinner and listView
    }

}
