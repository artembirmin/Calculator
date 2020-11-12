package com.example.presentation.calculatorslist;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.domain.calculatorslist.CalculatorsListInteractor;
import com.example.models.Calculator;
import com.example.models.CommonListItem;
import com.example.models.Weather;
import com.example.presentation.calculatorslist.adapters.CalculatorsListAdapter;
import com.example.presentation.calculatorslist.adapters.CalculatorsListAdapterImpl;
import com.example.presentation.routers.CommonCalculatorRouter;
import com.example.presentation.routers.CommonCalculatorRouterImpl;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

public class CalculatorsListPresenterImpl implements CalculatorsListPresenter {

    private static final String TAG = "CalculatorListPresenter";
    CalculatorsListInteractor interactor;
    CalculatorsListView activity;
    CalculatorsListAdapter adapter;
    private CommonCalculatorRouter router;

    public CalculatorsListPresenterImpl(CalculatorsListInteractor interactor) {
        this.interactor = interactor;
        router = new CommonCalculatorRouterImpl();
    }

    @Override
    public void attachView(CalculatorsListView calculatorsListActivity) {
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
        interactor.addWeather();
        adapter.notifyDataSetChanged();
    }
    private Disposable disposable;

    @Override
    public void onResume() {
        adapter.setCalculators(interactor.getCalculators());
         disposable = interactor.addWeather().subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::acceptWeather, this::onError);
    }
    private void onError(Throwable e) {
        Log.d(TAG, e.getMessage());
    }

    private void acceptWeather(Weather weather) {
        RxJavaPlugins.setErrorHandler(e ->Log.d(TAG, e.getMessage()));
        adapter.getItems().add(0, weather);
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
