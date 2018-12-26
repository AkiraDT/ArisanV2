package com.jamingup.arisanv2;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "anggota_table")
public class Anggota {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String namaP;
    private String namaK;

    public Anggota(String namaP, String namaK){
        this.namaP = namaP;
        this.namaK = namaK;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId(){return id;}

    public String getNamaP(){
        return namaP;
    }

    public String getNamaK(){
        return namaK;
    }

}
