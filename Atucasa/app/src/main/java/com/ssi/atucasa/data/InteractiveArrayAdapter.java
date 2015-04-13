package com.ssi.atucasa.data;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ssi.atucasa.R;

/**
 * Created by Enrique Vargas on 01/04/2015.
 */
public class InteractiveArrayAdapter extends ArrayAdapter<RadioButtonAddress> {

    private static final String TAG = "InteractiveArrayAdapter";
    private static int convertViewCounter = 0;

    private ArrayList<RadioButtonAddress> list;
    private LayoutInflater inflater = null;

    static class ViewHolder {
        protected TextView text;
        protected RadioButton radioButton;
        protected RadioGroup radioGroup;

    }

    private RadioButton listRadioButton = null;
    int listIndex = -1;

    public InteractiveArrayAdapter(Context context, ArrayList<RadioButtonAddress> list) {
        super(context, R.layout.fragment_address_list, list);
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        Log.v(TAG, "in getCount()");
        return list.size();
    }

    @Override
    public RadioButtonAddress getItem(int position)
    {
        Log.v(TAG, "in getItem() for position " + position);
        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        Log.v(TAG, "in getItemId() for position " + position);
        return position;
    }

    @Override
    public int getViewTypeCount()
    {
        Log.v(TAG, "in getViewTypeCount()");
        return 1;
    }

    @Override
    public int getItemViewType(int position)
    {
        Log.v(TAG, "in getItemViewType() for position " + position);
        return 0;
    }

    @Override
    public void notifyDataSetChanged()
    {
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.fragment_address_list, null);
            convertViewCounter++;
            holder = new ViewHolder();

            holder.text = (TextView) convertView.findViewById(R.id.labelAddress);
            holder.radioButton = (RadioButton) convertView.findViewById(R.id.checkAddress);
            holder.radioGroup = (RadioGroup) convertView.findViewById(R.id.radioGroupAdd);

            holder.radioButton.setOnClickListener(checkListener);

            /*holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    int size = getCount();

                    Log.v(TAG, "Tamano lista: " + size);
                    Log.v(TAG, "checkedId parameter: " + checkedId);

                    RadioButton auxRB;

                    for(int i = 0; i < group.getChildCount(); i++) {

                        auxRB = (RadioButton) group.getChildAt(i);
                        if ((auxRB.isChecked())&&(group.getChildAt(i).getId() != checkedId)) {

                            auxRB.setChecked(false);
                        }
                    }



                    RadioButtonAddress d = (RadioButtonAddress) holder.radioButton.getTag();
                    d.setSelected(!d.isSelected());

                }
            });*/


            convertView.setTag(holder);

            /*holder.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,
                                                     boolean isChecked) {
                            RadioButtonAddress element = (RadioButtonAddress) viewHolder.radioButton.getTag();
                            element.setSelected(buttonView.isChecked());
                            notifyDataSetChanged();

                        }
                    });



            view.setTag(viewHolder);
            viewHolder.radioButton.setTag(list.get(position));*/
        } else {
            /*view = convertView;
            ((ViewHolder) view.getTag()).radioButton.setTag(list.get(position));*/
            holder = (ViewHolder) convertView.getTag();


        }

        /*ViewHolder holder = (ViewHolder) view.getTag();
        holder.text.setText(list.get(position).getName());
        holder.radioButton.setChecked(list.get(position).isSelected());*/

        // Para porde hacer click en el checkbox
        RadioButtonAddress d = (RadioButtonAddress) getItem(position);
        holder.radioButton.setTag(d);
        // Setting all values in listview
        holder.text.setText(list.get(position).getAddress());
        holder.radioButton.setChecked(list.get(position).isSelected());


        return convertView;
    }

    public void setCheck(int position)
    {
        RadioButtonAddress d = list.get(position);

        d.setSelected(!d.isSelected());
        notifyDataSetChanged();
    }

    public void cancelSelectedPost()
    {

        int i = 0;
        while (i < getCount())
        {
            if (list.get(i).isSelected())
            {
                list.remove(list.indexOf(list.get(i)));
            } else
                i++;
        }
        notifyDataSetChanged();

    }

    public boolean haveSomethingSelected()
    {
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).isSelected())
                return true;
        return false;
    }

    /**
     * Este método es para poder seleccionar una fila directamente con el
     * checkbox en lugar de tener que pulsar en la liste en sí
     */
    private View.OnClickListener checkListener = new View.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {
            Log.v(TAG, "************SELECCION RADIO BUTTON *************");
            RadioButtonAddress d = (RadioButtonAddress) v.getTag();
            d.setSelected(!d.isSelected());

            View vMain = ((View) v.getParent());
            if (listRadioButton != null) listRadioButton.setChecked(false);
            listRadioButton = (RadioButton) v;

            if (listRadioButton.isChecked()) {
                listIndex = ((ViewGroup) vMain.getParent()).indexOfChild(vMain);
            } else {
                listRadioButton = null;
                listIndex = -1;
            }
        }
    };

}

