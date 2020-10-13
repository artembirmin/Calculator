package com.example.presentation.calculatorslist.adapters;

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
import java.util.List;

public class CalculatorsListAdapterImpl
        extends RecyclerView.Adapter<CalculatorsListAdapterImpl.CalcViewHolder>
        implements CalculatorsListAdapter{

    private static final String TAG = "qwerty";
    private List<Calculator> calculators;
    private OnCalculatorClickListener onCalculatorClickListener;

    public CalculatorsListAdapterImpl(List<Calculator> calculators, OnCalculatorClickListener calculatorClickListener) {
        this.calculators = calculators;
        setCalculators(calculators);
        this.onCalculatorClickListener = calculatorClickListener;
    }

    public void addNewCalculator(Calculator calculator){
        calculators.add(0,calculator);
        Log.d(TAG, "addNewCalculator: " + calculators);
        notifyDataSetChanged();
    }

    public void updateCalculator(Calculator calculator, int index) {
        calculators.remove(index);
        calculators.add(0, calculator);
    }

    @Override
    public void setCalculators(Collection<Calculator> calculators){
        this.calculators.clear();
        this.calculators.addAll(calculators);
        Collections.reverse(this.calculators);
        notifyDataSetChanged();
        //Дает адаптеру знать об изменении списка элементов и что надо перерисовать
    }

    public void clearItems(){
        calculators.clear();
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
        holder.bind(calculators.get(position));
    }

    @Override
    public int getItemCount() {
        return calculators.size();
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
