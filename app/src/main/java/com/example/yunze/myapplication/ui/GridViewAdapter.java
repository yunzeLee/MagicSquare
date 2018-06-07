package com.example.yunze.myapplication.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.yunze.myapplication.R;

/**
 * A {@link BaseAdapter} responsible for showing correct result in grid view
 */
public class GridViewAdapter extends BaseAdapter {

    // ----------------------
    //          FIELDS
    // ----------------------

    private final Context mContext;
    private final GridView mGridView;
    private int[] mSquare;

    /**
     * Constructor of {@link GridViewAdapter}
     * @param context of the current application
     * @param gridView that needs to show magic square
     */
    public GridViewAdapter(final Context context, final GridView gridView) {

        // Null check first
        if (context == null || gridView == null) {
            throw new NullPointerException(this.getClass().toString() + ": find null!");
        }
        mContext = context;
        mGridView = gridView;
        mSquare = new int[0];
    }

    @Override
    public int getCount() {
        return mSquare.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // If current convertView is null, inflate it
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.grid_text_view, null);
        }

        // TODO: try to make this text view auto-shrinking so when number become larger
        // TODO: it can show correctly in smaller size.
        final TextView textView = convertView.findViewById(R.id.grid_text);
        textView.setText(String.valueOf(mSquare[position]));
        return convertView;
    }

    /**
     * Helper method to update the result and shown in this grid view
     *
     * @param square that needs to shown
     */
    public void updateResult(final int[] square) {

        // Null check first
        if (square == null) {
            throw new NullPointerException(this.getClass().toString() + ": find null!");
        }

        mSquare = square;
        notifyDataSetChanged();
        mGridView.setNumColumns((int)Math.sqrt(square.length));
        mGridView.setVisibility(View.VISIBLE);
    }
}
