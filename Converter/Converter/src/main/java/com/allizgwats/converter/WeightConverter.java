package com.allizgwats.converter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class WeightConverter extends Fragment{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    //Spinners that the user will use to select the metric they want to convert from and to
    Spinner spinnerA;
    Spinner spinnerB;

    //The converted metric will be placed into this TextView
    TextView convertedWeightTextView;

    //The user will enter the number to be converted into this EditText
    EditText originalWeightEditText;

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
        spinnerA = (Spinner) rootView.findViewById(R.id.weightConverter_spinnerA);
        spinnerA.setOnItemSelectedListener(spinnerItemSelectedListener);

        spinnerB = (Spinner) rootView.findViewById(R.id.weightConverter_spinnerB);
        spinnerB.setOnItemSelectedListener(spinnerItemSelectedListener);

        convertedWeightTextView = (TextView) rootView.findViewById(R.id.weightConverter_convertedWeightTextView);

        originalWeightEditText = (EditText) rootView.findViewById(R.id.weightConverter_originalWeightEditText);
        originalWeightEditText.addTextChangedListener(new CustomTextWatcher(originalWeightEditText));

    }

    /**
     * Convert the value entered in the originalWeight EditText from
     * spinnerA's selected metric to spinnerB's selected metric
     * @param spinnerASelectedItemPosition the position of the currently selected view within spinnerA
     * @param spinnerBSelectedItemPosition the position of the currently selected view within spinnerB
     */
    private void calculateDifferencesBetweenMetrics(int spinnerASelectedItemPosition,
                                                    int spinnerBSelectedItemPosition) {

        final double originalWeightInput = Double.parseDouble(originalWeightEditText.getText().toString());
        double convertedWeight = 0;
        String identifier = "";

        switch(spinnerBSelectedItemPosition) {
            case 0: //Centigram
                convertedWeight = convertToCentigram(spinnerASelectedItemPosition, originalWeightInput);
                identifier = "cg";
                break;
            case 1: //Decigram
                convertedWeight = convertToDecigram(spinnerASelectedItemPosition, originalWeightInput);
                identifier = "dg";
                break;
            case 2: //Dekagram
                convertedWeight = convertToDekagram(spinnerASelectedItemPosition, originalWeightInput);
                identifier = "dag";
                break;
            case 3: //Gram
                convertedWeight = convertToGram(spinnerASelectedItemPosition, originalWeightInput);
                identifier = "g";
                break;
            case 4: //Kilogram
                convertedWeight = convertToKilogram(spinnerASelectedItemPosition, originalWeightInput);
                identifier = "kg";
                break;
            case 5: //Milligram
                convertedWeight = convertToMilligram(spinnerASelectedItemPosition, originalWeightInput);
                identifier = "mg";
                break;
            case 6: //Ounce
                convertedWeight = convertToOunce(spinnerASelectedItemPosition, originalWeightInput);
                identifier = "oz";
                break;
            case 7: //Pound
                convertedWeight = convertToPound(spinnerASelectedItemPosition, originalWeightInput);
                identifier = "lb";
                break;
            case 8: //Stone
                convertedWeight = convertToStone(spinnerASelectedItemPosition, originalWeightInput);
                identifier = "st";
                break;
            case 9: //Ton
                convertedWeight = convertToTon(spinnerASelectedItemPosition, originalWeightInput);
                identifier = "t";
                break;
        }

        String formattedConvertedWeight = String.format("%1$,.2f", convertedWeight);
        convertedWeightTextView.setText(formattedConvertedWeight + identifier);
    }

    private double convertToCentigram(int spinnerASelectedItemPosition, double orignalWeightInput) {

        double convertedValueToReturn = 0;

        switch(spinnerASelectedItemPosition) {
            case 0: //Centigram
                convertedValueToReturn = orignalWeightInput;
                break;
            case 1: //Decigram
                convertedValueToReturn = orignalWeightInput*10;
                break;
            case 2: //Dekagram
                convertedValueToReturn = orignalWeightInput*1000;
                break;
            case 3: //Gram
                convertedValueToReturn = orignalWeightInput*100;
                break;
            case 4: //Kilogram
                convertedValueToReturn = orignalWeightInput*100000;
                break;
            case 5: //Milligram
                convertedValueToReturn = orignalWeightInput*0.1;
                break;
            case 6: //Ounce
                convertedValueToReturn = orignalWeightInput*2834.95231;
                break;
            case 7: //Pound
                convertedValueToReturn = orignalWeightInput*45359.237;
                break;
            case 8: //Stone
                convertedValueToReturn = orignalWeightInput*635029.318;
                break;
            case 9: //Ton
                convertedValueToReturn = orignalWeightInput*100000000;
                break;
        }

        return convertedValueToReturn;
    }

    private double convertToDecigram(int spinnerASelectedItemPosition, double orignalWeightInput) {

        double convertedValueToReturn = 0;

        switch(spinnerASelectedItemPosition) {
            case 0: //Centigram
                convertedValueToReturn = orignalWeightInput*0.1;
                break;
            case 1: //Decigram
                convertedValueToReturn = orignalWeightInput;
                break;
            case 2: //Dekagram
                convertedValueToReturn = orignalWeightInput*100;
                break;
            case 3: //Gram
                convertedValueToReturn = orignalWeightInput*10;
                break;
            case 4: //Kilogram
                convertedValueToReturn = orignalWeightInput*10000;
                break;
            case 5: //Milligram
                convertedValueToReturn = orignalWeightInput*0.01;
                break;
            case 6: //Ounce
                convertedValueToReturn = orignalWeightInput*283.49523125;
                break;
            case 7: //Pound
                convertedValueToReturn = orignalWeightInput*4535.9237;
                break;
            case 8: //Stone
                convertedValueToReturn = orignalWeightInput*63502.9318;
                break;
            case 9: //Ton
                convertedValueToReturn = orignalWeightInput*10000000;
                break;
        }

        return convertedValueToReturn;
    }

    private double convertToDekagram(int spinnerASelectedItemPosition, double orignalWeightInput) {

        double convertedValueToReturn = 0;

        switch(spinnerASelectedItemPosition) {
            case 0: //Centigram
                convertedValueToReturn = orignalWeightInput*0.001;
                break;
            case 1: //Decigram
                convertedValueToReturn = orignalWeightInput*0.01;
                break;
            case 2: //Dekagram
                convertedValueToReturn = orignalWeightInput;
                break;
            case 3: //Gram
                convertedValueToReturn = orignalWeightInput*0.1;
                break;
            case 4: //Kilogram
                convertedValueToReturn = orignalWeightInput*100;
                break;
            case 5: //Milligram
                convertedValueToReturn = orignalWeightInput*0.0001;
                break;
            case 6: //Ounce
                convertedValueToReturn = orignalWeightInput*2.83495231;
                break;
            case 7: //Pound
                convertedValueToReturn = orignalWeightInput*45.359237;
                break;
            case 8: //Stone
                convertedValueToReturn = orignalWeightInput*635.029318;
                break;
            case 9: //Ton
                convertedValueToReturn = orignalWeightInput*0.00635029;
                break;
        }

        return convertedValueToReturn;
    }

    private double convertToGram(int spinnerASelectedItemPosition, double orignalWeightInput) {

        double convertedValueToReturn = 0;

        switch(spinnerASelectedItemPosition) {
            case 0: //Centigram
                convertedValueToReturn = orignalWeightInput*0.01;
                break;
            case 1: //Decigram
                convertedValueToReturn = orignalWeightInput*0.1;
                break;
            case 2: //Dekagram
                convertedValueToReturn = orignalWeightInput*10;
                break;
            case 3: //Gram
                convertedValueToReturn = orignalWeightInput;
                break;
            case 4: //Kilogram
                convertedValueToReturn = orignalWeightInput*1000;
                break;
            case 5: //Milligram
                convertedValueToReturn = orignalWeightInput*0.001;
                break;
            case 6: //Ounce
                convertedValueToReturn = orignalWeightInput*28.3495;
                break;
            case 7: //Pound
                convertedValueToReturn = orignalWeightInput*453.592;
                break;
            case 8: //Stone
                convertedValueToReturn = orignalWeightInput*6350.29;
                break;
            case 9: //Ton
                convertedValueToReturn = orignalWeightInput*1000000;
                break;
        }

        return convertedValueToReturn;
    }

    private double convertToKilogram(int spinnerASelectedItemPosition, double orignalWeightInput) {

        double convertedValueToReturn = 0;

        switch(spinnerASelectedItemPosition) {
            case 0: //Centigram
                convertedValueToReturn = orignalWeightInput*0.00001;
                break;
            case 1: //Decigram
                convertedValueToReturn = orignalWeightInput*0.0001;
                break;
            case 2: //Dekagram
                convertedValueToReturn = orignalWeightInput*0.01;
                break;
            case 3: //Gram
                convertedValueToReturn = orignalWeightInput*0.001;
                break;
            case 4: //Kilogram
                convertedValueToReturn = orignalWeightInput;
                break;
            case 5: //Milligram
                convertedValueToReturn = orignalWeightInput*0.000001;
                break;
            case 6: //Ounce
                convertedValueToReturn = orignalWeightInput*0.0283495;
                break;
            case 7: //Pound
                convertedValueToReturn = orignalWeightInput*0.453592;
                break;
            case 8: //Stone
                convertedValueToReturn = orignalWeightInput*6.35029;
                break;
            case 9: //Ton
                convertedValueToReturn = orignalWeightInput*907.185;
                break;
        }

        return convertedValueToReturn;
    }

    private double convertToMilligram(int spinnerASelectedItemPosition, double orignalWeightInput) {

        double convertedValueToReturn = 0;

        switch(spinnerASelectedItemPosition) {
            case 0: //Centigram
                convertedValueToReturn = orignalWeightInput*10;
                break;
            case 1: //Decigram
                convertedValueToReturn = orignalWeightInput*001;
                break;
            case 2: //Dekagram
                convertedValueToReturn = orignalWeightInput*10000;
                break;
            case 3: //Gram
                convertedValueToReturn = orignalWeightInput*1000;
                break;
            case 4: //Kilogram
                convertedValueToReturn = orignalWeightInput*1000000;
                break;
            case 5: //Milligram
                convertedValueToReturn = orignalWeightInput;
                break;
            case 6: //Ounce
                convertedValueToReturn = orignalWeightInput*0.000035274;
                break;
            case 7: //Pound
                convertedValueToReturn = orignalWeightInput*453592;
                break;
            case 8: //Stone
                convertedValueToReturn = orignalWeightInput*6350293.18;
                break;
            case 9: //Ton
                convertedValueToReturn = orignalWeightInput*1000000000;
                break;
        }

        return convertedValueToReturn;
    }

    private double convertToOunce(int spinnerASelectedItemPosition, double orignalWeightInput) {

        double convertedValueToReturn = 0;

        switch(spinnerASelectedItemPosition) {
            case 0: //Centigram
                convertedValueToReturn = orignalWeightInput*0.000352739619;
                break;
            case 1: //Decigram
                convertedValueToReturn = orignalWeightInput*0.00352739619;
                break;
            case 2: //Dekagram
                convertedValueToReturn = orignalWeightInput*0.352739619;
                break;
            case 3: //Gram
                convertedValueToReturn = orignalWeightInput*0.035274;
                break;
            case 4: //Kilogram
                convertedValueToReturn = orignalWeightInput*35.274;
                break;
            case 5: //Milligram
                convertedValueToReturn = orignalWeightInput*0.000035274;
                break;
            case 6: //Ounce
                convertedValueToReturn = orignalWeightInput;
                break;
            case 7: //Pound
                convertedValueToReturn = orignalWeightInput*16;
                break;
            case 8: //Stone
                convertedValueToReturn = orignalWeightInput*224;
                break;
            case 9: //Ton
                convertedValueToReturn = orignalWeightInput*35274;
                break;
        }

        return convertedValueToReturn;
    }

    private double convertToPound(int spinnerASelectedItemPosition, double orignalWeightInput) {

        double convertedValueToReturn = 0;

        switch(spinnerASelectedItemPosition) {
            case 0: //Centigram
                convertedValueToReturn = orignalWeightInput*0.000022046226218;
                break;
            case 1: //Decigram
                convertedValueToReturn = orignalWeightInput*0.000220462262;
                break;
            case 2: //Dekagram
                convertedValueToReturn = orignalWeightInput*0.0220462262;
                break;
            case 3: //Gram
                convertedValueToReturn = orignalWeightInput*0.00220462;
                break;
            case 4: //Kilogram
                convertedValueToReturn = orignalWeightInput*2.20462;
                break;
            case 5: //Milligram
                convertedValueToReturn = orignalWeightInput*0.0000022046226218;
                break;
            case 6: //Ounce
                convertedValueToReturn = orignalWeightInput*0.0625;
                break;
            case 7: //Pound
                convertedValueToReturn = orignalWeightInput;
                break;
            case 8: //Stone
                convertedValueToReturn = orignalWeightInput*14;
                break;
            case 9: //Ton
                convertedValueToReturn = orignalWeightInput*2204.62;
                break;
        }

        return convertedValueToReturn;
    }

    private double convertToStone(int spinnerASelectedItemPosition, double orignalWeightInput) {

        double convertedValueToReturn = 0;

        switch(spinnerASelectedItemPosition) {
            case 0: //Centigram
                convertedValueToReturn = orignalWeightInput*0.000001575;
                break;
            case 1: //Decigram
                convertedValueToReturn = orignalWeightInput*0.0000157473044;
                break;
            case 2: //Dekagram
                convertedValueToReturn = orignalWeightInput*0.00157473044;
                break;
            case 3: //Gram
                convertedValueToReturn = orignalWeightInput*0.000157473;
                break;
            case 4: //Kilogram
                convertedValueToReturn = orignalWeightInput*0.157473;
                break;
            case 5: //Milligram
                convertedValueToReturn = orignalWeightInput*0.000000157473;
                break;
            case 6: //Ounce
                convertedValueToReturn = orignalWeightInput*0.00446429;
                break;
            case 7: //Pound
                convertedValueToReturn = orignalWeightInput*0.0714286;
                break;
            case 8: //Stone
                convertedValueToReturn = orignalWeightInput;
                break;
            case 9: //Ton
                convertedValueToReturn = orignalWeightInput*157.473044;
                break;
        }

        return convertedValueToReturn;
    }

    private double convertToTon(int spinnerASelectedItemPosition, double orignalWeightInput) {

        double convertedValueToReturn = 0;

        switch(spinnerASelectedItemPosition) {
            case 0: //Centigram
                convertedValueToReturn = orignalWeightInput*0.00000001;
                break;
            case 1: //Decigram
                convertedValueToReturn = orignalWeightInput*0.0000001;
                break;
            case 2: //Dekagram
                convertedValueToReturn = orignalWeightInput*0.00001;
                break;
            case 3: //Gram
                convertedValueToReturn = orignalWeightInput*0.000001;
                break;
            case 4: //Kilogram
                convertedValueToReturn = orignalWeightInput*0.001;
                break;
            case 5: //Milligram
                convertedValueToReturn = orignalWeightInput*0.000000001;
                break;
            case 6: //Ounce
                convertedValueToReturn = orignalWeightInput*0.000028349523125;
                break;
            case 7: //Pound
                convertedValueToReturn = orignalWeightInput*0.00045359237;
                break;
            case 8: //Stone
                convertedValueToReturn = orignalWeightInput*0.00635029318;
                break;
            case 9: //Ton
                convertedValueToReturn = orignalWeightInput;
                break;
        }

        return convertedValueToReturn;
    }


                            /*******************************
                             * LISTENERS BEYOND THIS POINT *
                             *******************************/
    OnItemSelectedListener spinnerItemSelectedListener = new OnItemSelectedListener() {
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
     * Custom class for handling both hours and minutes inputting. Maintains minimum and maximum
     * values allowed in an edit text. Assign leading zeroes to single digits is handled in
     * the onEditorActionListener.
     */
    private class CustomTextWatcher implements TextWatcher {

        EditText editTextCaller;

        public CustomTextWatcher(EditText caller) {
            editTextCaller = caller;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

        @Override
        public void afterTextChanged(Editable editable) {
            final int maxLengthOfUserInput = 10;
            final int currentInputLength = editable.length();
            final String maxInput = "9 999 999 999";

            if(currentInputLength > maxLengthOfUserInput) {
                editTextCaller.setText(maxInput);
            }
            else if(currentInputLength == 0) {
                editTextCaller.setText("0");
            }

            final int spinnerASelectedItem = spinnerA.getSelectedItemPosition();
            final int spinnerBSelectedItem = spinnerB.getSelectedItemPosition();

            calculateDifferencesBetweenMetrics(spinnerASelectedItem, spinnerBSelectedItem);
        }
    }

}