package com.vinhnq.beans.googleAuthentication;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GoogleOauthResponse {
    String access_token;
    String code;
    String scope;
    String hd;
    String prompt;

    @Override
    public String toString() {
        return "GoogleOauthResponse{" +
                "access_token='" + access_token + '\'' +
                ", code='" + code + '\'' +
                ", scope='" + scope + '\'' +
                ", hd='" + hd + '\'' +
                ", prompt='" + prompt + '\'' +
                '}';
    }
}
