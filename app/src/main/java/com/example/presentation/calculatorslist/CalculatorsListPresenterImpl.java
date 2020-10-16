package com.example.presentation.calculatorslist;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.domain.calculatorslist.CalculatorsListInteractor;
import com.example.domain.calculatorslist.CalculatorsListInteractorImpl;
import com.example.models.Calculator;
import com.example.models.CommonListItem;
import com.example.presentation.calculatorslist.adapters.CalculatorsListAdapter;
import com.example.presentation.calculatorslist.adapters.CalculatorsListAdapterImpl;
import com.example.presentation.routers.CommonCalculatorRouter;
import com.example.presentation.routers.CommonCalculatorRouterImpl;

import java.util.LinkedList;
import java.util.List;

public class CalculatorsListPresenterImpl implements CalculatorsListPresenter {

    private static final String TAG = "CalculatorListPresenter";
    CalculatorsListInteractor interactor;
    CalculatorsListActivity activity;
    CalculatorsListAdapter adapter;
    private CommonCalculatorRouter router;

    public CalculatorsListPresenterImpl() {
        interactor = new CalculatorsListInteractorImpl(this);
        router = new CommonCalculatorRouterImpl();
    }

    @Override
    public void attachView(CalculatorsListActivity calculatorsListActivity) {
        activity = calculatorsListActivity;
    }

    @Override
    public void updateItems() {
        adapter.updateItems();
    }

    @Override
    public void detachView() {
        activity = null;
    }

    @Override
    public void onClickCalculator(int position) {
        router.goToCalculator((Activity) activity, interactor.getRealPosition(position, adapter.getItems()));
    }

    @Override
    public void onClickDeleteAll() {
        interactor.deleteAll();
        adapter.setCalculators(interactor.getCalculators());
        interactor.addWeather(adapter.getItems());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        adapter.setCalculators(interactor.getCalculators());
        interactor.addWeather(adapter.getItems());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setAdapter(RecyclerView recyclerView) {
        Log.d(TAG, "setAdapter: ");
        recyclerView.setLayoutManager(new LinearLayoutManager((Context) activity));
        LinkedList<CommonListItem> items = new LinkedList<>();
        adapter = new CalculatorsListAdapterImpl((List<Calculator>) interactor.getCalculators(), (CalculatorsListAdapterImpl.OnCalculatorClickListener) activity);
        //    Log.d(TAG, "setAdapter: " + adapter.getItems());
        //    interactor.addWeather(adapter.getItems());
        Log.d(TAG, "setAdapter: " + adapter.getItems());
        recyclerView.setAdapter((CalculatorsListAdapterImpl) adapter);
    }

    @Override
    public void goToNewCalculator(String name) {
        router.goToCalculator((Activity) activity, interactor.getNewCalculator(name));
    }

    @Override
    public Calculator getCalculator(int position) {
        return interactor.getCalculator(position, adapter.getItems());
    }
}
