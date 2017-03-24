package com.PocketMoodle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassPageFragment extends Fragment {

    Button uploadButton;
    Button openDiscussionGroup;
    String className;

    TextView classTitle;
    private static final String TAG = "ClassPageFragment";

    public ClassPageFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_class_page, container, false);

        // Retrieve the arguments passed by the calling class that is needed to display correct messages
        Bundle tempBundle = getArguments();
        className = tempBundle.getString("className");

        classTitle = (TextView) v.findViewById(R.id.setclassTitle);
        classTitle.setText(className);

        // Button to display file explorer beginning at root
        uploadButton = (Button)v.findViewById(R.id.openUploadDocument);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    // Fragment that will display the messages in the group
                    DocumentsFragment documentsFragment = new DocumentsFragment();

                    // Start the new fragment and replace the current fragment with the new one
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_container, documentsFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                } catch (Exception ex) {
                    // In case the open fails display error message in log
                    Log.d(TAG, "Error accessing Upload Document");
                }
            }
        });

        // Button to display discussion board
        openDiscussionGroup = (Button)v.findViewById(R.id.openDiscussionBoard);
        openDiscussionGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // Fragment that will display the messages in the group
                    DiscussionGroupsFragment tempFragment = new DiscussionGroupsFragment();

                    // Bundle to add arguments the fragment will need to function(like what a constructor does)
                    Bundle bundle = new Bundle();
                    bundle.putString("className", className);
                    tempFragment.setArguments(bundle);

                    // Start the new fragment and replace the current fragment with the new one
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_container, tempFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                } catch (Exception ex) {
                    // In case the open fails display error message in log
                    Log.d(TAG, "Error accessing discussionBoard");
                }
            }
        });
        return v;
    }
}
