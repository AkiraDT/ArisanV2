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
    private byte[] img;
    private String jenisKelamin;

    @Ignore
    public Peserta(String nama, String noTelp, String alamat){
        this.nama = nama;
        this.noTelp = noTelp;
        this.alamat = alamat;
    }

    public Peserta(String nama, String noTelp, String alamat, byte[] img, String jenisKelamin){
        this.nama = nama;
        this.noTelp = noTelp;
        this.alamat = alamat;
        this.img = img;
        this.jenisKelamin = jenisKelamin;
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

    public byte[] getImg(){
        return img;
    }

    public String getJenisKelamin(){return jenisKelamin;}

}
