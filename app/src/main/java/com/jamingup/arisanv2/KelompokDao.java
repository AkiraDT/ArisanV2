package com.jamingup.arisanv2;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface KelompokDao {

    @Insert
    void insert(Kelompok kelompok);

    @Update
    void update(Kelompok kelompok);

    @Delete
    void delete(Kelompok kelompok);

    @Query("DELETE FROM kelompok_table")
    void deleteAllKelompok();

    @Query("SELECT * FROM kelompok_table ORDER BY nama ASC")
    LiveData<List<Kelompok>> getAllKelompok();

    @Query("DELETE FROM anggota_table Where namaK Like :namaKelompok")
    void deleteAnggotaInKelompok(String namaKelompok);
}
