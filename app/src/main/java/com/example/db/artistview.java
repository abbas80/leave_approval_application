package com.example.db;

import android.app.Activity;

import java.util.List;

public class artistview {
    private Activity content;
    private List<artist> artistList;

    public artistview (Activity content,List<artist> artistList)
    {
        this.content=content;
        this.artistList=artistList;

    }
}
