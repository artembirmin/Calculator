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

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CalculatorsListPresenterImpl implements CalculatorsListPresenter {

    private static final String TAG = "CalculatorListPresenter";
    CalculatorsListInteractor interactor;
    CalculatorsListView activity;
    CalculatorsListAdapter adapter;
    CommonCalculatorRouter router;
    CompositeDisposable compositeDisposable;

    public CalculatorsListPresenterImpl(CalculatorsListInteractor interactor,
                                        CommonCalculatorRouter router,
                                        CalculatorsListAdapter adapter) {
        this.interactor = interactor;
        this.router = router;
        this.adapter = adapter;
    }

    @Override
    public void attachView(CalculatorsListView calculatorsListActivity) {
        activity = calculatorsListActivity;
    }

    @Override
    public void detachView() {
        activity = null;
    }

    @Override
    public void onClickCalculator(int position) {
        router.goToCalculator((Activity) activity, interactor.getIdByPosition(position, adapter.getItems()));
    }

    final protected void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    @Override
    public void onClickDeleteAll() {
        interactor.deleteAll();
        addCalculators();
        addWeather();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        addCalculators();
        addWeather();
    }

    @Override
    public void addCalculators() {
        addDisposable(interactor
                .getCalculators()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    adapter.setCalculators(list);
                    adapter.notifyDataSetChanged();
                }, e->System.out.println(e.getMessage())));
    }

    private void addWeather(){
        addDisposable(interactor.getWeather().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::acceptWeather, this::onError));
    }

    private void onError(Throwable e) {
        Log.d(TAG, e.getMessage());
    }

    private void acceptWeather(Weather weather) {
        adapter.getItems().add(0, weather);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void goToNewCalculator(String name) {
        addDisposable(interactor.getNewCalculator(name).subscribe(calculator ->
            router.goToCalculator((Activity) activity, calculator)
        ));
    }

}
