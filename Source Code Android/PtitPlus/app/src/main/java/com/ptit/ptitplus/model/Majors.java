package com.ptit.ptitplus.model;

public class Majors {
    private String tenNganh;
    private int imageResource;

    public String getTenNganh() {
        return tenNganh;
    }

    public void setTenNganh(String tenNganh) {
        this.tenNganh = tenNganh;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public Majors(String tenNganh, int imageResource) {
        this.tenNganh = tenNganh;
        this.imageResource = imageResource;
    }
}
