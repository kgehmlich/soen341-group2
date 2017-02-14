package com.mysampleapp;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.*;

import com.amazonaws.AmazonClientException;
import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.models.nosql.UserDetailsDO;
import com.mysampleapp.Services.GetAllClass;
import com.mysampleapp.Services.InsertUserDetails;

import android.os.*;
import android.content.Context;


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

        //GetAllClass d = new GetAllClass();
        List<String> s = new List<String>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @NonNull
            @Override
            public Iterator<String> iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(T[] ts) {
                return null;
            }

            @Override
            public boolean add(String s) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> collection) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends String> collection) {
                return false;
            }

            @Override
            public boolean addAll(int i, Collection<? extends String> collection) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> collection) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> collection) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public String get(int i) {
                return null;
            }

            @Override
            public String set(int i, String s) {
                return null;
            }

            @Override
            public void add(int i, String s) {

            }

            @Override
            public String remove(int i) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<String> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<String> listIterator(int i) {
                return null;
            }

            @NonNull
            @Override
            public List<String> subList(int i, int i1) {
                return null;
            }
        };
        //s = d.GetListOfClass();


        Runnable runnable = new Runnable() {
            public void run() {
                GetAllClass d = new GetAllClass();
                InsertUserDetails inS = new InsertUserDetails();
                inS.insertData("ENGR 292", 1.0);
                d.GetListOfClass();

            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();


*/

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }







}
