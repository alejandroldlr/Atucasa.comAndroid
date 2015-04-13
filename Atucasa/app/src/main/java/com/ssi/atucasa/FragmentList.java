package com.ssi.atucasa;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONException;

import java.util.StringTokenizer;

/**
 * A placeholder fragment containing a simple view.
 */
public  class FragmentList extends Fragment {
    final Integer MAX_RESTAURANTS = 4;
    private String[] restaurantList;
    private String[] foodTypeList;
    private String[] scheduleList;
    private String[] statusList;
    // private Integer[] imageIdList;
    RestaurantAdapter adapter;
    GetResultTask task;

    ListView list;
    Integer[] imageIdList = {
            R.drawable.wh,
            R.drawable.carniclub,
            R.drawable.factory,
            R.drawable.lcdc

    };


    public FragmentList() {
        restaurantList = new String[MAX_RESTAURANTS];
        foodTypeList = new String[MAX_RESTAURANTS];
        scheduleList = new String[MAX_RESTAURANTS];
        statusList = new String[MAX_RESTAURANTS];
        //imageIdList= new Integer [MAX_RESTAURANTS];
    }


    @Override
    public void onStart() {
        super.onStart();
        task = new GetResultTask();
        task.execute();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_restaurant_list, container, false);


        adapter = new
                RestaurantAdapter(getActivity(), restaurantList, imageIdList, foodTypeList, scheduleList, statusList);
        adapter.updateList(restaurantList, foodTypeList, scheduleList, statusList, imageIdList);
        list = (ListView) rootView.findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(getActivity(), RestaurantMenuActivity.class);
                intent.putExtra("restaurantName",restaurantList[position]);
                intent.putExtra("foodType",foodTypeList[position]);
                intent.putExtra("schedule",scheduleList[position]);
                intent.putExtra("status",statusList[position]);
                intent.putExtra("image",Integer.toString(position));
                startActivity(intent);

            }
        });
        return rootView;
    }

     class  GetResultTask extends AsyncTask<String, Void, String []> {

        @Override
        protected String[] doInBackground(String... params) {
            String[] strings;
            String resultString = Utility.getJsonStringFromNetwork();

            try {
              return Utility.parseFixtureJson(resultString);
            } catch (JSONException e) {
                //Log.e(LOG_TAG, "Error parsing" + e.getMessage(), e);
                e.printStackTrace();
                return new String[] {"No DATA"};
            }



        }

        @Override
        protected void onPostExecute(String[] strings) {
//                adapter.clear();
            int count = 0;
            String name = null;
            String foodType = null;
            String schedule = null;
            String status = null;
            String imageId = null;

            restaurantList = new String[strings.length];
            foodTypeList = new String[strings.length];
            scheduleList = new String[strings.length];
            statusList = new String[strings.length];
            // imageIdList = new Integer [strings.length];
            for (String result : strings) {

                StringTokenizer st = new StringTokenizer(result, ",");

                while(st.hasMoreTokens()) {
                    name = st.nextToken();
                    foodType = st.nextToken();
                    schedule = st.nextToken();
                    status = st.nextToken();
                    imageId = st.nextToken();
                }

                restaurantList[count] = name;
                foodTypeList[count] = foodType;
                scheduleList[count] = schedule;
                statusList[count] = status;
                //imageIdList[count] = imageId;

                count++;
            }
            adapter.updateList(restaurantList, foodTypeList, scheduleList, statusList, imageIdList);
            adapter.notifyDataSetChanged();
        }
    }


}
