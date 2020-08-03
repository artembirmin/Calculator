package com.example.calculator;

import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class StringUtil {

    private static  ArrayList<Character> numeral= new ArrayList<Character>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));
    private static final String TAG = "qwerty" ;

    public static void separation(EditText inputField) {
        if(inputField.getText().length() == 0) return;
        int startNumber;
        int endNumber;
        int selection = inputField.getSelectionStart() ;
        int rightSelectionPositions = inputField.getText().length() - selection;
        StringBuilder input = new StringBuilder(inputField.getText());
        if(selection != 0 && !numeral.contains(input.charAt(selection - 1)) && input.charAt(selection-1) != ' ') return;
        input.insert(0,'x');
        input.insert(input.length(), 'x');
        int i;
        for(i = selection; (input.charAt(i) >= '0' && input.charAt(i) <= '9') || input.charAt(i) == ' '; i--) {
        }
        if(input.charAt(i) == ',') return;
        startNumber = ++i;
        for(i = selection;(input.charAt(i) >= '0' && input.charAt(i) <= '9') || input.charAt(i) == ' '; i++) {
        }
        endNumber = i;
        StringBuilder number = new StringBuilder(input.substring(startNumber,endNumber).replaceAll("\\s","")).reverse();
        i = 0;
        for(int j = 0; j < number.length(); j++){
            if( (i+1)%3 == 0){
                number.insert(j+1, ' ');
                j++;
                i = 0;
            }
            else i++;
        }
        if(number.charAt(number.length()-1) == ' ')
            number.deleteCharAt(number.length()-1);
        number.reverse();
        input.deleteCharAt(0);
        input.deleteCharAt(input.length()-1);
        input.replace(startNumber-1,endNumber-1, number.toString());
        inputField.setText(input);
        inputField.setSelection(inputField.getText().length() - rightSelectionPositions);
    }


    public static void append(String operation, EditText inputField){
        StringBuilder input = new StringBuilder(inputField.getText());
        int selection = inputField.getSelectionStart();
        if(selection != input.length() && input.charAt(selection) == ' ') //Если знак ставится после пробела, сводим к случаю постановки перед пробелом. Он решается автоматом
            selection++;
        if(input.length() == 0 && operation.equals("−")) {
            input.insert(selection, operation);
            inputField.setText(input.toString());
            inputField.setSelection(selection + 1);
            return;
        }
        else if(selection == 0){
            input.insert(selection, operation);
            inputField.setText(input.toString());
            inputField.setSelection(selection + 1);
            return;
        }
        else if(input.charAt(selection - 1) != '−' && !numeral.contains(input.charAt(selection - 1)) && operation.equals("−")){
            input.insert(selection, operation);
            inputField.setText(input.toString());
            inputField.setSelection(selection + 1);
            StringUtil.serapationForOperatoin(inputField);
            return;
        } else if(input.charAt(selection - 1) == '−')
            return;
        input.insert(selection, operation);
        inputField.setText(input.toString());
        inputField.setSelection(selection + 1);
        StringUtil.serapationForOperatoin(inputField);
    }

    public static void serapationForOperatoin(EditText inputField) {
        StringBuilder input = new StringBuilder(inputField.getText());
        int selection = inputField.getSelectionStart();
        //if(input.charAt(selection - 2) ==  || input.charAt(selection))
        int rightSelectionPositions = inputField.getText().length() - selection;
        inputField.setSelection(selection-1);
        if(input.length() == 1)
            return;
        StringUtil.separation(inputField);
        inputField.setSelection(inputField.getText().length() - rightSelectionPositions);
    }

    public static void checkTextSize(EditText inputField){
        if(inputField.length() <=11)
            inputField.setTextSize(50);
        else if( inputField.length() < 14 && inputField.length() >= 12)
            inputField.setTextSize(48);
        else if(inputField.length() < 17 && inputField.length() >=14)
            inputField.setTextSize(40);
        else if(inputField.length() < 19 && inputField.length() >= 17)
            inputField.setTextSize(34);
        else inputField.setTextSize(30);
    }
}
