package com.softdmr.yuztemeleserozet;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

    // Ana URL ve Retrofit nesnesi tanımlandı
    private static final String BASE_URL = "http://api.harundemir.org/yuztemeleserozetleri/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        // Retrofit nesnesinin olmadığı durumda yeni bir Retrofit nesnesi oluşturuldu
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
