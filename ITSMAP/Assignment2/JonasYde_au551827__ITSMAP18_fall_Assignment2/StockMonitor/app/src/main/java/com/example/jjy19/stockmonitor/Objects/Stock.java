package com.example.jjy19.stockmonitor.Objects;

import android.annotation.SuppressLint;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// my main entity, with all the necesary information about the stock

@Entity(tableName = "stock_table")
public class Stock implements Parcelable {


    @PrimaryKey(autoGenerate = true)
    private int sid;

    @ColumnInfo(name = "Company Name")
    private String companyName;

    @ColumnInfo(name = "Symbol")
    private String symbol;

    @ColumnInfo(name = "Primary Exchange")
    private String primaryExchange;

    @ColumnInfo(name = "Latest Value")
    private double latestValue;


    @ColumnInfo(name = "Sell Value")
    private double sellValue;

    @ColumnInfo(name = "Bought Price")
    private double stockPrice;

    @ColumnInfo(name = "Number of stocks")
    private int numberOfStocks;

    @ColumnInfo(name = "Stock Sector")
    private String stockSector;

    @ColumnInfo(name = "Time Stamp")
    private String timeStamp;

    public Stock(String companyName, String symbol , double stockPrice, int numberOfStocks, String stockSector) {
        this.companyName = companyName;
        this.symbol = symbol;
        this.stockPrice = stockPrice;
        this.numberOfStocks = numberOfStocks;
        this.stockSector = stockSector;

        Date c = Calendar.getInstance().getTime();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy: " + "HH:mm:ss");
        String formattedDate = df.format(c);

        this.timeStamp = formattedDate;
    }

    public int getSid() { return sid; }

    public void setSid(int sid) { this.sid = sid; }

    public String getTimeStamp() { return timeStamp; }

    public void setTimeStamp(String timeStamp) { this.timeStamp = timeStamp; }

    public String getCompanyName() { return companyName; }

    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public double getStockPrice() { return stockPrice; }

    public void setStockPrice(double stockPrice) { this.stockPrice = stockPrice; }

    public int getNumberOfStocks() { return numberOfStocks; }

    public void setNumberOfStocks(int numberOfStocks) { this.numberOfStocks = numberOfStocks; }

    public String getStockSector() { return stockSector; }

    public void setStockSector(String stockSector) { this.stockSector = stockSector; }

    public String getSymbol() { return symbol; }

    public void setSymbol(String symbol) { this.symbol = symbol; }

    public String getPrimaryExchange() { return primaryExchange; }

    public void setPrimaryExchange(String primaryExchange) { this.primaryExchange = primaryExchange; }

    public double getLatestValue() { return latestValue; }

    public void setLatestValue(double latestValue) { this.latestValue = latestValue; }

    public double getSellValue() { return sellValue; }

    public void setSellValue(double sellValue) { this.sellValue = sellValue; }

    /*****************************/
    // make class parseable (Auto-generated)
    protected Stock(Parcel in) {
        sid = in.readInt();
        companyName = in.readString();
        symbol = in.readString();
        primaryExchange = in.readString();
        latestValue = in.readDouble();
        sellValue = in.readDouble();
        stockPrice = in.readDouble();
        numberOfStocks = in.readInt();
        stockSector = in.readString();
        timeStamp = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(sid);
        dest.writeString(companyName);
        dest.writeString(symbol);
        dest.writeString(primaryExchange);
        dest.writeDouble(latestValue);
        dest.writeDouble(sellValue);
        dest.writeDouble(stockPrice);
        dest.writeInt(numberOfStocks);
        dest.writeString(stockSector);
        dest.writeString(timeStamp);
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