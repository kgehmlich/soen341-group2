package com.PocketMoodle;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DocumentsFragment extends Fragment {

    Button uploadButton;
    Button backButton;
    TextView folder;

    ListView listView;

    File rootDirectoryPath;
    File currentFolder;
    private List<String> fileList = new ArrayList<String>();

    public DocumentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_documents, container, false);

        //Get root path in android device
        rootDirectoryPath = new File(Environment.getExternalStorageDirectory().getAbsolutePath());

        // Specify the current folder to begin at root
        currentFolder = rootDirectoryPath;

        folder = (TextView)v.findViewById(R.id.folder);

        // Button to display file explorer begining at root
        backButton = (Button)v.findViewById(R.id.backParent);
        backButton.setEnabled(false);

        backButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                DirectoryFiles(currentFolder.getParentFile());            }
        });


        // Button to display file explorer begining at root
        uploadButton = (Button)v.findViewById(R.id.uploadDocument);
        uploadButton.setOnClickListener(new OnClickListener(){

            // When going through file explorer reclicking upload document allows u to
            //  to go back to the file list(i.e the parent file of what u are in)
            @Override
            public void onClick(View v) {
                backButton.setEnabled(true);
                DirectoryFiles(rootDirectoryPath);
            }});

        // get ListView in fragment_document
        listView = (ListView)v.findViewById(R.id.directorylist);

        // Set on click listener to listen to the file the use chooses
        listView.setOnItemClickListener(new OnItemClickListener(){

            // When a file/directory is clicked. If a file is chosen change status message to File Selected
            // When a directory is clicked. Go into the directory to view files
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                File selected = new File(fileList.get(position));
                if(selected.isDirectory()){
                    DirectoryFiles(selected);
                }else {
                    setUploadStatusMessage("Status: File Selected");
                    backButton.setEnabled(false);
                }

            }});

        return v;

    }

    public void DirectoryFiles(File file){

        // Only allow the user to go to parent directory if he is not already at the root
        if(file.equals(rootDirectoryPath)){
            backButton.setEnabled(false);
        }else{
            backButton.setEnabled(true);
        }

        currentFolder = file;
        folder.setText(file.getPath());

        File[] files = file.listFiles();
        fileList.clear();
        for (File tempFile : files){
            fileList.add(tempFile.getPath());
        }

        ArrayAdapter<String> directoryList
                = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, fileList);
        listView.setAdapter(directoryList);
    }

    // Once a file is selected this method can update the Status message in fragment_add_document
    public void setUploadStatusMessage(String uploadStatusMessage)
    {
        TextView textView = (TextView) getView().findViewById(R.id.uploadStatus);
        textView.setText(uploadStatusMessage);
        textView.setTextColor(Color.GREEN);
    }
}