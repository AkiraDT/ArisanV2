package com.jamingup.arisanv2.Model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.jamingup.arisanv2.ArisanDatabase;

import java.util.List;

public class KelompokRepository {
    private KelompokDao kelompokDao;
    private LiveData<List<Kelompok>> allKelompok;

    public KelompokRepository(Application application){
        ArisanDatabase arisanDatabase = ArisanDatabase.getInstance(application);
        kelompokDao = arisanDatabase.kelompokDao();
        allKelompok = kelompokDao.getAllKelompok();
    }

    public void insert(Kelompok kelompok){
        new InsertKelompokAsyncTask(kelompokDao).execute(kelompok);
    };

    public void update(Kelompok kelompok){
        new UpdateKelompokAsyncTask(kelompokDao).execute(kelompok);
    };

    public void delete(Kelompok kelompok){
        new DeleteKelompokAsyncTask(kelompokDao).execute(kelompok);
    };

    public void deleteAllKelompok(){
        new DeleteAllKelompokAsyncTask(kelompokDao).execute();
    };

    public void deleteAnggotaInKelompok(String namaKelompok){new DeleteAnggotaInKelompokAsyncTask(kelompokDao).execute(namaKelompok);}

    public LiveData<List<Kelompok>> getAllKelompok() {
        return allKelompok;
    }

    private static class InsertKelompokAsyncTask extends AsyncTask<Kelompok, Void, Void>{
        private KelompokDao kelompokDao;

        private InsertKelompokAsyncTask(KelompokDao kelompokDao){
            this.kelompokDao = kelompokDao;
        }

        @Override
        protected Void doInBackground(Kelompok... kelompoks) {
            kelompokDao.insert(kelompoks[0]);
            return null;
        }
    }

    private static class UpdateKelompokAsyncTask extends AsyncTask<Kelompok, Void, Void>{
        private KelompokDao kelompokDao;

        private UpdateKelompokAsyncTask(KelompokDao kelompokDao){
            this.kelompokDao = kelompokDao;
        }

        @Override
        protected Void doInBackground(Kelompok... kelompoks) {
            kelompokDao.update(kelompoks[0]);
            return null;
        }
    }

    private static class DeleteKelompokAsyncTask extends AsyncTask<Kelompok, Void, Void>{
        private KelompokDao kelompokDao;

        private DeleteKelompokAsyncTask(KelompokDao kelompokDao){
            this.kelompokDao = kelompokDao;
        }

        @Override
        protected Void doInBackground(Kelompok... kelompoks) {
            kelompokDao.delete(kelompoks[0]);
            return null;
        }
    }

    private static class DeleteAllKelompokAsyncTask extends AsyncTask<Void, Void, Void>{
        private KelompokDao kelompokDao;

        private DeleteAllKelompokAsyncTask(KelompokDao kelompokDao){
            this.kelompokDao = kelompokDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            kelompokDao.deleteAllKelompok();
            return null;
        }
    }

    private  static class DeleteAnggotaInKelompokAsyncTask extends AsyncTask<String, Void, Void>{
        private KelompokDao kelompokDao;

        private DeleteAnggotaInKelompokAsyncTask(KelompokDao kelompokDao){
            this.kelompokDao = kelompokDao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            kelompokDao.deleteAnggotaInKelompok(strings[0]);
            return null;
        }
    }

}