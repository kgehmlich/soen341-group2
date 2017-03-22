package com.PocketMoodle;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.PocketMoodle.Services.GetAllClass;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DocumentsFragment extends Fragment implements View.OnClickListener {

    Button uploadButton;
    Button openDiscussionGroup;
    String className;
    Spinner changeClassSpinner;

    TextView textView;
    private static final String TAG = "DocumentsFragment";
    private int RESULT_CODE = 0;

    public static List<String> registeredClasses = new ArrayList<String>();



    public DocumentsFragment() {
        Runnable runnable = new Runnable() {
            public void run(){
                GetAllClass registered = new GetAllClass();
                registeredClasses = registered.GetAllClassRegisteredIn();
            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();
        // TODO find another wait to do that wait time
        while (mythread.isAlive()){

        }

        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ArrayList<String> selectOption = new ArrayList<String>();

        if(registeredClasses.size() > 0){
            for(String r: registeredClasses){
                selectOption.add(r);
            }
        }

        View v = inflater.inflate(R.layout.fragment_documents, container, false);
        textView = (TextView) v.findViewById(R.id.uploadStatus);
        changeClassSpinner = (Spinner) v.findViewById(R.id.change_class_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, selectOption);
        changeClassSpinner.setAdapter(adapter);

        Button submitB = (Button) v.findViewById(R.id.submitButton);

//        submitB.setOnClickListener();
        // Button to display file explorer begining at root
        uploadButton = (Button)v.findViewById(R.id.uploadDocument);
        uploadButton.setOnClickListener(this);

        // Button to display discussion board
        openDiscussionGroup = (Button)v.findViewById(R.id.openDiscussionBoard);
        openDiscussionGroup.setOnClickListener(this);

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        // Check which request we're responding to
        if (requestCode == RESULT_CODE) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {

                Uri uri = data.getData();
                textView.setText("Status: File selected - " + uri.getPath());
                textView.setTextColor(Color.GREEN);

                // Use this data variable to get the path or whatever detail of the chosen file details you need for the api

            }
        }
    }

    public void onClick(View v) {

        if(v.getId() == R.id.uploadDocument) {
            try {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(Intent.createChooser(intent, "Select a Document to Upload"), RESULT_CODE);

            } catch (Exception ex) {
                // In case the intent fails display error message in log
                Log.d(TAG, "Error accessing file explorer in class DocumentFragment");
            }
        }
        if(v.getId() == R.id.openDiscussionBoard) {
            try {
                // Fragment that will display the messages in the group
                DiscussionGroupsFragment tempFragment = new DiscussionGroupsFragment();

                // Retrieve the arguments passed by the calling class that is needed to display correct messages
                Bundle tempBundle = getArguments();
                className = tempBundle.getString("className");

                // Bundle to add arguments the fragment will need to function(like what a constructor does)
                Bundle bundle = new Bundle();
                bundle.putString("className", className);
                tempFragment.setArguments(bundle);

                // Start the new fragment and replace the current fragment with the new one
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, tempFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                ((MainActivity) getActivity()).setActionBarTitle("Discussion Chat");

            } catch (Exception ex) {
                // In case the open fails display error message in log
                Log.d(TAG, "Error accessing discussionBoard");
            }
        }
    }
}
