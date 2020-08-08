package com.example.calculator;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.calculator.R.id.btn_division;

public class MainActivity extends AppCompatActivity {

    EditText inputField;
    TextView outputField;
    Editable inputForField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM); //Скрыло клавиатуру
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        inputField = findViewById(R.id.edittext_input);
        outputField = findViewById(R.id.textview_output);
        Button backspace = findViewById(R.id.btn_backspace);
        backspace.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                inputField.setText("");
                StringUtil.checkTextSize(inputField);
                return true;
            }
        });
    }

    public void onNumberClick(View view) {
        Button button = (Button) view;
        inputForField = inputField.getText();
        inputForField.insert(inputField.getSelectionStart(), button.getText());
        StringUtil.separation(inputField);
        StringUtil.checkTextSize(inputField);
    }

    public void onOperationClick(View view) {
        Button button = (Button) view;
        switch (button.getId()) {
            case R.id.btn_clear:
                inputField.setText("");
                StringUtil.checkTextSize(inputField);
                break;
            case R.id.btn_backspace:
                ComplexOperations.backspace(inputField);
                StringUtil.checkTextSize(inputField);
                break;
            case R.id.btn_sum:
            case R.id.btn_difference:
            case R.id.btn_multiplication:
            case btn_division:
                StringUtil.appendArithmeticSign((String) button.getText(), inputField);
                StringUtil.checkTextSize(inputField);
                break;
            case R.id.bnt_fraction:
                ComplexOperations.fraction(inputField);
                StringUtil.checkTextSize(inputField);
                break;
        }
    }
}