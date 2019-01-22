package com.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class Alert {

public void showDialog(Context context, String msg){


    AlertDialog.Builder builder = new AlertDialog.Builder(context);

    builder.setMessage(msg);

    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();

        }
    });
    AlertDialog dialog = builder.create();

    dialog.show();
    return;

}


}
