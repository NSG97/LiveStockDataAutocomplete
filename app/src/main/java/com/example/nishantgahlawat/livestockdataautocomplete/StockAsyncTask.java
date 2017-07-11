package com.example.nishantgahlawat.livestockdataautocomplete;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Nishant Gahlawat on 10-07-2017.
 */

public class StockAsyncTask extends AsyncTask<String,Void,ArrayList<Stock>> {
    OnStockDownloadCompleteListener mListener;

    void setOnDownloadCompleteListener(OnStockDownloadCompleteListener mListener){
        this.mListener = mListener;
    }

    @Override
    protected ArrayList<Stock> doInBackground(String... params) {
        String urlString = params[0];
        try {
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();

            Scanner s =new Scanner(inputStream);
            String str = "";

            while(s.hasNext()){
                str += s.nextLine();
            }
            return parseStocks(str);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Stock> parseStocks(String str) {
        try {
            JSONObject stocksJSON = new JSONObject(str);
            JSONObject query = stocksJSON.getJSONObject("query");
            JSONObject result = query.getJSONObject("results");
            Object json = new JSONTokener(result.toString()).nextValue();

            if(json instanceof JSONArray) {
                JSONArray quote = result.getJSONArray("quote");

                ArrayList<Stock> stockArrayList = new ArrayList<Stock>();

                for (int i = 0; i < quote.length(); i++) {
                    JSONObject stockJSON = (JSONObject) quote.get(i);
                    String symbol = stockJSON.getString("symbol");
                    String name = stockJSON.getString("Name");
                    Long volume = stockJSON.getLong("AverageDailyVolume");
                    Double change = stockJSON.getDouble("Change");
                    Double daysLow = !stockJSON.isNull("DaysLow")?stockJSON.getDouble("DaysLow"):0;
                    Double daysHigh = !stockJSON.isNull("DaysHigh")?stockJSON.getDouble("DaysHigh"):0;
                    Double yearLow = !stockJSON.isNull("YearLow")?stockJSON.getDouble("YearLow"):0;
                    Double yearHigh = !stockJSON.isNull("YearHigh")?stockJSON.getDouble("YearHigh"):0;

                    Stock stock = new Stock(symbol,name,volume,change,daysLow,daysHigh,yearLow,yearHigh);

                    stockArrayList.add(stock);
                }

                return stockArrayList;
            }
            else{
                JSONObject stockJSON = result.getJSONObject("quote");

                ArrayList<Stock> stockArrayList = new ArrayList<Stock>();

                String symbol = stockJSON.getString("symbol");
                String name = stockJSON.getString("Name");
                Long volume = stockJSON.getLong("AverageDailyVolume");
                Double change = !stockJSON.isNull("Change")?stockJSON.getDouble("Change"):0;
                Double daysLow = !stockJSON.isNull("DaysLow")?stockJSON.getDouble("DaysLow"):0;
                Double daysHigh = !stockJSON.isNull("DaysHigh")?stockJSON.getDouble("DaysHigh"):0;
                Double yearLow = !stockJSON.isNull("YearLow")?stockJSON.getDouble("YearLow"):0;
                Double yearHigh = !stockJSON.isNull("YearHigh")?stockJSON.getDouble("YearHigh"):0;

                Stock stock = new Stock(symbol,name,volume,change,daysLow,daysHigh,yearLow,yearHigh);

                stockArrayList.add(stock);

                return stockArrayList;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Stock> stockArrayList) {
        super.onPostExecute(stockArrayList);
        if(mListener!=null){
            mListener.OnStockDownloadComplete(stockArrayList);
        }
    }

    interface OnStockDownloadCompleteListener{
        void OnStockDownloadComplete(ArrayList<Stock> stockArrayList);
    }
}
