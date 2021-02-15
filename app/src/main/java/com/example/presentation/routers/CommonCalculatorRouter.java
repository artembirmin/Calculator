package com.example.presentation.routers;

import android.app.Activity;

import com.example.models.Calculator;

public interface CommonCalculatorRouter {

    void goToCalculator(Activity activity, String id);

    void goToCalculator(Activity activity, Calculator newCalculator);
}
