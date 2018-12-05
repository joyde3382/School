package com.example.jjy19.stockmonitor;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jjy19.stockmonitor.Objects.Stock;

import java.util.ArrayList;
import java.util.List;


public class CustomListAdapter extends RecyclerView.Adapter<CustomListAdapter.StockHolder> {

    private List<Stock> stocks = new ArrayList<>();
    private static ClickListener clickListener;

    @NonNull
    @Override
    public StockHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_layout, parent, false);
        return new StockHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StockHolder holder, int position) {
        Stock currentStock = stocks.get(position);
        holder.listStockName.setText(currentStock.getCompanyName());
        holder.listStockPrice.setText(Double.toString(currentStock.getStockPrice()));
        holder.listStockDifferens.setText(Double.toString(currentStock.getPrimaryExchange()));

    }

    public void setOnItemClickListener(ClickListener clickListener) {
        CustomListAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }

    public void setStocks (List<Stock> stocks) {
        this.stocks = stocks;
        notifyDataSetChanged();
    }

    public Stock getStockAt(int position) {
        return stocks.get(position);
    }

//    public void updatePage ()

    static class StockHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView listStockName;
        private TextView listStockPrice;
        private TextView listStockDifferens;

        RelativeLayout parentLayout;

        public StockHolder(View itemView) {
            super(itemView);
            listStockName = itemView.findViewById(R.id.list_StockName);
            listStockPrice = itemView.findViewById(R.id.list_StockCurrentPrice);
            listStockDifferens = itemView.findViewById(R.id.list_StockDifferens);

            itemView.setOnClickListener((View.OnClickListener) this);
            itemView.setOnLongClickListener((View.OnLongClickListener) this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }
    }

}

//
//    public CustomListAdapter(Context context, int resource, ArrayList<Stock> objects) {
//        super(context, resource, objects);
//        mContext = context;
//        mResource = resource;
//    }
//
//    public void setStocks(List<Stock> stocks) {
//        this.stocks = stocks;
//        notifyDataSetChanged();
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        String stockName = getItem(position).getCompanyName();
//        double stockCurrentPrice = getItem(position).getStockPrice();
//        double stockDifferens = getItem(position).getStockPrice(); // TODO update with differens in price
//
//        LayoutInflater inflater = LayoutInflater.from(mContext);
//        convertView = inflater.inflate(mResource, parent, false);
//
//
//        TextView listStockName = convertView.findViewById(R.id.list_StockName);
//        listStockName.setText(stockName);
//
//        TextView listStockPrice = convertView.findViewById(R.id.list_StockCurrentPrice);
//        listStockPrice.setText(Double.toString(stockCurrentPrice));
//
//        TextView listStockDifferens = convertView.findViewById(R.id.list_StockDifferens);
//        listStockDifferens.setText(Double.toString(stockDifferens));
//
//
//        return convertView;
//        //return super.getView(position, convertView, parent);
//    }
//}
