package com.example.calculatormain;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expressioncalculator.ReversePolishNotation;

import static com.example.calculatormain.R.id.btn_division;
import static com.example.calculatormain.R.id.horiz_separator_6;

public class MainActivity extends AppCompatActivity {

    EditText inputField;
    AnswerTextView outputField;
    Editable inputForField;
    StringBuilder input;
    SharedPreferences preferences;
    ReversePolishNotation pn = new ReversePolishNotation();

    final static String SAVE_EXPRESSION = "save_expression";
    final static String SAVE_ANSWER = "save_answer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM); //Скрыло клавиатуру
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        inputField = (EditText) findViewById(R.id.edittext_input);
        outputField = (AnswerTextView) findViewById(R.id.textview_output);
        loadInstanceState();
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
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveInstanceState();
    }

    private void saveInstanceState() {
        preferences = getPreferences(MODE_PRIVATE);
        preferences.edit()
                .putString(SAVE_EXPRESSION,inputField.getText().toString())
                .putString(SAVE_ANSWER, outputField.getText().toString())
                .apply();
    }

    private void loadInstanceState(){
        preferences = getPreferences(MODE_PRIVATE);
        inputField.setText(preferences.getString(SAVE_EXPRESSION,""));
        outputField.setText(preferences.getString(SAVE_ANSWER,""));
        preferences.edit().clear().apply();
    }

    public void calculate(){
        input = new StringBuilder(inputField.getText().toString());
        if (StringUtil.isArithmeticOperation(input.charAt(input.length() - 1))){
            input.deleteCharAt(input.length() - 1);
        }
        if(input.length() == 0){
            outputField.setText("");
            return;
        }
        if (StringUtil.isArithmeticOperation(input.charAt(input.length() - 1))){
            input.deleteCharAt(input.length() - 1);
        }
        try {
            if(StringUtil.isNumberOnly(input.toString())) //TODO Сделать ситуацию с минусом в начале -134
                outputField.setText("");
            else
                outputField.setAnswerText(((String.valueOf(pn.calculateExpression(input.toString())))));
        } catch (Exception e){
            outputField.setText("Чо дурак?");
        }

    }

    public void onNumberClick(View view) {
        Button button = (Button) view;
        if(button.getId() == R.id.btn_0){
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
                break;
            case R.id.btn_backspace:
                complexOperations.onClickBackspace();
                break;
            case R.id.btn_sum:
            case R.id.btn_difference:
            case R.id.btn_multiplication:
            case btn_division:
                StringUtil.insertArithmeticSign((String) button.getText(), inputField);
                break;
            case R.id.bnt_fraction:
                complexOperations.onClickFraction();
                break;
            case R.id.btn_equal:
                if(outputField.length() == 0)
                    return;
                inputField.setText(StringUtil.format(outputField.getText().toString()));
                outputField.setText("");
                StringUtil.separation(inputField);
                return;
                //animation
        }
        StringUtil.checkTextSize(inputField);
        if(inputField.length() != 0)
            calculate();
        else outputField.setText("");
    }
}