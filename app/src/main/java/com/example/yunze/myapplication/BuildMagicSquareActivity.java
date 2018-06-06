package com.example.yunze.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * The {@link Activity} to build magic square based on the input number
 */
public class BuildMagicSquareActivity extends Activity {

    private final static String INPUT_NUMBER_KEY = "INPUT_NUMBER";
    private final static String ANSWER_KEY = "ANSWER";
    private final static String SQUARE_ROW_SEPERATOR = "/";
    private final static String SQUARE_NUMBER_SEPERATOR = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the input number
        final Intent intent = getIntent();
        final int number = intent.getIntExtra(INPUT_NUMBER_KEY, 0);

        // Build the magic square and translate it to a string to send back
        final int[][] square = buildMagicSquare(number);
        final String answer = translate(square);

        // Send the magic square result back
        intent.putExtra(ANSWER_KEY, answer);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    /**
     * Helper method to build the magic square
     * @param number that need to create square for
     * @return the magic square
     */
    private int[][] buildMagicSquare(final int number) {
        final int[][] magicSquare = new int[number][number];

        // Initialize position for 1
        int rowIndex = number / 2;
        int columnIndex = number - 1;

        // Start one by one put all values in magic square
        for (int num = 1; num <= number * number;) {
            if (rowIndex == -1 && columnIndex == number) {
                columnIndex = number - 2;
                rowIndex = 0;
            } else {
                // If out of square on right side, move it to left
                if (columnIndex == number) {
                    columnIndex = 0;
                }

                // If out of square on up side, move it to bottom
                if (rowIndex < 0) {
                    rowIndex = number - 1;
                }
            }

            // If current position already have number, move it left 2 steps
            // and down one step, then recheck that position
            if (magicSquare[rowIndex][columnIndex] != 0) {
                columnIndex -= 2;
                rowIndex++;
                continue;
            }

            // Set the current position after check
            magicSquare[rowIndex--][columnIndex++] = num++;
        }
        return magicSquare;
    }

    /**
     * Helper method to translate the int array to string so it can be sent
     * @param square that need to be translated
     * @return a {@link String} that represents the magic square
     * Note: Both this activity and {@link MainActivity} should know about
     * this translate rule.
     */
    private String translate(final int[][] square) {
        final int row = square.length;
        final int column = square[0].length;

        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < row; i++) {
            for(int j = 0; j < column; j++) {
                stringBuilder.append(square[i][j]);
                if (j != column - 1) {
                    stringBuilder.append(SQUARE_NUMBER_SEPERATOR);
                }
            }
            stringBuilder.append(SQUARE_ROW_SEPERATOR);
        }
        return stringBuilder.toString();
    }
}
