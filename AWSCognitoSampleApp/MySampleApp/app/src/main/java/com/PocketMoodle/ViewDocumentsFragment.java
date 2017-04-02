package com.PocketMoodle;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.PocketMoodle.Services.DocumentServices;

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


    private class FetchClassDocumentList extends AsyncTask<Void, Void, Void> {

        private ArrayList<String> classDocumentList;

        protected Void doInBackground(Void... voids) {
            // ****************************************
            // Get list of documents for this class
            // ****************************************
            DocumentServices documentServices = new DocumentServices(getContext());
            classDocumentList = documentServices.listDcoumentsForClass(className);

            return null;
        }

        protected void onPostExecute(Void unused) {
            ArrayAdapter<String> docAdapter = new ArrayAdapter<String>(
                    getContext(),
                    android.R.layout.simple_list_item_1,
                    classDocumentList);
            ListView docListView = (ListView) v.findViewById(R.id.documentListView);

            docListView.setAdapter(docAdapter);
        }
    }
}
