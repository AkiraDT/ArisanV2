package com.jamingup.arisanv2;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Peserta.class}, version = 2)
public abstract class ArisanDatabase extends RoomDatabase {

    private static ArisanDatabase instance;

    public abstract PesertaDao pesertaDao();

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
        private PesertaDao pesertaDao;

        private PopulateDbAsyncTask(ArisanDatabase db){
            this.pesertaDao = db.pesertaDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            pesertaDao.insert(new Peserta("Dennis", "22", "Babakan"));
            pesertaDao.insert(new Peserta("Akira", "23", "Anjatan"));
            pesertaDao.insert(new Peserta("Indrawan", "24", "Indramayu"));
            return null;
        }
    }

}
