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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.PocketMoodle.Services.DocumentServices;
import com.PocketMoodle.Services.GetAllClass;
import com.amazonaws.mobile.util.ImageSelectorUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class DocumentsFragment extends Fragment implements View.OnClickListener {

    View v;

    Button uploadButton;
    TextView textView;
    private static final String TAG = "DocumentsFragment";
    private int RESULT_DOCUMENT_SUCCESSFUL = 20;

    private String className;
    private String TAOrStudent;
    private Spinner changeClassSpinner; // Spinner to display registered classes
    private static List<String> registeredClasses = new ArrayList<String>(); // Array of classes that will contain the registered classes
    private static List<String> taClasses = new ArrayList<String>();

    public DocumentsFragment() {
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
        v = inflater.inflate(R.layout.fragment_documents, container, false);
        textView = (TextView) v.findViewById(R.id.uploadStatus);

        // Retrieve the arguments passed by the calling class that is needed to display correct messages
        Bundle tempBundle = getArguments();
        className = tempBundle.getString("className");
        TAOrStudent = tempBundle.getString("TAOrStudent");

        // Button to display file explorer begining at root
        uploadButton = (Button)v.findViewById(R.id.uploadDocument);
        uploadButton.setOnClickListener(this);
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
        changeClassSpinner = (Spinner) v.findViewById(R.id.change_class_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, selectOption);
        changeClassSpinner.setAdapter(adapter);

        //Behaviour of the changeClassSpinners' upon item selection
        changeClassSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DocumentsFragment documentFragment = new DocumentsFragment();
                String className = parent.getItemAtPosition(position).toString();

                if (className.equals("Change class")) {

                } else {
                    if (taClasses.contains(className)) { //Checks list of TA classes of users and compares to bundle className to determine if TA class selected
                        // Bundle to add arguments the fragment will need to function(like what a constructor does)
                        Bundle bundle = new Bundle();
                        bundle.putString("className", className);
                        bundle.putString("TAOrStudent", "TA"); // Sets TA user true, and gives TA priviledge to page
                        documentFragment.setArguments(bundle);

//                 Start the new fragment and replace the current fragment with the new one
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, documentFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        ((MainActivity) getActivity()).setActionBarTitle(className);

                    } else {
                        // Bundle to add arguments the fragment will need to function(like what a constructor does)
                        Bundle bundle = new Bundle();
                        bundle.putString("className", className);
                        bundle.putString("TAOrStudent", "Student"); // Gives studen priviledge
                        documentFragment.setArguments(bundle);

//                 Start the new fragment and replace the current fragment with the new one
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, documentFragment);
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
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Check the request we are responding to
        if (requestCode == RESULT_DOCUMENT_SUCCESSFUL) {

            // If the request was successful get the path and print it onto the screen
            if (resultCode == Activity.RESULT_OK) {

                Uri uri = data.getData();
                textView.setText("Status: File selected - " + uri.getPath());
                textView.setTextColor(Color.GREEN);

                // Use this data variable to get the path or whatever detail of the chosen file details you need for the api
                final String path = ImageSelectorUtils.getFilePathFromUri(getActivity(), uri);
                File selected = new File(path);

                // TODO: confirm that chosen file is correct

                // Get document title to pass to DocumentServices.upload()
//                TextView docTitleTv = (TextView) v.findViewById(R.id.documentTitleText);
//                String documentTitle = docTitleTv.getText().toString();
//
//                if (documentTitle.isEmpty())
//                {
//                    Toast.makeText(getContext(), "Please enter a title", Toast.LENGTH_LONG).show();
//                    return;
//                }

                DocumentServices ud = new DocumentServices(getContext());
                //ud.upload(selected, documentTitle, className);
                ud.upload(selected, selected.getName(), className);
            }
        }

    }

    public void onClick(View v) {

        if(v.getId() == R.id.uploadDocument) {
            try {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(Intent.createChooser(intent, "Select a Document to Upload"), RESULT_DOCUMENT_SUCCESSFUL);

            } catch (Exception ex) {
                // In case the intent fails display error message in log
                Log.d(TAG, "Error accessing file explorer in class DocumentFragment");
            }
        }
    }





}
