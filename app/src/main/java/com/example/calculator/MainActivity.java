package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.calculatop.R;

public class MainActivity extends AppCompatActivity {

    EditText inputField;
    TextView outputField;
    Editable input;
    int characters = 0;

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

        Button button = (Button)view;
        int selectionStart = inputField.getSelectionStart();
        input = inputField.getText();
        characters++;
        inputField.setText(input.insert(selectionStart,button.getText()));
        if(inputField.getSelectionStart() !=characters)
            inputField.setSelection(selectionStart+1);
        else
            inputField.setSelection(characters);
    }
}