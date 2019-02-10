package com.jamingup.arisanv2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.util.List;

public class AnggotaViewModel extends AndroidViewModel{
    private AnggotaRepository repository;
    private LiveData<List<Anggota>> allAnggota;

    public AnggotaViewModel(@NonNull Application application, String namaKelompok) {
        super(application);
        repository = new AnggotaRepository(application, namaKelompok);
        allAnggota = repository.getAllAnggota();
    }

    public void insert(Anggota anggota){
        repository.insert(anggota);
    };

    public void update(Anggota anggota){
        repository.update(anggota);
    };

    public void deleteAnggota(String namaAnggota){
        repository.deleteAnggota(namaAnggota);
    };

    public void deleteAllAnggota(String namaKelompok){
        repository.deleteAllKelompok(namaKelompok);
    };

    public LiveData<List<Anggota>> getAllAnggota() {
        return allAnggota;
    }

    public LiveData<List<Peserta>> getPesertaInKelompok(String namaKelompok){
        LiveData<List<Peserta>> peserta = repository.getPesertaInKelompok(namaKelompok);
        return peserta;
    }
}
