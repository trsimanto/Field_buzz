package com.towhid.fieldbuzz.fragment.fg_login.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.towhid.fieldbuzz.fragment.fg_form.view.FormFragment;
import com.towhid.fieldbuzz.network.error.ApiError;
import com.towhid.fieldbuzz.fragment.fg_login.viewModel.LoginFragmentViewModel;
import com.towhid.fieldbuzz.R;
import com.towhid.fieldbuzz.network.error.ErrorUtils2;
import com.towhid.fieldbuzz.network.model.sign_in.request.SignInReq;
import com.towhid.fieldbuzz.util.LoginStatus;
import com.towhid.fieldbuzz.util.Utility;
import com.towhid.fieldbuzz.shared_pref.SharedPref;

import java.util.Objects;
import java.util.UUID;

import retrofit2.HttpException;

public class LoginFragment extends Fragment {
    private LoginFragmentViewModel loginFragmentViewModel;
    private View rootView;
    private EditText etEmail;
    private EditText etPassword;
    private ImageView ivLogin;
    private Context mContext;
    private Activity mactivity;
    SharedPref sharedPref;
    public LoginFragment() {
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext=context;
        this.mactivity =(Activity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        init(rootView);
        action(rootView);
        return rootView;
    }

    private void init(View rootView) {
        sharedPref=new SharedPref(mContext);
        loginFragmentViewModel = new ViewModelProvider(this).get(LoginFragmentViewModel.class);
        observeViewModel();
        etEmail = rootView.findViewById(R.id.email);
        etPassword = rootView.findViewById(R.id.password);
        ivLogin = rootView.findViewById(R.id.login);
    }

    private void action(View rootView) {
        ivLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etEmail.getText().toString().isEmpty()) {
                    etEmail.setError("Email is required");
                    return;
                }
                if (!Utility.isValidEmail(etEmail.getText().toString())){
                    etEmail.setError("Enter a valid email");
                    return;
                }
                if (etPassword.getText().toString().isEmpty()){
                    etPassword.setError("Password is required");
                    return;
                }

                loginFragmentViewModel.doLogin(new SignInReq(etEmail.getText().toString().trim(), etPassword.getText().toString().trim()));
            }
        });
    }
    //Rx implement
    private void observeViewModel() {
        loginFragmentViewModel.observeLoginLiveData().observe(getActivity(), loginResponseResource -> {
            if (loginResponseResource != null) {
                switch (loginResponseResource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:
                        Toast.makeText(mContext,"Successfully Login", Toast.LENGTH_SHORT).show();
                        sharedPref.setLoginStatus(LoginStatus.form.toString());
                        sharedPref.setToken("Token "+Objects.requireNonNull(loginResponseResource.data).getToken());
                        sharedPref.setUuid(UUID.randomUUID().toString());
                        Utility.replaceFramgentWithoutBackStack(getActivity(), FormFragment.newInstance());
                        break;
                    case ERROR:
                        try {
                            HttpException error = (HttpException)loginResponseResource.throwable;
                            ApiError apiError=ErrorUtils2.INSTANCE.parseError(Objects.requireNonNull(Objects.requireNonNull(error).response()));
                            Toast.makeText(mContext, apiError.getMassage(), Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            Toast.makeText(mContext, "No Internet", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        });
    }
}