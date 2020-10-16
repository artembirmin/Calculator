package com.example.presentation.routers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.models.Calculator;
import com.example.presentation.calculator.CalculatorActivity;

public class CommonCalculatorRouterImpl implements CommonCalculatorRouter {

    @Override
    public void goToCalculator(Activity activity, int calculatorPosition) {
        Intent intent = new Intent((Context) activity, CalculatorActivity.class);
        intent.putExtra("selected_calculator", calculatorPosition);
        activity.startActivity(intent);
    }

    @Override
    public void goToCalculator(Activity activity, Calculator newCalculator) {
        Intent intent = new Intent((Context) activity, CalculatorActivity.class);
        intent.putExtra("new_calculator", newCalculator);
        activity.startActivity(intent);
    }
}
