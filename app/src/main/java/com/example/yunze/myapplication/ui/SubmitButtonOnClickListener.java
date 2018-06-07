package com.example.yunze.myapplication.ui;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yunze.myapplication.MainActivity;

/**
 * A {@link android.view.View.OnClickListener} to responsible for the submit button
 */
public class SubmitButtonOnClickListener implements View.OnClickListener {

    /**
     * A toast message to show when input is not invalid (e.g. empty, too big, negative or even number)
     */
    private static final String EMPTY_INPUT_WARNING = "Please input before submit!";
    private static final String INVALID_INPUT_WARNING = "This input is invalid!";
    private static final int MAX_VALID_INPUT_NUMBER = 24;

    // ----------------------
    //          FIELDS
    // ----------------------

    private final MainActivity mActivity;
    private final EditText mEditText;

    public SubmitButtonOnClickListener(final MainActivity activity,
                                       final EditText editText) {
        // Null check first
        if (activity == null || editText == null) {
            throw new NullPointerException(this.getClass().toString() + ": find null!");
        }
        mActivity = activity;
        mEditText = editText;
    }

    @Override
    public void onClick(final View view) {
        if (mEditText.getText() == null) return;

        // Hide the virtual keyboard first
        final InputMethodManager inputManager = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        final View currentFocus = mActivity.getCurrentFocus();
        if (inputManager != null && currentFocus != null) {
            inputManager.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        // Show a toast if the input number is empty
        final String input = mEditText.getText().toString();
        if (input.length() == 0) {
            Toast.makeText(mActivity, EMPTY_INPUT_WARNING, Toast.LENGTH_LONG).show();
            return;
        }

        // Show a toast if the input number is too big (larger than 50), negative or even
        final int number = Integer.parseInt(mEditText.getText().toString());
        if (number > MAX_VALID_INPUT_NUMBER || number <= 0 ||number % 2 == 0) {
            Toast.makeText(mActivity, INVALID_INPUT_WARNING, Toast.LENGTH_LONG).show();
            return;
        }

        // Create magic square only when input number is valid
        mActivity.startBuildMagicSquare(number);
    }
}
