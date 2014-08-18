package com.allizgwats.converter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class WeightConverter extends Fragment{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public WeightConverter newInstance(int sectionNumber) {
        WeightConverter fragment = new WeightConverter();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public WeightConverter() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.weight_converter_fragment, container, false);

        attachListeners(rootView);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((converter_activity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    private void attachListeners(View rootView) {
        Spinner spinnerA = (Spinner) rootView.findViewById(R.id.weightConverter_spinnerA);
        spinnerA.setOnItemSelectedListener();

        Spinner spinnerB = (Spinner) rootView.findViewById(R.id.weightConverter_spinnerB);
        spinnerA.setOnItemSelectedListener();

        TextView convertedWeight = (TextView) rootView.findViewById(R.id.weightConverter_convertedWeightTextView);

        EditText originalWeight = (EditText) rootView.findViewById(R.id.weightConverter_originalWeightEditText);

    }


}