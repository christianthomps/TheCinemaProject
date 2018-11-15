package com.mvc.bean;

public class RegisterBean {

    private String firstName;
    private String lastName;
    private String email;
    private String birthdate;
    private String password;
    private String phonenumber;
    private String address;
    private String promoOpt;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setfirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getfirstName() {
        return firstName;
    }

    public void setlastName(String lastName) {
        this.lastName = lastName;
    }

    public String getlastName() {
        return lastName;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getPromoOpt(){
        return promoOpt;
    }

    public void setPromoOpt(String promoOpt){
        this.promoOpt = promoOpt;
    }


}