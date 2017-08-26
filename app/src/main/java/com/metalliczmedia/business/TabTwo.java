package com.metalliczmedia.business;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Mohammad Omair on 11-03-2016.
 */
public class TabTwo extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_two, container, false);
        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.list_tab_two);

        recyclerView.setAdapter(new RecyclerView_Adapter_TabFive1(getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;}
}
