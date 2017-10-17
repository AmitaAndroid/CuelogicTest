package com.cuelogic.cuelogictest;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cuelogic.cuelogictest.db.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductFragment extends Fragment {

    GridView gridView;
    private ProgressDialog pDialog;
    private static String url = "https://mobiletest-hackathon.herokuapp.com/getdata/";
    CustomProductGridAdapter customProductGridAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        gridView = (GridView) view.findViewById(R.id.gv_product);

        customProductGridAdapter=new CustomProductGridAdapter(getActivity(), MainActivity.productArrayList);

        gridView.setAdapter(customProductGridAdapter);

        new GetProducts().execute();

        customProductGridAdapter.notifyDataSetChanged();

        return view;
    }

    private class GetProducts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e("Product Json", "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray products = jsonObj.getJSONArray("products");

                    // looping through All Contacts
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        String productName = c.getString("productname");
                        int price = Integer.parseInt(c.getString("price"));
                        String vendorName = c.getString("vendorname");
                        String vendorAddress = c.getString("vendoraddress");
                        String imageUrl = c.getString("productImg");
                        String phoneNumber = c.getString("phoneNumber");

                        Product product = new Product();

                        product.setName(productName);
                        product.setVendorName(vendorName);
                        product.setVendorAddress(vendorAddress);
                        product.setPhoneNo(phoneNumber);
                        product.setPrice(price);
                        product.setImageUrl(imageUrl);
                        product.setInCart(0);

                        MainActivity.productArrayList.add(product);
                    }
                } catch (final JSONException e) {
/*
                    Toast.makeText(getContext(),
                            "Json parsing error: " + e.getMessage(),
                            Toast.LENGTH_LONG)
                            .show();
*/

                }
            } else {
/*
                Toast.makeText(getContext(),
                        "Couldn't get json from server.",
                        Toast.LENGTH_LONG)
                        .show();
*/
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
        }

    }
}
