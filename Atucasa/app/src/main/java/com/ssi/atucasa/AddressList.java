package com.ssi.atucasa;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.ssi.atucasa.data.Address;
import com.ssi.atucasa.data.AddressDataSource;
import com.ssi.atucasa.data.InteractiveArrayAdapter;
import com.ssi.atucasa.data.RadioButtonAddress;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Enrique Vargas on 01/04/2015.
 */
public class AddressList extends ActionBarActivity {

    private AddressListFragment  newFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            newFragment = new AddressListFragment();
            transaction.add(R.id.container, newFragment);
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_address_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_goRestList:
                goRestaurantList();
                return true;
            /*case R.id.action_goAddressDelivery:
                goAddressDelivery();
                return true;*/
            case R.id.action_logoutAddList:
                Intent signout = new Intent(this,Login.class);
                signout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(signout);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void goRestaurantList(){
        Intent intentAddressList;
        if (newFragment.isAddressSelected()) {
            intentAddressList = new Intent(this, RestaurantList.class);
            startActivity(intentAddressList);
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Para continuar, seleccione una dirección")
                    .setTitle("Direcciones utilizadas")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            builder.show();
        }
    }

    public void goAddressDelivery(){
        Intent intentAddressList;
        intentAddressList = new Intent(this, DeliveryAddress.class);
        startActivity(intentAddressList);
    }

    public static class AddressListFragment extends ListFragment {

        private static final String LOG_TAG = AddressListFragment.class.getSimpleName();

        InteractiveArrayAdapter adapter;
        ArrayList<RadioButtonAddress> data;
        private AddressDataSource datasource;



        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            datasource = new AddressDataSource(getActivity());
            datasource.open();

            if (savedInstanceState == null){
                data = new ArrayList<RadioButtonAddress>();

                List<Address> addList = datasource.getAllAddresses();

                for (Address element:addList){
                    data.add(get(element.getAllAddress()));
                }

                adapter = new InteractiveArrayAdapter(getActivity(), data);
            } else {
                data = savedInstanceState.getParcelableArrayList("savedData");
                adapter = new InteractiveArrayAdapter(getActivity(), data);
            }
            setListAdapter(adapter);

        }


        private RadioButtonAddress get(String s) {
            return new RadioButtonAddress(s,false);
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            outState.putParcelableArrayList("savedData", data);
            super.onSaveInstanceState(outState);
        }

        @Override
        public void onResume() {
            datasource.open();
            super.onResume();
        }

        @Override
        public void onPause() {
            datasource.close();
            super.onPause();
        }

        public boolean isAddressSelected(){
            boolean flag = false;
            for (RadioButtonAddress element:data){
                if (element.isSelected()){
                    flag = true;
                    break;
                }
            }
            return flag;
        }


    }

}
