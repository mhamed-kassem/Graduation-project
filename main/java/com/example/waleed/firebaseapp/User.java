package com.example.waleed.firebaseapp;

/**
 * Created by waleed on 3/10/2018.
 */
public class User {

    public String name;
    public String mail;
    public String password;
    public String number;
    public String birthday;
    public String gender;
    public String Key;

    public User() {

    }

    public User(String name, String mail, String password) {
        this.name = name;
        this.mail = mail;
        this.password = password;
    }

    public User(String name, String mail, String password,
                String number, String birthday, String gender, String key) {
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.number = number;
        this.birthday = birthday;
        this.gender = gender;
        this.Key = key;

    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    public String getBirthday() {
        return birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getKey() {
        return this.Key;
    }
    public void setKey(String Key) {this.Key = Key;}
}

