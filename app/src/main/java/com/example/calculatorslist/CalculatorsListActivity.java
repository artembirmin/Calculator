package com.example.calculatorslist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calculatormain.CalculatorActivity;
import com.example.calculatormain.R;
import com.example.calculatorslist.adapters.NoNameAdapter;
import com.example.calculatorslist.database.CalculatorRepository;
import com.example.calculatorslist.database.CalculatorRoomDatabase;
import com.example.calculatorslist.database.CalculatorDao;
import com.example.models.Calculator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CalculatorsListActivity extends AppCompatActivity implements NoNameAdapter.OnCalculatorClickListener, CreateCalculatorBottomSheet.OnBottomSheetContinueClick {

    private static final String TAG = "CalculatorsList";
    List<Calculator> calculatorList = new ArrayList<>();
    CreateCalculatorBottomSheet bottomSheetDialog;
    NoNameAdapter adapter;
    CalculatorRepository calculatorRepository;
    private CalculatorRoomDatabase db;
    private CalculatorDao calculatorDao;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculators_list);
        initToolbar();
        initDB();
        calculatorList = calculatorDao.getAll();
        // StringUtil.reverse(calculatorList);
        Log.d(TAG, "onCreate:");
//        Log.d(TAG, "onCreate: list" + calculatorList);
        initRVWithNoNameAdapter();
        adapter.notifyDataSetChanged();
        bottomSheetDialog = new CreateCalculatorBottomSheet(CalculatorsListActivity.this);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.show(getSupportFragmentManager(), "");
            }
        });
        if (getIntent().hasExtra("old_calculator")) {
            Toast.makeText(this, "калькулятор получил old", Toast.LENGTH_LONG).show();
        }
        if (getIntent().hasExtra("new_calculator")) {
            Toast.makeText(this, "калькулятор получил new", Toast.LENGTH_LONG).show();
            adapter.addNewCalculator((Calculator) getIntent().getParcelableExtra("new_calculator"));
        }
        if (getIntent().hasExtra("updated_calculator")) {
            Toast.makeText(this, "калькулятор получил udp", Toast.LENGTH_LONG).show();
            adapter.updateCalculator((Calculator) getIntent().getParcelableExtra("updated_calculator"),
                    getIntent().getIntExtra("index", -1));
        }
    }

    @Override
    protected void onStop() {
        // calculatorDao.deleteAll();
        Log.d(TAG, "onStop: ");
        //  Log.d(TAG, "onStop: " + calculatorList);
        //calculatorDao.insert(calculatorList);
        super.onStop();
    }

    @Override
    protected void onResume() {
        adapter.setCalculators(calculatorRepository.getCalculators());
        adapter.notifyDataSetChanged();
        Log.d(TAG, "onResume: ");
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onCalculatorClick(int position) {
        //   Log.d("qwerty", "onCalculatorClick: " + position);
        Intent intent = new Intent(this, CalculatorActivity.class);
        intent.putExtra("selected_calculator", calculatorList.get(position));
        intent.putExtra("index", position);
        startActivity(intent);
        overridePendingTransition(R.anim.animate_swipe_left_enter, R.anim.animate_swipe_left_exit);
    }

    @Override
    public void onBottomSheetContinueClick(Calculator calculator) {
        Intent intent = new Intent(this, CalculatorActivity.class);
        intent.putExtra("new_calculator", calculator);
        intent.putExtra("index", calculator);
        startActivity(intent);
        overridePendingTransition(R.anim.animate_swipe_left_enter, R.anim.animate_swipe_left_exit);
    }

    private void initToolbar() {
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
                calculatorDao.deleteAll();
                calculatorList.clear();
                adapter.notifyDataSetChanged();
                break;
            }
            case R.id.update_third: {
                Calculator calculator = calculatorList.get(2);
                calculator.setExpression("2288");
                calculatorDao.update(calculator);
                adapter.notifyDataSetChanged();
            }
        }
        return true;
    }

    private void initDB() {
        db = CalculatorRoomDatabase.getDatabase(this);
        calculatorDao = db.CalculatorDao();
        calculatorRepository = new CalculatorRepository(this);
    }

    private void initRVWithNoNameAdapter() {
        RecyclerView rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoNameAdapter(calculatorList, this);
        rv.setAdapter(adapter);
        LinkedList<Calculator> calculators = new LinkedList<>();
        //  calculators.add(new Calculator("Калькулятор 1", "1235+433543+24*-3+5*-3", "242"));
        //  calculators.add(new Calculator("Калькулятор 2", "1232435+433543+24*-3+5*-3", "24242"));
        adapter.setCalculators(calculators);
        adapter.notifyDataSetChanged();
    }
}