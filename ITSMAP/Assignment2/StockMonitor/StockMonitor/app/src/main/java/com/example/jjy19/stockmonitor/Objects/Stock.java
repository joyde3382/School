package com.example.jjy19.stockmonitor.Objects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

@Entity
public class Stock implements Parcelable {



    @PrimaryKey(autoGenerate = true)
    private int sid;

    @ColumnInfo(name = "Company Name")
    private String companyName;

    @ColumnInfo(name = "Symbol")
    private String symbol;

    @ColumnInfo(name = "Primary Exchange")
    private double primaryExchange;

    @ColumnInfo(name = "Latest Value")
    private double latestValue;

    @ColumnInfo(name = "Bought Price")
    private double stockPrice;

    @ColumnInfo(name = "Number of stocks")
    private int numberOfStocks;

    @ColumnInfo(name = "Stock Sector")
    private String stockSector;



    @ColumnInfo(name = "Time Stamp")
    private Date timeStamp;

    public Stock(String companyName, String symbol , double stockPrice, int numberOfStocks, String stockSector) {
        this.companyName = companyName;
        this.symbol = symbol;
        this.stockPrice = stockPrice;
        this.numberOfStocks = numberOfStocks;
        this.stockSector = stockSector;
        this.timeStamp = new Date(System.currentTimeMillis());
    }

    public int getSid() { return sid; }

    public void setSid(int sid) { this.sid = sid; }

    public Date getTimeStamp() { return timeStamp; }

    public void setTimeStamp(Date timeStamp) { this.timeStamp = timeStamp; }

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

    public double getPrimaryExchange() { return primaryExchange; }

    public void setPrimaryExchange(double primaryExchange) { this.primaryExchange = primaryExchange; }

    public double getLatestValue() { return latestValue; }

    public void setLatestValue(double latestValue) { this.latestValue = latestValue; }


    public static Stock[] populateData() {

        return new Stock[]{

                new Stock("Apple", "aapl", 100, 5, "Technology"),
                new Stock("Microsoft", "MSFT", 200, 6, "Technology"),
                new Stock("Google", "GOOGL", 300, 7, "Technology"),
                new Stock("Tesla", "TSLA", 400, 8, "Technology"),
                new Stock("Vestas", "VWS", 230, 9, "Technology"),
                new Stock("Bitcoin", "XBT", 40, 10, "Technology"),
                new Stock("Ethereum", "GDAX", 10, 11, "Technology"),
                new Stock("General Motors", "GM", 240, 12, "Technology"),
                new Stock("Sony", "SNE", 440, 13, "Technology"),
                new Stock("Amazon", "AMZN", 500, 14, "Technology")

        };
    }
    /*****************************/
    // make class parseable (Auto-generated)
    protected Stock(Parcel in) {
        sid = in.readInt();
        companyName = in.readString();
        symbol = in.readString();
        primaryExchange = in.readDouble();
        latestValue = in.readDouble();
        stockPrice = in.readDouble();
        numberOfStocks = in.readInt();
        stockSector = in.readString();
        long tmpTimeStamp = in.readLong();
        timeStamp = tmpTimeStamp != -1 ? new Date(tmpTimeStamp) : null;
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
        dest.writeDouble(primaryExchange);
        dest.writeDouble(latestValue);
        dest.writeDouble(stockPrice);
        dest.writeInt(numberOfStocks);
        dest.writeString(stockSector);
        dest.writeLong(timeStamp != null ? timeStamp.getTime() : -1L);
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