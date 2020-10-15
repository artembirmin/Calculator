package com.example.presentation.calculatorslist.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.calculatormain.R;

import com.example.models.Calculator;import com.example.models.CommonListItem;
import com.example.models.Weather;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CalculatorsListAdapterImpl
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements CalculatorsListAdapter{


    private List<CommonListItem> items;
    private OnCalculatorClickListener onCalculatorClickListener;

    public CalculatorsListAdapterImpl(List<Calculator> calculators, OnCalculatorClickListener calculatorClickListener) {
        items = new LinkedList<>();
        items.addAll(calculators);
        setCalculators(calculators);
        this.onCalculatorClickListener = calculatorClickListener;
    }

    public void addWeather(Weather weather){
        items.add(weather);
        notifyDataSetChanged();
    }

    @Override
    public void setCalculators(Collection<Calculator> calculators){
        items.clear();
        items.addAll(calculators);
        Collections.reverse(items);
        notifyDataSetChanged();
    }

    @Override
    public List<CommonListItem> getItems() {
        return items;
    }

    @Override
    public int getItemViewType(int position) {
        if(items.get(position) instanceof Calculator)
            return CommonListItem.CALCULATOR;
        if(items.get(position) instanceof Weather)
            return CommonListItem.WEATHER;
        else return  -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == CommonListItem.CALCULATOR){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calculator_card_view, parent, false);
            return new CalcViewHolder(view, onCalculatorClickListener);
        }
        if(viewType == CommonListItem.WEATHER){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calculator_card_view, parent, false);
            return new WeatherViewHolder(view);
        }
        else return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
       if(holder instanceof CalcViewHolder)
           ((CalcViewHolder) holder).bind((Calculator) items.get(position));
       if(holder instanceof WeatherViewHolder)
           ((WeatherViewHolder) holder).bind((Weather) items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class WeatherViewHolder extends  RecyclerView.ViewHolder{

        private TextView weatherTextView;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            weatherTextView = itemView.findViewById(R.id.weather_text_view);
        }

        public void bind(Weather weather){
            weatherTextView.setText(weather.toString());
        }
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
