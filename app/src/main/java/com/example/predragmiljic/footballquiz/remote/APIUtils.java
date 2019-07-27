package com.example.predragmiljic.footballquiz.remote;

public class APIUtils {

    private static final String BASE_URL = "http://192.168.1.102:5000/api/";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
