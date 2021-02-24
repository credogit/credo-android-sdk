package com.impact.credopayment.Api.Services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.impact.credopayment.Api.Utils.TLSSocketFactory;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    private static final String BASE_URL = "https://credo-payments.nugitech.com/v1/";
    public String API_URL = BASE_URL;
    private Api api;

    public ApiClient() throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException{
        Gson gson = new GsonBuilder().setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .create();
        TLSSocketFactory tlsV1point2factory = new TLSSocketFactory();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request.Builder builder = original.newBuilder()
                                .addHeader("Accept", "application/json")
                                .method(original.method(), original.body());
                        Request request = builder.build();
                        return chain.proceed(request);
                    }
                }).sslSocketFactory(tlsV1point2factory, tlsV1point2factory.getX509TrustManager())
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .build();

        Retrofit retrofit = new  Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
        api = retrofit.create(Api.class);
    }

    public Api getApi(){return  api;}
}
