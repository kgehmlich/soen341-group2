package com.PocketMoodle;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.PocketMoodle.Services.DocumentServices;

import java.io.File;
import java.util.ArrayList;


public class ViewDocumentsFragment extends Fragment {

    private View v;

    private String className;

    public ViewDocumentsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_view_documents, container, false);


        // Retrieve the arguments passed by the calling class that is needed to display correct messages
        Bundle tempBundle = getArguments();
        className = tempBundle.getString("className");

        new FetchClassDocumentList().execute();

        return v;
    }


    public void onDocumentClick(String documentName) {
        Toast.makeText(getContext(), "Downloading " + documentName, Toast.LENGTH_SHORT).show();

        // Download file using DocumentServices.download()
        if (isExternalStorageWritable()) {
            // Create file to be saved (stores each class's documents in a separate folder)
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), className+"/"+documentName);

            DocumentServices documentServices = new DocumentServices(getContext());
            documentServices.download(documentName, className, file);
        }
    }


    private class FetchClassDocumentList extends AsyncTask<Void, Void, Void> {

        private ArrayList<String> classDocumentList;

        protected Void doInBackground(Void... voids) {
            // ****************************************
            // Get list of documents for this class
            // ****************************************
            DocumentServices documentServices = new DocumentServices(getContext());
            classDocumentList = documentServices.listDocumentsForClass(className);

            return null;
        }

        protected void onPostExecute(Void unused) {
            // ********************************************************
            // Set listView adapter to display list of documents
            // ********************************************************
            ArrayAdapter<String> docAdapter = new ArrayAdapter<String>(
                    getContext(),
                    android.R.layout.simple_list_item_1,
                    classDocumentList);
            ListView docListView = (ListView) v.findViewById(R.id.documentListView);

            docListView.setAdapter(docAdapter);


            // ********************************************************
            // Set listView click listener to download documents
            // ********************************************************
            docListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    onDocumentClick(((TextView)view).getText().toString());
                }
            });
        }
    }


    /**
     * From developer.android.com
     * Checks whether or not external storage is available for writing.
     * @return
     */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
}
