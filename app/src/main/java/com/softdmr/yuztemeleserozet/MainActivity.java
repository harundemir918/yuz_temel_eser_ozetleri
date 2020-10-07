package com.softdmr.yuztemeleserozet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity {

    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Yüz Temel Eser Özetleri");

        myDialog = new Dialog(this);
    }


    // Türk Klasiklerinin gösterimi için gereken ID değeri BookListActivity'e gönderildi
    public void turkishClassicsList(View view) {
        Intent intent = new Intent(MainActivity.this, BookListActivity.class);
        intent.putExtra("urlID", 1);

        startActivity(intent);
    }

    // Dünya Klasiklerinin gösterimi için gereken ID değeri BookListActivity'e gönderildi
    public void worldClassicsList(View view) {
        Intent intent = new Intent(MainActivity.this, BookListActivity.class);
        intent.putExtra("urlID", 2);

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.about) {
            showPopup(item);
        }
        return super.onOptionsItemSelected(item);
    }

    public void showPopup(MenuItem menuItem) {
        myDialog.setContentView(R.layout.popup_about);
        TextView popupDesc = myDialog.findViewById(R.id.popupDesc);
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
}
