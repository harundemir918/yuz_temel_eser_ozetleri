package com.softdmr.yuztemeleserozet;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class SummaryActivity extends AppCompatActivity {

    TextView textView;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        textView = findViewById(R.id.textView);

        // Kitap ad ve özet değerleri alındı
        Intent intent = getIntent();
        String bookName = intent.getStringExtra("bookName");
        String bookSummary = intent.getStringExtra("bookSummary");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(bookName);
        actionBar.setDisplayHomeAsUpEnabled(true);

        textView.setText(bookSummary);

        // TextView'deki yazının iki yana yaslı olması sağlandı
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            textView.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        }

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
