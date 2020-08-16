package com.example.plant;

public class ScreenItem {
    String Title,Desp;
    int ScrnImg;

    public ScreenItem(String title,String desp,int scrnImg)
    {
        Title=title;
        Desp=desp;
        ScrnImg=scrnImg;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDesp(String desp) {
        Desp = desp;
    }

    public void setScrnImg(int scrnImg) {
        ScrnImg = scrnImg;
    }

    public String getTitle() {
        return Title;
    }

    public String getDesp() {
        return Desp;
    }

    public int getScrnImg() {
        return ScrnImg;
    }
}

