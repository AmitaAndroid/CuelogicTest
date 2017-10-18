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
import android.widget.TextView;
import android.widget.Toast;

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
    ViewPagerAdapter adapter;
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
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        new GetProducts().execute();
//        Fragment fragment=new ProductFragment();
//        getFragmentManager().beginTransaction().ddetach(fragment).attach(fragment).commit();
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        setSupportActionBar(toolbar);

        viewPager.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        viewPager.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                adapter.notifyDataSetChanged();
                setupTabIcons();
                setToolbarHeading();
            }
        });
        for (int i=0;i<productArrayList.size();i++){
            Toast.makeText(this,productArrayList.get(i).getName(),Toast.LENGTH_SHORT);
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                adapter.notifyDataSetChanged();
                setupTabIcons();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setupViewPager(viewPager);
        productArrayList.add(new Product(2000, "Product 1", "Vendor 1", "Pune","https://placeholdit.imgix.net/~text?txtsize=70&txt=Product+5&w=500&h=500&txttrack=0","+919999999997"));


        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        toolbar.setTitle("Shop");
                        break;
                    case 1:
                        toolbar.setTitle("Cart");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        setupTabIcons();
    }

    private void setToolbarHeading() {
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter.addFrag(new ProductFragment(), "Product");
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

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
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

                    productArrayList.clear();
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

                        productArrayList.add(product);
                    }
                    new ProductFragment();
                } catch (final JSONException e) {
                    Toast.makeText(MainActivity.this, "Json parsing error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Couldn't get json from server.", Toast.LENGTH_SHORT).show();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            for (int i=0;i<productArrayList.size();i++){
                Toast.makeText(getApplicationContext(),productArrayList.get(i).getName(),Toast.LENGTH_SHORT);
            }
            if (pDialog.isShowing())
                pDialog.dismiss();
        }

    }
}