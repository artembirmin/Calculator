package com.example.presentation.calculatorslist;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.domain.calculatorslist.CalculatorsListInteractor;
import com.example.domain.calculatorslist.CalculatorsListInteractorImpl;
import com.example.models.Calculator;
import com.example.presentation.calculatorslist.adapters.CalculatorsListAdapter;
import com.example.presentation.calculatorslist.adapters.CalculatorsListAdapterImpl;

import java.util.LinkedList;
import java.util.List;

public class CalculatorsListPresenterImpl implements CalculatorsListPresenter {

    private static final String TAG = "CalculatorListPresenter";
    CalculatorsListInteractor interactor;
    CalculatorsListActivity activity;
    LinkedList<Calculator> calculators;
    CalculatorsListAdapter adapter;


    CalculatorsListPresenterImpl() {
        interactor = new CalculatorsListInteractorImpl();
    }

    @Override
    public void attachView(CalculatorsListActivity calculatorsListActivity) {
        activity = calculatorsListActivity;
    }

    @Override
    public void detachView() {
        activity = null;
    }

    @Override
    public void onClickCalculator(int position) {

    }

    @Override
    public void onClickDeleteAll() {
        interactor.deleteAll();
      //  calculators = (LinkedList<Calculator>) interactor.getCalculators();
        adapter.setCalculators(interactor.getCalculators());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        adapter.setCalculators(interactor.getCalculators());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setAdapter(RecyclerView recyclerView) {
        calculators = new LinkedList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager((Context) activity));
        adapter = new CalculatorsListAdapterImpl(calculators, (CalculatorsListAdapterImpl.OnCalculatorClickListener) activity);
        recyclerView.setAdapter((CalculatorsListAdapterImpl) adapter);
        adapter.setCalculators(calculators);
    }

    @Override
    public Calculator getCalculator(int position) {
        Log.d(TAG, "getCalculator: " + interactor.getCalculator(position));
        return interactor.getCalculator(position);
    }
}
