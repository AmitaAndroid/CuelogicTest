package com.cuelogic.cuelogictest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class ProductFragment extends Fragment implements IUpdate{

    GridView gridView;
    CustomProductGridAdapter customProductGridAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        gridView = (GridView) view.findViewById(R.id.gv_product);

        customProductGridAdapter=new CustomProductGridAdapter(getActivity(), MainActivity.productArrayList);

        gridView.setAdapter(customProductGridAdapter);

        customProductGridAdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void updateFragment() {
//        gridView.setAdapter(customProductGridAdapter);
//        customProductGridAdapter.notifyDataSetChanged();
        getFragmentManager()
                .beginTransaction()
                .detach(this)
                .attach(this)
                .commit();
    }
}
