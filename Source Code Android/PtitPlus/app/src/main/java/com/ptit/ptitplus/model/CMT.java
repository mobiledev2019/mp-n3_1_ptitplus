package com.ptit.ptitplus.model;

public class CMT {
    private String msv;
    private String cmt;

    public CMT(String msv, String cmt) {
        this.msv = msv;
        this.cmt = cmt;
    }

    public String getMsv() {
        return msv;
    }

    public void setMsv(String msv) {
        this.msv = msv;
    }

    public String getCmt() {
        return cmt;
    }

    public void setCmt(String cmt) {
        this.cmt = cmt;
    }
}
