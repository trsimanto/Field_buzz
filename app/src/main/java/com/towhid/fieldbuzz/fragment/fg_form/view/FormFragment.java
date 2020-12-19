package com.towhid.fieldbuzz.fragment.fg_form.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.towhid.fieldbuzz.R;
import com.towhid.fieldbuzz.fragment.fg_file.FileUploadFragment;
import com.towhid.fieldbuzz.fragment.fg_form.viewmodel.FormFragmentViewModel;
import com.towhid.fieldbuzz.network.error.ApiError;
import com.towhid.fieldbuzz.network.model.form_info.request.CvFile;
import com.towhid.fieldbuzz.network.model.form_info.request.FormInfoReq;
import com.towhid.fieldbuzz.network.model.form_info.response.FormInfoRes;
import com.towhid.fieldbuzz.shared_pref.SharedPref;
import com.towhid.fieldbuzz.util.LoginStatus;
import com.towhid.fieldbuzz.util.Utility;

import java.util.Objects;

import static com.towhid.fieldbuzz.util.Utility.isValidEmail;
import static com.towhid.fieldbuzz.util.Utility.isValidPhone;

public class FormFragment extends Fragment {

    private static final String token = "";
    private String mToken;
    private View rootView;
    private EditText name, email, phone, full_address, name_of_university, graduation_year, cgpa, experience_in_months, current_work_place_name, expected_salary, field_buzz_reference, github_project_url;
    RadioGroup radioGroup;
    RadioButton mobile, backend;
    TextView submit;
    FormFragmentViewModel formFragmentViewModel;
    Context mContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public FormFragment() {
    }

    public static FormFragment newInstance() {
        return new FormFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_form, container, false);

        // mToken
        init(rootView);
        action(rootView);
        return rootView;
    }

    private void init(View rootView) {
        formFragmentViewModel = new ViewModelProvider(this).get(FormFragmentViewModel.class);
        name = rootView.findViewById(R.id.name);
        email = rootView.findViewById(R.id.email);
        phone = rootView.findViewById(R.id.phone);
        full_address = rootView.findViewById(R.id.full_address);
        name_of_university = rootView.findViewById(R.id.name_of_university);
        graduation_year = rootView.findViewById(R.id.graduation_year);
        cgpa = rootView.findViewById(R.id.cgpa);
        experience_in_months = rootView.findViewById(R.id.experience_in_months);
        current_work_place_name = rootView.findViewById(R.id.current_work_place_name);
        expected_salary = rootView.findViewById(R.id.expected_salary);
        field_buzz_reference = rootView.findViewById(R.id.field_buzz_reference);
        github_project_url = rootView.findViewById(R.id.github_project_url);
        radioGroup = rootView.findViewById(R.id.radioGroup);
        mobile = rootView.findViewById(R.id.mobile);
        backend = rootView.findViewById(R.id.backend);
        submit = rootView.findViewById(R.id.submit);
    }

    private void action(View rootView) {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty()) {
                    name.setError("Name is required");
                    return;
                }
                if (email.getText().toString().isEmpty()) {
                    email.setError("Email is required");
                    return;
                }
                if (!isValidEmail(email.getText().toString())) {
                    email.setError("Email is not valid");
                    return;
                }
                if (phone.getText().toString().isEmpty()) {
                    phone.setError("Email is required");
                    return;
                }
                if (!isValidPhone(phone.getText().toString())) {
                    phone.setError("Phone number is not valid");
                    return;
                }
                if (name_of_university.getText().toString().isEmpty()) {
                    name_of_university.setError("University name is required");
                    return;
                }
                if (graduation_year.getText().toString().isEmpty()) {
                    graduation_year.setError("Graduation year is required");
                    return;
                }
                try {
                    int year = Integer.parseInt(graduation_year.getText().toString().trim());
                    if (year > 2020 || year < 2015) {
                        graduation_year.setError("Graduation year should between 2015-2020");
                        return;
                    }

                } catch (Exception e) {
                    graduation_year.setError("Graduation year is not valid");
                    return;
                }
                double c = 0.0;
                if (!cgpa.getText().toString().isEmpty()) {
                    try {
                        double cg = Double.parseDouble(cgpa.getText().toString().trim());
                        if (cg > 4.0 || cg < 2.0) {
                            cgpa.setError("CGPA should between 2.0 - 4.0");
                            return;
                        }
                        c = cg;

                    } catch (Exception e) {
                        cgpa.setError("cgpa is not valid");
                        return;
                    }
                }
                int exp = -1;
                if (!experience_in_months.getText().toString().isEmpty()) {
                    try {
                        int ex = Integer.parseInt(experience_in_months.getText().toString().trim());
                        if (ex > 100 || ex < 0) {
                            experience_in_months.setError("Experience should between 0 - 100");
                            return;
                        }
                        exp = ex;

                    } catch (Exception e) {
                        experience_in_months.setError("Experience is not valid");
                        return;
                    }
                }
                if (expected_salary.getText().toString().isEmpty()) {
                    expected_salary.setError("Expected Salary is required");
                    return;
                }
                try {
                    int salary = Integer.parseInt(expected_salary.getText().toString().trim());
                    if (salary > 60000 || salary < 15000) {
                        expected_salary.setError("Expected salary should between 15000-60000");
                        return;
                    }

                } catch (Exception e) {
                    expected_salary.setError("Expected salary is not valid");
                    return;
                }
                if (github_project_url.getText().toString().isEmpty()) {
                    github_project_url.setError("Github project url is required");
                    return;
                }
                formFragmentViewModel.getResultLiveData(new FormInfoReq(
                                Objects.requireNonNull(new SharedPref(mContext).getUuid()),
                                name.getText().toString(),
                                email.getText().toString(),
                                phone.getText().toString(),
                                !full_address.getText().toString().isEmpty() ? full_address.getText().toString() : null,
                                name_of_university.getText().toString(),
                                Integer.parseInt(graduation_year.getText().toString().trim()),
                                c != 0.0 ? c : null,
                                exp != -1 ? exp : null,
                                !current_work_place_name.getText().toString().isEmpty() ? current_work_place_name.getText().toString() : null,
                                mobile.isChecked() ? "Mobile" : "Backend",
                                Integer.parseInt(expected_salary.getText().toString().trim()),
                                !field_buzz_reference.getText().toString().isEmpty() ? field_buzz_reference.getText().toString() : null,
                                github_project_url.getText().toString(),
                                new CvFile(Objects.requireNonNull(new SharedPref(mContext).getUuid())),
                                System.currentTimeMillis(),
                                System.currentTimeMillis())
                        ,new SharedPref(mContext).getToken()).observe(Objects.requireNonNull(getActivity()), obj -> {
                    if (obj instanceof FormInfoRes) {
                        new SharedPref(mContext).setLoginStatus(LoginStatus.upload.toString());
                        new SharedPref(mContext).setFileTokenId(((FormInfoRes) obj).getCvFile().getId());
                        Utility.replaceFramgentWithoutBackStack(getActivity(), FileUploadFragment.newInstance());
                    } else if (obj instanceof ApiError) {
                        Toast.makeText(mContext, ((ApiError) obj).getMassage(), Toast.LENGTH_SHORT).show();
                    } else if (obj instanceof Throwable) {
                        Toast.makeText(mContext, "No internet or slow network", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}