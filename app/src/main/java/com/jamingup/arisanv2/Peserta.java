package com.jamingup.arisanv2;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

@Entity(tableName = "peserta_table")
public class Peserta {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nama;
    private String noTelp;
    private String alamat;
    @Ignore
    private Bitmap img;

    public Peserta(String nama, String noTelp, String alamat){
        this.nama = nama;
        this.noTelp = noTelp;
        this.alamat = alamat;
    }

    public Peserta(String nama, String noTelp, String alamat, Bitmap img){
        this.nama = nama;
        this.noTelp = noTelp;
        this.alamat = alamat;
        this.img = img;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId(){return id;}

    public String getNama(){
        return nama;
    }

    public String getNoTelp(){
        return noTelp;
    }

    public String getAlamat(){
        return alamat;
    }

    public Bitmap getImg(){
        return img;
    }

}