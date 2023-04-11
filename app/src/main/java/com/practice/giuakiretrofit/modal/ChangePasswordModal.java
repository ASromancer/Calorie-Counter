package com.practice.giuakiretrofit.modal;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.practice.giuakiretrofit.R;
import com.practice.giuakiretrofit.activity.LoginActivity;
import com.practice.giuakiretrofit.api.AccountApi;
import com.practice.giuakiretrofit.client.RetrofitClient;
import com.practice.giuakiretrofit.dto.Password;
import com.practice.giuakiretrofit.model.Account;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChangePasswordModal extends DialogFragment {
    private EditText oldPasswordEditText, newPasswordEditText;
    private Button changePasswordButton;
    private String apiResponse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.modal_change_password, container, false);
        oldPasswordEditText = view.findViewById(R.id.old_password);
        newPasswordEditText = view.findViewById(R.id.new_password);
        changePasswordButton = view.findViewById(R.id.change_password_button);
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = oldPasswordEditText.getText().toString();
                String newPassword = newPasswordEditText.getText().toString();
                callChangePassword(newPassword,oldPassword);
                dismiss();
                // Handle password change logic here
            }
        });
        return view;
    }

    private void callChangePassword(String newPassword, String oldPassword){
        AccountApi accountApi = RetrofitClient.getRetrofitInstance().create(AccountApi.class);
        Password password = new Password(newPassword,oldPassword,LoginActivity.username);
        Call<String> call = accountApi.changePassword("Bearer " + LoginActivity.token, password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200){
                    apiResponse = response.body();
                    Toast.makeText(getActivity(),"Success",Toast.LENGTH_LONG);
                }
                else {
                    Toast.makeText(getActivity(),"Fail",Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getActivity(),"ERROR!",Toast.LENGTH_LONG);
                t.printStackTrace();
            }
        });
    }
}

