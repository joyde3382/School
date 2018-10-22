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

    @ColumnInfo(name = "Name")
    private String stockName;

    @ColumnInfo(name = "Price")
    private double stockPrice;

    @ColumnInfo(name = "Number of stocks")
    private int numberOfStocks;

    @ColumnInfo(name = "Stock Sector")
    private String stockSector;

    private Date date;

    public Stock(String stockName, double stockPrice, int numberOfStocks, String stockSector) {
        this.stockName = stockName;
        this.stockPrice = stockPrice;
        this.numberOfStocks = numberOfStocks;
        this.stockSector = stockSector;
        this.date = new Date(System.currentTimeMillis());
    }

    public int getSid() { return sid; }

    public void setSid(int sid) { this.sid = sid; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

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
        sid = in.readInt();
        stockName = in.readString();
        stockPrice = in.readDouble();
        numberOfStocks = in.readInt();
        stockSector = in.readString();
        long tmpDate = in.readLong();
        date = tmpDate != -1 ? new Date(tmpDate) : null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(sid);
        dest.writeString(stockName);
        dest.writeDouble(stockPrice);
        dest.writeInt(numberOfStocks);
        dest.writeString(stockSector);
        dest.writeLong(date != null ? date.getTime() : -1L);
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
