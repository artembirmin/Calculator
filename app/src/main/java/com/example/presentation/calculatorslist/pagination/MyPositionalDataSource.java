package com.example.presentation.calculatorslist.pagination;

import android.util.Log;
import android.view.CollapsibleActionView;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import com.example.data.repositories.CalculatorsListRepository;
import com.example.models.Calculator;
import com.example.models.CommonListItem;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MyPositionalDataSource extends PositionalDataSource<CommonListItem> {

    private static final String TAG = MyPositionalDataSource.class.getSimpleName();
    private final CalculatorsListRepository calculatorsListRepository;

    public MyPositionalDataSource(CalculatorsListRepository calculatorsListRepository) {
        this.calculatorsListRepository = calculatorsListRepository;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<CommonListItem> callback) {
        Log.d(TAG, "loadInitial, requestedStartPosition = " + params.requestedStartPosition +
                ", requestedLoadSize = " + params.requestedLoadSize);
        calculatorsListRepository.getFromBySize(params.requestedStartPosition, params.requestedLoadSize).subscribe(list ->{
            LinkedList<CommonListItem> commonListItems = new LinkedList<>(list);
            callback.onResult(new LinkedList<>(list), 0);
        });
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<CommonListItem> callback) {
        Log.d(TAG, "loadRange, startPosition = " + params.startPosition + ", loadSize = " + params.loadSize);
        calculatorsListRepository.getFromBySize(params.startPosition,params.loadSize).subscribe(list ->{
            LinkedList<CommonListItem> commonListItems = new LinkedList<>(list);
            callback.onResult(new LinkedList<>(list));
        });

    }

}