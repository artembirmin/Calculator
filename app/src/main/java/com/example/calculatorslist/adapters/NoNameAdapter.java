package com.example.calculatorslist.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calculatorslist.CalculatorActivity;
import com.example.calculatorslist.R;
import com.example.calculatorslist.models.Calculator;

import java.util.ArrayList;
import java.util.Collection;

public class NoNameAdapter extends RecyclerView.Adapter<NoNameAdapter.CalcViewHolder> {

    private ArrayList<Calculator> calculatorList;
    private OnCalculatorClickListener onCalculatorClickListener;

    public NoNameAdapter(ArrayList<Calculator> calculatorList , OnCalculatorClickListener calculatorClickListener) {
        this.calculatorList = calculatorList;
        this.onCalculatorClickListener = calculatorClickListener;
    }

    public void setItems(Collection<Calculator> calculators){
        calculatorList.addAll(calculators);
        notifyDataSetChanged(); //Дает адаптеру знать об изменении списка элементов и что надо перерисовать
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
            nameTextView.setText(calculator.getName());
            contentTextView.setText(calculator.getContent());
        }

        @Override
        public void onClick(View view) {
            //Здесь основные действия
            Intent intent = new Intent(view.getContext(), CalculatorActivity.class);
            view.getContext().startActivity(intent);
            Log.d("qwerty", "onClick: " + getAdapterPosition());
            calculatorClickListener.onCalculatorClick(getAdapterPosition());
        }
    }

    public interface OnCalculatorClickListener {
        void onCalculatorClick(int position);
    }
}
