package com.ssi.atucasa;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Enrique Vargas on 01/04/2015.
 */
public class Login extends ActionBarActivity {

    private static final String LOG_TAG = Login.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG,"Entro a Login.onCreate");
        setContentView(R.layout.activity_login);

        if (savedInstanceState == null) {
            Log.d(LOG_TAG,"Creando instancia PlaceholderFragment");
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new LoginFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class LoginFragment extends Fragment {

        private TextView linkRegister;
        private Button btnLogin;
        private Button btnContinue;
        private EditText inputEmail;
        private EditText inputPassword;
        private TextView loginErrorMsg;

        private ImageView imgViewHouse;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Log.d(LOG_TAG,"Entro a PlaceholderFragment.onCreate");
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_login, container, false);
            Log.d(LOG_TAG,"Entro a PlaceholderFragment.onCreateView");
            imgViewHouse = (ImageView) rootView.findViewById(R.id.imgViewHouse);

            goAddressDelivery();
            /*inputEmail =  (EditText) rootView.findViewById(R.id.txtEmail);
            inputPassword = (EditText) rootView.findViewById(R.id.txtPass);
            btnLogin = (Button) rootView.findViewById(R.id.btnLogin);
            btnContinue = (Button) rootView.findViewById(R.id.btnContinue);
            linkRegister = (TextView) rootView.findViewById(R.id.viewRegister);
            loginErrorMsg = (TextView) rootView.findViewById(R.id.login_error);

            onContinueButton();

            onRegisterButton();*/


            return rootView;
        }

        public void goAddressDelivery(){
            imgViewHouse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentDeliveryAdd = new Intent(getActivity(), DeliveryAddress.class);
                    startActivity(intentDeliveryAdd);
                }
            });
        }

        public void onContinueButton(){

            btnContinue.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {
                    String email = inputEmail.getText().toString();
                    String password = inputPassword.getText().toString();

                    Intent intentDeliveryAdd;

                    User user = new User();

                    /*usuario.setOnLoginUsuario(new OnLoginUsuario() {
                        @Override
                        public void onLoginWrong(String msg) {loginErrorMsg.setText(msg);}
                        @Override
                        public void onLoginCorrect(JSONObject json, String msg) {

                        }
                    });*/

                    boolean loginOk = user.login(getActivity(), email, password);

                    if (loginOk) {
                        loginErrorMsg.setText("");
                        intentDeliveryAdd = new Intent(getActivity(), DeliveryAddress.class);
                        startActivity(intentDeliveryAdd);
                    }else {
                        loginErrorMsg.setText("Error en logueo");
                    }
                }
            });
        }

        public void onRegisterButton(){
            linkRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentUserReg = new Intent(getActivity(), UserRegister.class);
                    startActivity(intentUserReg);}
            });
        }


    }


}
