package com.PocketMoodle;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.PocketMoodle.Services.GetAllClass;
import com.PocketMoodle.Services.InsertUserDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RemoveClassFragment extends Fragment {


    Spinner removeClassSpinner;
    public static List<String> classList = new ArrayList<String>();
    private static final String TAG = "MyActivity";
    private static final String LOG_TAG = SignInActivity.class.getSimpleName();

    public RemoveClassFragment() {
        // Required empty public constructor

        Runnable runnable = new Runnable() {
            public void run() {
                GetAllClass getAllClass = new GetAllClass();
                classList = getAllClass.GetListOfClass();

            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();
        // TODO find another wait to do that wait time
        while (mythread.isAlive()){

        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ArrayList<String> option = new ArrayList<String>();

        if(classList.size() > 0){
            Log.d(TAG, "SIZE more 0");
            for(String s2: classList){
                option.add(s2);
            }
        }
        else
            Log.d(TAG,"Size less than 0" );


        View v = inflater.inflate(R.layout.fragment_remove_class, container, false);

        removeClassSpinner = (Spinner) v.findViewById(R.id.remove_class_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, option);
        removeClassSpinner.setAdapter(adapter);

        Button removeClassButton = (Button) v.findViewById(R.id.remove_class_button);

        removeClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean spinnerIsChecked = removeClassSpinner.getSelectedItem().toString() != null;
                FragmentActivity tmpActi = getActivity();
                final String SpinnerChoice;

                if(spinnerIsChecked){
                    SpinnerChoice = removeClassSpinner.getSelectedItem().toString();
                }
                else{
                    final AlertDialog.Builder errorDialogBuilder = new AlertDialog.Builder(tmpActi);
                    errorDialogBuilder.setTitle("Please choose a course you wish to remove");
                    errorDialogBuilder.show();
                    return;
                }

                Runnable runnable = new Runnable() {
                    public void run() {

                        //Call API or something here to do something with the class the user chose to remove
                        //String SpinnerChoice holds the class name the user chose

                    }
                };

                Thread mythread2 = new Thread(runnable);
                mythread2.start();
                final AlertDialog.Builder errorDialogBuilder = new AlertDialog.Builder(tmpActi);
                errorDialogBuilder.setTitle("Request has been sent...");
                errorDialogBuilder.show();

            }
        });
        return v;
    }

}
