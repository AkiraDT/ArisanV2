package com.jamingup.arisanv2.Tagihan;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jamingup.arisanv2.Kelompok.AdapterAnggota;
import com.jamingup.arisanv2.Kelompok.KelompokViewActivity;
import com.jamingup.arisanv2.Kelompok.PilihAnggotaFragment;
import com.jamingup.arisanv2.Model.Anggota;
import com.jamingup.arisanv2.Model.AnggotaViewModel;
import com.jamingup.arisanv2.Model.AnggotaViewModelFactory;
import com.jamingup.arisanv2.Model.Peserta;
import com.jamingup.arisanv2.R;

import java.util.List;

public class TagihanAnggotaActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private AdapterTagihanAnggota mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Typeface TextMeOneStyle;
    private String namaKelompok;

    private FragmentManager fragmentManager;

    private AnggotaViewModel anggotaViewModel;

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

        fragmentManager = this.getSupportFragmentManager();

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
        mRecyclerView.setAdapter(mAdapter);

    }

    private void updateRecycler() {
        anggotaViewModel.getPesertaInKelompok(namaKelompok).observe(this, new Observer<List<Peserta>>() {
            @Override
            public void onChanged(@Nullable List<Peserta> pesertas) {
                //update RecyclerView
                mAdapter.submitList(pesertas);
            }
        });
    }

}
