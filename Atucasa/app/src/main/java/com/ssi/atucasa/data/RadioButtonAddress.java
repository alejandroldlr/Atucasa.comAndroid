package com.ssi.atucasa.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Enrique Vargas on 01/04/2015.
 */
public class RadioButtonAddress implements Parcelable {

    private String address;
    private boolean selected;

    public RadioButtonAddress(String address, boolean selected) {
        this.address = address;
        this.selected = selected;
    }

    public RadioButtonAddress(Parcel in) {
        this.address = in.readString();
        this.selected = in.readInt() == 1 ? true:false;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getAddress());
        dest.writeInt(isSelected() ? 1 : 0);
    }


    public static final Parcelable.Creator<RadioButtonAddress> CREATOR = new Parcelable.Creator<RadioButtonAddress>() {
        public RadioButtonAddress createFromParcel(Parcel in) {
            return new RadioButtonAddress(in);
        }

        public RadioButtonAddress[] newArray(int size) {
            return new RadioButtonAddress[size];
        }
    };

}
