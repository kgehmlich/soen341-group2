package com.PocketMoodle;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.PocketMoodle.DiscussionBoard.MyDatabaseChat;
import com.amazonaws.mobile.AWSMobileClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */

public class DiscussionGroupsFragment extends Fragment {

    private MyDatabaseChat myDatabaseChat; // Class that has method to add Discussion groups
    private final AWSMobileClient awsMobileClient = AWSMobileClient.defaultMobileClient(); // To get user details

    private View view;                   // Store the inflater for fragment
    private Button addDiscusionTopic;    // Store link to add button on fragment
    private Button removeDiscusionTopic; // Store link to remove button on
    private EditText discussionTopic;    // Discussion topic to add
    private Spinner removeGroupSpinner;  // Drop down spinner to display all discussion groups for that course

    // Retrieve and store all the discussion groups available from database.
    private ArrayList<String> listOfDiscussionsFromDatabase = new ArrayList<>();
    private ListView fragmentDiscussionTopicList; // Display list of all the discussion groups on this listView.
    private ArrayAdapter<String> adapter1;        // Array adapter to format listView

    private String userName;  // UserName of message sender
    private String className;  // className chosen from list

    public DiscussionGroupsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_discussion_groups_fragment, container, false);

        // Retrieve passed class name
        Bundle bundle = getArguments();
        className = bundle.getString("className");

        /* Create an instance of the class that can add and remove messages/discussion groups
            It will be used to add and delete messages/discussion from database
        */
        myDatabaseChat = new MyDatabaseChat(getActivity(), className);

        // Find the buttons
        addDiscusionTopic    = (Button) view.findViewById(R.id.add_discussion_group);
        removeDiscusionTopic = (Button) view.findViewById(R.id.remove_discussion_group);

        // Find the listView that the discussion group list will be appended
        fragmentDiscussionTopicList = (ListView) view.findViewById(R.id.discussion_group_list);

        // Getting the user's username
        userName = awsMobileClient.getIdentityManager().getUserName();

        // Initialize adapter and pass the layout style to add the list of discussion groups
        adapter1 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,listOfDiscussionsFromDatabase);
        fragmentDiscussionTopicList.setAdapter(adapter1);

        // Create listener for add discussion button
        addDiscusionTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Alert dialog to prompt user to add the discussion group he want to add to the list
                AlertDialog.Builder addDiscusionGroupDialog = new AlertDialog.Builder(getActivity());

                addDiscusionGroupDialog.setTitle("Add Discussion Group");
                addDiscusionGroupDialog.setMessage("Write the title of the discussion group you want to add!");

                // Initalize discussion topic  Edit Text section where the user will type the group name
                discussionTopic = new EditText(getActivity());

                // Set the layout to have the EditText we just created
                addDiscusionGroupDialog.setView(discussionTopic);

                // Cancel button for dialog and its listener
                addDiscusionGroupDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });

                // Add button for dialog and listener which will call the method to add a discussion group to database.
                addDiscusionGroupDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        myDatabaseChat.addDiscussionGroup(discussionTopic.getText().toString());
                    }
                });

                addDiscusionGroupDialog.show(); // Display the dialog
            }
        });

        // Create a listener for remove discussion group
        removeDiscusionTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create dialog to show a spinner with a list of discussion groups to remove
                AlertDialog.Builder removeDiscusionGroupDialog = new AlertDialog.Builder(getActivity());

                // Initialize the spinner that will hold the list of all available discussion group
                removeGroupSpinner = new Spinner(getActivity());

                // Set adapter to inflate spinner with list of groups
                removeGroupSpinner.setAdapter(adapter1);

                removeDiscusionGroupDialog.setTitle("Remove Discussion Group");
                removeDiscusionGroupDialog.setMessage("Select the discussion group you want to remove!");
                removeDiscusionGroupDialog.setView(removeGroupSpinner);

                // Create a listener to close dialog if cancel is chosen
                removeDiscusionGroupDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });

                // Create a listener to remove the group chosen in spinner from database
                removeDiscusionGroupDialog.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Make sure there is a group in the list
                        if(removeGroupSpinner.getSelectedItem()!= null) {
                            String removeDiscussionGroup = removeGroupSpinner.getSelectedItem().toString();
                            myDatabaseChat.deleteDiscussionGroup(removeDiscussionGroup);
                        }
                    }
                });

                removeDiscusionGroupDialog.show(); // Display dialog
            }
        });

        /* Create a listener to the database values, so that if any values are added to the database.
             Then update the adapter that hold the list of groups in the listView and spinner.
        */
        myDatabaseChat.getDiscussionGroupListReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Use set to add all the groups and make sure that none of the groups are the same
                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();

                while (i.hasNext()){
                    set.add(((DataSnapshot)i.next()).getKey());
                }

                // Clear the old list and update with new values
                listOfDiscussionsFromDatabase.clear();
                listOfDiscussionsFromDatabase.addAll(set);

                adapter1.notifyDataSetChanged(); // update the adapter of the spinner and listView
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /* If a user chooses one of the group in the listView open the message fragment and
            pass the necessary values
        */
        fragmentDiscussionTopicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // Fragment that will display the messages in the group
                DiscussionBoardMessageFragment tempFragment = new DiscussionBoardMessageFragment();

                // Bundle to add arguments the fragment will need to function(like what a constructor does)
                Bundle bundle = new Bundle();
                bundle.putString("discussionTopic",((TextView)view).getText().toString());
                bundle.putString("userName",userName);
                bundle.putString("className",className);
                tempFragment.setArguments(bundle);

                // Start the new fragment and replace the current fragment with the new one
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, tempFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                ((MainActivity) getActivity()).setActionBarTitle("Discussion Chat");
            }
        });
        return view;
    }
}