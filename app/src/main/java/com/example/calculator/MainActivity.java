package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static com.example.calculator.R.id.btn_division;

public class MainActivity extends AppCompatActivity {

    EditText inputField;
    TextView outputField;
    Editable inputForField;
<<<<<<< HEAD
=======
    int rightSelectionPositions = 0;
>>>>>>> inputOutputField

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM); //Скрыло клавиатуру
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        inputField = (EditText) findViewById(R.id.edittext_input);
        outputField = (TextView) findViewById(R.id.textview_output);
<<<<<<< HEAD
        Button backspace = (Button) findViewById(R.id.btn_backspace);
        backspace.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                inputField.setText("99+");
                StringUtil.checkTextSize(inputField);
                return true;
            }
        });
=======

>>>>>>> inputOutputField
    }

    public void onNumberClick(View view){
        Button button = (Button) view;
        inputForField = inputField.getText();
<<<<<<< HEAD
        inputForField.insert(inputField.getSelectionStart(),button.getText());
        StringUtil.separation(inputField);
        StringUtil.checkTextSize(inputField);
=======
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

>>>>>>> inputOutputField
    }

    public void onOperationClick(View view) {
        Button button = (Button) view;
<<<<<<< HEAD
        switch (button.getId()){
            case R.id.btn_clear:
                inputField.setText("");
                StringUtil.checkTextSize(inputField);
                break;
            case R.id.btn_backspace:
                Operations.backspace(inputField);
                StringUtil.checkTextSize(inputField);
                break;
            case R.id.btn_sum:
            case R.id.btn_difference:
            case R.id.btn_multiplication:
            case btn_division:
                StringUtil.append((String) button.getText(), inputField);
                StringUtil.checkTextSize(inputField);
                break;
            case R.id.bnt_fraction:
                Operations.fraction(inputField);
                StringUtil.checkTextSize(inputField);
                break;
        }
=======
        if (button.getId() == R.id.btn_clear) {
            inputField.setText("");
        }


>>>>>>> inputOutputField
    }
}