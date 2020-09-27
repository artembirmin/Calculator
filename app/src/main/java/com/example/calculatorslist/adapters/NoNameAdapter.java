package com.example.calculatorslist.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.calculatormain.R;

import com.example.models.Calculator;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class NoNameAdapter extends RecyclerView.Adapter<NoNameAdapter.CalcViewHolder> {

    private static final String TAG = "qwerty";
    private List<Calculator> calculatorList;
    private OnCalculatorClickListener onCalculatorClickListener;

    public NoNameAdapter(List<Calculator> calculatorList , OnCalculatorClickListener calculatorClickListener) {
        this.calculatorList = calculatorList;
        this.onCalculatorClickListener = calculatorClickListener;
    }

    public void addNewCalculator(Calculator calculator){
        calculatorList.add(0,calculator);
        Log.d(TAG, "addNewCalculator: " + calculatorList);
        notifyDataSetChanged();
    }

    public void updateCalculator(Calculator calculator, int index) {
        calculatorList.remove(index);
        calculatorList.add(0, calculator);
    }

    public void setCalculators(Collection<Calculator> calculators){
        calculatorList.addAll(calculators);
        Collections.reverse(calculatorList);
        notifyDataSetChanged();
        //Дает адаптеру знать об изменении списка элементов и что надо перерисовать
    }

    public void clearItems(){
        calculatorList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CalcViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new CalcViewHolder(view, onCalculatorClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalcViewHolder holder, int position) {
        holder.bind(calculatorList.get(position));
    }

    @Override
    public int getItemCount() {
        return calculatorList.size();
    }

    static class CalcViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView nameTextView;
        private TextView contentTextView;
        private final OnCalculatorClickListener calculatorClickListener;

        public CalcViewHolder(@NonNull View itemView, OnCalculatorClickListener calculatorClickListener) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
            contentTextView = itemView.findViewById(R.id.content);
            this.calculatorClickListener = calculatorClickListener;
            itemView.setOnClickListener(this);
        }

        public void bind(Calculator calculator) {
            nameTextView.setText(calculator.getId());
            contentTextView.setText(calculator.getExpression());
            if(contentTextView.getText().length() == 0)
                contentTextView.setText("0");
        }

        @Override
        public void onClick(View view) {
            //Здесь основные действия
            calculatorClickListener.onCalculatorClick(getAdapterPosition());
        }
    }

    public interface OnCalculatorClickListener {
        void onCalculatorClick(int position);
    }
}
