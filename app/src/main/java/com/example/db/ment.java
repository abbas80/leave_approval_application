package com.example.db;

import java.util.ArrayList;
import java.util.List;

public class ment {
    public ment()
    {

    }
    String na;



    String ment;

    List<String> stringList=new ArrayList<String>();

    int ph;

    public ment(String na, String ment, List<String> stringList, int ph) {
        this.na = na;
        this.ment = ment;
        this.stringList = stringList;
        this.ph = ph;
    }

    public String getNa() {
        return na;
    }

    public void setNa(String na) {
        this.na = na;
    }

    public String getMent() {
        return ment;
    }

    public void setMent(String ment) {
        this.ment = ment;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    public int getPh() {
        return ph;
    }

    public void setPh(int ph) {
        this.ph = ph;
    }
    /*
    public ment(String na, String ment, String msg, int ph) {
        this.na = na;
        this.ment = ment;
        this.msg = msg;
        this.ph = ph;
    }

    public String getNa() {
        return na;
    }

    public void setNa(String na) {
        this.na = na;
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

    public int getPh() {
        return ph;
    }

    public void setPh(int ph) {
        this.ph = ph;
    }*/
}
