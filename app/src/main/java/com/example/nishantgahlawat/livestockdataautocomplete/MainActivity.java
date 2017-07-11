package com.example.nishantgahlawat.livestockdataautocomplete;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements StockAsyncTask.OnStockDownloadCompleteListener {

    AutoCompleteTextViewLoading searchStockACTV;
    StockSearchAutocompleteAdapter searchAutocompleteAdapter;
    ProgressBar progressBar;

    ListView stockListView;
    ArrayList<Stock> stockArrayList;
    StockAdapter stockAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchStockACTV = (AutoCompleteTextViewLoading) findViewById(R.id.stockSearchAutocomplete);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        searchStockACTV.setProgressBar(progressBar);

        searchAutocompleteAdapter = new StockSearchAutocompleteAdapter(this,android.R.layout.simple_dropdown_item_1line);

        searchStockACTV.setAdapter(searchAutocompleteAdapter);

        searchStockACTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchStockACTV.setText("");
                SearchStock searchStock = (SearchStock) searchAutocompleteAdapter.getItem(position);
                fetchStock(searchStock.getSymbol());
            }
        });

        stockListView = (ListView)findViewById(R.id.stockListView);

        stockArrayList = new ArrayList<>();

        stockAdapter = new StockAdapter(this,stockArrayList);

        stockListView.setAdapter(stockAdapter);

    }

    private void fetchStock(String symbol) {
        String urlString = "https://query.yahooapis.com/v1/public/yql?q=" +
                "select * from yahoo.finance.quote where symbol in (\""+symbol+"\")" +
                "&format=json&diagnostics=true&env=store://datatables.org/alltableswithkeys&callback=";

        StockAsyncTask stockAsyncTask = new StockAsyncTask();

        stockAsyncTask.setOnDownloadCompleteListener(this);
        stockAsyncTask.execute(urlString);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void OnStockDownloadComplete(ArrayList<Stock> stockArrayList) {
        this.stockArrayList.addAll(stockArrayList);
        stockAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.INVISIBLE);
    }
}
