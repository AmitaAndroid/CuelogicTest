package com.cuelogic.cuelogictest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class CartFragment extends Fragment {

    GridView gridView;
    CustomCartGridAdapter customCartGridAdapter;
    TextView textViewTotalPrice;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        gridView = (GridView) view.findViewById(R.id.gv_cart);
        textViewTotalPrice = (TextView) view.findViewById(R.id.tv_total_price);
        customCartGridAdapter = new CustomCartGridAdapter(getActivity(), MainActivity.cartArrayList, textViewTotalPrice);

        gridView.setAdapter(customCartGridAdapter);
        return view;
    }
}
