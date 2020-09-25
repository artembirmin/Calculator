
package com.example.calculatormain;

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
import com.example.expressioncalculator.ReversePolishNotation;
import com.example.models.Calculator;

public class CalculatorActivity extends AppCompatActivity {

    TextView nameField;
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
        setContentView(R.layout.activity_calculator);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM); //Скрыло клавиатуру
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        nameField = findViewById(R.id.textview_name);
        inputField = findViewById(R.id.edittext_input);
        outputField = findViewById(R.id.textview_output);
        if(getIntent().hasExtra("selected_calculator")){
            Calculator calculator = getIntent().getParcelableExtra("selected_calculator");
            nameField.setText(calculator.getName());
            inputField.setText(calculator.getContent());
            outputField.setText(calculator.getAnswer());
        }
        if(getIntent().hasExtra("new_calculator")){
            Calculator calculator = getIntent().getParcelableExtra("new_calculator");
            nameField.setText(calculator.getName());
            inputField.setText(calculator.getContent());
            outputField.setText(calculator.getAnswer());
        }
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
        overridePendingTransition(R.anim.animate_swipe_right_enter, R.anim.animate_swipe_right_exit);
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveInstanceState();
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
                break;
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
        if (inputField.length() != 0)
            calculate();
        else outputField.setText("");
    }
}