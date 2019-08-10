package com.jamingup.arisanv2.Kocok;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jamingup.arisanv2.Model.Kelompok;
import com.jamingup.arisanv2.Model.KelompokViewModel;
import com.jamingup.arisanv2.R;

import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;


public class KocokKelompokFragment extends Fragment {
    private RecyclerView mRecyclerView;
    public AdapterKocokKelompok mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Typeface GloriaFont;
    private KelompokViewModel kelompokViewModel;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        kelompokViewModel = ViewModelProviders.of(this).get(KelompokViewModel.class);
        updateRecycler();

        GloriaFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/GloriaHallelujah-Regular.ttf");
    }

    private void updateRecycler() {
        kelompokViewModel.getAllKelompok().observe(this, new Observer<List<Kelompok>>() {
            @Override
            public void onChanged(@Nullable List<Kelompok> kelompoks) {
                //update RecyclerView
                mAdapter.submitList(kelompoks);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kocok_kelompok, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_kelompok);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new SlideInUpAnimator());

        // specify an adapter (see also next example)
        mAdapter = new AdapterKocokKelompok(GloriaFont, getContext());
        mRecyclerView.setAdapter(mAdapter);

//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (dy > 0 && fab.getVisibility() == View.VISIBLE) {
//                    fab.hide();
//                } else if (dy < 0 && fab.getVisibility() != View.VISIBLE) {
//                    fab.show();
//                }
//            }
//        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                kelompokViewModel.deleteAnggotaInKelompok(mAdapter.getKelompokAt(viewHolder.getAdapterPosition()).getNama());
                kelompokViewModel.delete(mAdapter.getKelompokAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getContext(), mAdapter.getKelompokAt(viewHolder.getAdapterPosition()).getNama() + " dihapus", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(mRecyclerView);

        updateRecycler();
        return view;
    }


    void discardConfirmation(final Dialog dialog) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Anda yakin ingin membatalkan aksi");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (dialogInterface != null) {
                    dialogInterface.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

}
