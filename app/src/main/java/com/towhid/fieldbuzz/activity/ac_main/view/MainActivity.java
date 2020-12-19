package com.towhid.fieldbuzz.activity.ac_main.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.towhid.fieldbuzz.fragment.fg_file.FileUploadFragment;
import com.towhid.fieldbuzz.fragment.fg_form.view.FormFragment;
import com.towhid.fieldbuzz.fragment.fg_login.view.LoginFragment;
import com.towhid.fieldbuzz.R;
import com.towhid.fieldbuzz.fragment.fg_success.SuccessFragment;
import com.towhid.fieldbuzz.util.LoginStatus;
import com.towhid.fieldbuzz.util.Utility;
import com.towhid.fieldbuzz.shared_pref.SharedPref;

public class MainActivity extends AppCompatActivity {
    SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPref =new SharedPref(this);
        if (LoginStatus.login.toString().equals(sharedPref.getLoginStatus())) {
            Utility.replaceFramgentWithoutBackStack(MainActivity.this, LoginFragment.newInstance());
        }else if(LoginStatus.form.toString().equals(sharedPref.getLoginStatus())){
            Utility.replaceFramgentWithoutBackStack(MainActivity.this, FormFragment.newInstance());
        }else if(LoginStatus.upload.toString().equals(sharedPref.getLoginStatus())){
            Utility.replaceFramgentWithoutBackStack(MainActivity.this, FileUploadFragment.newInstance());
        }else if(LoginStatus.success.toString().equals(sharedPref.getLoginStatus())){
            Utility.replaceFramgentWithoutBackStack(MainActivity.this, SuccessFragment.newInstance(false));
        }
    }
}