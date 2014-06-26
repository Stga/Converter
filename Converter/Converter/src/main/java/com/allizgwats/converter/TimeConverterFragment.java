package com.allizgwats.converter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Fragment for converting 24 hour time input from one timezone to another.
 */
public class TimeConverterFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * The gui elements to be manipulated by user
     */
    private EditText hoursFrom;        private EditText minutesFrom;
    private EditText hoursTo;          private EditText minutesTo;
    private Button   timezonesFrom;    private Button   timezonesTo;
    private ListView timezoneSelector; private ArrayAdapter<String> timezoneAdapter;

    /**
     * Listeners for the edit texts. These are implemented in a private inner class
     */
    private CustomTextWatcher hoursTextWatcher;
    private CustomTextWatcher minutesTextWatcher;
    private String timezoneIdentifierFlag; //Identifer for setting which timezone is being selected

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public TimeConverterFragment newInstance(int sectionNumber) {
        TimeConverterFragment fragment = new TimeConverterFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public TimeConverterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.time_converter_fragment, container, false);

        //Attach listeners to relevant views inside inflated fragment "time_converter_fragment.
        attachListeners(rootView);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((converter_activity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    /**
     * Attach listeners to the following gui elements
     * Hours From EditText
     * Minutes From EditText
     * Timezone From Button
     * Timezone To Button
     * @param rootView The inflated TimeConverterFragment that will be displayed to the user. This
     *                 contains all views of the TimeConverterFragment layout.
     */
    private void attachListeners(View rootView) {
        hoursFrom = (EditText) rootView.findViewById(R.id.timeConverter_hoursFrom);
        hoursFrom.setOnEditorActionListener(timeEditedListener);
        hoursTextWatcher = new CustomTextWatcher(hoursFrom);
        hoursFrom.addTextChangedListener(hoursTextWatcher);

        minutesFrom = (EditText) rootView.findViewById(R.id.timeConverter_minutesFrom);
        minutesFrom.setOnEditorActionListener(timeEditedListener);
        minutesTextWatcher = new CustomTextWatcher(minutesFrom);
        minutesFrom.addTextChangedListener(minutesTextWatcher);

        timezonesFrom = (Button) rootView.findViewById(R.id.timeConverter_timezoneFrom);
        timezonesFrom.setOnClickListener(showDefaultTimezoneListener);

        timezonesTo = (Button) rootView.findViewById(R.id.timeConverter_timezoneTo);
        timezonesTo.setOnClickListener(showDefaultTimezoneListener);

        //Instantiate the un-clickable To pane items so they can be set in other methods
        hoursTo = (EditText) rootView.findViewById(R.id.timeConverter_hoursTo);
        minutesTo = (EditText) rootView.findViewById(R.id.timeConverter_minutesTo);

        timezoneSelector = (ListView) rootView.findViewById
                (R.id.timeConverter_timezoneSelectorListView);
        timezoneAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.time_converter_listview_item);
        timezoneSelector.setAdapter(timezoneAdapter);

        //TODO style the timezone buttons
    }


    /**
     * Calculate the differences between selected timezones using users inserted hours/minutes
     * TODO
     */
    private void calculateTimezoneDifferences() {
        Calendar cal = new GregorianCalendar(Time.YEAR, Time.MONTH, Time.MONTH_DAY);

        Toast.makeText(getActivity(),
                "Calculating timezone differences",
                Toast.LENGTH_SHORT)
                .show();
    }


    /**     *******************************************************************
     *      ***LISTENERS BEYOND THIS POINT - BEWARE ALL YE WHO VENTURE FORTH***
     *      *******************************************************************
    /**
     * OnEditorActionListener for the time input EditText's. Used for adding leading zeros to
     * single-digit inputs after the user has selected done on the keyboard.
     */
    private TextView.OnEditorActionListener timeEditedListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int eventId, KeyEvent keyEvent) {
            boolean isDone = eventId==EditorInfo.IME_ACTION_DONE
                    || keyEvent.getAction() == KeyEvent.KEYCODE_BACK;

            if(isDone && textView.getText() != null && !textView.getText().toString().equals("")) {
                final String usersInputString = textView.getText().toString();
                final int usersInputStringAsInt = Integer.parseInt(usersInputString);
                if(usersInputStringAsInt>=0 && usersInputStringAsInt<10) {
                    textView.setText("0" + usersInputStringAsInt);
                }
                calculateTimezoneDifferences();
                return false; //Close the keyboard
            }

            Toast.makeText(getActivity(), "Please enter a time", Toast.LENGTH_SHORT).show();
            return true; //Do not close keyboard
        }
    };

    /**
     * Listener for displaying the default region selection list and setting the flag
     * to either the from or to to identify which variable should be assigned to.
     */
    View.OnClickListener showDefaultTimezoneListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            timezoneIdentifierFlag = view.getId()==R.id.timeConverter_timezoneFrom ? "From" : "To";
            TextView timezoneIdentifierTextView =
                    (TextView) getView().findViewById(R.id.timeConverter_timezoneSelectorTextView);
            timezoneIdentifierTextView.setText("|-Selecting " + timezoneIdentifierFlag + " Timezone-|");
            //timezoneIdentifierTextView.setPaintFlags(timezoneIdentifierTextView.getPaintFlags()
            //        |   Paint.UNDERLINE_TEXT_FLAG);

            //Show the default list of timezones in the timezone selector ListView
            String[] timezoneList = getResources()
                    .getStringArray(R.array.timeConverter_timezoneListDefaultLevel);

            timezoneAdapter.clear();

            for(int i=0; i<timezoneList.length; i++) {
                timezoneAdapter.add(timezoneList[i]);
            }

            timezoneAdapter.notifyDataSetChanged();
            timezoneSelector.setVisibility(View.VISIBLE);

        }
    };

    /**
     * Custom class for handling both hours and minutes inputting. Maintains minimum and maximum
     * values allowed in an edit text. Assign leading zeroes to single digits is handled in
     * the onEditorActionListener.
     */
    private class CustomTextWatcher implements TextWatcher {

        private EditText editTextCaller;

        public CustomTextWatcher(EditText caller) {
            editTextCaller = caller;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

        @Override
        public void afterTextChanged(Editable editable) {
            final String newInputText = editable.toString();
            boolean mustSanitizeInput = false;
            final String min = "00";
            final String max = editTextCaller.getId()==R.id.timeConverter_hoursFrom ? "23" : "59";
            String sanityUpdateText = min; //must have some input for inserting as backup
            final String toastText = editTextCaller.getId()==R.id.timeConverter_hoursFrom
                    ? "Hours must be in range 0-23" : "Minutes must be in rage 0-59";

            if(!newInputText.equals("")) {
                final int newInputTextAsInt = Integer.parseInt(newInputText);
                if(newInputTextAsInt<0) {
                    sanityUpdateText=min; mustSanitizeInput=true;
                }
                else if(newInputTextAsInt>Integer.parseInt(max)) {
                    sanityUpdateText=max; mustSanitizeInput=true;
                }
            }

            if(mustSanitizeInput) {
                editTextCaller.removeTextChangedListener(hoursTextWatcher);
                editable.clear();
                editable.insert(0, sanityUpdateText);
                editTextCaller.addTextChangedListener(hoursTextWatcher);
                Toast.makeText(getActivity(), toastText, Toast.LENGTH_SHORT).show();
            }

            calculateTimezoneDifferences();
        }
    }
}
