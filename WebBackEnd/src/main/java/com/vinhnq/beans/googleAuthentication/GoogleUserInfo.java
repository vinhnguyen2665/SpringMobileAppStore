package com.vinhnq.beans.googleAuthentication;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GoogleUserInfo {
    String sub;
    String name;
    String given_name;
    String family_name;
    String picture;
    String email;
    boolean email_verified;
    String locale;
    String hd;

    @Override
    public String toString() {
        return "GoogleUserInfo{" +
                "sub='" + sub + '\'' +
                ", name='" + name + '\'' +
                ", given_name='" + given_name + '\'' +
                ", family_name='" + family_name + '\'' +
                ", picture='" + picture + '\'' +
                ", email='" + email + '\'' +
                ", email_verified=" + email_verified +
                ", locale='" + locale + '\'' +
                ", hd='" + hd + '\'' +
                '}';
    }
}
