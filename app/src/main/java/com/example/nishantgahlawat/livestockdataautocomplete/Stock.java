package com.example.nishantgahlawat.livestockdataautocomplete;

/**
 * Created by Nishant Gahlawat on 10-07-2017.
 */

public class Stock {
    String symbol;
    String name;
    long volume;
    Double change;
    Double daysLow;
    Double daysHigh;
    Double yearLow;
    Double yearHigh;

    public Stock(String symbol, String name, long volume, Double change, Double daysLow, Double daysHigh, Double yearLow, Double yearHigh) {
        this.symbol = symbol;
        this.name = name;
        this.volume = volume;
        this.change = change;
        this.daysLow = daysLow;
        this.daysHigh = daysHigh;
        this.yearLow = yearLow;
        this.yearHigh = yearHigh;
    }
}
