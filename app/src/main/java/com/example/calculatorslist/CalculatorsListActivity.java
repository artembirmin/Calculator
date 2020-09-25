package com.example.calculatorslist;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calculatormain.R;
import com.example.calculatorslist.adapters.ByGoogleAdapter;
import com.example.calculatorslist.adapters.NoNameAdapter;
import com.example.models.Calculator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class CalculatorsListActivity extends AppCompatActivity implements NoNameAdapter.OnCalculatorClickListener {

    ArrayList<Calculator> calculatorList = new ArrayList<>();
    CreateCalculatorBottomSheet bottomSheetDialog;
    ByGoogleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculators_list);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initRVWithByGoogleAdapter();
        FloatingActionButton fab = findViewById(R.id.fab);
        bottomSheetDialog = new CreateCalculatorBottomSheet(CalculatorsListActivity.this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.show(getSupportFragmentManager(), "");
        }
        });
    }

    @Override
    public void onCalculatorClick(int position) {
        Log.d("qwerty", "onCalculatorClick: " + position);
        overridePendingTransition(R.anim.animate_swipe_left_enter, R.anim.animate_swipe_left_exit);
    }

    public void addCalculator(Calculator calculator){
        adapter.addCalculator(calculator);
    }

    private void initRVWithByGoogleAdapter() {
        RecyclerView rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ByGoogleAdapter(calculatorList, CalculatorsListActivity.this);
        rv.setAdapter(adapter);
        adapter.setItems(Arrays.asList(new Calculator("qqqq", "13213", "131313")));
//        adapter.setItems(Arrays.asList(new Calculator("Калькулятор 1", "1232435+433543+24*-3+5*-3"), new Calculator("Калькулятор 1", "1232435+433543+24*-3+5*-3"),
//                new Calculator("Калькулятор 2", "1233434343425544535532435+433543+24*-3+5*-3"),
//                new Calculator("Калькулятор 3", "1232435+433543+24*-3+5*-3"),
//                new Calculator("Калькулятор 3", "*-3+5*-3"),
//                new Calculator("Калькулятор 3", "*-3+5*-3"),
//                new Calculator("Калькулятор 3", "*-3+5*-3"),
//                new Calculator("Калькулятор 3", "*-3+5*-3"),
//                new Calculator("Калькулятор 3", "*-3+5*-3"),
//                new Calculator("Калькулятор 3", "*-3+5*-3"),
//                new Calculator("Калькулятор 3", "*-3+5*-3"),
//                new Calculator("Калькулятор 3", "*-3+5*-3"),
//                new Calculator("Калькулятор 3", "*-3+5*-3"),
//                new Calculator("Калькулятор 3", "*-3+5*-3")));
        adapter.notifyDataSetChanged();
    }

    private void initRVWithNoNameAdapter() {
        RecyclerView rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        NoNameAdapter adapter = new NoNameAdapter(calculatorList, this);
        rv.setAdapter(adapter);
//        adapter.setItems(Arrays.asList(new Calculator("Калькулятор 1", "1232435+433543+24*-3+5*-3"), new Calculator("Калькулятор 1", "1232435+433543+24*-3+5*-3"),
//                new Calculator("Калькулятор 2", "1233434343425544535532435+433543+24*-3+5*-3"),
//                new Calculator("Калькулятор 3", "1232435+433543+24*-3+5*-3"),
//                new Calculator("Калькулятор 3", "*-3+5*-3"),
//                new Calculator("Калькулятор 3", "*-3+5*-3"),
//                new Calculator("Калькулятор 3", "*-3+5*-3"),
//                new Calculator("Калькулятор 3", "*-3+5*-3"),
//                new Calculator("Калькулятор 3", "*-3+5*-3"),
//                new Calculator("Калькулятор 3", "*-3+5*-3"),
//                new Calculator("Калькулятор 3", "*-3+5*-3"),
//                new Calculator("Калькулятор 3", "*-3+5*-3"),
//                new Calculator("Калькулятор 3", "*-3+5*-3"),
//                new Calculator("Калькулятор 3", "*-3+5*-3")));
        adapter.notifyDataSetChanged();
    }
}