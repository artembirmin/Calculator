package com.example.di.activity.calculatorslist;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.data.repositories.CalculatorsListRepository;
import com.example.data.repositories.WeatherApiRepository;
import com.example.di.activity.PerActivity;
import com.example.domain.calculatorslist.CalculatorsListInteractor;
import com.example.domain.calculatorslist.CalculatorsListInteractorImpl;
import com.example.presentation.calculatorslist.CalculatorsListPresenter;
import com.example.presentation.calculatorslist.CalculatorsListPresenterImpl;
import com.example.presentation.calculatorslist.CalculatorsListView;
import com.example.presentation.calculatorslist.adapters.CalculatorsListAdapter;
import com.example.presentation.calculatorslist.adapters.CalculatorsListAdapterImpl;
import com.example.presentation.routers.CommonCalculatorRouter;
import com.example.presentation.ui.bottomsheet.NewCalculatorBottomSheet;

import java.util.Collections;

import dagger.Module;
import dagger.Provides;

@Module
public class CalculatorsListModule {

    CalculatorsListView view;

    public CalculatorsListModule(CalculatorsListView view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    CalculatorsListPresenter provideCalculatorsListPresenter(CalculatorsListInteractor calculatorsListInteractor,
                                                             CommonCalculatorRouter commonCalculatorRouter,
                                                             CalculatorsListAdapter adapter) {
        return new CalculatorsListPresenterImpl(calculatorsListInteractor, commonCalculatorRouter, adapter);
    }

    @PerActivity
    @Provides
    LinearLayoutManager provideLinearLayoutManager() {
        return new LinearLayoutManager((Context) view);
    }

    @PerActivity
    @Provides
    NewCalculatorBottomSheet provideNewCalculatorBottomSheet() {
        return new NewCalculatorBottomSheet(view);
    }

    @PerActivity
    @Provides
    CalculatorsListAdapter provideCalculatorsListAdapter() {
        return new CalculatorsListAdapterImpl(Collections.emptyList(), view);
    }

    @PerActivity
    @Provides
    CalculatorsListInteractor provideCalculatorsListInteractor(CalculatorsListRepository calculatorsListRepository,
                                                               WeatherApiRepository weatherApiRepository) {
        return new CalculatorsListInteractorImpl(calculatorsListRepository, weatherApiRepository);
    }

}
