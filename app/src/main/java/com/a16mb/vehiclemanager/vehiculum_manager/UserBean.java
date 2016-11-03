package com.a16mb.vehiclemanager.vehiculum_manager;

/**
 * Created by Manpreet Bedi on 02-11-2016.
 */

public class UserBean {
    String email,password,firebaseToken;

    public UserBean(String email, String password, String firebaseToken) {
        this.email = email;
        this.password = password;
        this.firebaseToken = firebaseToken;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firebaseToken='" + firebaseToken + '\'' +
                '}';
    }

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

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    /*public boolean validatePerson(){
        return !(email.isEmpty() && password.isEmpty());
    }*/
}
