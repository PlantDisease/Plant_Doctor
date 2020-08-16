package com.example.plant;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class Loading extends UploadFile {
    private Activity activity;
    public static AlertDialog dialog;
    Loading(Activity myActivity){
        activity=myActivity;
    }

    public static void dismiss_dialog() {
        dialog.cancel();
    }

    void startloadingDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);

        LayoutInflater inflater=activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_dialog,null));
        builder.setCancelable(false);

        dialog=builder.create();
        dialog.show();
    }
    void dismissDialog(){
        dialog.dismiss();
    }
}
