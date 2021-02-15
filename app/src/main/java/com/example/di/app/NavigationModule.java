package com.example.di.app;

import com.example.presentation.routers.CommonCalculatorRouter;
import com.example.presentation.routers.CommonCalculatorRouterImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NavigationModule {

    @Singleton
    @Provides
    CommonCalculatorRouter provideCommonCalculatorRouter(){
        return new CommonCalculatorRouterImpl();
    }
}
