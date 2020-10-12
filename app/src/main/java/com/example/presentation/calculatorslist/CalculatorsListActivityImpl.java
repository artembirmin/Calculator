package com.example.presentation.calculatorslist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.domain.calculatorslist.CalculatorsListRepository;
import com.example.presentation.calculator.CalculatorActivity;
import com.example.calculatormain.R;
import com.example.presentation.calculatorslist.adapters.CalculatorsListAdapter;
import com.example.presentation.calculatorslist.adapters.CalculatorsListAdapterImpl;
import com.example.data.repositories.CalculatorsListRepositoryImpl;
import com.example.models.Calculator;
import com.example.presentation.ui.bottomsheet.NewCalculatorBottomSheet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CalculatorsListActivityImpl extends AppCompatActivity
        implements CalculatorsListActivity, CalculatorsListAdapterImpl.OnCalculatorClickListener,
        NewCalculatorBottomSheet.OnBottomSheetContinueClick {

    private static final String TAG = "CalculatorsList";
    List<Calculator> calculatorList = new ArrayList<>();
    NewCalculatorBottomSheet bottomSheetDialog;
    CalculatorsListAdapterImpl adapter;
    CalculatorsListRepository calculatorsListRepository;
    CalculatorsListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculators_list);
        presenter = new CalculatorsListPresenterImpl();
        presenter.attachView(this);

        calculatorsListRepository = new CalculatorsListRepositoryImpl();

        initToolbar();
        Log.d(TAG, "onCreate:");
        initRVWithNoNameAdapter();
        initBottomSheet(savedInstanceState);
        bottomSheetDialog = new NewCalculatorBottomSheet(this);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.show(getSupportFragmentManager(), "");
            }
        });
    }

    private void initBottomSheet(Bundle savedInstanceState) {

    }

    private void initToolbar() {
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: " + calculatorsListRepository.getCalculators());
        presenter.onResume();
        super.onResume();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: ");
        super.onStop();
    }

    @Override
    public void onCalculatorClick(int position) {
        //   Log.d("qwerty", "onCalculatorClick: " + position);
        Intent intent = new Intent(this, CalculatorActivity.class);
        intent.putExtra("selected_calculator", presenter.getCalculator(position));
        Log.d(TAG, "onCalculatorClick: " + presenter.getCalculator(position));
        intent.putExtra("index", position);
        startActivity(intent);
        overridePendingTransition(R.anim.animate_swipe_left_enter, R.anim.animate_swipe_left_exit);
    }

    @Override //Это должен делать роутер
    public void onBottomSheetContinueClick(Calculator calculator) {
        //Здесь должен быть вызов роутера
        Intent intent = new Intent(this, CalculatorActivity.class);
        intent.putExtra("new_calculator", calculator);
        intent.putExtra("index", calculator);
        startActivity(intent);
        overridePendingTransition(R.anim.animate_swipe_left_enter, R.anim.animate_swipe_left_exit);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all: {
                Log.d(TAG, "onOptionsItemSelected: before" + calculatorsListRepository.getCalculators());
                presenter.onClickDeleteAll();
                Log.d(TAG, "onOptionsItemSelected: after" + calculatorsListRepository.getCalculators());
                break;
            }
        }
        return true;
    }

    private void initRVWithNoNameAdapter() {
        presenter.setAdapter((RecyclerView) findViewById(R.id.recyclerView));
//        RecyclerView rv = findViewById(R.id.recyclerView);
//        rv.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new CalculatorsListAdapterImpl(calculatorList, this);
//        rv.setAdapter(adapter);
//        LinkedList<Calculator> calculators = new LinkedList<>();
//        adapter.setCalculators(calculators);
//        adapter.notifyDataSetChanged();
    }
}