package com.example.deepakrattan.xmlparsing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by deepak.rattan on 5/10/2017.
 */

public class HoroscopeAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Horoscope> horoscopeArrayList;
    private LayoutInflater inflater;

    public HoroscopeAdapter(Context context, ArrayList<Horoscope> horoscopeArrayList) {
        this.context = context;
        this.horoscopeArrayList = horoscopeArrayList;
    }

    @Override
    public int getCount() {
        return horoscopeArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return horoscopeArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.horoscope, viewGroup, false);

        TextView txtName = (TextView) view.findViewById(R.id.txtName);
        TextView txt = (TextView) view.findViewById(R.id.txt);


        Horoscope h =horoscopeArrayList.get(i);
        txtName.setText(h.getName());
        txt.setText(h.getText());


        return view;
    }
}
