package com.ssi.atucasa;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Alejandro on 04/04/2015.
 */
public class MenuAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] list;
    private final Integer[] imageId;
    private final String[] price;

    public MenuAdapter(Activity context,String[] list, Integer[] imageId,String[] price) {
        super(context, R.layout.restaurant_detail, list);
        this.context = context;
        this.list = list;
        this.imageId = imageId;
        this.price = price;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.restaurant_detail, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.list_item_string);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img_menu);
        TextView txtPrice = (TextView) rowView.findViewById(R.id.price);
        txtTitle.setText(list[position]);
        imageView.setImageResource(imageId[position]);
        txtPrice.setText(price[position]);
        return rowView;
    }

}

