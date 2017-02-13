package com.mysampleapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddClassFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    Spinner addClassSpinner;

    public AddClassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_add_class, container, false);

        addClassSpinner = (Spinner) v.findViewById(R.id.add_class_spinner);

        ArrayAdapter addClassAdapter;
        addClassAdapter = ArrayAdapter.createFromResource(this.getContext(), R.array.add_class_spinner,android.R.layout.simple_spinner_dropdown_item);
        addClassSpinner.setAdapter(addClassAdapter);

        addClassSpinner.setOnItemSelectedListener(this);

        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_add_class, container, false);
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
