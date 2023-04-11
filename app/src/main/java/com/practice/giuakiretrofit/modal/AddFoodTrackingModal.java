package com.practice.giuakiretrofit.modal;

import android.content.Intent;
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
import com.practice.giuakiretrofit.activity.ProfileActivity;
import com.practice.giuakiretrofit.activity.TrackingActivity;
import com.practice.giuakiretrofit.api.AccountApi;
import com.practice.giuakiretrofit.api.TrackingApi;
import com.practice.giuakiretrofit.client.RetrofitClient;
import com.practice.giuakiretrofit.dto.Password;
import com.practice.giuakiretrofit.model.Account;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddFoodTrackingModal extends DialogFragment {
    private EditText edtConsumedGram;
        private Button btnAddFood;
        private int foodId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null) {
            foodId = getArguments().getInt("foodId");
        }
        View view = inflater.inflate(R.layout.modal_add_food_tracking, container, false);
        edtConsumedGram = view.findViewById(R.id.edit_consumedGram);
        btnAddFood = view.findViewById(R.id.btn_add_food);
        btnAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valueConsumedGram = edtConsumedGram.getText().toString();

                if (valueConsumedGram.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter consumed Gram", Toast.LENGTH_SHORT).show();
                } else {
                    Double consumedGram = Double.parseDouble(valueConsumedGram);
                    TrackingApi trackingApi = RetrofitClient.getRetrofitInstance().create(TrackingApi.class);
                    Call<Void> call = trackingApi.addTracking( LoginActivity.userId, foodId, consumedGram, "Bearer " + LoginActivity.token);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getActivity(), "Add tracking Successfully", Toast.LENGTH_SHORT).show();
                                dismiss();
                                startTrackingActivity();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getActivity(), "Add tracking Fail", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        return view;
    }

    private void startTrackingActivity() {
        Intent intent = new Intent(getActivity(), TrackingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
