package com.jamingup.arisanv2;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class AnggotaRepository {
    private AnggotaDao anggotaDao;
    private LiveData<List<Anggota>> allAnggota;

    public AnggotaRepository(Application application, String namaKelompok){
        ArisanDatabase arisanDatabase = ArisanDatabase.getInstance(application);
        anggotaDao = arisanDatabase.anggotaDao();
        allAnggota = anggotaDao.getAllAnggota(namaKelompok);
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

    public LiveData<List<Anggota>> getAllAnggota() {
        return allAnggota;
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

}
