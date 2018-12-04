package com.jamingup.arisanv2;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

@Entity(tableName = "kelompok_table")
public class Kelompok {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nama;
    private int nominal;
    private int jumlahAnggota;
    @Ignore
    private Bitmap img;

    public Kelompok(String nama, int nominal, int jumlahAnggota){
        this.nama = nama;
        this.nominal = nominal;
        this.jumlahAnggota = jumlahAnggota;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId(){return id;}

    public String getNama(){
        return nama;
    }

    public int getNominal(){
        return nominal;
    }

    public int getJumlahAnggota(){
        return jumlahAnggota;
    }

    public Bitmap getImg(){
        return img;
    }

}
