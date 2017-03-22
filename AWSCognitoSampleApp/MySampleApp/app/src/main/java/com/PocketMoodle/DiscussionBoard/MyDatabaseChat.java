package com.PocketMoodle.DiscussionBoard;

import android.app.AlertDialog;
import android.support.v4.app.FragmentActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Laxman on 2017-03-19.
 */

public class MyDatabaseChat {

    private final String DATABASE_SECTION = "Discussion Board"; // Section in database to access
    private DatabaseReference databaseClassParent; // Store reference to list of discussion groups
    private DatabaseReference databaseClassChild; // Store reference to list of message for a group

    private FragmentActivity fragmentActivity; // Hold the activity of the fragment to display error messages.

    // Constructor for a class that wants to add discussion groups
    public MyDatabaseChat(FragmentActivity activity, String className) {
        fragmentActivity = activity;

        // Get the reference to the group list section of the database
        databaseClassParent = FirebaseDatabase.getInstance().getReference().child(DATABASE_SECTION).child(className);
    }

    // constructor for a class that wants to add messages for a discussion group
    public MyDatabaseChat(FragmentActivity activity, String className, String discussionTopic) {
        fragmentActivity = activity;

        // Get the reference to the group list and message section of database
        databaseClassParent = FirebaseDatabase.getInstance().getReference().child(DATABASE_SECTION).child(className);
        databaseClassChild = FirebaseDatabase.getInstance().getReference().child(DATABASE_SECTION).child(className).child(discussionTopic);
    }

    // Add discussion groups to the list
    public void addDiscussionGroup(String discussionTopic) {
        try{
            // Make sure the discussion group is already not in the database and then add it with HashMap
            Map<String,Object> databaseMap = new HashMap<String, Object>();
            databaseMap.put(discussionTopic,"");
            databaseClassParent.updateChildren(databaseMap); // Add to database
        }
        catch(Exception e) {
            // If there was an error display a dialog to the user in their current fragment
            AlertDialog.Builder errorDialogBuilder = new AlertDialog.Builder(fragmentActivity);
            errorDialogBuilder.setTitle("Unable to add discussion group. An unexpected error has occurred");
            errorDialogBuilder.show();
        }
    }

    // Delete discussion groups to the list
    public void deleteDiscussionGroup(String discussionTopic) {
        try{
            databaseClassParent.child(discussionTopic).setValue(null);
        }
        catch(Exception e) {
            AlertDialog.Builder errorDialogBuilder = new AlertDialog.Builder(fragmentActivity);
            errorDialogBuilder.setTitle("Unable to remove discussion group. An unexpected error has occurred");
            errorDialogBuilder.show();
        }
    }

    // Add chat messages to a particular discussion group
    public void addChatMessages(String userName, String messageToSend) {
        // Get the current time
        String timeAndDateOfSentMessage = DateFormat.getDateTimeInstance().format(new Date());

        //
        String databaseKey = databaseClassChild.push().getKey();

        DatabaseReference newChildMessageReference = databaseClassChild.child(databaseKey);
        Map<String,Object> databaseMapNewChild = new HashMap<String, Object>();
        databaseMapNewChild.put("name", userName);
        databaseMapNewChild.put("message", messageToSend);
        databaseMapNewChild.put("dateTime", timeAndDateOfSentMessage);

        newChildMessageReference.updateChildren(databaseMapNewChild);
    }

    public DatabaseReference getMessageListReference() {
        return databaseClassChild;
    }

    public DatabaseReference getDiscussionGroupListReference() {
        return databaseClassParent;
    }
}
