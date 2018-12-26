package com.jamingup.arisanv2;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface AnggotaDao {

    @Insert
    void insert(Anggota anggota);

    @Update
    void update(Anggota anggota);

    @Query("DELETE FROM anggota_table where namaP like :namaPeserta")
    void deleteAnggota(String namaPeserta);

    @Query("DELETE FROM anggota_table where namaK like :namaKelompok")
    void deleteAllAnggota(String namaKelompok);


    @Query("SELECT * FROM anggota_table where namaK like :namaKelompok ORDER BY namaP ASC")
    LiveData<List<Anggota>> getAllAnggota(String namaKelompok);
}
