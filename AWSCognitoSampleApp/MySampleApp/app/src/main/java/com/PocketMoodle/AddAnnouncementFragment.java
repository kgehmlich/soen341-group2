package com.PocketMoodle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.PocketMoodle.Services.AnnounServices;
import com.amazonaws.mobile.AWSMobileClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Laxman on 2017-03-24.
 */

public class AddAnnouncementFragment extends Fragment {

    private final AWSMobileClient awsMobileClient = AWSMobileClient.defaultMobileClient();

    private Button addAnnouncementTopic;      // Button to add announcement for a class
    private Button cancelAnnouncementTopic;   // Button to cancel adding an announcement
    private EditText announcementTitle;       // Title for the announcement the user is trying to add
    private EditText announcementDescription; // Description of the announcement the user is trying to add
    private String userName;                  // UserName of announcement creator
    private String className;                 // className that the announcement is for.
    // Announcement object for each announcement that holds the author, date, title, and description for an announcement
    private List<AnnounServices.Announcement> announcementList = new ArrayList<AnnounServices.Announcement>();

    public AddAnnouncementFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_announcement, container, false);

        // Get the details of the class that called this fragment.
        Bundle bundle = getArguments();
        className = bundle.getString("className");

        // Initialize the buttons and edit text's that the user will edit.
        addAnnouncementTopic    = (Button) view.findViewById(R.id.okAddAnnouncement);
        cancelAnnouncementTopic = (Button) view.findViewById(R.id.cancelAddAnnouncement);
        announcementTitle       = (EditText) view.findViewById(R.id.announcementTitle);
        announcementDescription = (EditText) view.findViewById(R.id.announcementDescription);

        userName = awsMobileClient.getIdentityManager().getUserName();

        // If user clicks add announcement then call api to add a new announcement for that class
        addAnnouncementTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Runnable runnable = new Runnable() {
                    public void run() {

                        AnnounServices aS = new AnnounServices();
                        announcementList = aS.GetAllAnnouncementsForClass(className);
                    }
                };

                Thread getAnnouncementThread = new Thread(runnable);
                getAnnouncementThread.start();

                // Wait so that we can retrieve all the announcements
                // TODO find another wait to do that wait time
                while (getAnnouncementThread.isAlive()){

                }

                Boolean announcementExists = false;

                // Make sure the announcement with the same title does not already exist
                for(AnnounServices.Announcement eachAnnouncement : announcementList) {

                    if(eachAnnouncement.getAnnouTitle().equals(announcementTitle.getText().toString())) {
                        announcementExists = true;
                        Toast announcementAlreadyExists = Toast.makeText(getActivity(), "Announcement with the same title already exists", Toast.LENGTH_LONG);
                        announcementAlreadyExists.show();
                        break;
                    }
                }

                // If announcement does not exist add the announcement
                if(announcementExists == false) {
                    Runnable runnable2 = new Runnable() {
                        public void run() {

                            AnnounServices aS = new AnnounServices();
                            aS.InsertAnnouncement(className, announcementTitle.getText().toString(), announcementDescription.getText().toString());
                        }
                    };
                    Thread addAnnouncementThread = new Thread(runnable2);
                    addAnnouncementThread.start();

                    // Wait so that announcement can be added to database
                    // TODO find another wait to do that wait time
                    while (addAnnouncementThread.isAlive()){

                    }

                    Toast announcementSuccess = Toast.makeText(getActivity(), "Announcement has been added", Toast.LENGTH_LONG);
                    announcementSuccess.show();
                }

                getFragmentManager().popBackStack(); // Return to previous fragment
            }
        });

        // If user clicks cancel return to previous fragment
        cancelAnnouncementTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getFragmentManager().popBackStack(); // Return to previous fragment
            }
        });

        return view;
    }
}