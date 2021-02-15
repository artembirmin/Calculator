package com.example.di.activity.calculatorslist;

import com.example.di.activity.PerActivity;
import com.example.di.activity.RepositoryModule;
import com.example.di.activity.calculator.CalculatorModule;
import com.example.di.app.AppComponent;
import com.example.di.app.RestModule;
import com.example.presentation.calculator.CalculatorActivity;
import com.example.presentation.calculatorslist.CalculatorsListActivity;

import dagger.Component;

@Component(dependencies = AppComponent.class, modules = {RepositoryModule.class, CalculatorsListModule.class})
@PerActivity
public interface CalculatorsListComponent {
    void inject(CalculatorsListActivity calculatorsListActivity);
}
