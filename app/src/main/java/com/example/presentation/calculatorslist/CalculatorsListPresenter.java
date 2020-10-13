package com.example.presentation.calculatorslist;

import androidx.recyclerview.widget.RecyclerView;

import com.example.models.Calculator;

public interface CalculatorsListPresenter {

    void attachView(CalculatorsListActivity calculatorsListActivity);

    void detachView();

    void onClickCalculator(int position);

    void onClickDeleteAll();

    void onResume();

    void setAdapter(RecyclerView recyclerView);

    void goToNewCalculator(String name);

    Calculator getCalculator(int position);
}
