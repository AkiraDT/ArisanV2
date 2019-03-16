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

    //Hapus Ntar
    @Query("SELECT * FROM PESERTA_TABLE LEFT JOIN ANGGOTA_TABLE WHERE PESERTA_TABLE.nama IN (SELECT ANGGOTA_TABLE.namaP FROM ANGGOTA_TABLE WHERE ANGGOTA_TABLE.namaK LIKE :namaKelompok) GROUP BY PESERTA_TABLE.nama")
    LiveData<List<Peserta>> getPesertaInKelompok(String namaKelompok);

    @Query("UPDATE KELOMPOK_TABLE SET jumlahAnggota = :jumlah WHERE nama LIKE :namaKelompok")
    void setJumlahAnggota(String namaKelompok, int jumlah);

}
