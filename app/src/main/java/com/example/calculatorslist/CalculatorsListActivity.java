package com.example.calculatorslist;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calculatorslist.adapters.ByGoogleAdapter;
import com.example.calculatorslist.adapters.NoNameAdapter;
import com.example.calculatorslist.models.Calculator;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class CalculatorsListActivity extends AppCompatActivity implements NoNameAdapter.OnCalculatorClickListener {

    ArrayList<Calculator> calculatorList = new ArrayList<>();
    InputMethodManager imm;
    EditText editText;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculators_list);
        editText = findViewById(R.id.edit_text);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerView);
        initRVWithByGoogleAdapter();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CalculatorsListActivity.this, "Fab fab fab", Toast.LENGTH_SHORT).show();
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(CalculatorsListActivity.this, R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(CalculatorsListActivity.this)
                        .inflate(R.layout.layout_bottom_sheet, (LinearLayout) findViewById(R.id.layout_top_rounded));
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
        }
        });
    }

    protected void showInputMethod() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

    @Override
    public void onCalculatorClick(int position) {
        Log.d("qwerty", "onCalculatorClick: " + position);
        overridePendingTransition(R.anim.animate_swipe_left_enter, R.anim.animate_swipe_left_exit);
    }

    private void initRVWithByGoogleAdapter() {
        RecyclerView rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        ByGoogleAdapter adapter = new ByGoogleAdapter(calculatorList, CalculatorsListActivity.this);
        rv.setAdapter(adapter);
        adapter.setItems(Arrays.asList(new Calculator("Калькулятор 1", "1232435+433543+24*-3+5*-3"), new Calculator("Калькулятор 1", "1232435+433543+24*-3+5*-3"),
                new Calculator("Калькулятор 2", "1233434343425544535532435+433543+24*-3+5*-3"),
                new Calculator("Калькулятор 3", "1232435+433543+24*-3+5*-3"),
                new Calculator("Калькулятор 3", "*-3+5*-3"),
                new Calculator("Калькулятор 3", "*-3+5*-3"),
                new Calculator("Калькулятор 3", "*-3+5*-3"),
                new Calculator("Калькулятор 3", "*-3+5*-3"),
                new Calculator("Калькулятор 3", "*-3+5*-3"),
                new Calculator("Калькулятор 3", "*-3+5*-3"),
                new Calculator("Калькулятор 3", "*-3+5*-3"),
                new Calculator("Калькулятор 3", "*-3+5*-3"),
                new Calculator("Калькулятор 3", "*-3+5*-3"),
                new Calculator("Калькулятор 3", "*-3+5*-3")));
        adapter.notifyDataSetChanged();
    }

    private void initRVWithNoNameAdapter() {
        RecyclerView rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        NoNameAdapter adapter = new NoNameAdapter(calculatorList, this);
        rv.setAdapter(adapter);
        adapter.setItems(Arrays.asList(new Calculator("Калькулятор 1", "1232435+433543+24*-3+5*-3"), new Calculator("Калькулятор 1", "1232435+433543+24*-3+5*-3"),
                new Calculator("Калькулятор 2", "1233434343425544535532435+433543+24*-3+5*-3"),
                new Calculator("Калькулятор 3", "1232435+433543+24*-3+5*-3"),
                new Calculator("Калькулятор 3", "*-3+5*-3"),
                new Calculator("Калькулятор 3", "*-3+5*-3"),
                new Calculator("Калькулятор 3", "*-3+5*-3"),
                new Calculator("Калькулятор 3", "*-3+5*-3"),
                new Calculator("Калькулятор 3", "*-3+5*-3"),
                new Calculator("Калькулятор 3", "*-3+5*-3"),
                new Calculator("Калькулятор 3", "*-3+5*-3"),
                new Calculator("Калькулятор 3", "*-3+5*-3"),
                new Calculator("Калькулятор 3", "*-3+5*-3"),
                new Calculator("Калькулятор 3", "*-3+5*-3")));
        adapter.notifyDataSetChanged();
    }
}