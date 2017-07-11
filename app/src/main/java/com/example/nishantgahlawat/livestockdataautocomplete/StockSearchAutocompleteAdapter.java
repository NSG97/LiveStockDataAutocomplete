package com.example.nishantgahlawat.livestockdataautocomplete;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

/**
 * Created by Nishant Gahlawat on 10-07-2017.
 */

public class StockSearchAutocompleteAdapter extends ArrayAdapter implements Filterable {

    private ArrayList<SearchStock> searchStockArrayList;


    public StockSearchAutocompleteAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
        searchStockArrayList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return searchStockArrayList.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return searchStockArrayList.get(position);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        Filter mFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint!=null){
                    try {
                        String term = constraint.toString();
                        ArrayList<SearchStock> result = new DownloadStockSearches().execute(term).get();
                        if(result!=null)
                            searchStockArrayList=result;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    filterResults.values = searchStockArrayList;
                    filterResults.count = searchStockArrayList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if(results!=null && results.count>0){
                    notifyDataSetChanged();
                }
                else{
                    notifyDataSetInvalidated();
                }
            }
        };
         return mFilter;
    }

    static class AutoViewHolder{
        TextView stockNameTV;
        TextView stockSymbolTV;

        public AutoViewHolder(TextView stockNameTV, TextView stockSymbolTV) {
            this.stockNameTV = stockNameTV;
            this.stockSymbolTV = stockSymbolTV;
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AutoViewHolder autoViewHolder;
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.search_autocomplete_layout,parent,false);

            TextView stockNameTV = (TextView) convertView.findViewById(R.id.stockNameAuto);
            TextView stockSymbolTV = (TextView) convertView.findViewById(R.id.stockSymbolAuto);

            autoViewHolder = new AutoViewHolder(stockNameTV,stockSymbolTV);

            convertView.setTag(autoViewHolder);
        }else{
            autoViewHolder = (AutoViewHolder) convertView.getTag();
        }

        SearchStock searchStock = searchStockArrayList.get(position);
        autoViewHolder.stockNameTV.setText(searchStock.getName());
        autoViewHolder.stockSymbolTV.setText(searchStock.getSymbol());

        return convertView;
    }

    private class DownloadStockSearches extends AsyncTask<String,Void,ArrayList<SearchStock>>{

        @Override
        protected ArrayList<SearchStock> doInBackground(String... params) {
            String urlString = "https://stocksearchapi.com/api/?search_text="+params[0]
                    +"&api_key=93f4792b6153b45e004bacdcef824e00d1d31f92";
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
                return parseSearches(str);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i("SearchStockAsyncTaskTAG", "passing NULL1");
            return null;
        }

        private ArrayList<SearchStock> parseSearches(String str) {
            try {
                JSONArray searchResult = new JSONArray(str);

                ArrayList<SearchStock> searchArrayList = new ArrayList<>();

                for (int i = 0; i < searchResult.length(); i++) {
                    JSONObject jsonObject = searchResult.getJSONObject(i);

                    String symbol = jsonObject.getString("company_symbol");
                    String name = jsonObject.getString("company_name");

                    SearchStock searchStock = new SearchStock(name,symbol);

                    searchArrayList.add(searchStock);
                }

                return searchArrayList;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
