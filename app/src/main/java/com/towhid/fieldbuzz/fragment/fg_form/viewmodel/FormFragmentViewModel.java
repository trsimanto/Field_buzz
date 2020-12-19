package com.towhid.fieldbuzz.fragment.fg_form.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.towhid.fieldbuzz.fragment.fg_form.repo.FormRepository;
import com.towhid.fieldbuzz.network.model.form_info.request.FormInfoReq;


public class FormFragmentViewModel  extends ViewModel {
    private FormRepository formRepository;

    public FormFragmentViewModel() {
        super();
        formRepository = FormRepository.getInstance();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public MutableLiveData<Object> getResultLiveData(FormInfoReq formInfoReq, String  token) {
        return formRepository.getForm(formInfoReq,token);
    }
}