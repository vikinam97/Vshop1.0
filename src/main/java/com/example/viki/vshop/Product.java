package com.example.viki.vshop;

public class Product {
    private String Id;
    private String Username;
    private String Name;
    private String Desp;
    private String Imageurl;
    private String Price;

    public Product() {
    }

    public Product(String id, String username, String name, String desp, String imageurl, String price) {
        Id = id;
        Username = username;
        Name = name;
        Desp = desp;
        Imageurl = imageurl;
        Price = price;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDesp() {
        return Desp;
    }

    public void setDesp(String desp) {
        Desp = desp;
    }

    public String getImageurl() {
        return Imageurl;
    }

    public void setImageurl(String imageurl) {
        Imageurl = imageurl;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
