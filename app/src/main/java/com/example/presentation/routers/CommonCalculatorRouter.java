package com.example.presentation.routers;

import android.app.Activity;

import com.example.models.Calculator;

public interface CommonCalculatorRouter {

    void goToCalculator(Activity activity, int calculatorPosition);

    void goToCalculator(Activity activity, Calculator newCalculator);
}
