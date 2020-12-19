package com.towhid.fieldbuzz.util;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Patterns;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.towhid.fieldbuzz.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class Utility {

    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
    public static boolean isValidPhone(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return Patterns.PHONE.matcher(target).matches();
        }
    }



    public static void replaceFramgentWithoutBackStack(Activity activity, Fragment fragment) {
        FragmentManager fragmentManager1 = ((FragmentActivity) activity).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager1.beginTransaction();
        fragmentTransaction.replace(R.id.container_activity_main, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

}