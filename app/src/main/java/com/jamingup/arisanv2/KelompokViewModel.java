package com.jamingup.arisanv2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class KelompokViewModel extends AndroidViewModel {
    private KelompokRepository repository;
    private LiveData<List<Kelompok>> allKelompok;

    public KelompokViewModel(@NonNull Application application) {
        super(application);
        repository = new KelompokRepository(application);
        allKelompok = repository.getAllKelompok();
    }

    public void insert(Kelompok kelompok){
        repository.insert(kelompok);
    };

    public void update(Kelompok kelompok){
        repository.update(kelompok);
    };

    public void delete(Kelompok kelompok){
        repository.delete(kelompok);
    };

    public void deleteAllKelompok(){
        repository.deleteAllKelompok();
    };

    public void deleteAnggotaInKelompok(String namaKelompok){repository.deleteAnggotaInKelompok(namaKelompok);}

    public LiveData<List<Kelompok>> getAllKelompok() {
        return allKelompok;
    }
}
