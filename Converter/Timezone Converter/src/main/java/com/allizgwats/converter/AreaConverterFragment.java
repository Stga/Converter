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

public class AreaConverterFragment extends Fragment{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    //Spinners that the user will use to select the metric they want to convert from and to
    private Spinner spinnerA;
    private Spinner spinnerB;

    //The converted metric will be placed into this TextView
    private TextView convertedAreaTextView;

    //The user will enter the number to be converted into this EditText
    private EditText originalAreaEditText;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public AreaConverterFragment newInstance(int sectionNumber) {
        AreaConverterFragment fragment = new AreaConverterFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public AreaConverterFragment(){}

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
        String[] spinnerMetricsArray = getResources().getStringArray(R.array.area_metrics);
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

        convertedAreaTextView = (TextView) rootView.findViewById(R.id.converter_convertedTextView);

        originalAreaEditText = (EditText) rootView.findViewById(R.id.converter_originalEditText);
        originalAreaEditText.setText("10");
        originalAreaEditText.addTextChangedListener
                (new CustomTextWatcher(originalAreaEditText, getActivity()));
        originalAreaEditText.setOnEditorActionListener(AreaEditedListener);

    }

    /**
     * Convert the value entered in the originalAreaEditText from
     * spinnerA's selected metric to spinnerB's selected metric
     * @param spinnerASelectedItemPosition the position of the currently selected view within spinnerA
     * @param spinnerBSelectedItemPosition the position of the currently selected view within spinnerB
     */
    private void calculateDifferencesBetweenMetrics(int spinnerASelectedItemPosition,
                                                    int spinnerBSelectedItemPosition) {
        if(originalAreaEditText.getText().toString().equals("")) {
            originalAreaEditText.setText("0");
        }

        final double originalAreaInput = Double.parseDouble(originalAreaEditText.getText().toString());
        double convertedArea = 0;
        String identifier = "";

        switch(spinnerBSelectedItemPosition) {
            case 0: //SquareKm
                convertedArea = convertToSquareKm(spinnerASelectedItemPosition, originalAreaInput);
                identifier = "Sq Km";
                break;
            case 1: //Hectare
                convertedArea = convertToHectare(spinnerASelectedItemPosition, originalAreaInput);
                identifier = "Ha";
                break;
            case 2: //SquareMeter
                convertedArea = convertToSquareMeter(spinnerASelectedItemPosition, originalAreaInput);
                identifier = "Sq M";
                break;
            case 3: //SquareMile
                convertedArea = convertToSquareMile(spinnerASelectedItemPosition, originalAreaInput);
                identifier = "Sq Mi";
                break;
            case 4: //Acre
                convertedArea = convertToAcre(spinnerASelectedItemPosition, originalAreaInput);
                identifier = "ac";
                break;
            case 5: //SquareYard
                convertedArea = convertToSquareYard(spinnerASelectedItemPosition, originalAreaInput);
                identifier = "Sq Yd";
                break;
            case 6: //SquareFoot
                convertedArea = convertToSquareFoot(spinnerASelectedItemPosition, originalAreaInput);
                identifier = "Sq Ft";
                break;
            case 7: //SquareInch
                convertedArea = convertToSquareInch(spinnerASelectedItemPosition, originalAreaInput);
                identifier = "Sq In";
                break;
        }

        Converter.setConvertedSpeedTextView(convertedAreaTextView, identifier, convertedArea);
    }

    /**
     * Convert the currently set number in originalAreaEditText from the metric selected
     * in spinnerA to SquareKm.
     * @param spinnerASelectedItemPosition The metric currently set in spinnerA
     * @param orignalAreaInput The number currently entered into the originalAreaEditText
     * @return the converted area from spinnerA metric to SquareKm
     */
    private double convertToSquareKm(int spinnerASelectedItemPosition, double orignalAreaInput) {

        double convertedValueToReturn = 0;

        switch(spinnerASelectedItemPosition) {
            case 0: //SquareKm
                convertedValueToReturn = orignalAreaInput;
                break;
            case 1: //Hectare
                convertedValueToReturn = orignalAreaInput*0.01;
                break;
            case 2: //SquareMeter
                convertedValueToReturn = orignalAreaInput*1e-6;
                break;
            case 3: //SquareMile
                convertedValueToReturn = orignalAreaInput*2.58999;
                break;
            case 4: //Acre
                convertedValueToReturn = orignalAreaInput*0.00404686;
                break;
            case 5: //SquareYard
                convertedValueToReturn = orignalAreaInput*8.3613e-7;
                break;
            case 6: //SquareFoot
                convertedValueToReturn = orignalAreaInput*9.2903e-8;
                break;
            case 7: //SquareInch
                convertedValueToReturn = orignalAreaInput*6.4516e-10;
                break;
        }

        return convertedValueToReturn;
    }

    /**
     * Convert the currently set number in originalAreaEditText from the metric selected
     * in spinnerA to Hectare.
     * @param spinnerASelectedItemPosition The metric currently set in spinnerA
     * @param orignalAreaInput The number currently entered into the originalAreaEditText
     * @return the converted area from spinnerA metric to Hectare
     */
    private double convertToHectare(int spinnerASelectedItemPosition, double orignalAreaInput) {

        double convertedValueToReturn = 0;

        switch(spinnerASelectedItemPosition) {
            case 0: //SquareKm
                convertedValueToReturn = orignalAreaInput*100;
                break;
            case 1: //Hectare
                convertedValueToReturn = orignalAreaInput;
                break;
            case 2: //SquareMeter
                convertedValueToReturn = orignalAreaInput*1e-4;
                break;
            case 3: //SquareMile
                convertedValueToReturn = orignalAreaInput*258.999;
                break;
            case 4: //Acre
                convertedValueToReturn = orignalAreaInput*0.404686;
                break;
            case 5: //SquareYard
                convertedValueToReturn = orignalAreaInput*8.3613e-5;
                break;
            case 6: //SquareFoot
                convertedValueToReturn = orignalAreaInput*9.2903e-6;
                break;
            case 7: //SquareInch
                convertedValueToReturn = orignalAreaInput*6.4516e-8;
                break;
        }

        return convertedValueToReturn;
    }

    /**
     * Convert the currently set number in originalAreaEditText from the metric selected
     * in spinnerA to SquareMeter.
     * @param spinnerASelectedItemPosition The metric currently set in spinnerA
     * @param orignalAreaInput The number currently entered into the originalAreaEditText
     * @return the converted area from spinnerA metric to SquareMeter
     */
    private double convertToSquareMeter(int spinnerASelectedItemPosition, double orignalAreaInput) {

        double convertedValueToReturn = 0;

        switch(spinnerASelectedItemPosition) {
            case 0: //SquareKm
                convertedValueToReturn = orignalAreaInput*1e+6;
                break;
            case 1: //Hectare
                convertedValueToReturn = orignalAreaInput*10000;
                break;
            case 2: //SquareMeter
                convertedValueToReturn = orignalAreaInput;
                break;
            case 3: //SquareMile
                convertedValueToReturn = orignalAreaInput*2.59e+6;
                break;
            case 4: //Acre
                convertedValueToReturn = orignalAreaInput*4046.86;
                break;
            case 5: //SquareYard
                convertedValueToReturn = orignalAreaInput*0.836127;
                break;
            case 6: //SquareFoot
                convertedValueToReturn = orignalAreaInput*0.092903;
                break;
            case 7: //SquareInch
                convertedValueToReturn = orignalAreaInput*0.00064516;
                break;
        }

        return convertedValueToReturn;
    }

    /**
     * Convert the currently set number in originalAreaEditText from the metric selected
     * in spinnerA to SquareMile.
     * @param spinnerASelectedItemPosition The metric currently set in spinnerA
     * @param orignalAreaInput The number currently entered into the originalAreaEditText
     * @return the converted area from spinnerA metric to SquareMile
     */
    private double convertToSquareMile(int spinnerASelectedItemPosition, double orignalAreaInput) {

        double convertedValueToReturn = 0;

        switch(spinnerASelectedItemPosition) {
            case 0: //SquareKm
                convertedValueToReturn = orignalAreaInput*0.386102;
                break;
            case 1: //Hectare
                convertedValueToReturn = orignalAreaInput*0.00386102;
                break;
            case 2: //SquareMeter
                convertedValueToReturn = orignalAreaInput*3.861e-7;
                break;
            case 3: //SquareMile
                convertedValueToReturn = orignalAreaInput;
                break;
            case 4: //Acre
                convertedValueToReturn = orignalAreaInput*0.0015625;
                break;
            case 5: //SquareYard
                convertedValueToReturn = orignalAreaInput*3.2283e-7;
                break;
            case 6: //SquareFoot
                convertedValueToReturn = orignalAreaInput*3.587e-8;
                break;
            case 7: //SquareInch
                convertedValueToReturn = orignalAreaInput*2.491e-10;
                break;
        }

        return convertedValueToReturn;
    }

    /**
     * Convert the currently set number in originalAreaEditText from the metric selected
     * in spinnerA to Acre.
     * @param spinnerASelectedItemPosition The metric currently set in spinnerA
     * @param orignalAreaInput The number currently entered into the originalAreaEditText
     * @return the converted area from spinnerA metric to Acre
     */
    private double convertToAcre(int spinnerASelectedItemPosition, double orignalAreaInput) {

        double convertedValueToReturn = 0;

        switch(spinnerASelectedItemPosition) {
            case 0: //SquareKm
                convertedValueToReturn = orignalAreaInput*247.105;
                break;
            case 1: //Hectare
                convertedValueToReturn = orignalAreaInput*2.47105;
                break;
            case 2: //SquareMeter
                convertedValueToReturn = orignalAreaInput*0.000247105;
                break;
            case 3: //SquareMile
                convertedValueToReturn = orignalAreaInput*640;
                break;
            case 4: //Acre
                convertedValueToReturn = orignalAreaInput;
                break;
            case 5: //SquareYard
                convertedValueToReturn = orignalAreaInput*0.000206612;
                break;
            case 6: //SquareFoot
                convertedValueToReturn = orignalAreaInput*2.2957e-5;
                break;
            case 7: //SquareInch
                convertedValueToReturn = orignalAreaInput*1.5942e-7;
                break;
        }

        return convertedValueToReturn;
    }

    /**
     * Convert the currently set number in originalAreaEditText from the metric selected
     * in spinnerA to SquareYard.
     * @param spinnerASelectedItemPosition The metric currently set in spinnerA
     * @param orignalAreaInput The number currently entered into the originalAreaEditText
     * @return the converted area from spinnerA metric to SquareYard
     */
    private double convertToSquareYard(int spinnerASelectedItemPosition, double orignalAreaInput) {

        double convertedValueToReturn = 0;

        switch(spinnerASelectedItemPosition) {
            case 0: //SquareKm
                convertedValueToReturn = orignalAreaInput*1.196e+6;
                break;
            case 1: //Hectare
                convertedValueToReturn = orignalAreaInput*11959.9;
                break;
            case 2: //SquareMeter
                convertedValueToReturn = orignalAreaInput*1.19599;
                break;
            case 3: //SquareMile
                convertedValueToReturn = orignalAreaInput*3.098e+6;
                break;
            case 4: //Acre
                convertedValueToReturn = orignalAreaInput*4840;
                break;
            case 5: //SquareYard
                convertedValueToReturn = orignalAreaInput;
                break;
            case 6: //SquareFoot
                convertedValueToReturn = orignalAreaInput*0.111111;
                break;
            case 7: //SquareInch
                convertedValueToReturn = orignalAreaInput*0.000771605;
                break;
        }

        return convertedValueToReturn;
    }

    /**
     * Convert the currently set number in originalAreaEditText from the metric selected
     * in spinnerA to SquareFoot.
     * @param spinnerASelectedItemPosition The metric currently set in spinnerA
     * @param orignalAreaInput The number currently entered into the originalAreaEditText
     * @return the converted area from spinnerA metric to SquareFoot
     */
    private double convertToSquareFoot(int spinnerASelectedItemPosition, double orignalAreaInput) {

        double convertedValueToReturn = 0;

        switch(spinnerASelectedItemPosition) {
            case 0: //SquareKm
                convertedValueToReturn = orignalAreaInput*1.076e+7;
                break;
            case 1: //Hectare
                convertedValueToReturn = orignalAreaInput*107639;
                break;
            case 2: //SquareMeter
                convertedValueToReturn = orignalAreaInput*10.7639;
                break;
            case 3: //SquareMile
                convertedValueToReturn = orignalAreaInput*2.788e+7;
                break;
            case 4: //Acre
                convertedValueToReturn = orignalAreaInput*43560;
                break;
            case 5: //SquareYard
                convertedValueToReturn = orignalAreaInput*9;
                break;
            case 6: //SquareFoot
                convertedValueToReturn = orignalAreaInput;
                break;
            case 7: //SquareInch
                convertedValueToReturn = orignalAreaInput*0.00694444;
                break;
        }

        return convertedValueToReturn;
    }

    /**
     * Convert the currently set number in originalAreaEditText from the metric selected
     * in spinnerA to SquareInch.
     * @param spinnerASelectedItemPosition The metric currently set in spinnerA
     * @param orignalAreaInput The number currently entered into the originalAreaEditText
     * @return the converted area from spinnerA metric to SquareInch
     */
    private double convertToSquareInch(int spinnerASelectedItemPosition, double orignalAreaInput) {

        double convertedValueToReturn = 0;

        switch(spinnerASelectedItemPosition) {
            case 0: //SquareKm
                convertedValueToReturn = orignalAreaInput*1.55e+9;
                break;
            case 1: //Hectare
                convertedValueToReturn = orignalAreaInput*1.55e+7;
                break;
            case 2: //SquareMeter
                convertedValueToReturn = orignalAreaInput*1550;
                break;
            case 3: //SquareMile
                convertedValueToReturn = orignalAreaInput*4.014e+9;
                break;
            case 4: //Acre
                convertedValueToReturn = orignalAreaInput*6.273e+6;
                break;
            case 5: //SquareYard
                convertedValueToReturn = orignalAreaInput*1296;
                break;
            case 6: //SquareFoot
                convertedValueToReturn = orignalAreaInput*144;
                break;
            case 7: //SquareInch
                convertedValueToReturn = orignalAreaInput;
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
    private final TextView.OnEditorActionListener AreaEditedListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int eventId, KeyEvent keyEvent) {
            boolean isDone = eventId== EditorInfo.IME_ACTION_DONE
                    || keyEvent.getAction() == KeyEvent.KEYCODE_BACK;

            if(isDone && textView.getText() != null && !textView.getText().toString().equals("")) {
                final int spinnerASelectedItem = spinnerA.getSelectedItemPosition();
                final int spinnerBSelectedItem = spinnerB.getSelectedItemPosition();
                calculateDifferencesBetweenMetrics(spinnerASelectedItem, spinnerBSelectedItem);

                return false; //Close the keyboard
            }

            Toast.makeText(getActivity(), "Please enter a Area", Toast.LENGTH_SHORT).show();
            return true; //Do not close keyboard
        }
    };
}
