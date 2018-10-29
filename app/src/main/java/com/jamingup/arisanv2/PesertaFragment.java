package com.jamingup.arisanv2;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class PesertaFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private  String [] myDataset;
    private ImageButton bigAddButton;
    private FloatingActionButton fab;
    private Typeface comicSansFont;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        comicSansFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/comic.ttf");
        setData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_peserta, container, false);

        bigAddButton = (ImageButton) view.findViewById(R.id.button_add_peserta_empty);

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPeserta();
            }
        });

        bigAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPeserta();
            }
        });

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_peserta);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new AdapterPeserta(myDataset, getContext(), comicSansFont);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fab.getVisibility() == View.VISIBLE) {
                    fab.hide();
                } else if (dy < 0 && fab.getVisibility() != View.VISIBLE) {
                    fab.show();
                }
            }
        });

        dataCheck();

        return view;
    }

    void dataCheck(){
        if(myDataset == null){
            fab.hide();
            mRecyclerView.setVisibility(View.GONE);
            bigAddButton.setVisibility(View.VISIBLE);
        }else{
            fab.show();
            mRecyclerView.setVisibility(View.VISIBLE);
            bigAddButton.setVisibility(View.GONE);

        }
    }

    void setData(){
        myDataset = new String[50];
//        for (int i = 0; i < 50; i++) {
//            myDataset[i] = "Boler " + (i+1) + " Bin Pitak " + i;
//        }
        myDataset =new String[]{"Saya", "dia", "kamu", "Anata", "Watashi", "Boku", "Kare",
            "Saya", "dia", "kamu", "Anata", "Watashi", "Boku", "Kare",
                "Saya", "dia", "kamu", "Anata", "Watashi", "Boku", "Kare",
                "Saya", "dia", "kamu", "Anata", "Watashi", "Boku", "Kare"};

    }

    private void addPeserta(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.add_peserta_form, null, false);
        final TextView namaPeserta = (TextView) view.findViewById(R.id.label_name);
        final TextView notelp = (TextView) view.findViewById(R.id.label_telp);
        final TextView alamat = (TextView) view.findViewById(R.id.label_alamat);
        ImageButton cancelButton = (ImageButton) view.findViewById(R.id.cancel_button);
        ImageButton acceptButton = (ImageButton) view.findViewById(R.id.accept_button);

        namaPeserta.setTypeface(comicSansFont);
        notelp.setTypeface(comicSansFont);
        alamat.setTypeface(comicSansFont);

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Masih dalam pengembangan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

}
