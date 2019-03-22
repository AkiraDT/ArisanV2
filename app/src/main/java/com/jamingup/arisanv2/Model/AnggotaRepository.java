package com.jamingup.arisanv2.Model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.jamingup.arisanv2.ArisanDatabase;

import java.util.List;

public class AnggotaRepository {
    private AnggotaDao anggotaDao;
    private LiveData<List<Anggota>> allAnggota;
    private LiveData<List<Peserta>> allPeserta;
    public int jumlahAnggota;

    public AnggotaRepository(Application application, String namaKelompok){
        ArisanDatabase arisanDatabase = ArisanDatabase.getInstance(application);
        anggotaDao = arisanDatabase.anggotaDao();
        allAnggota = anggotaDao.getAllAnggota(namaKelompok);
        allPeserta = anggotaDao.getPesertaInKelompok(namaKelompok);
    }


    public void insert(Anggota anggota){
        new InsertAnggotaAsyncTask(anggotaDao).execute(anggota);
    };

    public void update(Anggota anggota){
        new UpdateAnggotaAsyncTask(anggotaDao).execute(anggota);
    };

    public void deleteAllKelompok(String namaKelompok){
        new DeleteAllAnggotaAsyncTask(anggotaDao).execute(namaKelompok);
    };

    public void deleteAnggota(String namaAnggota){
        new DeleteAnggotaAsyncTask(anggotaDao).execute(namaAnggota);
    };

    public void setJumlahAnggota(String namaKelompok, int jumlah){
        new SetJumlahAnggotaAsyncTask(anggotaDao, namaKelompok, jumlah).execute();
    };


    public LiveData<List<Anggota>> getAllAnggota() {
        return allAnggota;
    }


    public LiveData<List<Peserta>> getPesertaInKelompok(String namaKelompok){
        LiveData<List<Peserta>> peserta = anggotaDao.getPesertaInKelompok(namaKelompok);
        return peserta;
    }

    private static class InsertAnggotaAsyncTask extends AsyncTask<Anggota, Void, Void>{
        private AnggotaDao anggotaDao;

        private InsertAnggotaAsyncTask(AnggotaDao anggotaDao){
            this.anggotaDao = anggotaDao;
        }

        @Override
        protected Void doInBackground(Anggota... anggotas) {
            anggotaDao.insert(anggotas[0]);
            return null;
        }
    }

    private static class UpdateAnggotaAsyncTask extends AsyncTask<Anggota, Void, Void>{
        private AnggotaDao anggotaDao;

        private UpdateAnggotaAsyncTask(AnggotaDao anggotaDao){
            this.anggotaDao = anggotaDao;
        }

        @Override
        protected Void doInBackground(Anggota... anggotas) {
            anggotaDao.update(anggotas[0]);
            return null;
        }
    }

    private static class DeleteAnggotaAsyncTask extends AsyncTask<String, Void, Void>{
        private AnggotaDao anggotaDao;

        private DeleteAnggotaAsyncTask(AnggotaDao anggotaDao){
            this.anggotaDao = anggotaDao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            anggotaDao.deleteAnggota(strings[0]);
            return null;
        }
    }

    private static class DeleteAllAnggotaAsyncTask extends AsyncTask<String, Void, Void>{
        private AnggotaDao anggotaDao;

        private DeleteAllAnggotaAsyncTask(AnggotaDao anggotaDao){
            this.anggotaDao = anggotaDao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            anggotaDao.deleteAllAnggota(strings[0]);
            return null;
        }
    }

    private static class SetJumlahAnggotaAsyncTask extends AsyncTask<String, Integer, Void>{
        private AnggotaDao anggotaDao;
        String namaKelompok;
        int jumlah;

        private SetJumlahAnggotaAsyncTask(AnggotaDao anggotaDao, String namaKelompok, int jumlah){
            this.anggotaDao = anggotaDao;
            this.namaKelompok = namaKelompok;
            this.jumlah = jumlah;
        }

        @Override
        protected Void doInBackground(String... strings) {
            anggotaDao.setJumlahAnggota(namaKelompok, jumlah);
            return null;
        }
    }

}
