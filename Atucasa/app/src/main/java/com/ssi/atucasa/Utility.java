package com.ssi.atucasa;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Formatter;

/**
 * Created by hp on 3/10/2015.
 */
public class Utility {
    private static final String LOG_TAG = Utility.class.getSimpleName();

    public static String getJsonStringFromNetwork() {
        Log.d(LOG_TAG, "Starting network connection");
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;


        try {
            final String FIXTURE_BASE_URL = "http://lopezdelarosa.com/atucasa/index.php";

            Uri builtUri = Uri.parse(FIXTURE_BASE_URL).buildUpon().build();
            URL url = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection)url.openConnection();
            // urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if (inputStream == null)
                return "";
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }

            if (buffer.length() == 0)
                return "";

            return buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                    e.printStackTrace();
                }
            }
        }

        return "";
    }

    public static String[] parseFixtureJson(String restaurantJson) throws JSONException {
        JSONObject jsonObject = new JSONObject(restaurantJson);
        ArrayList<String> result = new ArrayList<>();

        final String LIST = "restaurants";
        final String NAME = "name";
        final String FOOD_TYPE = "foodtype";
        final String SCHEDULE = "schedule";
        final String STATUS = "status";
        final String IMAGE_ID = "imageid";

        JSONArray restaurantArray = jsonObject.getJSONArray(LIST);

        for (int i = 0; i < restaurantArray.length(); i++) {
            String name;
            String foodType;
            String schedule;
            String status;
            String imageId;
            JSONObject matchObject = restaurantArray.getJSONObject(i);

            name = matchObject.getString(NAME);
            foodType = matchObject.getString(FOOD_TYPE);
            schedule = matchObject.getString(SCHEDULE);
            status = matchObject.getString(STATUS);
            imageId = matchObject.getString(IMAGE_ID);

            String resultString = new Formatter().format("%s, %s, %s, %s, %s,", name, foodType,schedule,status,imageId).toString();
            result.add(resultString);
        }
        return result.toArray(new String[result.size()]);
    }
}
