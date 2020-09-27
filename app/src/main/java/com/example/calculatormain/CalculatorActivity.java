
package com.example.calculatormain;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calculatorslist.CalculatorsListActivity;
import com.example.expressioncalculator.ReversePolishNotation;
import com.example.models.Calculator;

public class CalculatorActivity extends AppCompatActivity {

    private static final String TAG = "qwerty";
    TextView nameField;
    EditText inputField;
    AnswerTextView outputField;
    Editable inputForField;
    StringBuilder input;
    SharedPreferences preferences;
    ReversePolishNotation pn = new ReversePolishNotation();
    Calculator calculator;
    boolean isNewCalculator;
    boolean isUpdatedCalculator;
    int index;

    final static String SAVE_EXPRESSION = "save_expression";
    final static String SAVE_ANSWER = "save_answer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: calc");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM); //Скрыло клавиатуру
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        nameField = findViewById(R.id.textview_name);
        inputField = findViewById(R.id.edittext_input);
        outputField = findViewById(R.id.textview_output);
        if(getIntent().hasExtra("selected_calculator")){
            calculator = getIntent().getParcelableExtra("selected_calculator");
            index = getIntent().getIntExtra("index", -1);
            nameField.setText(calculator.getId());
            inputField.setText(calculator.getExpression());
            inputField.setSelection(inputField.length());
            outputField.setText(calculator.getAnswer());
            isNewCalculator = false;
        }
        if(getIntent().hasExtra("new_calculator")){
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
    public void finish() {
        super.finish();
        calculator.setExpression(inputField.getText().toString());
        calculator.setAnswer(outputField.getText().toString());
        Intent intent = new Intent(this, CalculatorsListActivity.class);
        if (isNewCalculator)
            intent.putExtra("new_calculator", calculator);
        else if(isUpdatedCalculator){
            intent.putExtra("updated_calculator", calculator);
            intent.putExtra("index", index);
        }
        else
            intent.putExtra("old_calculator", calculator);
        startActivity(intent);
        overridePendingTransition(R.anim.animate_swipe_right_enter, R.anim.animate_swipe_right_exit);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: calc");
        saveInstanceState();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: calc");
    }

    private void saveInstanceState() {
        preferences = getPreferences(MODE_PRIVATE);
        preferences.edit()
                .putString(SAVE_EXPRESSION, inputField.getText().toString())
                .putString(SAVE_ANSWER, outputField.getText().toString())
                .apply();
    }

    private void loadInstanceState() {
        preferences = getPreferences(MODE_PRIVATE);
        inputField.setText(preferences.getString(SAVE_EXPRESSION, ""));
        outputField.setText(preferences.getString(SAVE_ANSWER, ""));
        preferences.edit().clear().apply();
    }

    public void calculate() {
        isUpdatedCalculator = true;
        input = new StringBuilder(inputField.getText().toString());
        if (StringUtil.isArithmeticOperation(input.charAt(input.length() - 1))) {
            input.deleteCharAt(input.length() - 1);
        }
        if (input.length() == 0) {
            outputField.setText("");
            return;
        }
        if (StringUtil.isArithmeticOperation(input.charAt(input.length() - 1))) {
            input.deleteCharAt(input.length() - 1);
        }
        try {
            if (StringUtil.isNumberOnly(input.toString())) //TODO Сделать ситуацию с минусом в начале -134
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
        inputForField = inputField.getText(); //Нужен ли тут inputForField
        inputForField.insert(inputField.getSelectionStart(), button.getText());
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
                StringUtil.separation(inputField);
                return;
            //animation
        }
        StringUtil.checkTextSize(inputField);
        calculate();
    }
}