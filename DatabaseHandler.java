package com.example.hesham.thirdassignmentgps;
import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.maps.model.LatLng;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "lat";
    private static final String KEY_PH_NO = "longitude";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                +  KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }


    // Adding new contact
    void addLatLng(LatLng latlng) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        String lat=latlng.latitude+"";
        String longg=latlng.longitude+"";
        values.put(KEY_NAME, lat);
        values.put(KEY_PH_NO, longg);

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }

    // Getting All Contacts
    public double[][] getAllContacts() {

        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        double[][] arr=new double[cursor.getCount()][cursor.getColumnCount()];


        if (cursor.moveToFirst()) {
            int i=0;
            do {
                double x=Double.parseDouble(cursor.getString(0)); // latitude
                double y=Double.parseDouble(cursor.getString(1)); // longitude

                arr[i][0]=x;
                arr[i][1]=y;
                i++;
            } while (cursor.moveToNext());
        }

        return arr;
    }
    public ArrayList<LatLng> getlat() {

        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<LatLng> arr=new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                double x=Double.parseDouble(cursor.getString(0)); // latitude
                double y=Double.parseDouble(cursor.getString(1)); // longitude

                arr.add(new LatLng(x, y));

            } while (cursor.moveToNext());
        }

        return arr;
    }
}