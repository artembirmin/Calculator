package com.example.presentation.calculatorslist.adapters;

import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.example.calculatormain.R;
import com.example.models.Calculator;
import com.example.models.CommonListItem;
import com.example.models.Weather;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CalculatorsListAdapterImpl
        extends PagedListAdapter<CommonListItem,RecyclerView.ViewHolder>
        implements CalculatorsListAdapter {

    private static final String TAG = "Adapter";
    private List<CommonListItem> items;
    private OnCalculatorClickListener onCalculatorClickListener;

    @Override
    public void submitList(@Nullable PagedList<CommonListItem> pagedList) {
        super.submitList(pagedList);
    }

    public CalculatorsListAdapterImpl(List<Calculator> calculators,
                                      OnCalculatorClickListener calculatorClickListener){
        super(DIFF_CALLBACK);
        items = new LinkedList<>();
        items.addAll(calculators);
        setCalculators(calculators);
        notifyDataSetChanged();
        this.onCalculatorClickListener = calculatorClickListener;
    }

    public void initList(List<Calculator> calculators){
        items.addAll(calculators);
        setCalculators(calculators);
    }

    public void addWeather(Weather weather) {
        items.add(weather);
        notifyDataSetChanged();
    }

    @Override
    public void setCalculators(Collection<Calculator> calculators) {
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
    public void updateItems() {
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof Calculator)
            return CommonListItem.CALCULATOR;
        if (items.get(position) instanceof Weather)
            return CommonListItem.WEATHER;
        else return -1;
    }

    private static DiffUtil.ItemCallback<CommonListItem> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<CommonListItem>() {

                @Override
                public boolean areItemsTheSame(@NonNull CommonListItem oldItem, @NonNull CommonListItem newItem) {
                   // if(oldItem instanceof Calculator)
                    return ((Calculator) oldItem).getId().equals(((Calculator) newItem).getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull CommonListItem oldItem, @NonNull CommonListItem newItem) {
                    return ((Calculator) oldItem).equals(((Calculator) newItem));
                }
            };

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == CommonListItem.CALCULATOR) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calculator_card_view, parent, false);
            return new CalcViewHolder(view, onCalculatorClickListener);
        }
        if (viewType == CommonListItem.WEATHER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_card_view, parent, false);
            return new WeatherViewHolder(view);
        } else return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + position);
        if (holder instanceof CalcViewHolder)
            ((CalcViewHolder) holder).bind((Calculator) items.get(position));
        if (holder instanceof WeatherViewHolder)
            ((WeatherViewHolder) holder).bind((Weather) items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnCalculatorClickListener {
        void onCalculatorClick(int position);
    }

    static class WeatherViewHolder extends RecyclerView.ViewHolder {

        private static final String TAG = "Adapter";
        private TextView weatherTextView;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "WeatherViewHolder:");
            weatherTextView = itemView.findViewById(R.id.weather_text_view);
        }

        public void bind(Weather weather) {
            weatherTextView.setText(weather.toString());
        }
    }

    static class CalcViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final OnCalculatorClickListener calculatorClickListener;
        private TextView nameTextView;
        private TextView contentTextView;

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
            if (contentTextView.getText().length() == 0)
                contentTextView.setText("0");
        }

        @Override
        public void onClick(View view) {
            //Здесь основные действия
            calculatorClickListener.onCalculatorClick(getAdapterPosition());
        }
    }
}
