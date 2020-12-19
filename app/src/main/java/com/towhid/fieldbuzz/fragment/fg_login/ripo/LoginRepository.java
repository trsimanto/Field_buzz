package com.towhid.fieldbuzz.fragment.fg_login.ripo;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import com.towhid.fieldbuzz.fragment.fg_login.resorce.Resource;
import com.towhid.fieldbuzz.network.api.Api;
import com.towhid.fieldbuzz.network.api.RetrofitClient;
import com.towhid.fieldbuzz.network.model.sign_in.request.SignInReq;
import com.towhid.fieldbuzz.network.model.sign_in.response.SignInRes;

import io.reactivex.schedulers.Schedulers;


public class LoginRepository {
    private final Api mApiService;



    private static LoginRepository mRepository;

    public LoginRepository(Context context) {
        mApiService = RetrofitClient.Companion.getInstance().getApi();;
    }

    public static LoginRepository getRepository(Context context) {
        if (mRepository == null) {
            mRepository = new LoginRepository(context);
        }
        return mRepository;
    }

    public LiveData<Resource<SignInRes>> loginUser(SignInReq signInReq) {
        return LiveDataReactiveStreams.fromPublisher(
               mApiService.SignInRes_(signInReq)
                        .subscribeOn(Schedulers.io())
                        .map(Resource::success)
                        .onErrorReturn(e -> Resource.error(e,null)));
    }

}