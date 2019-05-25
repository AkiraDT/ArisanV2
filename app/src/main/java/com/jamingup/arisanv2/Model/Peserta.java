package com.jamingup.arisanv2.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

import java.io.Serializable;

@Entity(tableName = "peserta_table",
        indices = {@Index(value = "peserta_nama", unique = true)
})
public class Peserta implements Serializable{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "peserta_id")
    private int id;
    @ColumnInfo(name = "peserta_nama")
    private String nama;
    @ColumnInfo(name = "peserta_notelp")
    private String noTelp;
    @ColumnInfo(name = "peserta_alamat")
    private String alamat;
    @ColumnInfo(name = "peserta_img")
    private byte[] img;
    @ColumnInfo(name = "peserta_jeniskelamin")
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
