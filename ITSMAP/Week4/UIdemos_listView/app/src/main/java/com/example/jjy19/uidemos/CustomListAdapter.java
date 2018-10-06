package com.example.jjy19.uidemos;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomListAdapter extends ArrayAdapter<Demos> {

    private  static final String TAG = "DemoListAdapter";
    private Context mContext;
    int mResource;

    public CustomListAdapter(Context context, int resource, ArrayList<Demos> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String demoName = getItem(position).getItemName();

        Demos demo = new Demos(demoName);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);


        TextView demoTextName = convertView.findViewById(R.id.listViewText);
        demoTextName.setText(demoName);

        return convertView;
        //return super.getView(position, convertView, parent);
    }
}
