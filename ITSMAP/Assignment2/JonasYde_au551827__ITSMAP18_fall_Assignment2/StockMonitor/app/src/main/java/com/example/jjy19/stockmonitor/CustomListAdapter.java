package com.example.jjy19.stockmonitor;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


    // map object to text fields
    @Override
    public void onBindViewHolder(@NonNull StockHolder holder, int position) {
        Stock currentStock = stocks.get(position);
        holder.listStockName.setText(currentStock.getCompanyName());
        holder.listStockPrice.setText(Double.toString(currentStock.getStockPrice()));
        holder.listStockDifferens.setText(Double.toString(currentStock.getSellValue()));

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

    // init the holder class
    static class StockHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView listStockName;
        private TextView listStockPrice;
        private TextView listStockDifferens;

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