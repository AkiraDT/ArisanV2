package com.jamingup.arisanv2;

import android.Manifest;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

import static android.app.Activity.RESULT_OK;


public class PilihAnggotaFragment extends DialogFragment {

    private RecyclerView mRecyclerView;
    private AdapterPilihAnggota mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton fab;
    private Typeface TextMeOneStyle;

    private PesertaViewModel pesertaViewModel;
    //    private AnggotaViewModel anggotaViewModel;
    private String namaKelompok;
    private boolean allChecked;

    private Button btn_simpanAnggota;
    private Button btn_cancel;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        namaKelompok = b.getString("namaKelompok");
        AnggotaFragment ag = new AnggotaFragment();
        pesertaViewModel = ViewModelProviders.of(this).get(PesertaViewModel.class);
//        anggotaViewModel = ViewModelProviders.of(ag).get(AnggotaViewModel.class);

        updateRecycler();
        TextMeOneStyle = Typeface.createFromAsset(getActivity().getAssets(), "fonts/TextMeOne-Regular.ttf");
        allChecked = false;
    }

    private void updateRecycler() {
        pesertaViewModel.getPesertaWithoutKelompok(namaKelompok).observe(this, new Observer<List<Peserta>>() {
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
        View view = inflater.inflate(R.layout.fragment_pilih_anggota, container, false);

        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox_allCheck);

        TextView labelPilihSemua = (TextView) view.findViewById(R.id.label_pilihSemua);
        labelPilihSemua.setTypeface(TextMeOneStyle);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                allChecked = isChecked;
            }
        });

        labelPilihSemua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allChecked = !allChecked;
                checkBox.setChecked(allChecked);
            }
        });

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_pilihAnggota);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new SlideInUpAnimator());
        // specify an adapter (see also next example)
        mAdapter = new AdapterPilihAnggota(TextMeOneStyle, getContext());
        mRecyclerView.setAdapter(mAdapter);

//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,
//                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
//                pesertaViewModel.delete(mAdapter.getPesertaAt(viewHolder.getAdapterPosition()));
//                Toast.makeText(getContext(),mAdapter.getPesertaAt(viewHolder.getAdapterPosition()).getNama() + " dihapus", Toast.LENGTH_SHORT).show();
//            }
//        }).attachToRecyclerView(mRecyclerView);

        updateRecycler();

        btn_simpanAnggota = (Button) view.findViewById(R.id.btn_simpanAnggota);
        btn_simpanAnggota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnggotaFragment anggotaFragment = (AnggotaFragment) getActivity().getSupportFragmentManager().findFragmentByTag("Anggota");
                if (mAdapter.getCheckedItemList().isEmpty()) {
                    Toast.makeText(getContext(), "Belum ada yang terpilih", Toast.LENGTH_SHORT).show();
                } else {
                    if (anggotaFragment.isAnggotaEmpty()) {
                        if (mAdapter.getCheckedItemList().size() < 2) {
                            Toast.makeText(getContext(), "Minimal 2 Peserta", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    for (int i = 0; i < mAdapter.getCheckedItemList().size(); i++) {
                        anggotaFragment.addAnggota(mAdapter.getPesertaAt(mAdapter.getCheckedItemList().get(i)).getNama());
                    }

                    //anggotaFragment.updateJumlahAnggota();    //Urung Bisa
                    Toast.makeText(getContext(), mAdapter.getCheckedItemList().size() + "Anggota ditambahkan", Toast.LENGTH_SHORT).show();
                    getDialog().dismiss();
                }
            }
        });

        btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return view;
    }


    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }
}
