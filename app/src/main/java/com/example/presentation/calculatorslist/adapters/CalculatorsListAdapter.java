package com.example.presentation.calculatorslist.adapters;

import com.example.models.Calculator;

import java.util.Collection;

public interface CalculatorsListAdapter {
    void setCalculators(Collection<Calculator> calculators);

    void notifyDataSetChanged();
}
