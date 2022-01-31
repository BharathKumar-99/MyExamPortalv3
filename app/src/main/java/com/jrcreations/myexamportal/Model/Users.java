package com.jrcreations.myexamportal.Model;

public class Users {
    String name,pic,email,dob,phone;

    public Users() {
    }

    public Users(String name, String pic, String email, String dob, String phone) {
        this.name = name;
        this.pic = pic;
        this.email = email;
        this.dob = dob;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
