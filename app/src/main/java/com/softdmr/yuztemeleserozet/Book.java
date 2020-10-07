package com.softdmr.yuztemeleserozet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Book {

    // Kitap API deki kitap özellikleri tanımlandı

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("ad")
    @Expose
    private String name;

    @SerializedName("yazar")
    @Expose
    private String author;

    @SerializedName("ozet")
    @Expose
    private String summary;

    @SerializedName("kategori_id")
    @Expose
    private String categoryId;

    @SerializedName("kategori_ad")
    @Expose
    private String categoryName;

    @SerializedName("resim")
    @Expose
    private String cover;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getSummary() {
        return summary;
    }

    public String getCover() {
        return cover;
    }
}
