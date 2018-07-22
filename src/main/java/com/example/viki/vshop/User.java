package com.example.viki.vshop;

public class User {
    private String name;
    private String Phone;
    private String Mail;
    private String pass;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public User() {}

    public User(String name, String phone, String mail, String pass) {
        this.name = name;
        Phone = phone;
        Mail = mail;
        this.pass = pass;
    }





}
