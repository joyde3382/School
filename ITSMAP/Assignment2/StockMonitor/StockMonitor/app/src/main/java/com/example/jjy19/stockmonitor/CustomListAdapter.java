package com.example.jjy19.stockmonitor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jjy19.stockmonitor.Objects.Stock;

import java.util.ArrayList;


public class CustomListAdapter extends ArrayAdapter<Stock> {

    private Context mContext;
    int mResource;

    public CustomListAdapter(Context context, int resource, ArrayList<Stock> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String stockName = getItem(position).getCompanyName();
        double stockCurrentPrice = getItem(position).getStockPrice();
        double stockDifferens = getItem(position).getStockPrice(); // TODO update with differens in price

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);


        TextView listStockName = convertView.findViewById(R.id.list_StockName);
        listStockName.setText(stockName);

        TextView listStockPrice = convertView.findViewById(R.id.list_StockCurrentPrice);
        listStockPrice.setText(Double.toString(stockCurrentPrice));

        TextView listStockDifferens = convertView.findViewById(R.id.list_StockDifferens);
        listStockDifferens.setText(Double.toString(stockDifferens));


        return convertView;
        //return super.getView(position, convertView, parent);
    }
}
