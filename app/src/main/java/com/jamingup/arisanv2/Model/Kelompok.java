package com.jamingup.arisanv2.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

@Entity(tableName = "kelompok_table")
public class Kelompok {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nama;
    private int nominalHadiah;
    private String interval;
    private int jumlahAnggota;
    private int nominalSetoran;
    private byte[] img;

    public Kelompok(String nama, int nominalHadiah, String interval, byte[] img){
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

    public int getId(){return id;}

    public String getNama(){
        return nama;
    }

    public String getInterval(){return interval;}

    public int getNominalHadiah(){
        return nominalHadiah;
    }

    public int getNominalSetoran(){
        return nominalSetoran;
    }

    public int getJumlahAnggota(){
        return jumlahAnggota;
    }

    public byte[] getImg(){
        return img;
    }

    public void setJumlahAnggota(int jumlahAnggota) {
        this.jumlahAnggota = jumlahAnggota;
    }

    public void setNominalSetoran(int nominalSetoran) {
        this.nominalSetoran = nominalSetoran;
    }
}
