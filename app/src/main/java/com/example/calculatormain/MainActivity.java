package com.example.calculatormain;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM); //Скрыло клавиатуру
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        inputField = (EditText) findViewById(R.id.edittext_input);

        outputField = (AnswerTextView) findViewById(R.id.textview_output);
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
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        View separator = (View) findViewById(horiz_separator_6);
        separator.setBottom(120);
        Log.i("qwerty", String.valueOf(separator.getY()));
    }

    public void onNumberClick(View view) {
        Button button = (Button) view;
        inputForField = inputField.getText(); //Нужен ли тут inputForField
        inputForField.insert(inputField.getSelectionStart(), button.getText());
        StringUtil.separation(inputField);
        StringUtil.checkTextSize(inputField);
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
                if(inputField.length() != 0)
                    outputField.setAnswerText((String.valueOf(new ReversePolishNotation().calculateExpression(String.valueOf(inputField.getText())))));
                else outputField.setText("");
                //animation
        }
        StringUtil.checkTextSize(inputField);
    }
}