package com.PocketMoodle;

/**
 * Created by David on 2017-04-02.
 */

        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

public class DisplayGradesFragment extends Fragment {

    private TextView setGrade;       // Hold the location of the grade in the xml

    private int grade;       // Hold value of grade

    public DisplayGradesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_class_page, container, false);

        // Initialize the locations of the grade
        setGrade       = (TextView) view.findViewById(R.id.setGrade);

        // Retrieve the grade chosen from classPageFragment
        Bundle bundle = getArguments();
        grade = bundle.getInt("GradeValue");

        // Set the details of the grade on a fragment.
        setGrade.setText(grade);

        return view;
    }
}
