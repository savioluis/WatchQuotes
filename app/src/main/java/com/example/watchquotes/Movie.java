package com.example.watchquotes;

import android.widget.ImageView;

public class Movie {
    String name;
    String genre;
    String quote1;
    String quote2;
    String quote3;
    String quote4;
    int year;
    //ImageView cover;

    public Movie(String name, String genre, String quote1, String quote2, String quote3, String quote4, int year/*, ImageView cover*/) {
        this.name = name;
        this.genre = genre;
        this.quote1 = quote1;
        this.quote2 = quote2;
        this.quote3 = quote3;
        this.quote4 = quote4;
        this.year = year;
        //this.cover = cover;
    }

    public Movie(){
        this.name = "";
        this.genre = "";
        this.quote1 = "";
        this.quote2 = "";
        this.quote3 = "";
        this.quote4 = "";
        this.year = 0;
        //this.cover = cover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getQuote1() {
        return quote1;
    }

    public void setQuote1(String quote1) {
        this.quote1 = quote1;
    }

    public String getQuote2() {
        return quote2;
    }

    public void setQuote2(String quote2) {
        this.quote2 = quote2;
    }

    public String getQuote3() {
        return quote3;
    }

    public void setQuote3(String quote3) {
        this.quote3 = quote3;
    }

    public String getQuote4() {
        return quote4;
    }

    public void setQuote4(String quote4) {
        this.quote4 = quote4;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    /*public ImageView getCover() {
        return cover;
    }

    public void setCover(ImageView cover) {
        this.cover = cover;
    }*/
}
