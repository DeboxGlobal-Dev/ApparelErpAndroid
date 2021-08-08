package com.erp.apparel.Data;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.erp.apparel.R;


public class DialogManager {

    public static Dialog getLoaderDialog(Context context) {
        Dialog dialog = new Dialog(context, R.style.Theme_AppCompat_Dialog);
        View view = View.inflate(context,R.layout.custom_dialog_loader , null);
        dialog.setContentView(view);
        return dialog;
    }
}
