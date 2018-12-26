package com.jamingup.arisanv2;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class PesertaRepository {
    private PesertaDao pesertaDao;
    private LiveData<List<Peserta>> allPeserta;

    public PesertaRepository(Application application){
        ArisanDatabase arisanDatabase = ArisanDatabase.getInstance(application);
        pesertaDao = arisanDatabase.pesertaDao();
        allPeserta = pesertaDao.getAllPeserta();
    }

    public void insert(Peserta peserta){
        new InsertPesertaAsyncTask(pesertaDao).execute(peserta);
    };

    public void update(Peserta peserta){
        new UpdatePesertaAsyncTask(pesertaDao).execute(peserta);
    };

    public void delete(Peserta peserta){
        new DeletePesertaAsyncTask(pesertaDao).execute(peserta);
    };

    public void deleteAllPeserta(){
        new DeleteAllPesertaAsyncTask(pesertaDao).execute();
    };

    public LiveData<List<Peserta>> getAllPeserta() {
        return allPeserta;
    }

    public LiveData<List<Peserta>> getPesertaWithoutKelompok(String namaKelompok){
        LiveData<List<Peserta>> peserta = pesertaDao.getPesertaWithoutKelompok(namaKelompok);
        return peserta;
    }

    private static class InsertPesertaAsyncTask extends AsyncTask<Peserta, Void, Void>{
        private PesertaDao pesertaDao;

        private InsertPesertaAsyncTask(PesertaDao pesertaDao){
            this.pesertaDao = pesertaDao;
        }

        @Override
        protected Void doInBackground(Peserta... pesertas) {
            pesertaDao.insert(pesertas[0]);
            return null;
        }
    }

    private static class UpdatePesertaAsyncTask extends AsyncTask<Peserta, Void, Void>{
        private PesertaDao pesertaDao;

        private UpdatePesertaAsyncTask(PesertaDao pesertaDao){
            this.pesertaDao = pesertaDao;
        }

        @Override
        protected Void doInBackground(Peserta... pesertas) {
            pesertaDao.update(pesertas[0]);
            return null;
        }
    }

    private static class DeletePesertaAsyncTask extends AsyncTask<Peserta, Void, Void>{
        private PesertaDao pesertaDao;

        private DeletePesertaAsyncTask(PesertaDao pesertaDao){
            this.pesertaDao = pesertaDao;
        }

        @Override
        protected Void doInBackground(Peserta... pesertas) {
            pesertaDao.delete(pesertas[0]);
            return null;
        }
    }

    private static class DeleteAllPesertaAsyncTask extends AsyncTask<Void, Void, Void>{
        private PesertaDao pesertaDao;

        private DeleteAllPesertaAsyncTask(PesertaDao pesertaDao){
            this.pesertaDao = pesertaDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            pesertaDao.deleteAllPeserta();
            return null;
        }
    }

}
