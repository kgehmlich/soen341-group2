package com.PocketMoodle;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DocumentsFragment extends Fragment implements View.OnClickListener {

    Button uploadButton;

    TextView textView;
    private static final String TAG = "DocumentsFragment";
    private int RESULT_DOCUMENT_SUCCESSFUL = 20;

    public DocumentsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_documents, container, false);
        textView = (TextView) v.findViewById(R.id.uploadStatus);

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
