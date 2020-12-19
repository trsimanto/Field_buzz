package com.towhid.fieldbuzz.fragment.fg_login.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.towhid.fieldbuzz.application.FieldBuzz;
import com.towhid.fieldbuzz.fragment.fg_login.resorce.Resource;
import com.towhid.fieldbuzz.fragment.fg_login.ripo.LoginRepository;
import com.towhid.fieldbuzz.network.model.sign_in.request.SignInReq;
import com.towhid.fieldbuzz.network.model.sign_in.response.SignInRes;



public class LoginFragmentViewModel extends AndroidViewModel {

    private final MediatorLiveData<Resource<SignInRes>> mLoginLiveData = new MediatorLiveData<>();

    private final LoginRepository mRepository;

    public LoginFragmentViewModel(@NonNull Application application) {
        super(application);
        mRepository = LoginRepository.getRepository(FieldBuzz.getInstance());
    }

    public void doLogin(SignInReq signInReq) {

        LiveData<Resource<SignInRes>> source = mRepository.loginUser(signInReq);

        mLoginLiveData.addSource(source, data -> {
            if (data != null) {
                mLoginLiveData.setValue(data);
            }
            mLoginLiveData.removeSource(source);
        });
    }

    public LiveData<Resource<SignInRes>> observeLoginLiveData() {
        return mLoginLiveData;
    }
}