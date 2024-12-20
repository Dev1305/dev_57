package com.example.edemo;

public class Crypto {

    private int id;
    private String name;
    private String price;
    private String size;
    private String date;

    public Crypto(int id, String name, String price, String size, String date) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.size = size;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getSize() {
        return size;
    }

    public String getDate() {
        return date;
    }
}
