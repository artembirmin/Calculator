package com.example.presentation.calculator;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.calculatormain.R;
import com.example.data.repositories.CalculatorsListRepository;
import com.example.data.repositories.CalculatorsListRepositoryImpl;
import com.example.domain.calculator.ComplexOperations;
import com.example.domain.calculator.ExpressionCalculator;
import com.example.domain.calculator.ReversePolishNotation;
import com.example.domain.calculator.StringUtil;
import com.example.models.Calculator;
import com.example.presentation.ui.widgets.AnswerTextView;

public class CalculatorActivity extends AppCompatActivity {

    private static final String TAG = "CalculatorActivity";
    TextView nameField;
    EditText inputField;
    AnswerTextView outputField;
    StringBuilder input;
    ExpressionCalculator pn = new ReversePolishNotation();
    Calculator calculator;
    CalculatorsListRepository calculatorsListRepository;
    boolean isNewCalculator;
    boolean isUpdatedCalculator;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        nameField = findViewById(R.id.textview_name);
        inputField = findViewById(R.id.edittext_input);
        outputField = findViewById(R.id.textview_output);
        calculatorsListRepository = new CalculatorsListRepositoryImpl();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM); //Скрыло клавиатуру
        if (getIntent().hasExtra("selected_calculator")) {
            calculator = calculatorsListRepository.getCalculator(getIntent().getIntExtra("selected_calculator", -1));
            index = getIntent().getIntExtra("index", -1);
            nameField.setText(calculator.getId());
            inputField.setText(calculator.getExpression());
            inputField.setSelection(inputField.length());
            outputField.setText(calculator.getAnswer());
            isNewCalculator = false;
        }
        if (getIntent().hasExtra("new_calculator")) {
            calculator = getIntent().getParcelableExtra("new_calculator");
            nameField.setText(calculator.getId());
            inputField.setText(calculator.getExpression());
            outputField.setText(calculator.getAnswer());
            isNewCalculator = true;
        }
        StringUtil.checkTextSize(inputField);
        //loadInstanceState();
        Button backspace = findViewById(R.id.btn_backspace);
        backspace.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                inputField.setText("");
                outputField.setText("");
                StringUtil.checkTextSize(inputField);
                return true;
            }
        });
        //Slidr.attach(this);
        //Выпилить стиль, тему, атрибут в манифесте и бэкграунд в хмл
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
        calculator.setExpression(inputField.getText().toString());
        calculator.setAnswer(outputField.getText().toString());
        if (isNewCalculator) {
            Log.d(TAG, "finish: new calc");
            calculatorsListRepository.insertCalculator(calculator);
        } else if (isUpdatedCalculator) {
            Log.d(TAG, "finish: upd calc");
            calculatorsListRepository.updateCalculator(calculator);
        }
        super.onPause();
    }

    @Override
    public void finish() {
        super.finish();
    }

    public void calculate() {
        isUpdatedCalculator = true;
        input = new StringBuilder(inputField.getText().toString());

        try {
            if (!StringUtil.haveSmthToCalculate(input)) //TODO Сделать ситуацию с минусом в начале -134
                outputField.setText("");
            else
                outputField.setAnswerText(((String.valueOf(pn.calculateExpression(input.toString())))));
        } catch (Exception e) {
            outputField.setText("Чо дурак?");
        }
    }

    public void onNumberClick(View view) {
        Button button = (Button) view;
        if (button.getId() == R.id.btn_0) {
            new ComplexOperations(inputField, new StringBuilder(inputField.getText().toString())).insertZero();
            calculate();
            return;
        }
        inputField.getText().insert(inputField.getSelectionStart(), button.getText()); //Нужен ли тут inputForField
        StringUtil.separation(inputField);
        StringUtil.checkTextSize(inputField);
        calculate();
    }

    public void onOperationClick(View view) {
        ComplexOperations complexOperations = new ComplexOperations(inputField, new StringBuilder(inputField.getText().toString()));
        Button button = (Button) view;
        switch (button.getId()) {
            case R.id.btn_clear:
                inputField.setText("");
                outputField.setText("");
                return;
            case R.id.btn_backspace:
                complexOperations.onClickBackspace();
                break;
            case R.id.btn_sum:
            case R.id.btn_difference:
            case R.id.btn_multiplication:
            case R.id.btn_division:
                StringUtil.insertArithmeticSign((String) button.getText(), inputField);
                break;
            case R.id.bnt_fraction:
                complexOperations.onClickFraction();
                break;
            case R.id.btn_equal:
                if (outputField.length() == 0)
                    return;
                inputField.setText(StringUtil.format(outputField.getText().toString()));
                outputField.setText("");
                inputField.setSelection(inputField.length());
                StringUtil.separation(inputField);
                StringUtil.checkTextSize(inputField);
                return;
            //animation
        }
        StringUtil.checkTextSize(inputField);
        calculate();
    }
}