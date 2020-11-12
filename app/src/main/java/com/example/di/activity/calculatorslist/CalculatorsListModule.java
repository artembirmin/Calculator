package com.example.di.activity.calculatorslist;

import com.example.data.network.weather.WeatherApi;
import com.example.data.repositories.CalculatorsListRepository;
import com.example.data.repositories.WeatherApiRepository;
import com.example.di.activity.PerActivity;
import com.example.domain.calculatorslist.CalculatorsListInteractor;
import com.example.domain.calculatorslist.CalculatorsListInteractorImpl;
import com.example.presentation.calculatorslist.CalculatorsListPresenter;
import com.example.presentation.calculatorslist.CalculatorsListPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class CalculatorsListModule {

    @PerActivity
    @Provides
    CalculatorsListPresenter provideCalculatorsListPresenter(CalculatorsListInteractor calculatorsListInteractor){
        return  new CalculatorsListPresenterImpl(calculatorsListInteractor);
    }

    @PerActivity
    @Provides
    CalculatorsListInteractor provideCalculatorsListInteractor(
                                                               CalculatorsListRepository calculatorsListRepository,
                                                               WeatherApiRepository weatherApiRepository){
        return  new CalculatorsListInteractorImpl(calculatorsListRepository, weatherApiRepository);
    }

}
