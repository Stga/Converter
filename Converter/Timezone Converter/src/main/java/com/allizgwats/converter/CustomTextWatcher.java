package com.allizgwats.converter;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Custom class for user input into the originalTemperatureEditText to ensure length of input
 * does not exceed a maximum threshold and that it is not the empty string which would cause
 * exception to be thrown due to automatic calculations that occur after editing the EditText.
 */
public class CustomTextWatcher implements TextWatcher {

    private final EditText editTextCaller;
    private String beforeTextChangedString;
    private Activity currentActivity;

    public CustomTextWatcher(EditText caller, Activity activity) {
        editTextCaller = caller;
        currentActivity = activity;
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
            Toast.makeText(currentActivity, "Maximum digits reached", Toast.LENGTH_SHORT).show();
        }
    }
}
