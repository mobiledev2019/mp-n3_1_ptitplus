package com.ptit.ptitplus.model;

public class LichThi {
    private String stt;
    private String mmh;
    private String tenMH;
    private String ngayThi;
    private String gioBD;
    private String soPhut;
    private String phong;
    private String ghiChu;

    public LichThi(String stt, String mmh, String tenMH, String ngayThi, String gioBD, String soPhut, String phong, String ghiChu) {
        this.stt = stt;
        this.mmh = mmh;
        this.tenMH = tenMH;
        this.ngayThi = ngayThi;
        this.gioBD = gioBD;
        this.soPhut = soPhut;
        this.phong = phong;
        this.ghiChu = ghiChu;
    }

    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }

    public String getMmh() {
        return mmh;
    }

    public void setMmh(String mmh) {
        this.mmh = mmh;
    }

    public String getTenMH() {
        return tenMH;
    }

    public void setTenMH(String tenMH) {
        this.tenMH = tenMH;
    }

    public String getNgayThi() {
        return ngayThi;
    }

    public void setNgayThi(String ngayThi) {
        this.ngayThi = ngayThi;
    }

    public String getGioBD() {
        return gioBD;
    }

    public void setGioBD(String gioBD) {
        this.gioBD = gioBD;
    }

    public String getSoPhut() {
        return soPhut;
    }

    public void setSoPhut(String soPhut) {
        this.soPhut = soPhut;
    }

    public String getPhong() {
        return phong;
    }

    public void setPhong(String phong) {
        this.phong = phong;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
