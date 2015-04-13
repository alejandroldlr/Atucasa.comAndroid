package com.ssi.atucasa;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class RestaurantMenuActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_restaurant_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logoutRestMenu:
                Intent signout = new Intent(this,Login.class);
                signout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(signout);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {


        // Menu list
        private String[] menu = {
                "Hamburguesa doble",
                "Hamburguesa con queso",
                "Coca - Cola"

        };
        private Integer[] imgMenu = {
                R.drawable.burguer2,
                R.drawable.burguer4,
                R.drawable.cocacola
        };
        private String[] price = {
                "Bs. 20",
                "Bs. 25",
                "Bs. 10"

        };

        private Integer[] imageRestaurant = {
                R.drawable.wh,
                R.drawable.carniclub,
                R.drawable.factory,
                R.drawable.lcdc

        };

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_restaurant_menu, container, false);

            //Restaurant details

            String restaurantName = getActivity().getIntent().getExtras().getString("restaurantName");
            TextView txtRestaurant = (TextView) rootView.findViewById(R.id.txt);
            txtRestaurant.setText(restaurantName);

            String imageId = getActivity().getIntent().getExtras().getString("image");
            ImageView imgRest = (ImageView) rootView.findViewById(R.id.img);
            Integer iid = Integer.parseInt(imageId);
            imgRest.setImageResource(imageRestaurant[iid]);

            String foodType = getActivity().getIntent().getExtras().getString("foodType");
            TextView txtFoodType = (TextView) rootView.findViewById(R.id.food_type);
            txtFoodType.setText(foodType);

            String schedule = getActivity().getIntent().getExtras().getString("schedule");
            TextView txtSchedule = (TextView) rootView.findViewById(R.id.schedule);
            txtSchedule.setText(schedule);

            String status = getActivity().getIntent().getExtras().getString("status");
            TextView txtStatus = (TextView) rootView.findViewById(R.id.status);
            txtStatus.setText(status);

            //List menu
            MenuAdapter adapter = new MenuAdapter(getActivity(),menu, imgMenu,price);
            ListView lView = (ListView) rootView.findViewById(R.id.listMenu);
            lView.setAdapter(adapter);
            return rootView;
        }
    }
}
