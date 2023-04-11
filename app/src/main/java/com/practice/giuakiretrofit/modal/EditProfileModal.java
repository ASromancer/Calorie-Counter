package com.practice.giuakiretrofit.modal;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.google.gson.Gson;
import com.practice.giuakiretrofit.R;
import com.practice.giuakiretrofit.activity.LoginActivity;
import com.practice.giuakiretrofit.activity.ProfileActivity;
import com.practice.giuakiretrofit.api.AccountApi;
import com.practice.giuakiretrofit.api.UserApi;
import com.practice.giuakiretrofit.client.RetrofitClient;
import com.practice.giuakiretrofit.dto.Password;
import com.practice.giuakiretrofit.dto.UserInfo;
import com.practice.giuakiretrofit.dto.UserProfile;
import com.practice.giuakiretrofit.model.Account;
import com.practice.giuakiretrofit.model.User;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditProfileModal extends DialogFragment {
    private EditText edtDob, edtWeight, edtHeight, edtEmail, edtFirstname, edtLastname;

    RadioGroup radioGroupGender;
    RadioButton radioMale, radioFemale;
    private Button btnUpdateProfile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.modal_edit_profile, container, false);
        edtFirstname = view.findViewById(R.id.edt_firstname);
        edtLastname = view.findViewById(R.id.edt_lastname);
        edtDob = view.findViewById(R.id.edt_dob);
        radioGroupGender = view.findViewById(R.id.radioGroup_gender);
        edtWeight = view.findViewById(R.id.edt_weight);
        edtHeight = view.findViewById(R.id.edt_height);
        edtEmail = view.findViewById(R.id.edt_email);
        btnUpdateProfile = view.findViewById(R.id.btn_update_profile);
        radioMale = view.findViewById(R.id.radio_male);
        radioFemale = view.findViewById(R.id.radio_female);
        callGetUser();
        edtDob.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showDatePicker(view);
            }
        });

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callUpdateProfile();
            }
        });
        return view;
    }


    private void callUpdateProfile(){
        int genderId = radioGroupGender.getCheckedRadioButtonId();
        String firstname = String.valueOf(edtFirstname.getText());
        String lastname = String.valueOf(edtLastname.getText());
        String dob = String.valueOf(edtDob.getText());
        boolean gender;
        String strWeight = String.valueOf(edtWeight.getText());
        String strHeight = String.valueOf(edtHeight.getText());
        double weight = Double.parseDouble(strWeight);
        double height = Double.parseDouble(strHeight);
        String email = String.valueOf(edtEmail.getText());
        if(genderId == radioMale.getId()){
            gender = true;
        }
        else {
            gender = false;
        }
        UserApi userApi = RetrofitClient.getRetrofitInstance().create(UserApi.class);
        UserProfile userProfile = new UserProfile(LoginActivity.userId, firstname, lastname, dob, gender, weight, height, email);
        Gson gson = new Gson();
        String userJson = gson.toJson(userProfile);
        RequestBody userBody = RequestBody.create(MediaType.parse("application/json"), userJson);
        Log.i("JSON", String.valueOf(userBody));
        Call<Void> call = userApi.putUserData("Bearer " + LoginActivity.token, userBody);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200){
                    Log.i("SUCCESS", "SUCCESS!");
                    dismiss();
                }
                else {
                        Toast.makeText(getActivity(),"ERROR!",Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t)
            {
                Toast.makeText(getActivity(),"ERROR!",Toast.LENGTH_LONG);
                t.printStackTrace();
            }
        });
    }

    private void callGetUser(){
        UserApi userApi = RetrofitClient.getRetrofitInstance().create(UserApi.class);
        Call<User> call = userApi.getUserById("Bearer " + LoginActivity.token, LoginActivity.userId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200){
                    User user = response.body();
                    edtFirstname.setText(user.getFirstName());
                    edtLastname.setText(user.getLastName());
                    edtDob.setText(user.getDob());
                    int radioId;
                    if(user.getGender() == true){
                        radioId = radioMale.getId();
                    }
                    else {
                        radioId = radioFemale.getId();
                    }
                    radioGroupGender.check(radioId);
                    edtWeight.setText(String.valueOf(user.getWeight()));
                    edtHeight.setText(String.valueOf(user.getHeight()));
                }
                else {
                    Toast.makeText(getActivity(),"Fail",Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t)
            {
                Toast.makeText(getActivity(),"ERROR!",Toast.LENGTH_LONG);
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        // Restart the activity
        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void showDatePicker(View view) {
        // Lấy ngày hiện tại
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Tạo trình chọn ngày
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Cập nhật TextView với ngày được chọn

                        edtDob.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }
}

