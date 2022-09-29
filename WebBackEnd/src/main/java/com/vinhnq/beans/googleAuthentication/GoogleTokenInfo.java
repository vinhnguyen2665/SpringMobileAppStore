package com.vinhnq.beans.googleAuthentication;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GoogleTokenInfo {
    String iss;
    String azp;
    String aud;
    String sub;
    String hd;
    String email;
    boolean email_verified;
    String at_hash;
    String name;
    String picture;
    String given_name;
    String family_name;
    String locale;
    String iat;
    String exp;
    String alg;
    String kid;
    String typ;

    @Override
    public String toString() {
        return "GoogleTokenrInfo{" +
                "iss='" + iss + '\'' +
                ", azp='" + azp + '\'' +
                ", aud='" + aud + '\'' +
                ", sub='" + sub + '\'' +
                ", hd='" + hd + '\'' +
                ", email='" + email + '\'' +
                ", email_verified=" + email_verified +
                ", at_hash='" + at_hash + '\'' +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", given_name='" + given_name + '\'' +
                ", family_name='" + family_name + '\'' +
                ", locale='" + locale + '\'' +
                ", iat='" + iat + '\'' +
                ", exp='" + exp + '\'' +
                ", alg='" + alg + '\'' +
                ", kid='" + kid + '\'' +
                ", typ='" + typ + '\'' +
                '}';
    }
}
