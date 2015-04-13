package com.ssi.atucasa;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Alejandro on 26/03/2015.
 */
public class RestaurantAdapter extends ArrayAdapter<String> {
    private  Activity context;
    private  String[] restaurant;
    private  String[] foodType;
    private  Integer[] imageId;
    private  String[] schedule;
    private  String[] status;

    public RestaurantAdapter(Activity context,
                             String[] restaurant, Integer [] imageId, String[] foodType, String[] schedule, String[] status) {
        super(context, R.layout.list_single, restaurant);
        this.context = context;
//        this.restaurant = restaurant;
//        this.imageId = imageId;
//        this.foodType = foodType;
//        this.schedule = schedule;
//        this.status = status;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        TextView foodTypeText = (TextView) rowView.findViewById(R.id.food_type);
        TextView scheduleText = (TextView) rowView.findViewById(R.id.schedule);
        TextView statusText = (TextView) rowView.findViewById(R.id.status);
        txtTitle.setText(restaurant[position]);
        imageView.setImageResource(imageId[position]);
// To implement with imageViewHelper next release.
//        ImageView auxImage = new ImageView();
//        URL myUrl = null;
//        try {
//            myUrl = new URL(imageId [position]);
//            InputStream inputStream = (InputStream)myUrl.getContent();
//            Drawable drawable = Drawable.createFromStream(inputStream, null);
//            auxImage.setImageDrawable(drawable);
//            imageView.setImageDrawable(drawable);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        foodTypeText.setText(foodType[position]);
        scheduleText.setText(schedule[position]);
        statusText.setText(status[position]);

        return rowView;
    }

    public void  updateList(String [] restaurantList,
                            String []foodTypeList,
                            String [] scheduleList,
                            String [] statusList,
                            Integer [] imageIdList){
       this.restaurant = restaurantList;
       this.imageId = imageIdList;
       this.foodType = foodTypeList;
       this.schedule = scheduleList;
       this.status = statusList;
    }
}