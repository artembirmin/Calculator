package com.example.calculator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.calculatop.R;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText inputField;
    TextView outputField;
    Editable inputForField;
    int rightSelectionPositions = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM); //Скрыло клавиатуру
        inputField = (EditText) findViewById(R.id.edittext_input);
        outputField = (TextView) findViewById(R.id.textview_output);

    }

    public void onNumberClick(View view){
        Button button = (Button) view;
        inputForField = inputField.getText();
        int selectionStart = inputField.getSelectionStart();
        inputForField.insert(selectionStart,button.getText());
        rightSelectionPositions = inputForField.length() - selectionStart - 1;
        inputField.setText(StringUtil.separation(inputForField.toString(), selectionStart+1));//+1, т.к минммум на 1 позицию будет сдвиг
        inputField.setSelection(inputField.length() - rightSelectionPositions);
        if(inputField.length() <=11)
            inputField.setTextSize(50);

        if( inputField.length() < 14 && inputField.length() >= 12)
            inputField.setTextSize(48);

        if(inputField.length() < 17 && inputField.length() >=14)
            inputField.setTextSize(40);

        if(inputField.length() < 19 && inputField.length() >= 17)
            inputField.setTextSize(34);

        if(inputField.length() >=19)
            inputField.setTextSize(30);

        Log.i("qwerty",String.valueOf(inputField.length()));
        //if(inputField.length() > 12)
        //    inputField.setTextSize(42);

    }

    public void onOperationClick(View view) {
        Button button = (Button) view;
        if (button.getId() == R.id.btn_clear) {
            inputField.setText("");
        }


    }
}