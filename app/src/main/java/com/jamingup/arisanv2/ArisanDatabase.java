package com.jamingup.arisanv2;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

@Database(entities = {Peserta.class, Kelompok.class, Anggota.class}, version = 2)
public abstract class ArisanDatabase extends RoomDatabase {

    private static ArisanDatabase instance;

    public abstract PesertaDao pesertaDao();
    public abstract KelompokDao kelompokDao();
    public abstract AnggotaDao anggotaDao();

    public static synchronized ArisanDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ArisanDatabase.class, "arisan_database")
                    .addCallback(roomCallback)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
//            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private AnggotaDao anggotaDao;
        private PopulateDbAsyncTask(ArisanDatabase db){
            anggotaDao = db.anggotaDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            anggotaDao.insert(new Anggota("Mahmud", "Arisan Jamming"));
            return null;
        }
    }

}
