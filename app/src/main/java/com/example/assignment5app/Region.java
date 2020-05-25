package com.example.assignment5app;

public class Region {

    private String name;
    private String category;
    private int size;

    //Constructor for a mountain
    public Region(String mName, String mCategory, int mSize) {
        name = mName;
        category = mCategory;
        size = mSize;
    }

    @Override public String toString() {
        return name;
    }

    //Method that returns the name of mountain and two other properties
    public String info() {
        String str="Region ";
        str+=name;
        str+=" har för nuvarande ";
        str+=size;
        str+=" covid19-smittade och risknivån här är: ";
        str+=category;
        str+=".";

        return str;
    }

}