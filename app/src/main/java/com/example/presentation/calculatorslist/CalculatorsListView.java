package com.example.presentation.calculatorslist;

import com.example.presentation.calculatorslist.adapters.CalculatorsListAdapterImpl;
import com.example.presentation.ui.bottomsheet.NewCalculatorBottomSheet;

public interface CalculatorsListView extends CalculatorsListAdapterImpl.OnCalculatorClickListener,
        NewCalculatorBottomSheet.OnBottomSheetContinueClick{

    //FIXME Добавить в интерфейс адаптера нужные методы и зменить тип на интерфейс здесь и в активности
}
