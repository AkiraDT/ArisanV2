package com.jamingup.arisanv2.Model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
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

    public void setJumlahAnggota(String namaKelompok, int jumlah){
        repository.setJumlahAnggota(namaKelompok, jumlah);
    }

    public void setStatusBayarAnggota(String namaKelompok, String namaAnggota, int status){
        repository.setStatusBayarAnggota(namaKelompok, namaAnggota, status);
    }

    public LiveData<List<Anggota>> getAllAnggota() {
        return allAnggota;
    }

    public LiveData<List<Peserta>> getPesertaInKelompok(String namaKelompok){
        LiveData<List<Peserta>> peserta = repository.getPesertaInKelompok(namaKelompok);
        return peserta;
    }

    public LiveData<List<AnggotaTagihan>> getAnggotaTagihan(String namaKelompok){
        LiveData<List<AnggotaTagihan>> anggotaTagihan = repository.getAnggotaTagihan(namaKelompok);
        return anggotaTagihan;
    }
}
