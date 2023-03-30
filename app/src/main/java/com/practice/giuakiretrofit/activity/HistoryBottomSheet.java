package com.practice.giuakiretrofit.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.practice.giuakiretrofit.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.practice.giuakiretrofit.dto.FoodTrackingHistory;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class HistoryBottomSheet extends BottomSheetDialogFragment {
    private List<FoodTrackingHistory> trackingHistoryList;

    private RecyclerView recyclerView;

    public HistoryBottomSheet(List<FoodTrackingHistory> trackingHistoryList) {
        this.trackingHistoryList = trackingHistoryList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_item_list, container, false);

        // Handle show consumed history list
        recyclerView = view.findViewById(R.id.historyRecylerView);

        HistoryViewAdapter historyViewAdapter = new HistoryViewAdapter(getContext());
        historyViewAdapter.setHistoryList(trackingHistoryList);

        recyclerView.setAdapter(historyViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        return view;
    }
}
