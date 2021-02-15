package com.example.presentation.calculatorslist.adapters;

import com.example.models.Calculator;
import com.example.models.CommonListItem;

import java.util.Collection;
import java.util.List;

public interface CalculatorsListAdapter {
    void setCalculators(Collection<Calculator> calculators);

    void notifyDataSetChanged();

    List<CommonListItem> getItems();

    void updateItems();
}
