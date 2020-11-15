package com.example.presentation.calculatorslist;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.App;
import com.example.calculatormain.R;
import com.example.data.repositories.CalculatorsListRepository;
import com.example.di.activity.calculatorslist.CalculatorsListModule;
import com.example.di.activity.calculatorslist.DaggerCalculatorsListComponent;
import com.example.presentation.calculatorslist.adapters.CalculatorsListAdapter;
import com.example.presentation.calculatorslist.adapters.CalculatorsListAdapterImpl;
import com.example.presentation.ui.bottomsheet.NewCalculatorBottomSheet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

public class CalculatorsListActivity extends AppCompatActivity
        implements CalculatorsListView {

    private static final String TAG = "CalculatorsList";

    @Inject
    NewCalculatorBottomSheet bottomSheetDialog;
    @Inject
    LinearLayoutManager manager;
    @Inject
    CalculatorsListAdapter adapter;
    @Inject
    CalculatorsListPresenter presenter;

    private void setAdapter(){
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(manager);
        presenter.addCalculators();//Вынести сюда подписку, если надо
        recyclerView.setAdapter((CalculatorsListAdapterImpl) adapter);//Приведение такое надо?
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculators_list);

        DaggerCalculatorsListComponent.builder()
                .appComponent(App.getInstance().getAppComponent())
                .calculatorsListModule(new CalculatorsListModule(this))
                .build().inject(this);

        setAdapter();
        presenter.attachView(this);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bottomSheetDialog = new NewCalculatorBottomSheet(this); //Тут DI надо
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> bottomSheetDialog.show(getSupportFragmentManager(), ""));
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