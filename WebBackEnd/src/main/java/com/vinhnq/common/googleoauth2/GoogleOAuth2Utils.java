package com.vinhnq.common.googleoauth2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vinhnq.beans.googleAuthentication.GoogleOauthToken;
import com.vinhnq.beans.googleAuthentication.GoogleTokenInfo;
import com.vinhnq.beans.googleAuthentication.GoogleUserInfo;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class GoogleOAuth2Utils {
    public static final String BASE_URL_TOKEN_API = "https://oauth2.googleapis.com/";
    public static final String BASE_URL_USER_INFO_API = "https://www.googleapis.com/";
    public static final String BASE_URL_TOKEN_INFO_API = "https://www.googleapis.com/";
    private static Gson gson;
    private static Retrofit retrofit;
    private static RetrofitAPI retrofitAPI;

    public interface RetrofitAPI {
        @POST("token")
        Call<GoogleOauthToken> oauthTokenApi(@Query("client_secret") String client_secret,
                                             @Query("client_id") String client_id,
                                             @Query("code") String code,
                                             @Query("grant_type") String grant_type,
                                             @Query("redirect_uri") String redirect_uri);

        @GET("oauth2/v3/userinfo")
        Call<GoogleUserInfo> userInfoApi(@Query("access_token") String access_token);

        @GET("oauth2/v3/tokeninfo")
        Call<GoogleTokenInfo> tokenInfoApi(@Query("id_token") String id_token);
    }

    public GoogleOAuth2Utils(String BASE_URL) {

        gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        retrofitAPI = retrofit.create(RetrofitAPI.class);
    }




    public static RetrofitAPI getRetrofitAPI() {
        return retrofitAPI;
    }

    public static void setRetrofitAPI(RetrofitAPI retrofitAPI) {
        GoogleOAuth2Utils.retrofitAPI = retrofitAPI;
    }
}
