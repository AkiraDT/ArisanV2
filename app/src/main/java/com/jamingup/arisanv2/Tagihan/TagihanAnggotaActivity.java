package com.jamingup.arisanv2.Tagihan;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.jamingup.arisanv2.Kelompok.AdapterAnggota;
import com.jamingup.arisanv2.Kelompok.KelompokViewActivity;
import com.jamingup.arisanv2.Kelompok.PilihAnggotaFragment;
import com.jamingup.arisanv2.Model.Anggota;
import com.jamingup.arisanv2.Model.AnggotaTagihan;
import com.jamingup.arisanv2.Model.AnggotaViewModel;
import com.jamingup.arisanv2.Model.AnggotaViewModelFactory;
import com.jamingup.arisanv2.Model.Peserta;
import com.jamingup.arisanv2.R;

import java.util.ArrayList;
import java.util.List;

public class TagihanAnggotaActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private AdapterTagihanAnggota mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Typeface TextMeOneStyle;
    private String namaKelompok;

    private AnggotaViewModel anggotaViewModel;

    private Button btn_simpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagihan_anggota);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        namaKelompok = getIntent().getStringExtra("nama");
        setTitle("Tagihan Arisan " + namaKelompok);
        anggotaViewModel = ViewModelProviders.of(this, new AnggotaViewModelFactory(getApplication(), namaKelompok)).get(AnggotaViewModel.class);
        updateRecycler();

        TextMeOneStyle = Typeface.createFromAsset(this.getAssets(), "fonts/TextMeOne-Regular.ttf");

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_peserta);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.setItemAnimator(new SlideInUpAnimator());
        // specify an adapter (see also next example)
        mAdapter = new AdapterTagihanAnggota(TextMeOneStyle, this);
//        mAdapter = new AdapterTagihanSementara(TextMeOneStyle, this);

        btn_simpan = (Button) findViewById(R.id.btn_simpanstatus);
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mAdapter.getEdited()) {
                    Toast.makeText(getApplicationContext(), "Belum ada Perubahan", Toast.LENGTH_SHORT).show();
                } else {
                    saveConfirmation();
                }
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    private void updateRecycler() {
        anggotaViewModel.getAnggotaTagihan(namaKelompok).observe(this, new Observer<List<AnggotaTagihan>>() {
            @Override
            public void onChanged(@Nullable List<AnggotaTagihan> anggotaTagihans) {
                mAdapter.setAnggotaList(anggotaTagihans);
                mAdapter.submitList(anggotaTagihans);
            }
        });
    }

    void saveStatusBayar() {
        String s = "";
        List<AnggotaTagihan> anggotaList = mAdapter.getAnggotaList();
        for (int i = 0; i < anggotaList.size(); i++) {
            anggotaViewModel.setStatusBayarAnggota(namaKelompok, anggotaList.get(i).getNama(), anggotaList.get(i).getStatusBayar());
            s = s.concat("Peserta "+ (i+1) + ": " + anggotaList.get(i).getNama()+", "+ anggotaList.get(i).getStatusBayar()+"\n");
        }
//        anggotaViewModel.setStatusBayarAnggota(namaKelompok, anggotaList.get(1).getNama(), anggotaList.get(1).getStatusBayar());
//        s = s.concat("Peserta "+ (1+1) + ": " + anggotaList.get(1).getNama()+", "+ anggotaList.get(1).getStatusBayar()+"\n");
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        mAdapter.setEdited(false);
        updateRecycler();
    }

    void saveConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Anda yakin ingin menyimpan status pembayaran");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                saveStatusBayar();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
