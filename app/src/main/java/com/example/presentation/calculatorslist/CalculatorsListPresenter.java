package com.example.presentation.calculatorslist;

import androidx.recyclerview.widget.RecyclerView;

import com.example.models.Calculator;
import com.example.presentation.calculatorslist.adapters.CalculatorsListAdapterImpl;

public interface CalculatorsListPresenter{

    void attachView(CalculatorsListView calculatorsListActivity);

    void detachView();

    void onClickCalculator(int position);

    void onClickDeleteAll();

    void onResume();

    void addCalculators();

    void goToNewCalculator(String name);

}
