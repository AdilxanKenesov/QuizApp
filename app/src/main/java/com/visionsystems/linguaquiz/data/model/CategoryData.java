package com.visionsystems.linguaquiz.data.model;



public class CategoryData {
    private int level;
    private String name;
    private int img;
    public int startColor;
    public int endColor;

    private boolean isOpened;

    public CategoryData(int level, String name, int img,int startColor, int endColor, boolean isOpened){
        this.level = level;
        this.name = name;
        this.img = img;
        this.startColor = startColor;
        this.endColor = endColor;
        this.isOpened = isOpened;
    }


    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }
    public void setImg(int img) {
        this.img = img;
    }

    public void setEndColor(int endColor) {
        this.endColor = endColor;
    }

    public void setStartColor(int startColor) {
        this.startColor = startColor;
    }

    public int getEndColor() {
        return endColor;
    }

    public int getStartColor() {
        return startColor;
    }

    public boolean isOpened() {
        return isOpened;
    }
    public void setOpened(boolean opened) {
        isOpened = opened;
    }


}
