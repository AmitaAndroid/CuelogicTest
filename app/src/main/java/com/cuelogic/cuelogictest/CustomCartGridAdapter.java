package com.cuelogic.cuelogictest;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomCartGridAdapter extends BaseAdapter {
    private Context mContext;
    ArrayList<Product> productArrayList = new ArrayList<>();
    TextView textViewTotalPrice;

    public CustomCartGridAdapter(Context c, ArrayList<Product> productArrayList, TextView textViewTotalPrice) {
        mContext = c;
        this.productArrayList.clear();
        this.productArrayList.addAll(productArrayList);
        this.textViewTotalPrice = textViewTotalPrice;
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

        if (convertView == null) {
            grid = inflater.inflate(R.layout.cart_item_view, null);
            ImageView imageView = (ImageView) grid.findViewById(R.id.iv_product);
            TextView textViewProductName = (TextView) grid.findViewById(R.id.tv_product_name);
            TextView textViewVendorName = (TextView) grid.findViewById(R.id.tv_vendor_name);
            TextView textViewVendorAddress = (TextView) grid.findViewById(R.id.tv_vendor_address);
            TextView textViewProductPrice = (TextView) grid.findViewById(R.id.tv_price);
            Button btnCallVendor = (Button) grid.findViewById(R.id.btn_call_vendor);

            Button btnRemoveFromCart = (Button) grid.findViewById(R.id.btn_remove_from_cart);

            Picasso.with(mContext).load(productArrayList.get(position).getImageUrl()).into(imageView);
            textViewProductName.setText(productArrayList.get(position).getName());
            textViewVendorName.setText(productArrayList.get(position).getVendorName());
            textViewVendorAddress.setText(productArrayList.get(position).getVendorAddress());
            textViewProductPrice.setText("Price: Rs. " + productArrayList.get(position).getPrice());
            textViewTotalPrice.setText("Total Price: " + calculateTotalPrice());

            btnCallVendor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                    phoneIntent.setData(Uri.parse("tel:" + MainActivity.cartArrayList.get(position).getPhoneNo()));
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.CALL_PHONE}, 200);
                    } else {
                        mContext.startActivity(phoneIntent);
                    }
                }
            });

            btnRemoveFromCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MainActivity.cartArrayList.size() > 0) {
                        MainActivity.cartArrayList.remove(position);
                        Toast.makeText(v.getContext(), "Removed from Cart", Toast.LENGTH_SHORT).show();
                        textViewTotalPrice.setText("Total Price: " + calculateTotalPrice());
                        notifyDataSetChanged();
                    }

                }
            });
        } else {
            grid = (View) convertView;
        }


        return grid;
    }

    private int calculateTotalPrice() {
        int totalPrice = 0;
        for (int i = 0; i < MainActivity.cartArrayList.size(); i++) {
            totalPrice += MainActivity.cartArrayList.get(i).getPrice();
        }
        return totalPrice;
    }
}