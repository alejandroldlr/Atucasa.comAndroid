package com.ssi.atucasa;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ssi.atucasa.data.Address;
import com.ssi.atucasa.data.AddressDataSource;

/**
 * Created by Enrique Vargas on 01/04/2015.
 */
public class DeliveryAddress extends ActionBarActivity {

    private static final String LOG_TAG = DeliveryAddress.class.getSimpleName();
    private AddressFragment  newFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_address);
        if (savedInstanceState == null) {
            /*getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new AddressFragment())
                    .commit();*/

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            newFragment = new AddressFragment();
            transaction.add(R.id.container, newFragment);
            transaction.commit();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_delivery_address, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_goSaveAddress:
                newFragment.actionSave();
                return true;
            case R.id.action_goAddressList:
                Intent intentAddressList;
                intentAddressList = new Intent(this, AddressList.class);
                startActivity(intentAddressList);
                return true;
            case R.id.action_mapAddress:
                Intent intentAddressMap = new Intent(this,MapAddress.class);
                startActivity(intentAddressMap);
                return true;
            case R.id.action_logout:
                Intent signout = new Intent(this,Login.class);
                signout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(signout);
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class AddressFragment extends Fragment {

        private static final String LOG_TAG = AddressFragment.class.getSimpleName();

        private EditText inputStreet;
        private EditText inputStreetNro;
        private EditText inputStreetRef1;
        private EditText inputStreetRef2;
        private EditText inputBuilding;
        private EditText inputPhoneNumber;
        private TextView loginErrorMsg;

        /*private ImageButton imgButtonSaveAdd;
        private ImageButton imgButtonListAdd;*/

        private AddressDataSource datasource;

        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            datasource = new AddressDataSource(getActivity());
            datasource.open();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_delivery_address, container, false);

            inputStreet = (EditText) rootView.findViewById(R.id.txtStreet);
            inputStreetNro = (EditText) rootView.findViewById(R.id.txtStreetNro);
            inputStreetRef1 = (EditText) rootView.findViewById(R.id.txtStreetRef1);
            inputStreetRef2 = (EditText) rootView.findViewById(R.id.txtStreetRef2);
            inputBuilding = (EditText) rootView.findViewById(R.id.txtBuilding);
            inputPhoneNumber = (EditText) rootView.findViewById(R.id.txtPhoneNumber);

            /*imgButtonSaveAdd = (ImageButton) rootView.findViewById(R.id.imgButtonSaveAdd);
            imgButtonSaveAdd.setBackgroundColor(Color.TRANSPARENT);
            imgButtonListAdd = (ImageButton) rootView.findViewById(R.id.imgButtonListAdd);
            imgButtonListAdd.setBackgroundColor(Color.TRANSPARENT);

            onSaveButton();
            getAdressList();*/

            return rootView;
        }

        public String validateAddress(){

            String value ="";

            String street = inputStreet.getText().toString();
            String streetNro = inputStreetNro.getText().toString();
            String streetRef1 = inputStreetRef1.getText().toString();
            String streetRef2 = inputStreetRef2.getText().toString();
            String phoneNumber = inputPhoneNumber.getText().toString();

            street = street.trim();
            streetNro = streetNro.trim();
            streetRef1 = streetRef1.trim();
            streetRef2 = streetRef2.trim();
            phoneNumber = phoneNumber.trim();

            if(street.isEmpty()){
                value = "- Calle\n";
            }

            if (streetNro.isEmpty()){
                value = value + "- Nro. casa\n";
            }

            if (streetRef1.isEmpty()){
                value = value + "- Calle referencia 1\n";
            }

            if (streetRef2.isEmpty()){
                value = value + "- Calle referencia 2\n";
            }

            if (phoneNumber.isEmpty()){
                value = value + "- Telefono referencia";
            }

            return value;
        }

        public void actionSave(){

            String messageAlert = validateAddress();

            //Si no se registro algun campo obligatorio de la dirección
            if (!(messageAlert == null||messageAlert.isEmpty()||messageAlert.equalsIgnoreCase(""))){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setMessage("Ingrese los siguientes datos:\n" + messageAlert)
                        .setTitle("Campos obligatorios dirección")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                builder.show();
            }else{

                saveAddress();

            }
        }

        /*
        public void onSaveButton(){

            imgButtonSaveAdd.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {

                    String messageAlert = validateAddress();

                    //Si no se registro algun campo obligatorio de la dirección
                    if (!(messageAlert == null||messageAlert.isEmpty()||messageAlert.equalsIgnoreCase(""))){
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                        builder.setMessage("Ingrese los siguientes datos:\n" + messageAlert)
                                .setTitle("Campos obligatorios dirección")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        builder.show();
                    }else{

                        saveAddress();

                    }
                }
                                              }

            );
        }*/

        public void saveAddress(){
            Address newAddress = new Address();
            newAddress.setStreet(inputStreet.getText().toString());
            newAddress.setHouseNumber(inputStreetNro.getText().toString());
            newAddress.setReference1Street(inputStreetRef1.getText().toString());
            newAddress.setReference2Street(inputStreetRef2.getText().toString());
            newAddress.setBuilding(inputBuilding.getText().toString());
            newAddress.setReferencePhone(inputPhoneNumber.getText().toString());

            datasource.createAddress(newAddress);
            //Toast.makeText(getActivity(), "Dirección guardada",Toast.LENGTH_SHORT).show();

            Intent intentAddressList;
            intentAddressList = new Intent(getActivity(), RestaurantList.class);
            startActivity(intentAddressList);


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

        /*public void getAdressList(){
            imgButtonListAdd.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {

                    Intent intentAddressList;

                    intentAddressList = new Intent(getActivity(), AddressList.class);
                    startActivity(intentAddressList);

                }
            });
        }*/




    }
}
