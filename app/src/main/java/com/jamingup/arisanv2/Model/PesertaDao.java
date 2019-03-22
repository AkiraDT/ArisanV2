package com.jamingup.arisanv2.Model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface PesertaDao {

    @Insert
    void insert(Peserta peserta);

    @Update
    void update(Peserta peserta);

    @Delete
    void delete(Peserta peserta);

    @Query("DELETE FROM peserta_table")
    void deleteAllPeserta();

    @Query("SELECT * FROM peserta_table ORDER BY nama ASC")
    LiveData<List<Peserta>> getAllPeserta();

    @Query("SELECT * FROM PESERTA_TABLE LEFT JOIN ANGGOTA_TABLE WHERE PESERTA_TABLE.nama NOT IN (SELECT ANGGOTA_TABLE.namaP FROM ANGGOTA_TABLE WHERE ANGGOTA_TABLE.namaK LIKE :namaKelompok) OR ANGGOTA_TABLE.namaK IS NULL GROUP BY PESERTA_TABLE.nama")
    LiveData<List<Peserta>> getPesertaWithoutKelompok(String namaKelompok);

    @Query("SELECT * FROM PESERTA_TABLE LEFT JOIN ANGGOTA_TABLE WHERE PESERTA_TABLE.nama IN (SELECT ANGGOTA_TABLE.namaP FROM ANGGOTA_TABLE WHERE ANGGOTA_TABLE.namaK LIKE :namaKelompok) OR ANGGOTA_TABLE.namaK IS NULL GROUP BY PESERTA_TABLE.nama")
    LiveData<List<Peserta>> getPesertaInKelompok(String namaKelompok);
}
