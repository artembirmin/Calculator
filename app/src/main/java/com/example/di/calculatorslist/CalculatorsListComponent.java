package com.example.di.calculatorslist;

import com.example.di.app.AppComponent;

import dagger.Component;

@Component(dependencies = AppComponent.class, modules = {RepositoryModule.class})
public interface CalculatorsListComponent {
}
