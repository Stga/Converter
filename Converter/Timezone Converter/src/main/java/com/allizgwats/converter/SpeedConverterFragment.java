package com.allizgwats.converter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SpeedConverterFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    //Spinners that the user will use to select the metric they want to convert from and to
    private Spinner spinnerA;
    private Spinner spinnerB;

    //The converted metric will be placed into this TextView
    private TextView convertedSpeedTextView;

    //The user will enter the number to be converted into this EditText
    private EditText originalSpeedEditText;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public SpeedConverterFragment newInstance(int sectionNumber) {
        SpeedConverterFragment fragment = new SpeedConverterFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public SpeedConverterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.converter_fragment, container, false);

        initializeViews(rootView);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((converter_activity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    private void initializeViews(View rootView) {
        String[] spinnerMetricsArray = getResources().getStringArray(R.array.speed_metrics);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                spinnerMetricsArray);

        spinnerA = (Spinner) rootView.findViewById(R.id.converter_spinnerA);
        spinnerA.setAdapter(spinnerAdapter);
        spinnerA.setOnItemSelectedListener(spinnerItemSelectedListener);

        spinnerB = (Spinner) rootView.findViewById(R.id.converter_spinnerB);
        spinnerB.setAdapter(spinnerAdapter);
        spinnerB.setOnItemSelectedListener(spinnerItemSelectedListener);

        convertedSpeedTextView = (TextView) rootView.findViewById(R.id.converter_convertedTextView);

        originalSpeedEditText = (EditText) rootView.findViewById(R.id.converter_originalEditText);
        originalSpeedEditText.setText("10");
        originalSpeedEditText.addTextChangedListener
                (new CustomTextWatcher(originalSpeedEditText, getActivity()));
        originalSpeedEditText.setOnEditorActionListener(SpeedEditedListener);

    }

    /**
     * Convert the value entered in the originalSpeedEditText from
     * spinnerA's selected metric to spinnerB's selected metric
     * @param spinnerASelectedItemPosition the position of the currently selected view within spinnerA
     * @param spinnerBSelectedItemPosition the position of the currently selected view within spinnerB
     */
    private void calculateDifferencesBetweenMetrics(int spinnerASelectedItemPosition,
                                                    int spinnerBSelectedItemPosition) {
        if(originalSpeedEditText.getText().toString().equals("")) {
            originalSpeedEditText.setText("0");
        }

        final double originalWeightInput = Double.parseDouble(originalSpeedEditText.getText().toString());
        double convertedWeight = 0;
        String identifier = "";

        switch(spinnerBSelectedItemPosition) {
            case 0: //MilesHour
                convertedWeight = convertToMilesHour(spinnerASelectedItemPosition, originalWeightInput);
                identifier = "mph";
                break;
            case 1: //FeetSec
                convertedWeight = convertToFeetSec(spinnerASelectedItemPosition, originalWeightInput);
                identifier = "fps";
                break;
            case 2: //MetersSec
                convertedWeight = convertToMetersSec(spinnerASelectedItemPosition, originalWeightInput);
                identifier = "mps";
                break;
            case 3: //KmHour
                convertedWeight = convertToKmHour(spinnerASelectedItemPosition, originalWeightInput);
                identifier = "kph";
                break;
            case 4: //Knot
                convertedWeight = convertToKnot(spinnerASelectedItemPosition, originalWeightInput);
                identifier = "kts";
                break;
        }

        Converter.setConvertedSpeedTextView(convertedSpeedTextView, identifier, convertedWeight);
    }

    /**
     * Convert the currently set number in originalSpeedEditText from the metric selected
     * in spinnerA to MilesHour.
     * @param spinnerASelectedItemPosition The metric currently set in spinnerA
     * @param orignalWeightInput The number currently entered into the originalSpeedEditText
     * @return the converted weight from spinnerA metric to MilesHour
     */
    private double convertToMilesHour(int spinnerASelectedItemPosition, double orignalWeightInput) {

        double convertedValueToReturn = 0;

        switch(spinnerASelectedItemPosition) {
            case 0: //MilesHour
                convertedValueToReturn = orignalWeightInput;
                break;
            case 1: //FeetSec
                convertedValueToReturn = orignalWeightInput*0.681818;
                break;
            case 2: //MetersSec
                convertedValueToReturn = orignalWeightInput*2.23694;
                break;
            case 3: //KmHour
                convertedValueToReturn = orignalWeightInput*0.621371;
                break;
            case 4: //Knot
                convertedValueToReturn = orignalWeightInput*1.15078;
                break;
        }

        return convertedValueToReturn;
    }

    /**
     * Convert the currently set number in originalSpeedEditText from the metric selected
     * in spinnerA to FeetSec.
     * @param spinnerASelectedItemPosition The metric currently set in spinnerA
     * @param orignalWeightInput The number currently entered into the originalSpeedEditText
     * @return the converted weight from spinnerA metric to FeetSec
     */
    private double convertToFeetSec(int spinnerASelectedItemPosition, double orignalWeightInput) {

        double convertedValueToReturn = 0;

        switch(spinnerASelectedItemPosition) {
            case 0: //MilesHour
                convertedValueToReturn = orignalWeightInput*1.46667;
                break;
            case 1: //FeetSec
                convertedValueToReturn = orignalWeightInput;
                break;
            case 2: //MetersSec
                convertedValueToReturn = orignalWeightInput*3.28084;
                break;
            case 3: //KmHour
                convertedValueToReturn = orignalWeightInput*0.911344;
                break;
            case 4: //Knot
                convertedValueToReturn = orignalWeightInput*1.68781;
                break;
        }

        return convertedValueToReturn;
    }

    /**
     * Convert the currently set number in originalSpeedEditText from the metric selected
     * in spinnerA to MetersSec.
     * @param spinnerASelectedItemPosition The metric currently set in spinnerA
     * @param orignalWeightInput The number currently entered into the originalSpeedEditText
     * @return the converted weight from spinnerA metric to MetersSec
     */
    private double convertToMetersSec(int spinnerASelectedItemPosition, double orignalWeightInput) {

        double convertedValueToReturn = 0;

        switch(spinnerASelectedItemPosition) {
            case 0: //MilesHour
                convertedValueToReturn = orignalWeightInput*0.44704;
                break;
            case 1: //FeetSec
                convertedValueToReturn = orignalWeightInput*0.3048;
                break;
            case 2: //MetersSec
                convertedValueToReturn = orignalWeightInput;
                break;
            case 3: //KmHour
                convertedValueToReturn = orignalWeightInput*0.277778;
                break;
            case 4: //Knot
                convertedValueToReturn = orignalWeightInput*0.514444;
                break;
        }

        return convertedValueToReturn;
    }

    /**
     * Convert the currently set number in originalSpeedEditText from the metric selected
     * in spinnerA to KmHour.
     * @param spinnerASelectedItemPosition The metric currently set in spinnerA
     * @param orignalWeightInput The number currently entered into the originalSpeedEditText
     * @return the converted weight from spinnerA metric to KmHour
     */
    private double convertToKmHour(int spinnerASelectedItemPosition, double orignalWeightInput) {

        double convertedValueToReturn = 0;

        switch(spinnerASelectedItemPosition) {
            case 0: //MilesHour
                convertedValueToReturn = orignalWeightInput*1.60934;
                break;
            case 1: //FeetSec
                convertedValueToReturn = orignalWeightInput*1.09728;
                break;
            case 2: //MetersSec
                convertedValueToReturn = orignalWeightInput*3.6;
                break;
            case 3: //KmHour
                convertedValueToReturn = orignalWeightInput;
                break;
            case 4: //Knot
                convertedValueToReturn = orignalWeightInput*1.852;
                break;
        }

        return convertedValueToReturn;
    }

    /**
     * Convert the currently set number in originalSpeedEditText from the metric selected
     * in spinnerA to Knot.
     * @param spinnerASelectedItemPosition The metric currently set in spinnerA
     * @param orignalWeightInput The number currently entered into the originalSpeedEditText
     * @return the converted weight from spinnerA metric to Knot
     */
    private double convertToKnot(int spinnerASelectedItemPosition, double orignalWeightInput) {

        double convertedValueToReturn = 0;

        switch(spinnerASelectedItemPosition) {
            case 0: //MilesHour
                convertedValueToReturn = orignalWeightInput*0.868976;
                break;
            case 1: //FeetSec
                convertedValueToReturn = orignalWeightInput*0.592484;
                break;
            case 2: //MetersSec
                convertedValueToReturn = orignalWeightInput*1.94384;
                break;
            case 3: //KmHour
                convertedValueToReturn = orignalWeightInput*0.539957;
                break;
            case 4: //Knot
                convertedValueToReturn = orignalWeightInput;
                break;
        }

        return convertedValueToReturn;
    }

    /*******************************
     * LISTENERS BEYOND THIS POINT *
     *******************************/
    private final AdapterView.OnItemSelectedListener spinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            final int spinnerASelectedItem = spinnerA.getSelectedItemPosition();
            final int spinnerBSelectedItem = spinnerB.getSelectedItemPosition();

            calculateDifferencesBetweenMetrics(spinnerASelectedItem, spinnerBSelectedItem);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };

    /**
     * OnEditorActionListener for the time input EditText's. Used for adding zero to replace
     * empty string input
     */
    private final TextView.OnEditorActionListener SpeedEditedListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int eventId, KeyEvent keyEvent) {
            boolean isDone = eventId==EditorInfo.IME_ACTION_DONE
                    || keyEvent.getAction() == KeyEvent.KEYCODE_BACK;

            if(isDone && textView.getText() != null && !textView.getText().toString().equals("")) {
                final int spinnerASelectedItem = spinnerA.getSelectedItemPosition();
                final int spinnerBSelectedItem = spinnerB.getSelectedItemPosition();
                calculateDifferencesBetweenMetrics(spinnerASelectedItem, spinnerBSelectedItem);

                return false; //Close the keyboard
            }

            Toast.makeText(getActivity(), "Please enter a Speed", Toast.LENGTH_SHORT).show();
            return true; //Do not close keyboard
        }
    };
}
