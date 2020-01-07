package com.example.db;

public class mentorr {
    String ment;
    String msg;
    String email;

    int ph;

    public mentorr(String ment, String msg, String email, int ph) {
        this.ment = ment;
        this.msg = msg;
        this.email = email;
        this.ph = ph;
    }

    public String getMent() {
        return ment;
    }

    public void setMent(String ment) {
        this.ment = ment;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPh() {
        return ph;
    }

    public void setPh(int ph) {
        this.ph = ph;
    }
}
