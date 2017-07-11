package com.example.nishantgahlawat.livestockdataautocomplete;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Nishant Gahlawat on 10-07-2017.
 */

public class StockAdapter extends ArrayAdapter

    {

        ArrayList<Stock> stockArrayList;
        Context context;

    public StockAdapter(@NonNull Context context, ArrayList<Stock> stockArrayList) {
        super(context, 0);
        this.context = context;
        this.stockArrayList = stockArrayList;
    }

        @Override
        public int getCount() {
        return stockArrayList.size();
    }

        static class StockViewHolder{
            TextView stockNameTV;
            TextView stockSymbolTV;
            TextView stockVolumeTV;
            TextView stockChangeTV;
            TextView stockDayLowTV;
            TextView stockDayHighTV;
            TextView stockYearLowTV;
            TextView stockYearHighTV;

            public StockViewHolder(TextView stockNameTV, TextView stockSymbolTV, TextView stockVolumeTV, TextView stockChangeTV,
                                   TextView stockDayLowTV, TextView stockDayHighTV,
                                   TextView stockYearLowTV, TextView stockYearHighTV) {
                this.stockNameTV = stockNameTV;
                this.stockSymbolTV = stockSymbolTV;
                this.stockVolumeTV = stockVolumeTV;
                this.stockChangeTV = stockChangeTV;
                this.stockDayLowTV = stockDayLowTV;
                this.stockDayHighTV = stockDayHighTV;
                this.stockYearLowTV = stockYearLowTV;
                this.stockYearHighTV = stockYearHighTV;
            }
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        StockViewHolder stockViewHolder;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.stock_list_item,null);

            TextView stockNameTV = (TextView)convertView.findViewById(R.id.stockName);
            TextView stockSymbolTV = (TextView)convertView.findViewById(R.id.stockSymbol);
            TextView stockVolumeTV = (TextView)convertView.findViewById(R.id.stockVolume);
            TextView stockChangeTV = (TextView)convertView.findViewById(R.id.stockChange);
            TextView stockDayLowTV = (TextView)convertView.findViewById(R.id.stockDayLow);
            TextView stockDayHighTV = (TextView)convertView.findViewById(R.id.stockDayHigh);
            TextView stockYearLowTV = (TextView)convertView.findViewById(R.id.stockYearLow);
            TextView stockYearHighTV = (TextView)convertView.findViewById(R.id.stockYearHigh);

            stockViewHolder = new StockViewHolder(stockNameTV,stockSymbolTV,stockVolumeTV,stockChangeTV,
                    stockDayLowTV,stockDayHighTV,stockYearLowTV,stockYearHighTV);

            convertView.setTag(stockViewHolder);
        }else{
            stockViewHolder = (StockViewHolder)convertView.getTag();
        }

        Stock stock = stockArrayList.get(position);

        stockViewHolder.stockNameTV.setText("Name:\n"+stock.name);
        stockViewHolder.stockSymbolTV.setText("Symbol:\n"+stock.symbol);
        stockViewHolder.stockVolumeTV.setText("Volume:\n"+stock.volume+"");
        stockViewHolder.stockChangeTV.setText("Percentage Change:\n"+stock.change+"");
        stockViewHolder.stockDayLowTV.setText("Day Low:\n"+stock.daysLow+"");
        stockViewHolder.stockDayHighTV.setText("Day High:\n"+stock.daysHigh+"");
        stockViewHolder.stockYearLowTV.setText("Year Low:\n"+stock.yearLow+"");
        stockViewHolder.stockYearHighTV.setText("Year High:\n"+stock.yearHigh+"");


        return convertView;
    }
}
