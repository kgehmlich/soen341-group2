package com.PocketMoodle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Laxman on 2017-03-24.
 */
public class DisplayAnnouncementFragment extends Fragment {

    private TextView setTitle;       // Hold the location of the title in the xml
    private TextView setDescription; // Hold the location of the description in the xml
    private TextView setAuthorDate;  // Hold the location of the author and date create in the xml

    private String title;       // Hold title of announcement
    private String description; // Hold description of announcement
    private String author;      // Hold the author and date created of the announcement
    private String date;        // Hold the date created of the announcement

    public DisplayAnnouncementFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_display_announcement, container, false);

        // Initialize the locations of the announcement details
        setTitle = (TextView) view.findViewById(R.id.setAnnouncementTitle);
        setDescription = (TextView) view.findViewById(R.id.setAnnouncementDescription);
        setAuthorDate = (TextView) view.findViewById(R.id.setAnnouncementAuthorDate);

        // Retrieve the title, description, author and date of the announcement chosen from classPageFragment
        Bundle bundle = getArguments();
        title = bundle.getString("AnnouncementTitle");
        description = bundle.getString("AnnouncementDescription");
        author = bundle.getString("AnnouncementAuthor");
        date = bundle.getString("AnnouncementDate");

        // Set the details of the announcement on a fragment.
        setTitle.setText(title);
        setDescription.setText(description);
        setAuthorDate.setText("Posted by " + author + " on " + date + ".");

        return view;
    }
}