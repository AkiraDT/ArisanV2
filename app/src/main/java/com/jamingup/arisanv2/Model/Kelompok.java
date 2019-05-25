package com.jamingup.arisanv2.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

import java.io.Serializable;

@Entity(tableName = "kelompok_table",
        indices = {@Index(value = "kelompok_nama", unique = true)
        })
public class Kelompok implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "kelompok_id")
    private int id;
    @ColumnInfo(name = "kelompok_nama")
    private String nama;
    @ColumnInfo(name = "kelompok_hadiah")
    private int nominalHadiah;
    @ColumnInfo(name = "kelompok_interval")
    private String interval;
    @ColumnInfo(name = "kelompok_jumlahanggota")
    private int jumlahAnggota;
    @ColumnInfo(name = "kelompok_setoran")
    private int nominalSetoran;
    @ColumnInfo(name = "kelompok_img")
    private byte[] img;

    public Kelompok(String nama, int nominalHadiah, String interval, byte[] img) {
        this.nama = nama;
        this.nominalHadiah = nominalHadiah;
        this.interval = interval;
        this.jumlahAnggota = 0;
        this.nominalSetoran = 0;
        this.img = img;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getInterval() {
        return interval;
    }

    public int getNominalHadiah() {
        return nominalHadiah;
    }

    public int getNominalSetoran() {
        return nominalSetoran;
    }

    public int getJumlahAnggota() {
        return jumlahAnggota;
    }

    public byte[] getImg() {
        return img;
    }

    public void setJumlahAnggota(int jumlahAnggota) {
        this.jumlahAnggota = jumlahAnggota;
    }

    public void setNominalSetoran(int nominalSetoran) {
        this.nominalSetoran = nominalSetoran;
    }
}
