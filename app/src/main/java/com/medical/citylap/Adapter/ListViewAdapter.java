package com.medical.citylap.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.medical.citylap.R;

public class ListViewAdapter  extends BaseAdapter {
    Context context;
    String []item;
    LayoutInflater layoutInflater;

    @Override
    public int getCount() {

        return item.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View iteme=layoutInflater.inflate(R.layout.item_loctaion,parent,false);
        textView=iteme.findViewById(R.id.text_location);
        textView.setText(item[position]);

        return iteme;
    }

    public ListViewAdapter(Context context, String[] item) {
        this.context = context;
        this.item = item;
    }

}
