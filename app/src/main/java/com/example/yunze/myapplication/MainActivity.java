package com.example.yunze.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.yunze.myapplication.ui.GridViewAdapter;
import com.example.yunze.myapplication.ui.SubmitButtonOnClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * The main {@link Activity} to show the magic square after it built
 */
public class MainActivity extends Activity {

    // ----------------------
    //       CONSTANTS
    // ----------------------

    private final static String INPUT_NUMBER_KEY = "INPUT_NUMBER";
    private final static String ANSWER_KEY = "ANSWER";
    private final static String SQUARE_ROW_SEPARATOR = "/";
    private final static String SQUARE_NUMBER_SEPARATOR = " ";

    private final static int REQUEST_CODE = 1;

    // ----------------------
    //         FIELDS
    // ----------------------

    private GridViewAdapter mGridViewAdapter;
    private LinearLayout mMagicSquareLayout;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up
        mMagicSquareLayout = findViewById(R.id.magic_square);
        final GridView gridView = findViewById(R.id.magic_square_grid);
        final EditText editText = findViewById(R.id.input_edit_text);
        final Button button = findViewById(R.id.submit_button);

        // Set the grid view adapter for loading magic square data
        mGridViewAdapter = new GridViewAdapter(this, gridView);
        gridView.setAdapter(mGridViewAdapter);

        // Set the onClickListener to response when user click on the submit button
        button.setOnClickListener(new SubmitButtonOnClickListener(this, editText));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Determine the request code, if correct, retrieve the answer
        if (requestCode == REQUEST_CODE) {
            final String magicSquare = data.getStringExtra(ANSWER_KEY);

            // Parse the retrieved string data and update
            final int[] square = parseMagicSquare(magicSquare);
            mGridViewAdapter.updateResult(square);
            mMagicSquareLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * A method to call {@link BuildMagicSquareActivity} to build magic square by sending the input number
     *
     * @param number that needs for building square
     * @see BuildMagicSquareActivity
     */
    public void startBuildMagicSquare(final int number) {
        final Intent intent = new Intent(MainActivity.this, BuildMagicSquareActivity.class);
        intent.putExtra(INPUT_NUMBER_KEY, number);
        startActivityForResult(intent, REQUEST_CODE);
    }

    // ----------------------
    //     HELPER METHOD
    // ----------------------

    /**
     * A helper method to parse the retrieve string data
     *
     * @param data that representing built magic square
     * @return an int array that contains all numbers of magic square
     */
    private int[] parseMagicSquare(final String data) {

        // Null check first
        if (data == null) {
            throw new NullPointerException(this.getClass().toString() + ": find null!");
        }

        // Split the string with separator to get each row
        final String[] rows = data.split(SQUARE_ROW_SEPARATOR);
        List<Integer> result = new ArrayList<>();

        // For each row, split with separator to get each number
        for (final String row : rows) {
            final String[] numbers = row.split(SQUARE_NUMBER_SEPARATOR);
            for (final String number : numbers) {
                result.add(Integer.valueOf(number));
            }
        }

        // Create an int array to holding all magic square numbers
        final int[] square = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            square[i] = result.get(i);
        }
        return square;
    }
}
