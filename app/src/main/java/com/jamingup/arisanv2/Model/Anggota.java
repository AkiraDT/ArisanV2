package com.jamingup.arisanv2.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "anggota_table",
        foreignKeys = {
                @ForeignKey(entity = Peserta.class,
                        parentColumns = "peserta_nama",
                        childColumns = "namaP",
                        onDelete = CASCADE),
                @ForeignKey(entity = Kelompok.class,
                        parentColumns = "kelompok_nama",
                        childColumns = "namaK",
                        onDelete = CASCADE)}
        ,
        indices = {@Index("namaP"),
                @Index("namaK")
        })

public class Anggota implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "anggota_id")
    private int id;
    //    @ColumnInfo(name = "anggota_namapeserta")
    private String namaP;
    //    @ColumnInfo(name = "anggota_namakelompok")
    private String namaK;
    @ColumnInfo(name = "anggota_statusbayar")
    private int statusBayar;
    @ColumnInfo(name = "anggota_statusmenang")
    private int statusMenang;

    public void setStatusBayar(int statusBayar) {
        this.statusBayar = statusBayar;
    }

    public void setStatusMenang(int statusMenang) {
        this.statusMenang = statusMenang;
    }

    public Anggota(String namaP, String namaK) {
        this.namaP = namaP;
        this.namaK = namaK;
        this.statusBayar = 0;
        this.statusMenang = 0;
    }

    public int getStatusBayar() {
        return statusBayar;
    }

    public int getStatusMenang() {
        return statusMenang;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNamaP() {
        return namaP;
    }

    public String getNamaK() {
        return namaK;
    }

}
