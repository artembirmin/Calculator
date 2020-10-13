package com.example.presentation.calculatorslist;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calculatormain.R;
import com.example.presentation.calculatorslist.adapters.CalculatorsListAdapterImpl;
import com.example.presentation.ui.bottomsheet.NewCalculatorBottomSheet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CalculatorsListActivityImpl extends AppCompatActivity
        implements CalculatorsListActivity, CalculatorsListAdapterImpl.OnCalculatorClickListener,
        NewCalculatorBottomSheet.OnBottomSheetContinueClick {

    private static final String TAG = "CalculatorsList";
    private NewCalculatorBottomSheet bottomSheetDialog;
    private CalculatorsListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculators_list);
        presenter = new CalculatorsListPresenterImpl(); //Тут DI надо
        presenter.attachView(this);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        presenter.setAdapter((RecyclerView) findViewById(R.id.recyclerView));
        bottomSheetDialog = new NewCalculatorBottomSheet(this); //Тут DI надо
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.show(getSupportFragmentManager(), "");
            }
        });
    }

    @Override
    protected void onResume() {
        presenter.onResume();
        super.onResume();
    }

    @Override
    public void onCalculatorClick(int position) {
        presenter.onClickCalculator(position);
       // overridePendingTransition(R.anim.animate_swipe_left_enter, R.anim.animate_swipe_left_exit);
    }

    @Override
    public void onBottomSheetContinueClick(String name) {
        presenter.goToNewCalculator(name);
       // overridePendingTransition(R.anim.animate_swipe_left_enter, R.anim.animate_swipe_left_exit);
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
                presenter.onClickDeleteAll();
                break;
            }
        }
        return true;
    }
}