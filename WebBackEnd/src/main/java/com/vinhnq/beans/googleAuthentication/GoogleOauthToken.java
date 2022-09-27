package com.vinhnq.beans.googleAuthentication;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GoogleOauthToken {
    String access_token;
    int expires_in;
    String id_token;
    String scope;
    String token_type;

    public String getAccessToken() {
        return access_token;
    }

    public void setAccessToken(String access_token) {
        this.access_token = access_token;
    }

    public int getExpiresIn() {
        return expires_in;
    }

    public void setExpiresIn(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getIdToken() {
        return id_token;
    }

    public void setIdToken(String id_token) {
        this.id_token = id_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setTokenType(String token_type) {
        this.token_type = token_type;
    }

    @Override
    public String toString() {
        return "GoogleOauthToken{" +
                "access_token='" + access_token + '\'' +
                ", expires_in=" + expires_in +
                ", id_token='" + id_token + '\'' +
                ", scope='" + scope + '\'' +
                ", token_type='" + token_type + '\'' +
                '}';
    }
}
