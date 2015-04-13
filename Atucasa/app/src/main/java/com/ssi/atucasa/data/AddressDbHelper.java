package com.ssi.atucasa.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Enrique Vargas on 26/03/2015.
 */
public class AddressDbHelper  extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "atucasa.db";
    public static final String TABLE_NAME = "address";
    public static final String COLUMN_ID = "_ID";
    public static final String COLUMN_STREET = "street_avenue";
    public static final String COLUMN_HOUSE_NUMBER = "house_number";
    public static final String COLUMN_REF1_STREET = "reference1_street";
    public static final String COLUMN_REF2_STREET = "reference2_street";
    public static final String COLUMN_BUILDING = "building";
    public static final String COLUMN_REF_PHONE = "reference_phone";


    public AddressDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_STREET + " TEXT NOT NULL," +
                COLUMN_HOUSE_NUMBER + " INTEGER NOT_NULL," +
                COLUMN_REF1_STREET + " TEXT NOT NULL," +
                COLUMN_REF2_STREET + " TEXT NOT NULL," +
                COLUMN_REF_PHONE + " INTEGER NOT_NULL," +
                COLUMN_BUILDING + " TEXT" +
                ");";
        db.execSQL(SQL_CREATE_LOCATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }
}
