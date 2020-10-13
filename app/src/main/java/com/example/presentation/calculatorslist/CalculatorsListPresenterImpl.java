package com.example.presentation.calculatorslist;

import android.app.Activity;
import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.domain.calculatorslist.CalculatorsListInteractor;
import com.example.domain.calculatorslist.CalculatorsListInteractorImpl;
import com.example.models.Calculator;
import com.example.presentation.calculatorslist.adapters.CalculatorsListAdapter;
import com.example.presentation.calculatorslist.adapters.CalculatorsListAdapterImpl;
import com.example.presentation.routers.CommonCalculatorRouter;
import com.example.presentation.routers.CommonCalculatorRouterImpl;

import java.util.List;

public class CalculatorsListPresenterImpl implements CalculatorsListPresenter {

    private static final String TAG = "CalculatorListPresenter";
    CalculatorsListInteractor interactor;
    CalculatorsListActivity activity;
    CalculatorsListAdapter adapter;
    private CommonCalculatorRouter router;

    CalculatorsListPresenterImpl() {
        interactor = new CalculatorsListInteractorImpl();
        router = new CommonCalculatorRouterImpl();
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
        router.goToCalculator((Activity) activity, position);
    }

    @Override
    public void onClickDeleteAll() {
        interactor.deleteAll();
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
        recyclerView.setLayoutManager(new LinearLayoutManager((Context) activity));
        adapter = new CalculatorsListAdapterImpl((List<Calculator>) interactor.getCalculators(), (CalculatorsListAdapterImpl.OnCalculatorClickListener) activity);
        recyclerView.setAdapter((CalculatorsListAdapterImpl) adapter);
    }

    @Override
    public void goToNewCalculator(String name) {
        router.goToCalculator((Activity) activity, interactor.getNewCalculator(name));
    }

    @Override
    public Calculator getCalculator(int position) {
        return interactor.getCalculator(position);
    }
}
