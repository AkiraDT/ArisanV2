package com.jamingup.arisanv2.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Query;

public class AnggotaTagihan {
    @ColumnInfo(name = "anggota_id")
    private int id;
    @ColumnInfo(name = "peserta_nama")
    private String nama;
    @ColumnInfo(name = "peserta_img")
    private byte[] img;
    @ColumnInfo(name = "anggota_statusbayar")
    private int statusBayar;
    @ColumnInfo(name = "anggota_statusmenang")
    private int statusMenang;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public void setStatusBayar(int statusBayar) {
        this.statusBayar = statusBayar;
    }

    public void setStatusMenang(int statusMenang) {
        this.statusMenang = statusMenang;
    }

    public String getNama() {
        return nama;
    }

    public byte[] getImg() {
        return img;
    }

    public int getStatusBayar() {
        return statusBayar;
    }

    public int getStatusMenang() {
        return statusMenang;
    }
}
