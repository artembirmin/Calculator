package com.example.calculator;

import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;

public class ComplexOperations {

    private static ArrayList<Character> numeral = new ArrayList<Character>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));
    private static final int END_OF_FIELD = 0;

    public static void backspace(EditText inputField) {
        StringBuilder input = new StringBuilder(inputField.getText());
        int selection = inputField.getSelectionStart();
        if (input.length() == 1){
            processOneSymbol(inputField, input);
            return;
        }
        if (selection == 0) {
            //если курсор на 0, то сбросить его
            processSelectionOnEnd(inputField, input);
            return;
        }
        if(input.charAt(selection - 1) == ' '){
            input.deleteCharAt(selection - 2);
            inputField.setText(input.toString());
            inputField.setSelection(selection - 1);
            StringUtil.separation(inputField);
            return;
        }
        if (selection == 1 && input.charAt(selection) == ',' && input.charAt(selection - 1) == '0') {
            //если на 1 и начало дроби, то удалить и скинуть
            input.delete(selection -1, selection + 1);
            inputField.setText(input.toString());
            StringUtil.separation(inputField);
            inputField.setSelection(inputField.getText().length());
            return;
        }
        if (selection == 1) {
            //если на 1, то удалить и скинуть
            input.deleteCharAt(selection - 1);
            inputField.setText(input.toString());
            StringUtil.separation(inputField);
            inputField.setSelection(input.length());
            return;
        }
        if (selection == 2 && input.charAt(1) == ',' && input.charAt(0) == '0') {
            //аналогично верхнему случаю, только для дроби
            input.delete(0, 2);
            inputField.setText(input.toString());
            StringUtil.separation(inputField);
            inputField.setSelection(inputField.getText().length());
            return;
        }
        if (selection > 2 && selection != input.length() && ((input.charAt(selection - 1) == ',' && input.charAt(selection - 2) == '0' && !numeral.contains(input.charAt(selection - 3)))
                ||(input.charAt(selection) == ',' && input.charAt(selection - 1) == '0' && !numeral.contains(input.charAt(selection - 3))))) {
            //верхний случай для варианта, когда это дробь с 0 и до 0 есть операция
            input.delete(selection - 2, selection);
            inputField.setText(input.toString());
            inputField.setSelection(selection - 1);
            StringUtil.separation(inputField);
            try {
                inputField.setText(selection - 2);
            } catch (Exception ex) {
                inputField.setSelection(input.length());
            }//FIXME
            return;
        }
        if(input.charAt(selection - 1) ==  ',' && selection != input.length()) {
            int oldNumberSpaces = StringUtil.getNumberOf(' ', inputField.getText().toString().substring(0, selection));
            input.deleteCharAt(selection - 1);
            inputField.setText(input.toString());
            inputField.setSelection(selection - 1);
            StringUtil.separation(inputField);
            int newNumberSpaces = StringUtil.getNumberOf(' ', inputField.getText().toString().substring(0, selection));
            inputField.setSelection(selection - 1 - oldNumberSpaces + newNumberSpaces);
            return;
        }
        input.deleteCharAt(selection - 1);
        inputField.setText(input.toString());
        inputField.setSelection(selection - 1);
        StringUtil.separation(inputField);
    }

    private static void processSelectionOnEnd(EditText inputField, StringBuilder input) {
        inputField.setSelection(input.length());
        StringUtil.separation(inputField);
    }

    private static void processOneSymbol(EditText inputField, StringBuilder input) {
        input.deleteCharAt(0);
        inputField.setText(input.toString());
        inputField.setSelection(0);
        StringUtil.separation(inputField);
    }

    public static void fraction(EditText inputField) { //TODO
        StringBuilder input = new StringBuilder(inputField.getText());
        int selection = inputField.getSelectionStart();
        if(StringUtil.isFraction(inputField) || (selection==0 && input.length() > 2 && !numeral.contains(input.charAt(selection))))
            return;
        if (input.length() == 0 || (selection == 0 && numeral.contains(input.charAt(selection)))) {
            input.insert(0, "0,");
            inputField.setText(input.toString());
            try{
                inputField.setSelection(3);
                StringUtil.separation(inputField);
                inputField.setSelection(2);
            } catch (Exception ex){
                inputField.setSelection(2);
            }
            return;
        }
        if (!numeral.contains(input.charAt(selection - 1)) && input.charAt(selection - 1) != ' ') {
            input.insert(selection, "0,");
            inputField.setText(input.toString());
            selection += 2;
            try{
                inputField.setSelection(selection + 1);
                StringUtil.separation(inputField);
                inputField.setSelection(selection);
            } catch (Exception ex){
                inputField.setSelection(selection);
            }
            return;
        }
        if (((selection == input.length() && numeral.contains(input.charAt(selection - 1))) ||
                (selection < input.length() && numeral.contains(input.charAt(selection - 1) ) || input.charAt(selection - 1) == ' '))) {
            if (input.charAt(selection - 1) == ' ') {
                inputField.setSelection(--selection);
            }
            input.insert(selection, ",");
            inputField.setText(input.toString());
            inputField.setSelection(selection);
            int oldNumberSpaces = StringUtil.getNumberOf(' ', inputField.getText().toString().substring(0, selection));
            try{
                StringUtil.separation(inputField);
                int newNumberSpaces = StringUtil.getNumberOf(' ', inputField.getText().toString().substring(0, selection));
                inputField.setSelection(selection + 1 - oldNumberSpaces + newNumberSpaces);
                StringUtil.separation(inputField);
                inputField.setSelection(selection + 1 - oldNumberSpaces + newNumberSpaces);
            } catch (Exception ex){
                inputField.setSelection(selection);
            }
            return;
        }
    }
}
