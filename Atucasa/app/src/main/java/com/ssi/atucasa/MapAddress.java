package com.ssi.atucasa;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Enrique Vargas on 11/04/2015.
 */
public class MapAddress extends Activity {

    private static final String LOG_TAG = MapAddress.class.getSimpleName();

    private boolean isGPSEnabled = false;
    private boolean canGetLocation = false;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; //Minima distancia para realizar cambios en metros, 10 metros
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 2; // //Minimo tiempo entre las actualizaciones de ubicacion, en milisegundos,2 minutos

    private static final int TWO_MINUTES = 1000 * 60 * 2;
    // Google Map
    private GoogleMap googleMap;
    /*private LocationManager locationManager;
    private Location myLocation;
    private Location lastKnownLocation;
    private double latitude;
    private double longitude;*/
    private LatLng latLong;

    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);

        try {
            initilizeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Funcion que carga el mapa. Si el mapa no esta creado entonces crea uno
     * */
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

            // Verificar si el mapa fue creado
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),"Lo lamentamos! no se pudo generar el mapa", Toast.LENGTH_SHORT).show();
            }
        }

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        gps = new GPSTracker(MapAddress.this);

        // Verificar si se puede obtener la ubicacion
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            latLong = new LatLng(latitude, longitude);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLong, 15);
            googleMap.animateCamera(cameraUpdate);
            String addressLine = gps.getAddressLine(this);
            Log.d(LOG_TAG, "La Direccion es:"+addressLine+" ************************");

            //gps.stopUsingGPS();
        }else{
            gps.showSettingsAlert();
        }

        //requestLocation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }

   /* public void requestLocation(){

        LocationListener locationListener;
        try {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            Log.d(LOG_TAG, "Entro a requestLocation()  ********************");
            //Si GPS esta habilitado, obtener lat/long usando el Servicio GPS
            if (isGPSEnabled) {
                Log.d(LOG_TAG, "GPS Habilitado  ********************");
                this.canGetLocation = true;

                if (this.myLocation == null) {
                   locationListener = new LocationListener() {
                       @Override
                        public void onLocationChanged(Location location) {
                       // Called when a new location is found by GPS location provider.
                            Log.d(LOG_TAG, "Entro a onLocationChanged  ********************");
                            lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if(isBetterLocation(location, lastKnownLocation)){
                                myLocation = location;
                                Log.d(LOG_TAG, "Mejor ubicacion location ********************");
                            }else{
                                myLocation = lastKnownLocation;
                                Log.d(LOG_TAG, "Mejor ubicacion lastKnownLocation ********************");
                            }


                        }

                        public void onStatusChanged(String provider, int status, Bundle extras) {}

                        public void onProviderEnabled(String provider) {}

                        public void onProviderDisabled(String provider) {}
                   };

                    Log.d(LOG_TAG, "Invocando locationManager.requestLocationUpdates ********************");
                    // Register the listener with the Location Manager to receive location updates
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (android.location.LocationListener)locationListener);

                    if (locationManager != null) {
                        Log.d(LOG_TAG, "locationManager IS NOT NULL ********************");
                        if (myLocation == null){
                          myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        }
                        updateGPSCoordinates();
                        //Para que se deje de buscar una actualizacion con el GPS y para que no
                        //consuma bateria
                        locationManager.removeUpdates((android.location.LocationListener)locationListener);
                    }
                 }
            }
        }catch (Exception e){
            Log.e("Error : Ubicacion", "No se pudo conectar al LocationManager", e);
        }

    }*/

    /*public void updateGPSCoordinates()
    {
        Log.d(LOG_TAG, "Entro a updateGPSCoordinates ********************");
        if (this.myLocation != null)
        {   Log.d(LOG_TAG, "myLocation IS NOT NULL ********************");
            latitude = this.myLocation.getLatitude();
            longitude = this.myLocation.getLongitude();

            latLong = new LatLng(latitude, longitude);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLong, 15);
            googleMap.animateCamera(cameraUpdate);
        }
    }*/

    /** Determines whether one Location reading is better than the current Location fix
     * @param location  The new Location that you want to evaluate
     * @param currentBestLocation  The current Location fix, to which you want to compare the new one
     */
    /*protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }*/

    /** Checks whether two providers are the same */
    /*private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }*/
}