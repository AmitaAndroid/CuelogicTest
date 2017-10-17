package com.cuelogic.cuelogictest.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cuelogic.cuelogictest.Product;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Cuelogic.db";
    public static final String TABLE_NAME = "products";
    public static final String ID = "id";
    public static final String PRODUCT_NAME = "name";
    public static final String VENDOR_NAME = "vendor_name";
    public static final String VENDOR_ADDRESS = "vendor_address";
    public static final String PHONE = "phone";
    public static final String IMAGE_URL = "image_url";
    public static final String PRICE = "price";
    public static final String IS_IN_CART = "isInCart";

    private HashMap hp;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table " + TABLE_NAME +
                        " (id integer primary key, name text,vendor_name text,vendor_address text, phone text,image_url text,price text,isInCart integer DEFAULT 0)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertProduct(String name, String vendorName, String vendorAddress, String imageUrl, String phoneNo, int price, int isInCart) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCT_NAME, name);
        contentValues.put(VENDOR_NAME, vendorName);
        contentValues.put(VENDOR_ADDRESS, vendorAddress);
        contentValues.put(PHONE, phoneNo);
        contentValues.put(PRICE, price);
        contentValues.put(IMAGE_URL, imageUrl);
        contentValues.put(IS_IN_CART, isInCart);

        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public int getTotalRows() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME+" where id=" + id + "", null);
        return res;
    }

    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            Product product=new Product();
            product.setName(res.getString(res.getColumnIndex(PRODUCT_NAME)));
            product.setVendorName(res.getString(res.getColumnIndex(VENDOR_NAME)));
            product.setVendorAddress(res.getString(res.getColumnIndex(VENDOR_ADDRESS)));
            product.setPhoneNo(res.getString(res.getColumnIndex(PHONE)));
            product.setPrice(Integer.parseInt(res.getString(res.getColumnIndex(PRICE))));
            product.setImageUrl(res.getString(res.getColumnIndex(IMAGE_URL)));
            product.setInCart(Integer.parseInt(res.getString(res.getColumnIndex(IS_IN_CART))));

            array_list.add(product);
            res.moveToNext();
        }
        return array_list;
    }
}