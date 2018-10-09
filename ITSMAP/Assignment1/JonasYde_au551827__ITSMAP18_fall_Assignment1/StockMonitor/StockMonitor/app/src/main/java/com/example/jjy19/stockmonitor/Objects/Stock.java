package com.example.jjy19.stockmonitor.Objects;

import android.os.Parcel;
import android.os.Parcelable;

public class Stock implements Parcelable {

    private String stockName;
    private double stockPrice;
    private int numberOfStocks;
    private String stockSector;

    public Stock(String stockName, double stockPrice, int numberOfStocks, String stockSector) {
        this.stockName = stockName;
        this.stockPrice = stockPrice;
        this.numberOfStocks = numberOfStocks;
        this.stockSector = stockSector;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public double getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(double stockPrice) {
        this.stockPrice = stockPrice;
    }

    public int getNumberOfStocks() {
        return numberOfStocks;
    }

    public void setNumberOfStocks(int numberOfStocks) {
        this.numberOfStocks = numberOfStocks;
    }

    public String getStockSector() {
        return stockSector;
    }

    public void setStockSector(String stockSector) {
        this.stockSector = stockSector;
    }

    /*****************************/
    // make class parseable (Auto-generated)

    protected Stock(Parcel in) {
        stockName = in.readString();
        stockPrice = in.readDouble();
        numberOfStocks = in.readInt();
        stockSector = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(stockName);
        dest.writeDouble(stockPrice);
        dest.writeInt(numberOfStocks);
        dest.writeString(stockSector);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Stock> CREATOR = new Parcelable.Creator<Stock>() {
        @Override
        public Stock createFromParcel(Parcel in) {
            return new Stock(in);
        }

        @Override
        public Stock[] newArray(int size) {
            return new Stock[size];
        }
    };
}
