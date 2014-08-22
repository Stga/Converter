package com.allizgwats.converter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class LengthConverter extends Fragment{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    //Spinners that the user will use to select the metric they want to convert from and to
    private Spinner spinnerA;
    private Spinner spinnerB;

    //The converted metric will be placed into this TextView
    private TextView convertedLengthTextView;

    //The user will enter the number to be converted into this EditText
    private EditText originalLengthEditText;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public LengthConverter newInstance(int sectionNumber) {
        LengthConverter fragment = new LengthConverter();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public LengthConverter() {
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
        String[] spinnerMetricsArray = getResources().getStringArray(R.array.length_metrics);
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

        convertedLengthTextView = (TextView) rootView.findViewById(R.id.converter_convertedTextView);

        originalLengthEditText = (EditText) rootView.findViewById(R.id.converter_originalEditText);
        originalLengthEditText.setText("10");
        originalLengthEditText.addTextChangedListener(new CustomTextWatcher(originalLengthEditText));
        originalLengthEditText.setOnEditorActionListener(lengthEditedListener);

    }

    /**
     * Set the text of the convertedLengthTextView TextView
     * @param stringToSetInTextView the text to entry into the TextView
     */
    private void setConvertedLengthTextView(String stringToSetInTextView) {
        convertedLengthTextView.setText(stringToSetInTextView);
    }

    /**
     * Convert the value entered in the originalLengthEditText from
     * spinnerA's selected metric to spinnerB's selected metric
     * @param spinnerASelectedItemPosition the position of the currently selected view within spinnerA
     * @param spinnerBSelectedItemPosition the position of the currently selected view within spinnerB
     */
    private void calculateDifferencesBetweenMetrics(int spinnerASelectedItemPosition,
                                                    int spinnerBSelectedItemPosition) {
        if(originalLengthEditText.getText().toString().equals("")) {
            originalLengthEditText.setText("0");
        }

        final double originalWeightInput = Double.parseDouble(originalLengthEditText.getText().toString());
        double convertedWeight = 0;
        String identifier = "";

        switch(spinnerBSelectedItemPosition) {
            case 0: //Kilometer
                convertedWeight = convertToKilometer(spinnerASelectedItemPosition, originalWeightInput);
                identifier = "km";
                break;
            case 1: //Meter
                convertedWeight = convertToMeter(spinnerASelectedItemPosition, originalWeightInput);
                identifier = "m";
                break;
            case 2: //Centimeter
                convertedWeight = convertToCentimeter(spinnerASelectedItemPosition, originalWeightInput);
                identifier = "cm";
                break;
            case 3: //Millimeter
                convertedWeight = convertToMillimeter(spinnerASelectedItemPosition, originalWeightInput);
                identifier = "mm";
                break;
            case 4: //Mile
                convertedWeight = convertToMile(spinnerASelectedItemPosition, originalWeightInput);
                identifier = "mi";
                break;
            case 5: //Yard
                convertedWeight = convertToYard(spinnerASelectedItemPosition, originalWeightInput);
                identifier = "yd";
                break;
            case 6: //Foot
                convertedWeight = convertToFoot(spinnerASelectedItemPosition, originalWeightInput);
                identifier = "ft";
                break;
            case 7: //Inch
                convertedWeight = convertToInch(spinnerASelectedItemPosition, originalWeightInput);
                identifier = "in";
                break;
            case 8: //NauticalMile
                convertedWeight = convertToNauticalMile(spinnerASelectedItemPosition, originalWeightInput);
                identifier = "nm";
                break;
        }


        //Format the text by stripping all trailing zereos and adding in an identifier based on
        //what unit was converted to.
        String strippedConvertedWeight = new BigDecimal(convertedWeight)
                .stripTrailingZeros().toPlainString();

        //Format the String by adding in ',' in appropriate places
        DecimalFormat df = new DecimalFormat("#,###.##");
        String formattedConvertedWeight = df.format(Double.parseDouble(strippedConvertedWeight));

        setConvertedLengthTextView(formattedConvertedWeight + identifier);
    }

    /**
     * Convert the currently set number in originalLengthEditText from the metric selected
     * in spinnerA to Kilometer.
     * @param spinnerASelectedItemPosition The metric currently set in spinnerA
     * @param orignalWeightInput The number currently entered into the originalLengthEditText
     * @return the converted weight from spinnerA metric to Kilometer
     */
    private double convertToKilometer(int spinnerASelectedItemPosition, double orignalWeightInput) {

        double convertedValueToReturn = 0;

        switch(spinnerASelectedItemPosition) {
            case 0: //Kilometer
                convertedValueToReturn = orignalWeightInput;
                break;
            case 1: //Meter
                convertedValueToReturn = orignalWeightInput*0.001;
                break;
            case 2: //Centimeter
                convertedValueToReturn = orignalWeightInput*0.00001;
                break;
            case 3: //Millimeter
                convertedValueToReturn = orignalWeightInput*0.000001;
                break;
            case 4: //Mile
                convertedValueToReturn = orignalWeightInput*1.60934;
                break;
            case 5: //Yard
                convertedValueToReturn = orignalWeightInput*0.0009144;
                break;
            case 6: //Foot
                convertedValueToReturn = orignalWeightInput*0.0003048;
                break;
            case 7: //Inch
                convertedValueToReturn = orignalWeightInput*0.0000254;
                break;
            case 8: //NauticalMile
                convertedValueToReturn = orignalWeightInput*1.852;
                break;
        }

        return convertedValueToReturn;
    }

    /**
     * Convert the currently set number in originalLengthEditText from the metric selected
     * in spinnerA to Meter.
     * @param spinnerASelectedItemPosition The metric currently set in spinnerA
     * @param orignalWeightInput The number currently entered into the originalLengthEditText
     * @return the converted weight from spinnerA metric to Meter
     */
    private double convertToMeter(int spinnerASelectedItemPosition, double orignalWeightInput) {

        double convertedValueToReturn = 0;

        switch(spinnerASelectedItemPosition) {
            case 0: //Kilometer
                convertedValueToReturn = orignalWeightInput*1000;
                break;
            case 1: //Meter
                convertedValueToReturn = orignalWeightInput;
                break;
            case 2: //Centimeter
                convertedValueToReturn = orignalWeightInput*0.01;
                break;
            case 3: //Millimeter
                convertedValueToReturn = orignalWeightInput*0.001;
                break;
            case 4: //Mile
                convertedValueToReturn = orignalWeightInput*1609.34;
                break;
            case 5: //Yard
                convertedValueToReturn = orignalWeightInput*0.9144;
                break;
            case 6: //Foot
                convertedValueToReturn = orignalWeightInput*0.3048;
                break;
            case 7: //Inch
                convertedValueToReturn = orignalWeightInput*0.0254;
                break;
            case 8: //NauticalMile
                convertedValueToReturn = orignalWeightInput*1852;
                break;
        }

        return convertedValueToReturn;
    }

    /**
     * Convert the currently set number in originalLengthEditText from the metric selected
     * in spinnerA to Centimeter.
     * @param spinnerASelectedItemPosition The metric currently set in spinnerA
     * @param orignalWeightInput The number currently entered into the originalLengthEditText
     * @return the converted weight from spinnerA metric to Centimeter
     */
    private double convertToCentimeter(int spinnerASelectedItemPosition, double orignalWeightInput) {

        double convertedValueToReturn = 0;

        switch(spinnerASelectedItemPosition) {
            case 0: //Kilometer
                convertedValueToReturn = orignalWeightInput*100000;
                break;
            case 1: //Meter
                convertedValueToReturn = orignalWeightInput*100;
                break;
            case 2: //Centimeter
                convertedValueToReturn = orignalWeightInput;
                break;
            case 3: //Millimeter
                convertedValueToReturn = orignalWeightInput*0.1;
                break;
            case 4: //Mile
                convertedValueToReturn = orignalWeightInput*160934;
                break;
            case 5: //Yard
                convertedValueToReturn = orignalWeightInput*91.44;
                break;
            case 6: //Foot
                convertedValueToReturn = orignalWeightInput*30.48;
                break;
            case 7: //Inch
                convertedValueToReturn = orignalWeightInput*2.54;
                break;
            case 8: //NauticalMile
                convertedValueToReturn = orignalWeightInput*185200;
                break;
        }

        return convertedValueToReturn;
    }

    /**
     * Convert the currently set number in originalLengthEditText from the metric selected
     * in spinnerA to Millimeter.
     * @param spinnerASelectedItemPosition The metric currently set in spinnerA
     * @param orignalWeightInput The number currently entered into the originalLengthEditText
     * @return the converted weight from spinnerA metric to Millimeter
     */
    private double convertToMillimeter(int spinnerASelectedItemPosition, double orignalWeightInput) {

        double convertedValueToReturn = 0;

        switch(spinnerASelectedItemPosition) {
            case 0: //Kilometer
                convertedValueToReturn = orignalWeightInput*1000000;
                break;
            case 1: //Meter
                convertedValueToReturn = orignalWeightInput*1000;
                break;
            case 2: //Centimeter
                convertedValueToReturn = orignalWeightInput*10;
                break;
            case 3: //Millimeter
                convertedValueToReturn = orignalWeightInput;
                break;
            case 4: //Mile
                convertedValueToReturn = orignalWeightInput*1609000;
                break;
            case 5: //Yard
                convertedValueToReturn = orignalWeightInput*914.4;
                break;
            case 6: //Foot
                convertedValueToReturn = orignalWeightInput*304.8;
                break;
            case 7: //Inch
                convertedValueToReturn = orignalWeightInput*25.4;
                break;
            case 8: //NauticalMile
                convertedValueToReturn = orignalWeightInput*1852000;
                break;
        }

        return convertedValueToReturn;
    }

    /**
     * Convert the currently set number in originalLengthEditText from the metric selected
     * in spinnerA to Mile.
     * @param spinnerASelectedItemPosition The metric currently set in spinnerA
     * @param orignalWeightInput The number currently entered into the originalLengthEditText
     * @return the converted weight from spinnerA metric to Mile
     */
    private double convertToMile(int spinnerASelectedItemPosition, double orignalWeightInput) {

        double convertedValueToReturn = 0;

        switch(spinnerASelectedItemPosition) {
            case 0: //Kilometer
                convertedValueToReturn = orignalWeightInput*0.621371;
                break;
            case 1: //Meter
                convertedValueToReturn = orignalWeightInput*0.000621371;
                break;
            case 2: //Centimeter
                convertedValueToReturn = orignalWeightInput*0.0000062137;
                break;
            case 3: //Millimeter
                convertedValueToReturn = orignalWeightInput*0.00000062137;
                break;
            case 4: //Mile
                convertedValueToReturn = orignalWeightInput;
                break;
            case 5: //Yard
                convertedValueToReturn = orignalWeightInput*0.000568182;
                break;
            case 6: //Foot
                convertedValueToReturn = orignalWeightInput*0.000189394;
                break;
            case 7: //Inch
                convertedValueToReturn = orignalWeightInput*0.000015783;
                break;
            case 8: //NauticalMile
                convertedValueToReturn = orignalWeightInput*1.15078;
                break;
        }

        return convertedValueToReturn;
    }

    /**
     * Convert the currently set number in originalLengthEditText from the metric selected
     * in spinnerA to Yard.
     * @param spinnerASelectedItemPosition The metric currently set in spinnerA
     * @param orignalWeightInput The number currently entered into the originalLengthEditText
     * @return the converted weight from spinnerA metric to Yard
     */
    private double convertToYard(int spinnerASelectedItemPosition, double orignalWeightInput) {

        double convertedValueToReturn = 0;

        switch(spinnerASelectedItemPosition) {
            case 0: //Kilometer
                convertedValueToReturn = orignalWeightInput*1093.61;
                break;
            case 1: //Meter
                convertedValueToReturn = orignalWeightInput*1.09361;
                break;
            case 2: //Centimeter
                convertedValueToReturn = orignalWeightInput*0.0109361;
                break;
            case 3: //Millimeter
                convertedValueToReturn = orignalWeightInput*0.00109361;
                break;
            case 4: //Mile
                convertedValueToReturn = orignalWeightInput*1760;
                break;
            case 5: //Yard
                convertedValueToReturn = orignalWeightInput;
                break;
            case 6: //Foot
                convertedValueToReturn = orignalWeightInput*0.333333;
                break;
            case 7: //Inch
                convertedValueToReturn = orignalWeightInput*0.0277778;
                break;
            case 8: //NauticalMile
                convertedValueToReturn = orignalWeightInput*2025.37;
                break;
        }

        return convertedValueToReturn;
    }

    /**
     * Convert the currently set number in originalLengthEditText from the metric selected
     * in spinnerA to Foot.
     * @param spinnerASelectedItemPosition The metric currently set in spinnerA
     * @param orignalWeightInput The number currently entered into the originalLengthEditText
     * @return the converted weight from spinnerA metric to Foot
     */
    private double convertToFoot(int spinnerASelectedItemPosition, double orignalWeightInput) {

        double convertedValueToReturn = 0;

        switch(spinnerASelectedItemPosition) {
            case 0: //Kilometer
                convertedValueToReturn = orignalWeightInput*3280.84;
                break;
            case 1: //Meter
                convertedValueToReturn = orignalWeightInput*3.28084;
                break;
            case 2: //Centimeter
                convertedValueToReturn = orignalWeightInput*0.0328084;
                break;
            case 3: //Millimeter
                convertedValueToReturn = orignalWeightInput*0.00328084;
                break;
            case 4: //Mile
                convertedValueToReturn = orignalWeightInput*5280;
                break;
            case 5: //Yard
                convertedValueToReturn = orignalWeightInput*3;
                break;
            case 6: //Foot
                convertedValueToReturn = orignalWeightInput;
                break;
            case 7: //Inch
                convertedValueToReturn = orignalWeightInput*0.0833333;
                break;
            case 8: //NauticalMile
                convertedValueToReturn = orignalWeightInput*6076.12;
                break;
        }

        return convertedValueToReturn;
    }

    /**
     * Convert the currently set number in originalLengthEditText from the metric selected
     * in spinnerA to Inch.
     * @param spinnerASelectedItemPosition The metric currently set in spinnerA
     * @param orignalWeightInput The number currently entered into the originalLengthEditText
     * @return the converted weight from spinnerA metric to Inch
     */
    private double convertToInch(int spinnerASelectedItemPosition, double orignalWeightInput) {

        double convertedValueToReturn = 0;

        switch(spinnerASelectedItemPosition) {
            case 0: //Kilometer
                convertedValueToReturn = orignalWeightInput*39370.1;
                break;
            case 1: //Meter
                convertedValueToReturn = orignalWeightInput*39.3701;
                break;
            case 2: //Centimeter
                convertedValueToReturn = orignalWeightInput*0.393701;
                break;
            case 3: //Millimeter
                convertedValueToReturn = orignalWeightInput*0.0393701;
                break;
            case 4: //Mile
                convertedValueToReturn = orignalWeightInput*63360;
                break;
            case 5: //Yard
                convertedValueToReturn = orignalWeightInput*36;
                break;
            case 6: //Foot
                convertedValueToReturn = orignalWeightInput*12;
                break;
            case 7: //Inch
                convertedValueToReturn = orignalWeightInput;
                break;
            case 8: //NauticalMile
                convertedValueToReturn = orignalWeightInput*72913.4;
                break;
        }

        return convertedValueToReturn;
    }

    /**
     * Convert the currently set number in originalLengthEditText from the metric selected
     * in spinnerA to NauticalMile.
     * @param spinnerASelectedItemPosition The metric currently set in spinnerA
     * @param orignalWeightInput The number currently entered into the originalLengthEditText
     * @return the converted weight from spinnerA metric to NauticalMile
     */
    private double convertToNauticalMile(int spinnerASelectedItemPosition, double orignalWeightInput) {

        double convertedValueToReturn = 0;

        switch(spinnerASelectedItemPosition) {
            case 0: //Kilometer
                convertedValueToReturn = orignalWeightInput*0.539957;
                break;
            case 1: //Meter
                convertedValueToReturn = orignalWeightInput*0.000539957;
                break;
            case 2: //Centimeter
                convertedValueToReturn = orignalWeightInput*0.0000053996;
                break;
            case 3: //Millimeter
                convertedValueToReturn = orignalWeightInput*0.00000053996;
                break;
            case 4: //Mile
                convertedValueToReturn = orignalWeightInput*0.868976;
                break;
            case 5: //Yard
                convertedValueToReturn = orignalWeightInput*0.000493737;
                break;
            case 6: //Foot
                convertedValueToReturn = orignalWeightInput*0.000164579;
                break;
            case 7: //Inch
                convertedValueToReturn = orignalWeightInput*0.000013715;
                break;
            case 8: //NauticalMile
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
    private final TextView.OnEditorActionListener lengthEditedListener = new TextView.OnEditorActionListener() {
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

            Toast.makeText(getActivity(), "Please enter a length", Toast.LENGTH_SHORT).show();
            return true; //Do not close keyboard
        }
    };

    /**
     * Custom class for user input into the originalLengthEditText to ensure length of input
     * does not exceed a maximum threshold and that it is not the empty string which would cause
     * exception to be thrown due to automatic calculations that occur after editing the EditText.
     */
    private class CustomTextWatcher implements TextWatcher {

        private final EditText editTextCaller;
        private String beforeTextChangedString;

        public CustomTextWatcher(EditText caller) {
            editTextCaller = caller;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            beforeTextChangedString = charSequence.toString();
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

        @Override
        public void afterTextChanged(Editable editable) {
            final int maxLengthOfUserInput = 10;
            final int currentInputLength = editable.length();

            if(currentInputLength > maxLengthOfUserInput) {
                editTextCaller.setText(beforeTextChangedString);
                Toast.makeText(getActivity(), "Maximum digits reached", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
