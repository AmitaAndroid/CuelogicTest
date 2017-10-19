package com.cuelogic.cuelogictest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomProductGridAdapter extends BaseAdapter {
    private Context mContext;
    ArrayList<Product> productArrayList = new ArrayList<>();

    public CustomProductGridAdapter(Context c, ArrayList<Product> productArrayList) {
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
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.product_item_view, null);
            ImageView imageView = (ImageView) grid.findViewById(R.id.iv_product);
            TextView textViewProductName = (TextView) grid.findViewById(R.id.tv_product_name);
            TextView textViewVendorName = (TextView) grid.findViewById(R.id.tv_vendor_name);
            TextView textViewVendorAddress = (TextView) grid.findViewById(R.id.tv_vendor_address);
            TextView textViewProductPrice = (TextView) grid.findViewById(R.id.tv_price);
            TextView btnAddToCart = (TextView) grid.findViewById(R.id.tv_add_to_cart);

            Picasso.with(mContext).load(productArrayList.get(position).getImageUrl()).into(imageView);
            textViewProductName.setText(productArrayList.get(position).getName());
            textViewVendorName.setText(productArrayList.get(position).getVendorName());
            textViewVendorAddress.setText(productArrayList.get(position).getVendorAddress());
            textViewProductPrice.setText("Price: Rs. "+productArrayList.get(position).getPrice());

            btnAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.cartArrayList.add(MainActivity.productArrayList.get(position));
                    Toast.makeText(v.getContext(), "Added to Cart", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();

                }
            });
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}