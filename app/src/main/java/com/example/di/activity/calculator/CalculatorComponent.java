package com.example.di.activity.calculator;

import com.example.di.activity.PerActivity;
import com.example.di.activity.RepositoryModule;
import com.example.di.app.AppComponent;
import com.example.presentation.calculator.CalculatorActivity;

import dagger.Component;

@Component(dependencies = AppComponent.class, modules = {RepositoryModule.class, CalculatorModule.class})
@PerActivity
public interface CalculatorComponent {
    void inject(CalculatorActivity calculatorActivity);
}
