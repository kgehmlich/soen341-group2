package com.PocketMoodle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.PocketMoodle.Services.GetAllClass;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private static final String TAG = "FromHomeFragment";
    public static List<String> ListOfClassUserIsIn = new ArrayList<String>();
    public ListView classListXmlLink;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        classListXmlLink = (ListView)v.findViewById(R.id.classList);

        // Set on click listener to listen to the class the person
        classListXmlLink.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            // When a class from the class list is clicked do something
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


            }});

        /**
         * ListOfClassIsIn is a list of all class the user is registered in...
         * The runnable object can call back-end method because it is outisde of main thread
         */
        Runnable runnable = new Runnable() {
            public void run() {
                GetAllClass GetClassForUser = new GetAllClass();
                ListOfClassUserIsIn = GetClassForUser.GetAllClassRegisteredIn();
            }
        };

        Thread ThreadGetClassForAUser = new Thread(runnable);

        try{
            ThreadGetClassForAUser.start();
            // For now sleep until we find a better way to waste time. Since wait keeps getting woken up
            Thread.sleep(1000);
        }
        catch (Exception exp){
            Log.e(TAG, exp.getMessage().toString());
        }
        while(ThreadGetClassForAUser.isAlive()){


        // Allows for the list of classes the user is in to appear in the list view in the xml
        ArrayAdapter<String> studentClassListAdapter
                = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, ListOfClassUserIsIn);
        classListXmlLink.setAdapter(studentClassListAdapter);


        // Inflate the layout for this fragment
        return v;
    }

}
