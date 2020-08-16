package com.example.plant;

import android.icu.text.CaseMap;

public class model {
    private String Title;
    private int img;

    public model(String title, int image) {
        this.Title = title;
        this.img = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}


