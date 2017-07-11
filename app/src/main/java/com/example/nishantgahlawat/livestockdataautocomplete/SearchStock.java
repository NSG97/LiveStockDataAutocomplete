package com.example.nishantgahlawat.livestockdataautocomplete;

/**
 * Created by Nishant Gahlawat on 10-07-2017.
 */

public class SearchStock {
    private String name;
    private String symbol;

    public SearchStock(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }
}
