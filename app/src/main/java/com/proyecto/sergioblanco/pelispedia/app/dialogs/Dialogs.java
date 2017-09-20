package com.proyecto.sergioblanco.pelispedia.app.dialogs;

import android.content.Context;

/**
 * Created by Sergio Blanco on 17/08/2017.
 */

public class Dialogs {

    /**
     * indicates if loading dialog is hidden
     */
    private static boolean hideLoading;

    /**
     * loading dialog
     */
    private static CustomProgressDialog loading;

    /**
     * Method to show loading dialog
     */
    public static void showLoadingDialog(Context context) {
        if(loading == null){
            //create dialog
            loading  = new CustomProgressDialog(context,false);
            //set properties
            loading.setCancelable(false);
        }

        //loading dialog is showing
        hideLoading = false;

        loading.show();
    }

    /**
     * Method to hide loading dialog
     */
    public static void hideLoadingDialog() {
        //check dialog
        if ((loading != null)) {
            try {
                //hide loading
                loading.dismiss();
                //reset loading
                loading = null;
            } catch (Exception a) {
                //kill loading
                loading = null;
            }
        }
        //kill loading
        loading = null;
    }
}
