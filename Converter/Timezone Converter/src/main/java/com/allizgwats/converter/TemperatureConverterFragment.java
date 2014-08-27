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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class TemperatureConverterFragment extends Fragment{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    //Spinners that the user will use to select the metric they want to convert from and to
    private Spinner spinnerA;
    private Spinner spinnerB;

    //The converted metric will be placed into this TextView
    private TextView convertedTemperatureTextView;

    //The user will enter the number to be converted into this EditText
    private EditText originalTemperatureEditText;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public TemperatureConverterFragment newInstance(int sectionNumber) {
        TemperatureConverterFragment fragment = new TemperatureConverterFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public TemperatureConverterFragment() {
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
        String[] spinnerMetricsArray = getResources().getStringArray(R.array.temperature_metrics);
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

        convertedTemperatureTextView = (TextView) rootView.findViewById(R.id.converter_convertedTextView);

        originalTemperatureEditText = (EditText) rootView.findViewById(R.id.converter_originalEditText);
        originalTemperatureEditText.setText("10");
        originalTemperatureEditText.addTextChangedListener
                (new CustomTextWatcher(originalTemperatureEditText, getActivity()));
        originalTemperatureEditText.setOnEditorActionListener(temperatureEditedListener);
    }

    /**
     * Convert the value entered in the originalTemperature EditText from
     * spinnerA's selected metric to spinnerB's selected metric
     * @param spinnerASelectedItemPosition the position of the currently selected view within spinnerA
     * @param spinnerBSelectedItemPosition the position of the currently selected view within spinnerB
     */
    private void calculateDifferencesBetweenMetrics(int spinnerASelectedItemPosition,
                                                    int spinnerBSelectedItemPosition) {
        if(originalTemperatureEditText.getText().toString().equals("")) {
            originalTemperatureEditText.setText("0");
        }

        final double originalTemperatureInput = Double.parseDouble(originalTemperatureEditText.getText().toString());
        double convertedTemperature = 0;
        String identifier = "";

        switch(spinnerBSelectedItemPosition) {
            case 0: //Celsius
                convertedTemperature = convertToCelsius(spinnerASelectedItemPosition, originalTemperatureInput);
                identifier = "C";
                break;
            case 1: //Fahrenheit
                convertedTemperature = convertToFahrenheit(spinnerASelectedItemPosition, originalTemperatureInput);
                identifier = "F";
                break;
            case 2: //Kelvin
                convertedTemperature = convertToKelvin(spinnerASelectedItemPosition, originalTemperatureInput);
                identifier = "K";
                break;
        }

        Converter.setConvertedSpeedTextView(convertedTemperatureTextView, identifier, convertedTemperature);
    }

    /**
     * Convert the currently set number in originalTemperatureEditText from the metric selected
     * in spinnerA to Celsius.
     * @param spinnerASelectedItemPosition The metric currently set in spinnerA
     * @param orignalTemperatureInput The number currently entered into the originalTemperatureEditText
     * @return the converted temperature from spinnerA metric to Celsius
     */
    private double convertToCelsius(int spinnerASelectedItemPosition, double orignalTemperatureInput) {

        double convertedValueToReturn = 0;

        switch(spinnerASelectedItemPosition) {
            case 0: //Celsius
                convertedValueToReturn = orignalTemperatureInput;
                break;
            case 1: //Fahrenheit Deduct 32, then multiply by 5, then divide by 9
                convertedValueToReturn = ((orignalTemperatureInput-32)*5)/9;
                break;
            case 2: //Kelvin
                convertedValueToReturn = orignalTemperatureInput-273.15;
                break;
        }

        return convertedValueToReturn;
    }

    /**
     * Convert the currently set number in originalTemperatureEditText from the metric selected
     * in spinnerA to Fahrenheit.
     * @param spinnerASelectedItemPosition The metric currently set in spinnerA
     * @param orignalTemperatureInput The number currently entered into the originalTemperatureEditText
     * @return the converted temperature from spinnerA metric to Fahrenheit
     */
    private double convertToFahrenheit(int spinnerASelectedItemPosition, double orignalTemperatureInput) {

        double convertedValueToReturn = 0;

        switch(spinnerASelectedItemPosition) {
            case 0: //Celsius Multiply by 9, then divide by 5, then add 32
                convertedValueToReturn = ((orignalTemperatureInput*9)/5)+32;
                break;
            case 1: //Fahrenheit
                convertedValueToReturn = orignalTemperatureInput;
                break;
            case 2: //Kelvin (K - 273.15)* 1.8000 + 32.00
                convertedValueToReturn = (orignalTemperatureInput - 273.15)* 1.8000 + 32.00;
                break;
        }

        return convertedValueToReturn;
    }

    /**
     * Convert the currently set number in originalTemperatureEditText from the metric selected
     * in spinnerA to Kelvin.
     * @param spinnerASelectedItemPosition The metric currently set in spinnerA
     * @param orignalTemperatureInput The number currently entered into the originalTemperatureEditText
     * @return the converted temperature from spinnerA metric to Kelvin
     */
    private double convertToKelvin(int spinnerASelectedItemPosition, double orignalTemperatureInput) {

        double convertedValueToReturn = 0;

        switch(spinnerASelectedItemPosition) {
            case 0: //Celsius
                convertedValueToReturn = orignalTemperatureInput+273.15;
                break;
            case 1: //Fahrenheit (Fahrenheit + 459.67) * 5 / 9
                convertedValueToReturn = ((orignalTemperatureInput+ 459.67)*5)/9;
                break;
            case 2: //Kelvin
                convertedValueToReturn = orignalTemperatureInput;
                break;
        }

        return convertedValueToReturn;
    }

    /*******************************
     * LISTENERS BEYOND THIS POINT *
     *******************************/
    private final OnItemSelectedListener spinnerItemSelectedListener = new OnItemSelectedListener() {
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
    private final TextView.OnEditorActionListener temperatureEditedListener = new TextView.OnEditorActionListener() {
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

            Toast.makeText(getActivity(), "Please enter a temperature", Toast.LENGTH_SHORT).show();
            return true; //Do not close keyboard
        }
    };



}