package com.allizgwats.converter;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Custom class for handling both hours and minutes inputting. Maintains minimum and maximum
 * values allowed in an edit text. Assign leading zeroes to single digits is handled in
 * the onEditorActionListener.
 */
public class TimeCustomTextWatcher implements TextWatcher {

    private final EditText editTextCaller;
    private Activity currentActivity;

    public TimeCustomTextWatcher(EditText caller, Activity activity) {
        editTextCaller = caller;
        currentActivity = activity;
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
            Toast.makeText(currentActivity, toastText, Toast.LENGTH_SHORT).show();
        }
    }
}
