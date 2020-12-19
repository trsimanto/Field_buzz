package com.towhid.fieldbuzz.fragment.fg_form.repo;


import androidx.lifecycle.MutableLiveData;

import com.towhid.fieldbuzz.network.api.Api;
import com.towhid.fieldbuzz.network.api.RetrofitClient;
import com.towhid.fieldbuzz.network.error.ErrorUtils2;
import com.towhid.fieldbuzz.network.model.form_info.request.FormInfoReq;
import com.towhid.fieldbuzz.network.model.form_info.response.FormInfoRes;


import org.jetbrains.annotations.NotNull;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormRepository {
    private static final FormRepository ourInstance = new FormRepository();
    private Api api;

    public static FormRepository getInstance() {
        return ourInstance;
    }

    private FormRepository() {
        api = RetrofitClient.Companion.getInstance().getApi();

    }

    public MutableLiveData<Object> getForm(FormInfoReq formInfoReq,String token) {
        MutableLiveData<Object> resultLiveData = new MutableLiveData<>();
        Call<FormInfoRes> call = api.FormInfoRes_(formInfoReq, token);
        call.enqueue(new Callback<FormInfoRes>() {
            @Override
            public void onResponse(@NotNull Call<FormInfoRes> call, @NotNull Response<FormInfoRes> response) {
                if (response.isSuccessful())
                    resultLiveData.setValue(response.body());
                else
                    resultLiveData.setValue(ErrorUtils2.INSTANCE.parseError(response));
            }

            @Override
            public void onFailure(@NotNull Call<FormInfoRes> call, @NotNull Throwable t) {
                resultLiveData.setValue(t);
            }
        });
        return resultLiveData;
    }


}
