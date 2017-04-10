package com.PocketMoodle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.PocketMoodle.DiscussionBoard.MyDatabaseChat;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Created by Laxman on 2017-03-17.
 */

public class DiscussionBoardMessageFragment extends Fragment {

    private MyDatabaseChat myDatabaseChat; // Class that has methods to add messages to discussion group

    private Button buttonSendMessage; // Send message button
    private EditText messageToSend; // Message user will type to send
    private TextView discussionTopicTitle; // The discussion group name
    private ListView fragmentDiscussionMessageList; // ListView to add message list


    private ArrayAdapter<String> messageListAdapter; // adapter for list of messages and layout
    public static ArrayList<String> listOfMessagesFromDatabase; // Hold the list of messages

    private String userName; // User name of user
    private String discussionTopic; // Discussion topic to add messages to
    private String className; // Course the discussion is in

    public DiscussionBoardMessageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_discussion_board_message, container, false);

        // Find the widgets in the fragment
        buttonSendMessage = (Button) v.findViewById(R.id.send_message);
        messageToSend = (EditText) v.findViewById(R.id.message_to_send);
        discussionTopicTitle = (TextView) v.findViewById(R.id.discussion_title);
        fragmentDiscussionMessageList = (ListView) v.findViewById(R.id.message_list);

        // Retrieve the arguments passed by the calling class that is needed to display correct messages
        Bundle bundle = getArguments();

        userName = bundle.getString("userName");
        discussionTopic = bundle.getString("discussionTopic");
        className = bundle.getString("className");

        listOfMessagesFromDatabase = new ArrayList<>(); // Arraylist to hold the messages for a group

        // Adapter to set the list of messages
        messageListAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listOfMessagesFromDatabase);
        fragmentDiscussionMessageList.setAdapter(messageListAdapter);

        /* Create an instance of the class that can add and remove messages/discussion groups
            It will be used to add and delete messages/discussion from database
        */
        myDatabaseChat = new MyDatabaseChat(getActivity(), className, discussionTopic);

        // Set the title of the fragment with the discussion topic
        discussionTopicTitle.setText(discussionTopic);

        // Create a listener for the send message button to add a message to the database
        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myDatabaseChat.addChatMessages(userName, messageToSend.getText().toString());
                messageToSend.setText(""); // Clear the EditText where user writes the message
            }
        });

        /* Create a listener to see if the database is updated. So that when other users or current
            user add messages the current users list is updated.
        */
        myDatabaseChat.getMessageListReference().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                listOfMessagesFromDatabase.addAll(retrieveChatMessages(dataSnapshot, discussionTopic));
                messageListAdapter.notifyDataSetChanged(); // update the adapter that holds the list

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                listOfMessagesFromDatabase.addAll(retrieveChatMessages(dataSnapshot, discussionTopic));
                messageListAdapter.notifyDataSetChanged(); // update the adapter that holds the list
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return v;
    }

    // Get the list of messages for a discussion topic
    public Set<String> retrieveChatMessages(DataSnapshot dataSnapshot, String discussionTopic) {

        // Holds the list of messages
        Set<String> chatMessageSet = new HashSet<String>();

        // Create an iterator and go through all the chilren and add all the messages to the set
        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()) {
            String chatDateAndTime = (String) ((DataSnapshot) i.next()).getValue();
            String chatMessage = (String) ((DataSnapshot) i.next()).getValue();
            String chatUserName = (String) ((DataSnapshot) i.next()).getValue();
            chatMessageSet.add(chatUserName + " Posted by " + chatDateAndTime + "\n\n" + chatMessage);
        }

        return chatMessageSet;
    }
}