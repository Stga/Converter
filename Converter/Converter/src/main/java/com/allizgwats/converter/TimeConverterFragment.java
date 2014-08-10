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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
     * The gui elements to be shown to the user
     */
    private EditText hoursFrom;        private EditText minutesFrom;
    private TextView convertedTime;    private TextView currentlySelectedTimezoneIdentifier;
    private Button   timezonesFrom;    private Button   timezonesTo;
    private ListView timezonesListView; private ArrayAdapter<String> timezonesListViewAdapter;
    private Button   convertTimeButton;

    //Used to hold string array resources for updating timezone ListView. Holds original timezone list
    private String[] timezoneArrayA;
    //Used to hold string array resources for updating timezone ListView. Holds converted timezone list
    private String[] timezoneArrayB;

    //Simple counter for identifying what level of list is shown
    private int timezoneASelectorDepth=0;
    private int timezoneBSelectorDepth=0;

    //Marker to allow for scrolling and highlighting of a previous user selection after switching
    //to a timezone which has already been set. (e.g. timezoneA is set, user switches to B then A,
    //scroll to previous selection on timezoneA and highlight it)
    private int timezoneASelectedPosition = 0;
    private int timezoneBSelectedPosition = 0;

    //Identifer for setting which timezone is being selected
    private boolean timezoneAIsSelected;

    /**
     * Listeners for the edit texts. These are implemented in a private inner class
     */
    private CustomTextWatcher hoursTextWatcher;
    private CustomTextWatcher minutesTextWatcher;

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

        setLevelZeroTimezoneSelectorItems();

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
        hoursFrom.setOnEditorActionListener(timeEditedListener);//when DONE is pressed on pad
        hoursTextWatcher = new CustomTextWatcher(hoursFrom);
        hoursFrom.addTextChangedListener(hoursTextWatcher);//when input number outside correct set

        minutesFrom = (EditText) rootView.findViewById(R.id.timeConverter_minutesFrom);
        minutesFrom.setOnEditorActionListener(timeEditedListener);
        minutesTextWatcher = new CustomTextWatcher(minutesFrom);
        minutesFrom.addTextChangedListener(minutesTextWatcher);

        timezonesFrom = (Button) rootView.findViewById(R.id.timeConverter_timezoneFrom);
        timezonesFrom.setOnClickListener(showTimezonesButtonListener);

        timezonesTo = (Button) rootView.findViewById(R.id.timeConverter_timezoneTo);
        timezonesTo.setOnClickListener(showTimezonesButtonListener);

        //Instantiate the converted time textview so it can be updated when needed
        convertedTime = (TextView) rootView.findViewById(R.id.timeConverter_convertedTime);

        //Button the user presses to convert entered time from timezone A to timezone B
        convertTimeButton = (Button) rootView.findViewById(R.id.timeConverter_convertInputButton);
        convertTimeButton.setOnClickListener(convertTimeButtonListener);

        timezonesListView = (ListView) rootView.findViewById
                (R.id.timeConverter_timezoneSelectorListView);

        timezonesListView.setOnItemClickListener(timezoneSelectorListener);
    }


    /**
     * TODO Calculate the differences between selected timezones using users inserted hours/minutes
     */
    private void calculateTimezoneDifferences() {
        Calendar cal = new GregorianCalendar(Time.YEAR, Time.MONTH, Time.MONTH_DAY);

        Toast.makeText(getActivity(),
                "Calculating timezone differences",
                Toast.LENGTH_SHORT)
                .show();
    }

    /**
     * Set the default items of both timezoneArrayA and timezoneArrayB to be displayed when user
     * first selects either the timezoneAButton or timezoneBButton
     */
    private void setLevelZeroTimezoneSelectorItems() {
        timezoneArrayA = getResources()
                .getStringArray(R.array.timeConverter_timezoneListDefaultLevel);

        timezoneArrayB = getResources()
                .getStringArray(R.array.timeConverter_timezoneListDefaultLevel);
    }

    /**
     * Update arrays that are used by ListView adapter to show new items in the timezone ListView
     * @param stringArrayResource items that will be placed into timezone string arrays for updating
     */
    //TODO - update the timezone arrays when an item is selected from the ListView
    private void updateTimezoneArray(String[] stringArrayResource) {
        //Show the default list of timezones in the timezone selector ListView
        //timezoneList = getResources()
        //        .getStringArray(R.array.timeConverter_timezoneListDefaultLevel);

        //For allowing user to return to a higher level on the ListView
        if(timezoneASelectorDepth > 0) {
            timezonesListViewAdapter.add("-- Back --");
        }
    }

    /**
     * Updates the TextView used for showing the user which timezone they are currently selecting
     * for by means of a small message in a TextView
     */
     void updateCurrentSelectorMessage() {
        currentlySelectedTimezoneIdentifier =
                (TextView) getView().findViewById(R.id.timeConverter_timezoneSelectorTextView);
        final String selectedTimezoneIdentifierString =
                timezoneAIsSelected ? "First" : "Second";
        currentlySelectedTimezoneIdentifier.setText
                ("|-Select " + selectedTimezoneIdentifierString + " Timezone-|");
    }


    /**     *******************************************************************
     *      ***LISTENERS BEYOND THIS POINT - BEWARE ALL YE WHO VENTURE FORTH***
     *      *******************************************************************
     */
    /**
     * Listener for the button the user presses to convert input time from timezone A to timezone B.
     * This should simply redirect to another method that will handle the parse from strings to
     * integers and timezone conversions.
     */
    private OnClickListener convertTimeButtonListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            calculateTimezoneDifferences();
        }
    };

    /**
     * OnClickListener for the timezones ListView.
     * This is used for navigating through the timezone ListView selection and
     * assigning the users selected timezone positions within the ListView and
     * highlighting the choices the user makes after selection and when returning to the ListView
     * after leaving it.
     */
    //TODO - write going through the regions when clicking the ListView
    private OnItemClickListener timezoneSelectorListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            //This is used to get around the NullPointer Exception that can occur when trying to
            //access ListView items using the onItemClicks view(args #2). The ListViews view
            //recycling mechanism will cause highlighting of more than one item when using that.
            TextView selectedTextInsideListView;
            int finalDepth = 4;

            //An item in timezoneA list has been selected
            if(timezoneAIsSelected) {
                if(timezoneASelectorDepth == finalDepth) {
                    //User has selected an actual timezone, store this selection in order to be able to
                    //return to it later.
                    timezoneASelectedPosition = i; //used in timezoneA button OnClick
                    selectedTextInsideListView = (TextView) view;
                    selectedTextInsideListView.setSelected(true); //causes color change in text
                } else {
                    //User is navigating through the region selection. React based on what region
                    //they have selected
                    TextView test = (TextView) view;
                    Toast.makeText(getActivity(), test.getText(), Toast.LENGTH_SHORT).show();
                }
            }
            //An item in timezoneB list has been selected
            else {
                if(timezoneBSelectorDepth == finalDepth) {
                    //User has selected an actual timezone, store this selection in order to be able to
                    //return to it later.
                    timezoneBSelectedPosition = i; //used in timezoneB button OnClick
                    selectedTextInsideListView = (TextView) view;
                    selectedTextInsideListView.setSelected(true); //causes color change in text
                } else {
                    //User is navigating through the region selection. React based on what region
                    //they have selected

                }
            }
        }
    };

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
                return false; //Close the keyboard
            }

            Toast.makeText(getActivity(), "Please enter a time", Toast.LENGTH_SHORT).show();
            return true; //Do not close keyboard
        }
    };

    /**
     * Listener for displaying the timezone which corresponds with the button the user has pressed
     * i.e. show the From list if the From button is selected and vice versa.
     */
    OnClickListener showTimezonesButtonListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            //Find which timezone button the user has pressed in order to call OnClickListener
            timezoneAIsSelected = view.getId() == R.id.timeConverter_timezoneFrom;

            updateCurrentSelectorMessage();

            //Set the timezone adapter resource based on which timezone the user is setting
            //currently. This is used to change the selection color of the ListView items i.e.
            //having blue text for the timezoneA's selected item and red for timezoneB's selected item
            if(timezoneAIsSelected) {
                timezonesListViewAdapter = new ArrayAdapter<String>(getActivity(),
                        R.layout.time_converter_listview_item_timezone_a);
            } else {
                timezonesListViewAdapter = new ArrayAdapter<String>(getActivity(),
                        R.layout.time_converter_listview_item_timezone_b);
            }

            timezonesListView.setAdapter(timezonesListViewAdapter);

            timezonesListViewAdapter.clear();

            if(timezoneAIsSelected) {
                for(String timezoneArrayItem : timezoneArrayA) {
                    timezonesListViewAdapter.add(timezoneArrayItem);
                }
            }
            else {
                for(String timezoneArrayItem : timezoneArrayB) {
                    timezonesListViewAdapter.add(timezoneArrayItem);
                }
            }

            timezonesListViewAdapter.notifyDataSetChanged();
            timezonesListView.setVisibility(View.VISIBLE);

            if(timezoneAIsSelected && timezoneASelectedPosition > -1) {
                //Force the ListView to select the last selected A point. This changes the text
                //color and brings focus to this view i.e. shows it to the user
                timezonesListView.requestFocusFromTouch();
                timezonesListView.setSelection(timezoneASelectedPosition);
            }
            else if(!timezoneAIsSelected && timezoneBSelectedPosition > -1) {
                //Force the ListView to select the last selected A point. This changes the text
                //color and brings focus to this view i.e. shows it to the user
                timezonesListView.requestFocusFromTouch();
                timezonesListView.setSelection(timezoneBSelectedPosition);
            }

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
            String sanityUpdateText = min; //used in times when user attempts to enter empty string
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
                //The TextChangeListener must be removed in order to prevent endless recursion
                //due to manually changing the data in the editText.
                editTextCaller.removeTextChangedListener(hoursTextWatcher);
                editable.clear();
                editable.insert(0, sanityUpdateText);
                editTextCaller.addTextChangedListener(hoursTextWatcher);
                Toast.makeText(getActivity(), toastText, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
