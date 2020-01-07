package com.example.db;

public class artist {
    String na;
    int ph;
    String msg;
    String mentor;
    int f;
    public artist()
    {

    }

    public artist(String na, int ph, String msg, String mentor, int f) {
        this.na = na;
        this.ph = ph;
        this.msg = msg;
        this.mentor = mentor;
        this.f = f;
    }

    public String getNa() {
        return na;
    }

    public void setNa(String na) {
        this.na = na;
    }

    public int getPh() {
        return ph;
    }

    public void setPh(int ph) {
        this.ph = ph;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMentor() {
        return mentor;
    }

    public void setMentor(String mentor) {
        this.mentor = mentor;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }
}
