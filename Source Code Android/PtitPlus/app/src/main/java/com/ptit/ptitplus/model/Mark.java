package com.ptit.ptitplus.model;

public class Mark {
    private String code;
    private String name;
    private String soTC;
    private String CC;
    private String KT;
    private String TH;
    private String BT;
    private String Thi_1;
    private String Thi_2;
    private String TK_num;
    private String TK_string;
    private String ky_hoc;
    private String msv;

    public Mark(String code, String name, String soTC, String CC, String KT, String TH, String BT, String thi_1, String thi_2, String TK_num, String TK_string, String ky_hoc, String msv) {
        this.code = code;
        this.name = name;
        this.soTC = soTC;
        this.CC = CC;
        this.KT = KT;
        this.TH = TH;
        this.BT = BT;
        Thi_1 = thi_1;
        Thi_2 = thi_2;
        this.TK_num = TK_num;
        this.TK_string = TK_string;
        this.ky_hoc = ky_hoc;
        this.msv = msv;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSoTC() {
        return soTC;
    }

    public void setSoTC(String soTC) {
        this.soTC = soTC;
    }

    public String getCC() {
        return CC;
    }

    public void setCC(String CC) {
        this.CC = CC;
    }

    public String getKT() {
        return KT;
    }

    public void setKT(String KT) {
        this.KT = KT;
    }

    public String getTH() {
        return TH;
    }

    public void setTH(String TH) {
        this.TH = TH;
    }

    public String getBT() {
        return BT;
    }

    public void setBT(String BT) {
        this.BT = BT;
    }

    public String getThi_1() {
        return Thi_1;
    }

    public void setThi_1(String thi_1) {
        Thi_1 = thi_1;
    }

    public String getThi_2() {
        return Thi_2;
    }

    public void setThi_2(String thi_2) {
        Thi_2 = thi_2;
    }

    public String getTK_num() {
        return TK_num;
    }

    public void setTK_num(String TK_num) {
        this.TK_num = TK_num;
    }

    public String getTK_string() {
        return TK_string;
    }

    public void setTK_string(String TK_string) {
        this.TK_string = TK_string;
    }

    public String getKy_hoc() {
        return ky_hoc;
    }

    public void setKy_hoc(String ky_hoc) {
        this.ky_hoc = ky_hoc;
    }

    public String getMsv() {
        return msv;
    }

    public void setMsv(String msv) {
        this.msv = msv;
    }
}
