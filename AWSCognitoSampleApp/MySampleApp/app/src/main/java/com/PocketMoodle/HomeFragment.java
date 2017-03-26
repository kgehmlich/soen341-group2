package com.PocketMoodle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.PocketMoodle.Services.AnnounServices;
import com.PocketMoodle.Services.GetAllClass;
import com.PocketMoodle.Services.GradesServices;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private static final String TAG = "FromHomeFragment";
    public static List<String> ListOfSTUClassUserIsIn = new ArrayList<String>();
    public static List<String> ListOfTAClassUserIsIn = new ArrayList<String>();
    public ListView STUclassListXmlLink;
    public ListView TAclassListXmlLink;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        STUclassListXmlLink = (ListView) v.findViewById(R.id.STUclassList);
        TAclassListXmlLink = (ListView) v.findViewById(R.id.TAclassList);

        // Set on click listener to listen to the class the person
        STUclassListXmlLink.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            // When a class from the student class list is clicked do something
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ClassPageFragment classPageFragment = new ClassPageFragment();

                String className = ((TextView)view).getText().toString();

                // Bundle to add arguments the fragment will need to function(like what a constructor does)
                Bundle bundle = new Bundle();
                bundle.putString("className", className);
                classPageFragment.setArguments(bundle);

                // Start the new fragment and replace the current fragment with the new one
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, classPageFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                ((MainActivity) getActivity()).setActionBarTitle(className);


            }
        });
        TAclassListXmlLink.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    // When a class from the TA class list is clicked do something
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        ClassPageFragment classPageFragment = new ClassPageFragment();

                        String className = ((TextView)view).getText().toString(); // Get chosen class name from list

                        // Bundle to add arguments the fragment will need to function(like what a constructor does)
                        Bundle bundle = new Bundle();
                        bundle.putString("className", className);
                        classPageFragment.setArguments(bundle);

                        // Start the new fragment and replace the current fragment with the new one
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, classPageFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        ((MainActivity) getActivity()).setActionBarTitle(className);
                    }
                });

        /**
         * ListOfClassIsIn is a list of all class the user is registered in...
         * The runnable object can call back-end method because it is outisde of main thread
         */

        Runnable runnable = new Runnable() {
            public void run() {
                GetAllClass GetClassForUser = new GetAllClass();
                AnnounServices aS = new AnnounServices();
                aS.InsertAnnouncement("COEN 341", "This is a Title", "Class is cancel because of snow");
                ListOfSTUClassUserIsIn = GetClassForUser.GetAllClassYouAreStudent();
                ListOfTAClassUserIsIn = GetClassForUser.GetAllClassYouAreTA();
            }
        };

        Thread ThreadGetClassForAUser = new Thread(runnable);

        try {
            ThreadGetClassForAUser.start();
            // For now sleep until we find a better way to waste time. Since wait keeps getting woken up
            Thread.sleep(1000);
        } catch (Exception exp) {
            Log.e(TAG, exp.getMessage().toString());
        }
        while (ThreadGetClassForAUser.isAlive()) {

        }
        // Allows for the list of classes the user is in to appear in the list view in the xml
        ArrayAdapter<String> studentClassListAdapter
                = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, ListOfSTUClassUserIsIn);
        STUclassListXmlLink.setAdapter(studentClassListAdapter);

        ArrayAdapter<String> TAClassListAdapter
                = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, ListOfTAClassUserIsIn);
        TAclassListXmlLink.setAdapter(TAClassListAdapter);

        // Inflate the layout for this fragment
        return v;


    }

    /*void goToDocumentFragment(){
        Intent intent = new Intent (getActivity(), DocumentsFragment.class);                        // Connects profile activity with mainactivity for the transition
        startActivity(intent);
    }*/
}
