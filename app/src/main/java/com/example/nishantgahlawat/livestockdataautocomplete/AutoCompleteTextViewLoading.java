package com.example.nishantgahlawat.livestockdataautocomplete;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;

/**
 * Created by Nishant Gahlawat on 10-07-2017.
 */

public class AutoCompleteTextViewLoading extends android.support.v7.widget.AppCompatAutoCompleteTextView {

    ProgressBar progressBar;

    public AutoCompleteTextViewLoading(Context context) {
        super(context);
    }

    public AutoCompleteTextViewLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoCompleteTextViewLoading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setProgressBar(ProgressBar progressBar){
        this.progressBar = progressBar;
        progressBar.setVisibility(INVISIBLE);
    }

    @Override
    protected void performFiltering(CharSequence text, int keyCode) {
        progressBar.setVisibility(VISIBLE);
        super.performFiltering(text, keyCode);
    }

    @Override
    public void onFilterComplete(int count) {
        progressBar.setVisibility(INVISIBLE);
        super.onFilterComplete(count);
    }
}
