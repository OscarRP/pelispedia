package com.proyecto.sergioblanco.pelispedia.app.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.proyecto.sergioblanco.pelispedia.R;
import com.proyecto.sergioblanco.pelispedia.app.controllers.NavigationController;
import com.proyecto.sergioblanco.pelispedia.app.interfaces.AppInterfaces;
import com.proyecto.sergioblanco.pelispedia.app.models.Film;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio Blanco on 29/8/17.
 */

public class Utils {

    /**
     * Method to register user, save in preferences
     */
    public void register(Activity activity, String nick) {
        //save user is registered in preferences
        boolean userLogged = true;
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constants.USER_LOGGED, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constants.USER_LOGGED, userLogged);
        editor.putString(Constants.NICK, nick);

        editor.commit();
    }

    /**
     * Method to change fragement with params
     */
    public void goToDetail (Activity activity, Film film) {
        //set film in bundle
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.FILM, film);
        bundle.putBoolean(Constants.GO_TO_DETAIL, true);

        //set navigation controller
        NavigationController navigationController = new NavigationController();

        //open activity detail
        navigationController.changeActivity(activity, bundle);
    }

    /**
     * Method to set image
     */
    public void setImage(String userPhotoUrl, final Activity activity, final ImageView profileImage, final AppInterfaces.ISetImge setImageListener) {

        if  (userPhotoUrl.isEmpty()) {
            profileImage.setImageResource(R.mipmap.profile);
        } else {
            try {
                //Rounded image
                Glide.with(activity).load(userPhotoUrl).asBitmap().centerCrop().placeholder(R.mipmap.profile).into(new BitmapImageViewTarget(profileImage) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(activity.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        profileImage.setImageDrawable(circularBitmapDrawable);
                        if (setImageListener != null) {
                            setImageListener.setImage();
                        }
                    }
                });
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    /**
     * Method to select a Image source
     */
    public static void selectProfileImage(final String[] options, final Activity activity, final String tittle, final AppInterfaces.IRemoveImage removeListener) {
        //create dialog
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(activity);
        builder.setTitle(tittle);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    //check for permissions
                    if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        //open camera
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        activity.startActivityForResult(intent, Constants.SELECT_CAMERA);
                    } else {
                        //request permissions
                        checkAndRequestPermissions(activity, Constants.SELECT_CAMERA);
                    }

                } else if (item==1) {
                    //check for permissions
                    if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        //open gallery
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        activity.startActivityForResult(Intent.createChooser(intent, tittle), Constants.SELECT_GALLERY);
                    } else {
                        //request permissions
                        checkAndRequestPermissions(activity, Constants.SELECT_GALLERY);
                    }
                }else if (item == 2){
                    removeListener.removeImage();
                } else if (item == 3) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    /**
     * Method to check all request permissions
     */
    private static void checkAndRequestPermissions(Activity activity, int permission) {
        int permissionCamera = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        int permissionWrite = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        //check if user pressed select from gallery
        if (permission == Constants.SELECT_GALLERY) {
            //check permission
            if (permissionWrite != PackageManager.PERMISSION_GRANTED) {
                //request permission
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.SELECT_GALLERY);
            }
            //check if user pressed select from camera
        } else if (permission == Constants.SELECT_CAMERA) {
            //list of permissions needed
            List<String> listPermissionsNeeded = new ArrayList<>();
            //check external storage permission
            if (permissionWrite != PackageManager.PERMISSION_GRANTED) {
                //add to permissions needed
                listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            //check camera permission
            if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
                //add to permissions needed
                listPermissionsNeeded.add(Manifest.permission.CAMERA);
            }
            //request permissions needed
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), Constants.SELECT_CAMERA);
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.CAMERA},  Constants.SELECT_CAMERA);
            }
        }
    }

    /**
     * Method to check write external permission
     */
    public static boolean isPermission(Activity activity) {

        //init variable
        boolean ok = true;

        //check id device is Android M
        if (Build.VERSION.SDK_INT >= 23) {
            if(activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        return  ok;
    }

    /**
     * Method to close session and go to login
     */
    public void closeSession(Activity activity) {
        //close session
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constants.USER_LOGGED, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constants.USER_LOGGED, false);

        editor.commit();

        //go to login
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.GO_TO_LOGIN, true);
        NavigationController navigationController = new NavigationController();
        navigationController.changeActivity(activity, bundle);
    }
}
