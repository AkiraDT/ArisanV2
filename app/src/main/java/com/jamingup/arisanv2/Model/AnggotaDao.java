package com.jamingup.arisanv2.Model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface AnggotaDao {

    @Insert
    void insert(Anggota anggota);

    @Update
    void update(Anggota anggota);

    @Query("DELETE FROM anggota_table where namaK like :namaPeserta")
    void deleteAnggota(String namaPeserta);

    @Query("DELETE FROM anggota_table where namaK like :namaKelompok")
    void deleteAllAnggota(String namaKelompok);

    @Query("SELECT * FROM anggota_table where namaK like :namaKelompok ORDER BY namaP ASC")
    LiveData<List<Anggota>> getAllAnggota(String namaKelompok);

//    @Query("SELECT * FROM PESERTA_TABLE LEFT JOIN ANGGOTA_TABLE WHERE PESERTA_TABLE.peserta_nama IN (SELECT ANGGOTA_TABLE.anggota_namapeserta FROM ANGGOTA_TABLE WHERE ANGGOTA_TABLE.anggota_namakelompok LIKE :namaKelompok) GROUP BY PESERTA_TABLE.peserta_nama")
//    LiveData<List<Peserta>> getPesertaInKelompok(String namaKelompok);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM PESERTA_TABLE INNER JOIN ANGGOTA_TABLE ON PESERTA_TABLE.peserta_nama LIKE ANGGOTA_TABLE.namaP WHERE ANGGOTA_TABLE.namaK LIKE :namaKelompok")
    LiveData<List<Peserta>> getPesertaInKelompok(String namaKelompok);

    @Query("SELECT ANGGOTA_TABLE.anggota_id, PESERTA_TABLE.peserta_nama, PESERTA_TABLE.peserta_img, ANGGOTA_TABLE.anggota_statusbayar, ANGGOTA_TABLE.anggota_statusmenang FROM PESERTA_TABLE INNER JOIN ANGGOTA_TABLE WHERE PESERTA_TABLE.peserta_nama IN (SELECT ANGGOTA_TABLE.namaP FROM ANGGOTA_TABLE WHERE ANGGOTA_TABLE.namaK LIKE :namaKelompok) GROUP BY PESERTA_TABLE.peserta_nama")
    LiveData<List<AnggotaTagihan>> getAnggotaTagihan(String namaKelompok);

    @Query("UPDATE KELOMPOK_TABLE SET kelompok_jumlahanggota = :jumlah WHERE kelompok_nama LIKE :namaKelompok")
    void setJumlahAnggota(String namaKelompok, int jumlah);

    @Query("UPDATE anggota_table SET anggota_statusbayar = :status WHERE namaP LIKE :namaAnggota AND namaK LIKE :namaKelompok")
    void setStatusBayar(String namaKelompok, String namaAnggota, int status);

}
