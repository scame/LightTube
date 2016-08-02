package com.example.scame.lighttubex.data.rest;


import com.example.scame.lighttubex.data.entities.TokenEntity;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface TokenApi {

    @FormUrlEncoded
    @POST("oauth2/v4/token")
    Observable<TokenEntity> getAccessToken(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("oauth2/v4/token")
    Call<TokenEntity> getRefreshedToken(@FieldMap Map<String, String> params);
}
