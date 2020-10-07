package com.softdmr.yuztemeleserozet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BookApi {

    // GET metodu kullanılarak URL'nin son kısmı tanımlandı ve parametre olarak id değeri verildi
    @GET("v1/kitaplar/{id}")
    Call<List<Book>> getBook(@Path("id") int id);
}
