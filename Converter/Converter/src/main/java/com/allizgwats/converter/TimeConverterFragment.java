package com.allizgwats.converter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TimeZone;

/**
 * Fragment for converting 24 hour time input from one timezone to another.
 */
public class TimeConverterFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    //The gui elements to be shown to the user
    private EditText hoursFromEditText;
    private EditText minutesFromEditText;
    private TextView convertedTimeDisplay;
    private ListView timezonesListView;
    private TextView timezoneAIdentifierText;
    private TextView timezoneBIdentifierText;

    //Used to hold string array resources for updating timezone ListView.
    private String[] timezoneArrayA;
    private String[] timezoneArrayB;

    //Counters for identifying how deep a user has progressed through the timezonesListView
    private int timezoneASelectorDepth=0;
    private int timezoneBSelectorDepth=0;

    //Identifier strings used to allow a user to progress backwards after making a selection
    //These will be placed at the top of the timezonesListView
    private String userSelectedRegionA;
    private String userSelectedRegionB;

    //Identifier for setting which timezone is being selected for (A or B)
    private boolean timezoneAIsSelected;

    //The timezone objects to be assigned to when the user selects a timezone to use in the
    //timezonesListView
    private String timezoneA;
    private String timezoneB;

    //Set up HashMap to be used for getting timezonesListView
    private HashMap<String, String[]> timezoneMap;

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

        //Set the default timezoneLists that will be displayed in the timezonesListView
        setLevelZeroTimezoneSelectorItems();

        //Create the HashMap to be used for all conversion and navigation operations in the fragment
        TimezoneMapManager timezoneMapManager = new TimezoneMapManager();
        timezoneMap = timezoneMapManager.getTimezoneMap();

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((converter_activity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    /**
     * Attach listeners to the fragments gui elements
     * @param rootView The inflated TimeConverterFragment view that will be displayed to the user.
     */
    private void attachListeners(View rootView) {
        hoursFromEditText = (EditText) rootView.findViewById(R.id.timeConverter_hoursFrom);
        hoursFromEditText.setOnEditorActionListener(timeEditedListener);//when DONE is pressed on pad
        CustomTextWatcher hoursTextWatcher = new CustomTextWatcher(hoursFromEditText);
        hoursFromEditText.addTextChangedListener(hoursTextWatcher);//when input number outside correct set

        minutesFromEditText = (EditText) rootView.findViewById(R.id.timeConverter_minutesFrom);
        minutesFromEditText.setOnEditorActionListener(timeEditedListener);//when DONE is pressed on pad
        CustomTextWatcher minutesTextWatcher = new CustomTextWatcher(minutesFromEditText);
        minutesFromEditText.addTextChangedListener(minutesTextWatcher);//when input number outside correct set

        Button timezonesFromButton = (Button) rootView.findViewById(R.id.timeConverter_timezoneFrom);
        timezonesFromButton.setOnClickListener(showTimezonesButtonListener);

        Button timezonesToButton = (Button) rootView.findViewById(R.id.timeConverter_timezoneTo);
        timezonesToButton.setOnClickListener(showTimezonesButtonListener);

        convertedTimeDisplay = (TextView) rootView.findViewById(R.id.timeConverter_convertedTime);

        timezoneAIdentifierText = (TextView) rootView
                .findViewById(R.id.timeConverter_originalTimeIdentifierText);

        timezoneBIdentifierText = (TextView) rootView
                .findViewById(R.id.timeConverter_convertedTimeIdentifierText);

        Button convertTimeButton = (Button) rootView.findViewById(R.id.timeConverter_convertInputButton);
        convertTimeButton.setOnClickListener(convertTimeButtonListener);

        timezonesListView = (ListView) rootView.findViewById
                (R.id.timeConverter_timezoneSelectorListView);
        timezonesListView.setOnItemClickListener(timezoneListViewListener);
    }

    /**
     * Set the default items of both timezoneArrayA and timezoneArrayB to be displayed when user
     * first selects either the timezoneAButton or timezoneBButton
     */
    private void setLevelZeroTimezoneSelectorItems() {
        TimezoneMapManager timezoneMapManager = new TimezoneMapManager();
        HashMap<String, String[]> timezoneMap = timezoneMapManager.getTimezoneMap();
        timezoneArrayA = timezoneMap.get("Regions");
        timezoneArrayB = timezoneMap.get("Regions");
    }

    /**
     * Calculate the difference between a users selected time and two user selected timezones
     * (hoursFromEditText/minutesFromEditText, timezoneA/timezoneB).
     * @return String representation of the users input time after conversion from timezoneA
     *         to timezoneB
     */
    private String calculateTimezoneDifferences() {
        String[] timezoneAArray = timezoneMap.get(timezoneA);

        //Create a new calendar instance using the user selected timezoneA.
        //Set the time of day using the user input times from EditTexts.
        GregorianCalendar fromCal = new GregorianCalendar(TimeZone.getTimeZone(timezoneAArray[0]));
        fromCal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hoursFromEditText.getText().toString()));
        fromCal.set(Calendar.MINUTE, Integer.parseInt(minutesFromEditText.getText().toString()));

        //Get the user selected timezoneB and create a new calendar of this timezone.
        //Using the previous calendars current time in milliseconds an easy conversion can be
        //made to the new timezone by simply setting them to the "same" time.
        String[] timezoneBArray = timezoneMap.get(timezoneB);
        GregorianCalendar toCal = new GregorianCalendar(TimeZone.getTimeZone(timezoneBArray[0]));
        toCal.setTimeInMillis(fromCal.getTimeInMillis());

        //Get String representations of the converted time from the relevant calendar.
        //These will be concatenated into a single String to be returned and placed into the
        //converted time TextView shown to the user.
        final String convertedHours = String.valueOf(toCal.get(Calendar.HOUR_OF_DAY));
        final String convertedMinutes = String.valueOf(toCal.get(Calendar.MINUTE));
        String convertedTimeDisplay;

        //Concatenate time and add leading zeroes to any single digit number to keep the displayed
        //times consistent across all timezone conversions.
        convertedTimeDisplay = toCal.get(Calendar.HOUR_OF_DAY)<10 ? "0"+convertedHours : convertedHours;
        convertedTimeDisplay += ":";
        convertedTimeDisplay += toCal.get(Calendar.MINUTE)<10 ? "0"+convertedMinutes : convertedMinutes;

        return convertedTimeDisplay;
    }

    /**
     * Change the items in the timezonesSelector ListView with new items
     * This assumes that the timezoneArrays and the timezoneAIsSelected boolean
     * have already been updated and uses conditionals to decide what layout and array to use
     * for the ListView adapter
     */
    private void replaceItemsInTimezoneSelectorListView() {
        //Set the timezone adapter resource based on which timezone the user is setting
        //currently. This is used to change the selection color of the ListView items i.e.
        //having blue text for the timezoneA's selected item and red for timezoneB's selected item
        ArrayAdapter<String> timezonesListViewAdapter;
        if(timezoneAIsSelected) {
            timezonesListViewAdapter = new ArrayAdapter<String>(getActivity(),
                    R.layout.time_converter_listview_item_timezone_a);
        } else {
            timezonesListViewAdapter = new ArrayAdapter<String>(getActivity(),
                    R.layout.time_converter_listview_item_timezone_b);
        }

        //Reattach adapter after it has been assigned to based on which button the user selected
        //If this is not done, the items will not appear in the ListView
        timezonesListView.setAdapter(timezonesListViewAdapter);

        //Remove all elements currently in the timezonesListView
        timezonesListViewAdapter.clear();

        //Add items to the timezonesListView based on what timezoneButton is used
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

        //ListView Adapters data may have been changed redraw the timezonesListView
        //ListView with the new data
        timezonesListViewAdapter.notifyDataSetChanged();
        timezonesListView.setVisibility(View.VISIBLE);
    }

    /**
     * Updates the TextView used for showing the user which timezone type they are currently using
     * (i.e. Timezone A or Timezone B)
     */
     private void updateTimezoneSelectorIdentifierMessage() {
         TextView currentlySelectedTimezoneIdentifier =
            (TextView) getView().findViewById(R.id.timeConverter_timezoneSelectorTextView);

        //Colors to be used when setting the text color of the TextView
        final int aIsSelectedColor = getResources().getColor(R.color.timezoneAColor);
        final int bIsSelectedColor = getResources().getColor(R.color.timezoneBColor);

        //Set the colour of the text based on what timezone the user is selecting
        currentlySelectedTimezoneIdentifier.setTextColor(
            timezoneAIsSelected ? aIsSelectedColor : bIsSelectedColor
        );

        final String selectedTimezoneIdentifierString = timezoneAIsSelected ? "From" : "To";

        currentlySelectedTimezoneIdentifier.setText
        ("|-Select " + selectedTimezoneIdentifierString + " Timezone-|");
    }

    /**
     * Set the text of the convertedTimeDisplay TextView
     * @param timeToSetInConvertedTimeTextView The String to assign to the
     * convertedTimeDisplay TextView if it has a valid (HH:mm) format.
     */
    private void setConvertedTime(String timeToSetInConvertedTimeTextView) {
        //Ensure entered String matches HH:mm format before entering it
        String matchPattern = "\\d\\d:\\d\\d";

        if(timeToSetInConvertedTimeTextView.matches(matchPattern)){
            convertedTimeDisplay.setText(timeToSetInConvertedTimeTextView);
        }
    }


    /**     *******************************************************************
     *      ***LISTENERS BEYOND THIS POINT - BEWARE ALL YE WHO VENTURE FORTH***
     *      *******************************************************************
     *
    /**
     * Listener for the button the user presses to convert input time from timezone A to timezone B.
     * This should simply redirect to another method that will handle the parse from strings to
     * integers and timezone conversions.
     */
    private OnClickListener convertTimeButtonListener = new OnClickListener() {
        @Override
        public void onClick(View view) {

            //If both Timezones are set, do the time conversion. Otherwise inform the user which
            //timezone they are missing.
            if(timezoneA==null) {
                Toast.makeText(getActivity(), "Please select a \'From\' Timezone",
                        Toast.LENGTH_SHORT).show();
            }
            else if(timezoneB==null) {
                Toast.makeText(getActivity(), "Please select a \'To\' Timezone",
                        Toast.LENGTH_SHORT).show();
            }
            else {
                setConvertedTime(calculateTimezoneDifferences());
            }
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
    private OnItemClickListener timezoneListViewListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            //TextView is used to get around the NullPointer Exception that can occur when trying to
            //access ListView items using the onItemClicks view(args #2). The ListViews view
            //recycling mechanism will cause highlighting of more than one item when using that.
            final TextView selectedItemInListView = (TextView) view;

            //Used for highlighting users choices when returning to a timezone list where a choice has
            //already been made and to identify when a user is selecting a timezone to be used or is still
            //navigating through the ListView hierarchy
            final int finalSelectorDepth = 2;

            //Use the text within the ListView row as Key to get new data from HashMap.
            //Exception will not be thrown here as null will be assigned if key is not found in map
            String[] stringArrayFromMap = timezoneMap.get(selectedItemInListView.getText().toString());

            //ArrayList is used to easily add an item in front of the value(array) that is
            //returned from the HashMap
            java.util.ArrayList<String> list = new ArrayList<String>();

            //An item in timezoneA list has been selected
            if(timezoneAIsSelected) {
                if(timezoneASelectorDepth>0 && i==0) {
                    //Add in an item to the list in order to allow the user to navigate
                    //backwards through the timezonesListView
                    if(timezoneASelectorDepth==2){
                        list.add("Regions");
                    }

                    //Add the value(String[]) retrieved from the HashMap to the list
                    Collections.addAll(list, stringArrayFromMap);

                    //Transform the list and assign it to the array that is used by the
                    //ListViewAdapter for updating the content of the timezonesListView
                    timezoneArrayA = list.toArray(new String[list.size()]);

                    //User is navigating "backwards" through the ListView, update their position
                    timezoneASelectorDepth--;
                }
                else if(timezoneASelectorDepth == finalSelectorDepth && i!=0) {
                    //Assign the java timezone Id so it can be used later for calculating time
                    //difference
                    timezoneA = selectedItemInListView.getText().toString();
                    timezoneAIdentifierText.setText(timezoneA);
                }
                else {
                    //Add in an item to the list in order to allow the user to navigate
                    //backwards through the timezonesListView
                    if(timezoneASelectorDepth==0){
                        list.add("Regions");
                        userSelectedRegionA = selectedItemInListView.getText().toString();
                    }
                    else if(timezoneASelectorDepth==1){
                        list.add(userSelectedRegionA);
                    }

                    //Add the values(String[]) retrieved from the HashMap to the list
                    Collections.addAll(list, stringArrayFromMap);

                    //Transform the list and assign it to the array that is used by the
                    //ListViewAdapter for updating the content of the timezonesListView
                    timezoneArrayA = list.toArray(new String[list.size()]);

                    //User is navigating "forwards" through the ListView, update their position
                    timezoneASelectorDepth++;
                }
            }

            //An item in timezoneB list has been selected
            else {
                if(timezoneBSelectorDepth>0 && i==0) {
                    //Add in an item to the list in order to allow the user to navigate
                    //backwards through the timezonesListView
                    if(timezoneBSelectorDepth==2){
                        list.add("Regions");
                    }

                    //Add the value(String[]) retrieved from the HashMap to the list
                    Collections.addAll(list, stringArrayFromMap);

                    //Transform the list and assign it to the array that is used by the
                    //ListViewAdapter for updating the content of the timezonesListView
                    timezoneArrayB = list.toArray(new String[list.size()]);

                    //User is navigating "backwards" through the ListView, update their position
                    timezoneBSelectorDepth--;
                }
                else if(timezoneBSelectorDepth == finalSelectorDepth && i!=0) {
                    //Assign the java timezone Id so it can be used later for calculating time
                    //difference
                    timezoneB = selectedItemInListView.getText().toString();
                    timezoneBIdentifierText.setText(timezoneB);
                }
                else {
                    //Add in an item to the list in order to allow the user to navigate
                    //backwards through the timezonesListView
                    if(timezoneBSelectorDepth==0){
                        list.add("Regions");
                        userSelectedRegionB = selectedItemInListView.getText().toString();
                    }
                    else if(timezoneBSelectorDepth==1){
                        list.add(userSelectedRegionB);
                    }

                    //Add the values(String[]) retrieved from the HashMap to the list
                    Collections.addAll(list, stringArrayFromMap);

                    //Transform the list and assign it to the array that is used by the
                    //ListViewAdapter for updating the content of the timezonesListView
                    timezoneArrayB = list.toArray(new String[list.size()]);

                    //User is navigating "forwards" through the ListView, update their position
                    timezoneBSelectorDepth++;
                }
            }

            //Redraw the timezonesListView using the updated data the user has selected
            replaceItemsInTimezoneSelectorListView();

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
            //Find which timezone button the user has pressed
            timezoneAIsSelected = view.getId()==R.id.timeConverter_timezoneFrom;

            updateTimezoneSelectorIdentifierMessage();

            replaceItemsInTimezoneSelectorListView();
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
            String sanityUpdateText = min;//Used in times when user enters incorrect values(e.g."")
            final String toastText = editTextCaller.getId()==R.id.timeConverter_hoursFrom
                    ? "Hours must be in range 0-23" : "Minutes must be in rage 0-59";

            if(!newInputText.equals("")) {
                final int newInputTextAsInt = Integer.parseInt(newInputText);
                if(newInputTextAsInt<0) {
                    sanityUpdateText=min;
                    mustSanitizeInput=true;
                }
                else if(newInputTextAsInt>Integer.parseInt(max)) {
                    sanityUpdateText=max;
                    mustSanitizeInput=true;
                }
            }

            if(mustSanitizeInput) {
                editable.clear();
                editable.insert(0, sanityUpdateText);
                Toast.makeText(getActivity(), toastText, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
