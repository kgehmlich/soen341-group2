package com.PocketMoodle;


import android.app.AlertDialog;

import android.content.DialogInterface;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.util.*;

import com.PocketMoodle.Services.ClassServices;
import com.PocketMoodle.Services.InsertUserDetails;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddClassFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    Spinner addClassSpinner;
    private String password = "moodle";
    private String input1 = "";
    public static List<String> s = new ArrayList<String>();
    public static List<String> userClassList = new ArrayList<String>();
    private static final String TAG = "MyActivity";
    private static final String LOG_TAG = SignInActivity.class.getSimpleName();
    public AddClassFragment() {
        // Required empty public constructor
        Runnable runnable = new Runnable() {
            public void run() {
                ClassServices d = new ClassServices();
                s = d.GetListOfClass();

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

        ArrayList<String> option = new ArrayList<String>();

        if(s.size() > 0){
            Log.d(TAG, "SIZE more 0");
            for(String s2: s){
                option.add(s2);
            }
        }
        else
            Log.d(TAG,"Size less than 0" );



        View v = inflater.inflate(R.layout.fragment_add_class, container, false);
        addClassSpinner = (Spinner) v.findViewById(R.id.add_class_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, option);
        addClassSpinner.setAdapter(adapter);


        Button button = (Button) v.findViewById(R.id.button);
        final RadioButton TA = (RadioButton) v.findViewById(R.id.radioButton);
        final RadioButton STU = (RadioButton) v.findViewById(R.id.radioButton2);


        //**********************
        //Prompt password for TA
        //**********************
        TA.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Please enter password for TA authorization");
            // Set up the input
                final EditText input = new EditText(getActivity());
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);

            // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        input1 = input.getText().toString();
                    //cancel if incorrect password
                        if(input1.equals(password))
                            TA.setChecked(true);
                        if(input1.equalsIgnoreCase(password))
                            TA.setChecked(true);
                        else
                            TA.setChecked(false);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
        //**********************
        //end of password prompt
        //**********************

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean spinnerIsChecked = addClassSpinner.getSelectedItem().toString() != null;
                FragmentActivity tmpActi = getActivity();
                final String SpinnerChoice;
                final String TAorSTU;
                if(spinnerIsChecked){
                    SpinnerChoice = addClassSpinner.getSelectedItem().toString();
                }
                else{
                    Toast savetoast = Toast.makeText(getActivity(), "Please choose a course", Toast.LENGTH_LONG);
                    savetoast.show();
                    /*final AlertDialog.Builder errorDialogBuilder = new AlertDialog.Builder(tmpActi);
                    errorDialogBuilder.setTitle("Please choose a course");
                    errorDialogBuilder.show();*/
                    return;
                }

                if(TA.isChecked() ^ STU.isChecked() ){
                    if(TA.isChecked()) {
                        TAorSTU = "1.0";
                    }
                    else {
                        TAorSTU = "2.0";
                    }
                }
                else{
                    Toast savetoast = Toast.makeText(getActivity(), "Please choose TA or Student", Toast.LENGTH_LONG);
                    savetoast.show();
                    /*final AlertDialog.Builder errorDialogBuilder = new AlertDialog.Builder(tmpActi);
                    errorDialogBuilder.setTitle("Please choose TA or Student");
                    errorDialogBuilder.show();*/
                    TAorSTU = "0.0";
                    return;
                }


                if(spinnerIsChecked && (TA.isChecked() ^ STU.isChecked() )){


                    Runnable runnable = new Runnable() {
                        public void run() {

                            ClassServices ClassList = new ClassServices();
                            userClassList = ClassList.GetAllClassRegisteredIn();

                        }
                    };

                    Thread myThread2 = new Thread(runnable);
                    myThread2.start();

                    // Wait for thread to gather entire list of classes that user is in
                    while (myThread2.isAlive()) {

                    }

                    if(userClassList.contains(SpinnerChoice)){
                        Toast savetoast = Toast.makeText(getActivity(), "You are already enrolled in this class", Toast.LENGTH_LONG);
                        savetoast.show();
                        /*final AlertDialog.Builder errorDialogBuilder = new AlertDialog.Builder(tmpActi);
                        errorDialogBuilder.setTitle("Please choose TA or Student");
                        errorDialogBuilder.show();*/
                        return;
                    }

                    else{

                        Runnable runnable2 = new Runnable() {
                            public void run() {

                                InsertUserDetails InserNewClass = new InsertUserDetails();
                                double F = Double.valueOf(TAorSTU);
                                InserNewClass.insertData(SpinnerChoice, F);


                            }
                        };

                        Thread myThread3 = new Thread(runnable2);
                        myThread3.start();
                        Toast savetoast = Toast.makeText(getActivity(), "Request has been sent", Toast.LENGTH_LONG);
                        savetoast.show();
                    /*final AlertDialog.Builder errorDialogBuilder = new AlertDialog.Builder(tmpActi);
                    errorDialogBuilder.setTitle("Request have been sent...");
                    errorDialogBuilder.show();*/
                    }
                }

            }
        });

        return v;



    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /*TextView classChoice = (TextView) view;
        Toast.makeText(this.getContext(), classChoice.getText(),Toast.LENGTH_SHORT).show();
*/

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }









}
