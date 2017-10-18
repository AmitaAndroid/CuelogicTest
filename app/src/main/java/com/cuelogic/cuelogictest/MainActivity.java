package com.cuelogic.cuelogictest;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ProgressDialog pDialog;
    ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
    private int[] tabIcons = {
            R.drawable.product_icon,
            R.drawable.cart_icon
    };
    public static ArrayList<Product> cartArrayList = new ArrayList<>();
    public static ArrayList<Product> productArrayList = new ArrayList<>();

    private static String url = "https://mobiletest-hackathon.herokuapp.com/getdata/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });
        setupViewPager(viewPager);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                 adapter.notifyDataSetChanged();
                 /*switch (position) {
                     case 0:
                         ProductFragment productFragment=new ProductFragment();
                         productFragment.updateFragment();
                         break;

                     case 1:
                         CartFragment cartFragment=new CartFragment();
                         cartFragment.updateFragment();
                         break;
                 }*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        new GetProducts().execute();
//cartArrayList.add(new Product(2000, "Product 1", "Vendor 1", "Pune","https://placeholdit.imgix.net/~text?txtsize=70&txt=Product+5&w=500&h=500&txttrack=0","+919999999997"));
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        setupTabIcons();
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ProductFragment productFragment = new ProductFragment();/*
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("ProductArrayList", productArrayList);
        productFragment.setArguments(bundle);*/
        adapter.addFrag(productFragment, "Product");
        adapter.addFrag(new CartFragment(), "Cart");
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    class GetProducts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
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

                    JSONArray products = jsonObj.getJSONArray("products");

                    MainActivity.productArrayList.clear();
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);
/*
                        String productName = c.getString("productname");
                        int price = Integer.parseInt(c.getString("price"));
                        String vendorName = c.getString("vendorname");
                        String vendorAddress = c.getString("vendoraddress");
                        String imageUrl = c.getString("productImg");
                        String phoneNumber = c.getString("phoneNumber");*/

                        Product product = new Product();

                        product.setName(c.getString("productname"));
                        product.setVendorName(c.getString("vendorname"));
                        product.setVendorAddress(c.getString("vendoraddress"));
                        product.setPhoneNo(c.getString("phoneNumber"));
                        product.setPrice(Integer.parseInt(c.getString("price")));
                        product.setImageUrl(c.getString("productImg"));
//                        product.setInCart(0);

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