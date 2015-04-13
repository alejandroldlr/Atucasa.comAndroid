package com.ssi.atucasa.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Enrique Vargas on 29/03/2015.
 */
public class AddressDataSource {

    // Database fields
    private SQLiteDatabase database;
    private AddressDbHelper dbAddressHelper;
    private String[] allColumns = { AddressDbHelper.COLUMN_ID,
                                    AddressDbHelper.COLUMN_STREET,
                                    AddressDbHelper.COLUMN_HOUSE_NUMBER,
                                    AddressDbHelper.COLUMN_REF1_STREET,
                                    AddressDbHelper.COLUMN_REF2_STREET,
                                    AddressDbHelper.COLUMN_BUILDING,
                                    AddressDbHelper.COLUMN_REF_PHONE};

    public AddressDataSource(Context context) {
        dbAddressHelper = new AddressDbHelper(context);
    }

    public void open() throws SQLException {
        database = dbAddressHelper.getWritableDatabase();
    }

    public void close() {
        dbAddressHelper.close();
    }

    public void createAddress(Address address) {
        ContentValues values = new ContentValues();
        values.put(AddressDbHelper.COLUMN_STREET, address.getStreet());
        values.put(AddressDbHelper.COLUMN_HOUSE_NUMBER, address.getHouseNumber());
        values.put(AddressDbHelper.COLUMN_REF1_STREET, address.getReference1Street());
        values.put(AddressDbHelper.COLUMN_REF2_STREET, address.getReference2Street());
        values.put(AddressDbHelper.COLUMN_BUILDING, address.getBuilding());
        values.put(AddressDbHelper.COLUMN_REF_PHONE, address.getReferencePhone());

        long insertId = database.insert(AddressDbHelper.TABLE_NAME, null,
                values);

        /*Cursor cursor = database.query(AddressDbHelper.TABLE_NAME,
                allColumns, AddressDbHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);

        cursor.moveToFirst();
        Address newAddress = cursorToAddress(cursor);
        cursor.close();

        return newAddress;*/
    }

    public void deleteAddress(Address address) {
        long id = address.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(AddressDbHelper.TABLE_NAME, AddressDbHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Address> getAllAddresses() {
        List<Address> addressList = new ArrayList<Address>();
        Address address;

        Cursor cursor = database.query(AddressDbHelper.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            address = cursorToAddress(cursor);
            addressList.add(address);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return addressList;
    }

    private Address cursorToAddress(Cursor cursor) {
        Address address = new Address();
        address.setId(cursor.getLong(0));
        address.setStreet(cursor.getString(1));
        address.setHouseNumber(cursor.getString(2));
        address.setReference1Street(cursor.getString(3));
        address.setReference2Street(cursor.getString(4));
        address.setBuilding(cursor.getString(5));
        address.setReferencePhone(cursor.getString(6));

        return address;
    }


}
