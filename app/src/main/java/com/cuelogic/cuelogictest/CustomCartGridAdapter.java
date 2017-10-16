package com.cuelogic.cuelogictest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CustomCartGridAdapter extends BaseAdapter {
    private Context mContext;
    ArrayList<Product> productArrayList;

    public CustomCartGridAdapter(Context c, ArrayList<Product> productArrayList) {
        mContext = c;
        this.productArrayList.clear();
        this.productArrayList.addAll(productArrayList);
    }

    @Override
    public int getCount() {
        return productArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return productArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.cart_item_view, null);
            ImageView imageView = (ImageView) grid.findViewById(R.id.iv_product);
            TextView textViewProductName = (TextView) grid.findViewById(R.id.tv_product_name);
            TextView textViewVendorName = (TextView) grid.findViewById(R.id.tv_vendor_name);
            TextView textViewVendorAddress = (TextView) grid.findViewById(R.id.tv_vendor_address);
            TextView textViewProductPrice = (TextView) grid.findViewById(R.id.tv_price);

            imageView.setImageBitmap(getBitmapFromURL(productArrayList.get(position).getImageUrl()));
            textViewProductName.setText(productArrayList.get(position).getName());
            textViewVendorName.setText(productArrayList.get(position).getVendorName());
            textViewVendorAddress.setText(productArrayList.get(position).getVendorAddress());
            textViewProductPrice.setText(productArrayList.get(position).getPrice()+"");
        } else {
            grid = (View) convertView;
        }

        return grid;
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }
}