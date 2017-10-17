package com.cuelogic.cuelogictest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
        // TODO Auto-generated method stub
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
            Button btnAddToCart = (Button) grid.findViewById(R.id.btn_add_to_cart);

            Picasso.with(mContext).load(productArrayList.get(position).getImageUrl()).into(imageView);
//            imageView.setImageBitmap(getBitmapFromURL(productArrayList.get(position).getImageUrl()));
            textViewProductName.setText(productArrayList.get(position).getName());
            textViewVendorName.setText(productArrayList.get(position).getVendorName());
            textViewVendorAddress.setText(productArrayList.get(position).getVendorAddress());
            textViewProductPrice.setText("Price: Rs. "+productArrayList.get(position).getPrice());

            btnAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.cartArrayList.add(MainActivity.productArrayList.get(position));
                }
            });
        } else {
            grid = (View) convertView;
        }

        return grid;
    }

    /*public static Bitmap getBitmapFromURL(String src) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;

        try {
            in = new BufferedInputStream(new URL(url).openStream(), IO_BUFFER_SIZE);

            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, IO_BUFFER_SIZE);
            copy(in, out);
            out.flush();

            final byte[] data = dataStream.toByteArray();
            BitmapFactory.Options options = new BitmapFactory.Options();
            //options.inSampleSize = 1;

            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
        } catch (IOException e) {
            Log.e(TAG, "Could not load Bitmap from: " + url);
        } finally {
            closeStream(in);
            closeStream(out);
        }

        return bitmap;
    }*/
}