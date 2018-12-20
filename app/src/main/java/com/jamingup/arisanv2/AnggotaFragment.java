package com.jamingup.arisanv2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;


public class AnggotaFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private AdapterAnggota mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Typeface TextMeOneStyle;

    private PesertaViewModel pesertaViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pesertaViewModel = ViewModelProviders.of(this).get(PesertaViewModel.class);
        updateRecycler();
        TextMeOneStyle = Typeface.createFromAsset(getActivity().getAssets(), "fonts/TextMeOne-Regular.ttf");
    }

    private void updateRecycler() {
        pesertaViewModel.getAllPeserta().observe(this, new Observer<List<Peserta>>() {
            @Override
            public void onChanged(@Nullable List<Peserta> pesertas) {
                //update RecyclerView
                mAdapter.submitList(pesertas);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_anggota, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_anggota);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new GridLayoutManager(getActivity(),4);
        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.setItemAnimator(new SlideInUpAnimator());
        // specify an adapter (see also next example)
        mAdapter = new AdapterAnggota(TextMeOneStyle, getContext());
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }




}
