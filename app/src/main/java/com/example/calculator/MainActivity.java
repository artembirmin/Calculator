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
    StringBuilder inputForCalculations;
    int rightSelectionPositions = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM); //Скрыло клавиатуру
        inputField = (EditText) findViewById(R.id.edittext_input);
        outputField = (TextView) findViewById(R.id.textview_output);
        inputField.setText("1 233 123");
    }

    public void onNumberClick(View view){

       // int numberOfSpaces;
        Button button = (Button) view;
        inputForField = inputField.getText();

        int selectionStart = inputField.getSelectionStart();
        inputForField.insert(selectionStart,button.getText());
        rightSelectionPositions = inputForField.length() - selectionStart - 1;




        inputField.setText(StringUtil.separation(inputForField.toString(), selectionStart+1));//+1, т.к минммум на 1 позицию будет сдвиг
        inputField.setSelection(inputField.length() - rightSelectionPositions);
    }

    public void onOperationClick(View view) {
        Button button = (Button) view;
       //if(button.getText() == "C"){
            inputField.setText("");
      //  }



    }
}