package com.example.calculator;

import android.annotation.SuppressLint;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;

public class Operations {

    private static ArrayList<Character> numeral = new ArrayList<Character>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));

    public static void backspace(EditText inputField){
        StringBuilder input = new StringBuilder(inputField.getText());
        int selection = inputField.getSelectionStart();
        if(input.length() == 1){ //если один символ в поле боя
            input.deleteCharAt(0);
            inputField.setText(input.toString());
            inputField.setSelection(0);
            StringUtil.separation(inputField);
            return;
        } else  if(selection == 0){ //если курсор на 0, то сбросить его
            inputField.setSelection(input.length());
            StringUtil.separation(inputField);
            return;
        } else if(input.charAt(selection - 1) == ' ') //если перед курсором пробел, то удалить символ после него
            input.deleteCharAt(selection - 2);
        else if(selection == 1) { //если на 1, то удалить и скинуть
            input.deleteCharAt(selection - 1);
            inputField.setText(input.toString());
            inputField.setSelection(input.length());
            StringUtil.separation(inputField);
            return;
        }   else if(selection == 2 && input.charAt(1) == ',' && input.charAt(0) == '0') { //аналогично верхнему случаю, только для дроби
            input.delete(0,2);
            inputField.setText(input.toString());
            inputField.setSelection(selection + 1);
            StringUtil.separation(inputField);
            inputField.setSelection(input.length());
            return;
        }
        else if(input.charAt(selection - 1) == ',' && input.charAt(selection - 2) == '0' && !numeral.contains(input.charAt(selection - 3))) { //верхний случай для варианта, когда это дробь с 0 и до 0 есть операция
            input.delete(selection - 1,selection - 3);
            inputField.setText(input.toString());
            inputField.setSelection(selection + 1);
            StringUtil.separation(inputField);
            inputField.setSelection(inputField.getText().length());
            return;
        }
        else
            input.deleteCharAt(selection - 1);
        inputField.setText(input.toString());
        inputField.setSelection(selection-1);
        StringUtil.separation(inputField);
    }

    public static void fraction(EditText inputField){
        StringBuilder input = new StringBuilder(inputField.getText());
        int selection = inputField.getSelectionStart();
        //int rightSelectionPositions = inputField.getText().length() - selection;
        if(input.length() == 0){
            input.insert(selection, "0,");
            inputField.setText(input.toString());
            inputField.setSelection(selection + 2);
            return;
        } else if(selection == input.length() - 1 && input.length() == 1){
            input.insert(selection, "0,");
            inputField.setText(input.toString());
            inputField.setSelection(selection + 2);
            return;
        } else if(selection == 0 && !numeral.contains(input.charAt(selection + 1)))
            return;
        else if(selection == 0){
            input.insert(selection, "0,");
            inputField.setText(input.toString());
            inputField.setSelection(selection + 2);
            return;
        }
    }
}
