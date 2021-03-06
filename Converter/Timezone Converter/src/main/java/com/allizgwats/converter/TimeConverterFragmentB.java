package com.allizgwats.converter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Fragment for converting 24 hour time input from one timezone to another.
 * The Timezones are presented in a single level list and using the Timezones names.
 */
public class TimeConverterFragmentB extends Fragment {

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

    //Identifier for setting which timezone is being selected for (A or B)
    private boolean timezoneAIsSelected;

    //The timezone objects to be assigned to when the user selects a timezone to use in the
    //timezonesListView
    private String timezoneA;
    private String timezoneB;

    //Representations of the items the user has selected in a ListView, used for scrolling
    //back to a previous selection through navigation
    private int positionOfPreviouslySelectedItemInListViewA = -1;
    private int positionOfPreviouslySelectedItemInListViewB = -1;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public TimeConverterFragmentB newInstance(int sectionNumber) {
        TimeConverterFragmentB fragment = new TimeConverterFragmentB();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public TimeConverterFragmentB() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.time_converter_fragment, container, false);

        //Attach listeners to relevant views inside inflated fragment "time_converter_fragment.
        attachListeners(rootView);

        //Set the default timezoneLists that will be displayed in the timezonesListView
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
     * Attach listeners to the fragments gui elements
     * @param rootView The inflated TimeConverterFragmentB view that will be displayed to the user.
     */
    private void attachListeners(View rootView) {
        hoursFromEditText = (EditText) rootView.findViewById(R.id.timeConverter_hoursFrom);
        hoursFromEditText.setOnEditorActionListener(timeEditedListener);//when DONE is pressed on pad
        TimeCustomTextWatcher hoursTextWatcher = new TimeCustomTextWatcher(hoursFromEditText, getActivity());
        hoursFromEditText.addTextChangedListener(hoursTextWatcher);//when input number outside correct set

        minutesFromEditText = (EditText) rootView.findViewById(R.id.timeConverter_minutesFrom);
        minutesFromEditText.setOnEditorActionListener(timeEditedListener);//when DONE is pressed on pad
        TimeCustomTextWatcher minutesTextWatcher = new TimeCustomTextWatcher(minutesFromEditText, getActivity());
        minutesFromEditText.addTextChangedListener(minutesTextWatcher);//when input number outside correct set

        Button timezonesFromButton = (Button) rootView.findViewById(R.id.timeConverter_timezoneFrom);
        timezonesFromButton.setOnClickListener(showTimezonesButtonListener);

        Button timezonesToButton = (Button) rootView.findViewById(R.id.timeConverter_timezoneTo);
        timezonesToButton.setOnClickListener(showTimezonesButtonListener);

        convertedTimeDisplay = (TextView) rootView.findViewById(R.id.timeConverter_convertedTime);

        timezoneAIdentifierText = (TextView) rootView
                .findViewById(R.id.timeConverter_timezoneAIdentifierText);

        timezoneBIdentifierText = (TextView) rootView
                .findViewById(R.id.timeConverter_timezoneBIdentifierText);

        timezonesListView = (ListView) rootView.findViewById
                (R.id.timeConverter_timezoneSelectorListView);
        timezonesListView.setOnItemClickListener(timezoneListViewListener);
    }

    /**
     * Set the default items of both timezoneArrayA and timezoneArrayB to be displayed when user
     * first selects either the timezoneAButton or timezoneBButton
     */
    private void setLevelZeroTimezoneSelectorItems() {
        timezoneArrayA = TimeZone.getAvailableIDs();
        timezoneArrayB = TimeZone.getAvailableIDs();

        //Set the input time to the current time on the device for ease of use if the user
        //does not realize they can edit the input EditTexts
        Time time = new Time();
        time.setToNow();
        time.format3339(true);//Set the time object to use 24-hour time format

        final int hours = time.hour;
        String hoursAsText = hours<10 ? "0" + String.valueOf(time.hour) : String.valueOf(time.hour);
        hoursFromEditText.setText(hoursAsText);

        final int minutes = time.minute;
        String minutesAsText = minutes<10 ? "0" + String.valueOf(time.minute) : String.valueOf(time.minute);
        minutesFromEditText.setText(minutesAsText);
    }

    /**
     * Calculate the difference between a users selected time and two user selected timezones
     * (hoursFromEditText/minutesFromEditText, timezoneA/timezoneB).
     */
    private void calculateTimezoneDifferences() {
        //If both Timezones are set, do the time conversion. Otherwise inform the user which
        //timezone they are missing.
        if(timezoneA==null) {
            Toast.makeText(getActivity(), "Please select a TimezoneA item",
                    Toast.LENGTH_SHORT).show();
        }
        else if(timezoneB==null) {
            Toast.makeText(getActivity(), "Please select a TimezoneB item",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            //Create a new calendar instance using the user selected timezoneA.
            //Set the time of day using the user input times from EditTexts.
            GregorianCalendar fromCal = new GregorianCalendar(TimeZone.getTimeZone(timezoneA));
            fromCal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hoursFromEditText.getText().toString()));
            fromCal.set(Calendar.MINUTE, Integer.parseInt(minutesFromEditText.getText().toString()));

            //Get the user selected timezoneB and create a new calendar of this timezone.
            //Using the previous calendars current time in milliseconds an easy conversion can be
            //made to the new timezone by simply setting them to the "same" time.
            GregorianCalendar toCal = new GregorianCalendar(TimeZone.getTimeZone(timezoneB));
            toCal.setTimeInMillis(fromCal.getTimeInMillis());

            //Get String representations of the converted time from the relevant calendar.
            //These will be concatenated into a single String to be returned and placed into the
            //converted time TextView shown to the user.
            final String convertedHours = String.valueOf(toCal.get(Calendar.HOUR_OF_DAY));
            final String convertedMinutes = String.valueOf(toCal.get(Calendar.MINUTE));
            String convertedTimeDisplay;

            //Concatenate time and add leading zeroes to any single digit number to keep the displayed
            //times consistent across all timezone conversions.
            convertedTimeDisplay = toCal.get(Calendar.HOUR_OF_DAY) < 10 ? "0" + convertedHours : convertedHours;
            convertedTimeDisplay += ":";
            convertedTimeDisplay += toCal.get(Calendar.MINUTE) < 10 ? "0" + convertedMinutes : convertedMinutes;

            setConvertedTime(convertedTimeDisplay);
        }
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

            if(positionOfPreviouslySelectedItemInListViewA != -1) {
                //Force the ListView to select the last selected A point. This changes the text
                //color and brings focus to this view i.e. shows it to the user
                timezonesListView.requestFocusFromTouch();
                timezonesListView.setSelection(positionOfPreviouslySelectedItemInListViewA);
            }
        }
        else {
            for(String timezoneArrayItem : timezoneArrayB) {
                timezonesListViewAdapter.add(timezoneArrayItem);
            }

            if(positionOfPreviouslySelectedItemInListViewB != -1) {
                //Force the ListView to select the last selected A point. This changes the text
                //color and brings focus to this view i.e. shows it to the user
                timezonesListView.requestFocusFromTouch();
                timezonesListView.setSelection(positionOfPreviouslySelectedItemInListViewB);
            }
        }

        //ListView Adapters data may have been changed redraw the timezonesListView
        //ListView with the new data
        timezonesListViewAdapter.notifyDataSetChanged();
        timezonesListView.setVisibility(View.VISIBLE);
    }

    /**     *********************************
     *      ***LISTENERS BEYOND THIS POINT***
     *      *********************************
     *
    /**
     * OnClickListener for the timezones ListView.
     * This is used for navigating through the timezone ListView selection and
     * assigning the users selected timezone positions within the ListView and
     * highlighting the choices the user makes after selection and when returning to the ListView
     * after leaving it.
     */
    private final AdapterView.OnItemClickListener timezoneListViewListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            //TextView is used to get around the NullPointer Exception that can occur when trying to
            //access ListView items using the onItemClicks view(args #2). The ListViews view
            //recycling mechanism will cause highlighting of more than one item when using that.
            final TextView selectedItemInListView = (TextView) view;

            //An item in timezoneA list has been selected
            if(timezoneAIsSelected) {
                //Assign the java timezone Id so it can be used later for calculating time
                //difference
                timezoneA = selectedItemInListView.getText().toString();
                timezoneAIdentifierText.setText(timezoneA);

                positionOfPreviouslySelectedItemInListViewA = i;

                //Highlight the selected item and change the color of the text within it
                timezonesListView.requestFocusFromTouch();
                timezonesListView.setSelection(positionOfPreviouslySelectedItemInListViewA);

                Toast.makeText(getActivity(), "TimezoneA set to: " + timezoneA,
                        Toast.LENGTH_SHORT).show();
                calculateTimezoneDifferences();
            }

            //An item in timezoneB list has been selected
            else {
                //Assign the java timezone Id so it can be used later for calculating time
                //difference
                timezoneB = selectedItemInListView.getText().toString();
                timezoneBIdentifierText.setText(timezoneB);

                positionOfPreviouslySelectedItemInListViewB = i;

                //Highlight the selected item and change the color of the text within it
                timezonesListView.requestFocusFromTouch();
                timezonesListView.setSelection(positionOfPreviouslySelectedItemInListViewB);

                Toast.makeText(getActivity(), "TimezoneB set to: " + timezoneB,
                        Toast.LENGTH_SHORT).show();
                calculateTimezoneDifferences();
            }
        }
    };

    /**
     * OnEditorActionListener for the time input EditText's. Used for adding leading zeros to
     * single-digit inputs after the user has selected done on the keyboard.
     */
    private final TextView.OnEditorActionListener timeEditedListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int eventId, KeyEvent keyEvent) {
            boolean isDone = eventId== EditorInfo.IME_ACTION_DONE
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
     * Listener for displaying the timezone which corresponds with the button the user has pressed
     * i.e. show the From list if the From button is selected and vice versa.
     */
    private final View.OnClickListener showTimezonesButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Find which timezone button the user has pressed
            timezoneAIsSelected = view.getId()==R.id.timeConverter_timezoneFrom;

            updateTimezoneSelectorIdentifierMessage();

            replaceItemsInTimezoneSelectorListView();
        }
    };
}
