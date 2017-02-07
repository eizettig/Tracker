package com.zettig.tracker.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;

/**
 * Created by Altair on 07.02.2017.
 */

public class ManagerAlertDialog {
    public static void showDialog(Context context, int resourceMessage) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setMessage(resourceMessage)
                .setPositiveButton("OK", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false)
                .create()
                .show();
    }

    public static void showDialog(Context context, String message) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setMessage(message)
                .setPositiveButton("OK", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false)
                .create()
                .show();
    }

    public static void showDialog(Context context, int messageResource, DialogInterface.OnDismissListener dismissListener) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setMessage(messageResource)
                .setPositiveButton("OK", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .create()
                .setOnDismissListener(dismissListener);
        builder.show();
    }

    public static void showDialog(Context context, int messageResource, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setMessage(messageResource)
                .setPositiveButton("OK", onClickListener)
                .setIcon(android.R.drawable.ic_dialog_alert).create().show();
    }

    public static void showDialog(Context context, int messageResource, int resTitleBtnOk, int resTitleBtnCancle,
                                  DialogInterface.OnClickListener clickOnPositiove, DialogInterface.OnClickListener clickOnNigative) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setMessage(messageResource)
                .setPositiveButton(resTitleBtnOk, clickOnPositiove)
                .setNegativeButton(resTitleBtnCancle, clickOnNigative)
                .setIcon(android.R.drawable.ic_dialog_alert).create().show();
    }

    public static void showDialog(Context context, String message, int resTitleBtnOk, int resTitleBtnCancle,
                                  DialogInterface.OnClickListener clickOnPositiove, DialogInterface.OnClickListener clickOnNigative) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setMessage(message)
                .setPositiveButton(resTitleBtnOk, clickOnPositiove)
                .setNegativeButton(resTitleBtnCancle, clickOnNigative)
                .setIcon(android.R.drawable.ic_dialog_alert).create().show();
    }
}
