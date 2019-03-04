package com.park.model;

public class Users {
    private Integer id;

    private String username;

    private String number;

    private String passwd;

    private String headeruri;

    private String nickname;

    private Float balance;

    private Byte credit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd == null ? null : passwd.trim();
    }

    public String getHeaderuri() {
        return headeruri;
    }

    public void setHeaderuri(String headeruri) {
        this.headeruri = headeruri == null ? null : headeruri.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public Byte getCredit() {
        return credit;
    }

    public void setCredit(Byte credit) {
        this.credit = credit;
    }
}