package com.jamingup.arisanv2.Kelompok;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jamingup.arisanv2.Model.Anggota;
import com.jamingup.arisanv2.Model.AnggotaViewModel;
import com.jamingup.arisanv2.Model.AnggotaViewModelFactory;
import com.jamingup.arisanv2.Model.Peserta;
import com.jamingup.arisanv2.R;

import java.util.List;


public class AnggotaFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private AdapterAnggota mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Typeface TextMeOneStyle;
    private String namaKelompok;

    private FragmentManager fragmentManager;
    private PilihAnggotaFragment pilihAnggotaFragment;

    private AnggotaViewModel anggotaViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getArguments();
        namaKelompok = b.getString("namaKelompok");
        anggotaViewModel = ViewModelProviders.of(this, new AnggotaViewModelFactory(getActivity().getApplication(), namaKelompok)).get(AnggotaViewModel.class);
        updateRecycler();
        TextMeOneStyle = Typeface.createFromAsset(getActivity().getAssets(), "fonts/TextMeOne-Regular.ttf");

        pilihAnggotaFragment = new PilihAnggotaFragment();
        fragmentManager = getActivity().getSupportFragmentManager();
        pilihAnggotaFragment.setArguments(b);
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

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getContext(), "Masih nyoba", Toast.LENGTH_SHORT).show();
                pilihAnggotaFragment.show(fragmentManager, "Anggota "+ namaKelompok);
            }
        });

        return view;
    }

    public void addAnggota(String namaP){
        anggotaViewModel.insert(new Anggota(namaP, namaKelompok));
        updateRecycler();
    }

    public void setJumlahAnggota(int jumlahAnggota){
//        KelompokFragment kelompokFragment = (KelompokFragment) getActivity().getSupportFragmentManager().findFragmentByTag("kelompoks");
        int jumlah;
        jumlah = ((KelompokViewActivity)getActivity()).getJumlahAnggota();
        jumlah += jumlahAnggota;
        anggotaViewModel.setJumlahAnggota(namaKelompok, jumlah);
        ((KelompokViewActivity)getActivity()).updateLabelJumlahAnggota(jumlah);
    }


    public boolean isAnggotaEmpty(){
        if(mAdapter.getItemCount() > 1){
            return false;
        }else {
            return true;
        }
    }

}