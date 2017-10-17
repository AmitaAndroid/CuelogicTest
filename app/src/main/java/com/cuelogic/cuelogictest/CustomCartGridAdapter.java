package com.cuelogic.cuelogictest;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CustomCartGridAdapter extends BaseAdapter {
    private Context mContext;
    ArrayList<Product> productArrayList = new ArrayList<>();

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)

        {
            grid = inflater.inflate(R.layout.cart_item_view, null);
            ImageView imageView = (ImageView) grid.findViewById(R.id.iv_product);
            TextView textViewProductName = (TextView) grid.findViewById(R.id.tv_product_name);
            TextView textViewVendorName = (TextView) grid.findViewById(R.id.tv_vendor_name);
            TextView textViewVendorAddress = (TextView) grid.findViewById(R.id.tv_vendor_address);
            TextView textViewProductPrice = (TextView) grid.findViewById(R.id.tv_price);
            Button btnCallVendor = (Button) grid.findViewById(R.id.btn_call_vendor);
            Button btnRemoveFromCart = (Button) grid.findViewById(R.id.btn_remove_from_cart);


            Picasso.with(mContext).load(productArrayList.get(position).getImageUrl()).into(imageView);
//            imageView.setImageBitmap(getBitmapFromURL(productArrayList.get(position).getImageUrl()));
            textViewProductName.setText(productArrayList.get(position).getName());
            textViewVendorName.setText(productArrayList.get(position).getVendorName());
            textViewVendorAddress.setText(productArrayList.get(position).getVendorAddress());
            textViewProductPrice.setText("Price: Rs. " + productArrayList.get(position).getPrice());

            btnCallVendor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                    phoneIntent.setData(Uri.parse("tel:"+MainActivity.cartArrayList.get(position).getPhoneNo()));
                    if (ActivityCompat.checkSelfPermission(mContext,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mContext.startActivity(phoneIntent);
                }
            });

            btnRemoveFromCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.cartArrayList.remove(position);
                }
            });
        } else

        {
            grid = (View) convertView;
        }


        return grid;
    }
}