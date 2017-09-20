package com.proyecto.sergioblanco.pelispedia.app.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

import com.proyecto.sergioblanco.pelispedia.R;


/**
 * Created by Sergio Blanco on 22/08/2017.
 */

public class CustomProgressDialog extends Dialog {

    public CustomProgressDialog(@NonNull Context context, boolean isResult) {
        super(context, R.style.customDialog);

        if(isResult){
            //set content view
            setContentView(R.layout.result_dialog);
        }else {
            //set content view
            setContentView(R.layout.loading_dialog);
        }
    }
}
