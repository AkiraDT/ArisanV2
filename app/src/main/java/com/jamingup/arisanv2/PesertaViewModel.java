package com.jamingup.arisanv2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class PesertaViewModel extends AndroidViewModel {
    private PesertaRepository repository;
    private LiveData<List<Peserta>> allPeserta;

    public PesertaViewModel(@NonNull Application application) {
        super(application);
        repository = new PesertaRepository(application);
        allPeserta = repository.getAllPeserta();
    }

    public void insert(Peserta peserta){
        repository.insert(peserta);
    };

    public void update(Peserta peserta){
        repository.update(peserta);
    };

    public void delete(Peserta peserta){
        repository.delete(peserta);
    };

    public void deleteAllPeserta(){
        repository.deleteAllPeserta();
    };

    public LiveData<List<Peserta>> getPesertaWithoutKelompok(String namaKelompok){
        return repository.getPesertaWithoutKelompok(namaKelompok);
    }

    public LiveData<List<Peserta>> getAllPeserta() {
        return allPeserta;
    }
}
