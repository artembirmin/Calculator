package com.example.presentation.calculatorslist.pagination;

import androidx.recyclerview.widget.DiffUtil;

import com.example.models.Calculator;
import com.example.models.CommonListItem;

import java.util.List;

public class MyDiffUtilCallback extends DiffUtil.Callback {

    private final List<CommonListItem> oldList;
    private final List<CommonListItem> newList;

    public MyDiffUtilCallback(List<CommonListItem> oldList, List<CommonListItem> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        CommonListItem oldItem = oldList.get(oldItemPosition);
        CommonListItem newItem = newList.get(newItemPosition);
        return ((Calculator) oldItem).getId().equals(((Calculator) newItem).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        CommonListItem oldItem = oldList.get(oldItemPosition);
        CommonListItem newItem = newList.get(newItemPosition);
        return ((Calculator) oldItem).equals(((Calculator) newItem));
    }
}
