package com.softdmr.yuztemeleserozet;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class LoadingDialog {
    Activity activity;
    AlertDialog dialog;

    LoadingDialog (Activity myActivity) {
        activity = myActivity;
    }

    void showDialog () {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater layoutInflater = activity.getLayoutInflater();
        builder.setView(layoutInflater.inflate(R.layout.progress, null));

        dialog = builder.create();
        dialog.show();
    }

    void dismissDialog () {
        dialog.dismiss();
    }
}
