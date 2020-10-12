package com.example.presentation.calculatorslist;

import androidx.recyclerview.widget.RecyclerView;

import com.example.models.Calculator;

import java.util.List;

public interface CalculatorsListPresenter {

    void attachView(CalculatorsListActivity calculatorsListActivity);
    void detachView();
    void onClickCalculator(int position);
    void onClickDeleteAll();
    void onResume();
    void setAdapter(RecyclerView recyclerView);
    Calculator getCalculator(int position);
}
