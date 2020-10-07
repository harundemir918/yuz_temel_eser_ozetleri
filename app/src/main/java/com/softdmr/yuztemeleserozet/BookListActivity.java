package com.softdmr.yuztemeleserozet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookListActivity extends AppCompatActivity {

    // Gereken tanımlamalar yapıldı
    Dialog myDialog;
    SearchView searchView;
    ListView listView;
    ListViewAdapter adapter;
    LoadingDialog loadingDialog;
    BookApi bookApi;
    int urlID;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        // Tanımlamalara karşılık gelen atamalar yapıldı
        myDialog = new Dialog(this);
        loadingDialog = new LoadingDialog(BookListActivity.this);

        searchView = findViewById(R.id.searchBar);
        listView = findViewById(R.id.listView);

        // ActionBar'daki uygulama başlığı "Yüz Temel Eser Özetleri" olarak değiştirildi
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Yüz Temel Eser Özetleri");
        actionBar.setDisplayHomeAsUpEnabled(true);

        // MainActivity'den gelen ID değeri burada urlID adlı değişkene atandı
        Intent intent = getIntent();
        urlID = intent.getIntExtra("urlID", 1);

        // Uygulamanın çalışması için internet izni istendi
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.INTERNET}, 1);
        }
        else {
            getJSON();
        }

        // Kitap arama için gereken metotlar oluşturuldu
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    adapter.filter("");
                    listView.clearTextFilter();
                }
                else {
                    adapter.filter(newText);
                }

                return true;
            }
        });

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    // ActionBar'da menü butonu oluşturuldu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Menü butonundaki item seçildiğinde
    * "Hakkında" penceresinin açılması için gereken metot oluşturuldu
    */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.about) {
            showPopup(item);
        }

        return super.onOptionsItemSelected(item);
    }

    // Geri butonu oluşturuldu
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    /* "Hakkında" penceresi oluşturuldu ve
    * Assets klasörünün içinde bulunan text dosyasındaki metin yazdırıldı
    */
    public void showPopup(MenuItem item) {

        // myDialog nesnesinin view i ayarlandı
        myDialog.setContentView(R.layout.popup_about);

        // Açıklamanın yazdırılacağı popupDesc nesnesi tanımlandı
        TextView popupDesc = myDialog.findViewById(R.id.popupDesc);

        // Text tanımlandı
        String text = "";
        try {
            InputStream is = getAssets().open("Kitap Listesi.txt");
            int size = is.available();
            byte [] buffer = new byte[size];
            is.read(buffer);
            is.close();
            text = new String(buffer);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        popupDesc.setText(text);
        myDialog.show();
    }

    // İnternet üzerindeki kitap API Retrofit kütüphanesi kullanılarak JSON formatında çekildi
    public void getJSON() {

        // Client sınıfındaki getClient() metodu ile bookApi değişkeni oluşturuldu
        bookApi = Client.getClient().create(BookApi.class);

        /* bookApi arayüzünün getBook() metodu
        * urlID değişkeni kullanılarak çağrıldı ve call değişkenine atandı
        */
        Call<List<Book>> call = bookApi.getBook(urlID);

        // Yükleme ekranı açıldı
        loadingDialog.showDialog();

        // Çekilen kitap API'nin ekrana yazdırılması için gereken metot oluşturuldu
        call.enqueue(new Callback<List<Book>>() {

            // Metodun başarılı olma durumunda yapılacaklar
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {

                // Gelen cevabın başarılı olmadığı durumda hata kodu ekrana yazdırıldı
                if (!response.isSuccessful()) {
                    Toast.makeText(BookListActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                }

                // Gelen cevap bookList adlı değişkene atandı
                List<Book> bookList = response.body();

                // Listenin ekrana aktarımı için adapter oluşturuldu
                adapter = new ListViewAdapter(getApplicationContext(), bookList);

                // ListView'in kullanacağı adapter atandı
                listView.setAdapter(adapter);

                // Yükleme ekranı kapatıldı
                loadingDialog.dismissDialog();
            }

            // Metodun başarısız olma durumunda yapılacaklar
            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {

                // Ekrana hata mesajı yazdırıldı
                Toast.makeText(BookListActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
