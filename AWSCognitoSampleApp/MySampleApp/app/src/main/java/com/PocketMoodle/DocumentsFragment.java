package com.PocketMoodle;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.PocketMoodle.Services.DocumentServices;
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

    public DocumentsFragment() {
        // Required empty public constructor
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

        //if user clicks on Upload Document, ask for storage permission first
        if(v.getId() == R.id.uploadDocument) {
            try {
                if (ActivityCompat.checkSelfPermission(getContext(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getContext(),
                                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                                    android.Manifest.permission.ACCESS_FINE_LOCATION},
                            1);
                } else {
                    Log.e("DB", "PERMISSION GRANTED");
                }
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
