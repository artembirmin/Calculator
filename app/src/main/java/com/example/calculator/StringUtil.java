package com.example.calculator;

import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class StringUtil {

    private static final String TAG = "qwerty";
    private static ArrayList<Character> numeral = new ArrayList<Character>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));

    public static int getNumberOf(char character, String thisString){
        int amt = 0;
        for(char ch : thisString.toCharArray()) {
             if(ch == character)
                 amt++;
        }
        return amt;
    }

    public static void separation(EditText inputField) {
        if (inputField.getText().length() == 0) return;
        StringBuilder input = new StringBuilder(inputField.getText());
        int selection = inputField.getSelectionStart();
        int rightSelectionPositions = inputField.getText().length() - selection; //запомнил позицию курсора
        if (selection != 0 && !numeral.contains(input.charAt(selection - 1)) && input.charAt(selection - 1) != ',' && input.charAt(selection - 1) != ' ')
            return;
        //Для случая, когда сепарация вызывается между знаками операций
        input.insert(0, 'x');
        input.insert(input.length(), 'x');
        int i;
        for (i = selection; (input.charAt(i) >= '0' && input.charAt(i) <= '9') || input.charAt(i) == ' '; i--) {
        }
        int startNumber = ++i;
        if (selection == 0 || input.charAt(selection) == ',') //!!!
            selection++;
        for (i = selection; (input.charAt(i) >= '0' && input.charAt(i) <= '9') || input.charAt(i) == ' '; i++) {
        }
        int endNumber = i; // -- не делается, ибо при выделении подстроки номер конца не включается (?)
        if (input.charAt(startNumber - 1) == ',') {
            StringBuilder number = new StringBuilder(input.substring(startNumber, endNumber).replaceAll("\\s", ""));
            input.deleteCharAt(0);
            input.deleteCharAt(input.length() - 1);
            input.replace(startNumber - 1, endNumber - 1, number.toString());
            inputField.setText(input);
            inputField.setSelection(inputField.getText().length() - rightSelectionPositions);
            return;
        }
        StringBuilder number = new StringBuilder(input.substring(startNumber, endNumber).replaceAll("\\s", "")).reverse();
        //убрали пробелы в нужном промежутке и перевернули
        i = 0;
        for (int j = 0; j < number.length(); j++) {
            if ((i + 1) % 3 == 0) {
                number.insert(j + 1, ' ');
                j++;
                i = 0;
            } else i++;
        }
        if (number.charAt(number.length() - 1) == ' ')
            number.deleteCharAt(number.length() - 1);
        number.reverse();
        input.deleteCharAt(0);
        input.deleteCharAt(input.length() - 1);
        input.replace(startNumber - 1, endNumber - 1, number.toString());
        inputField.setText(input);
        try {
            inputField.setSelection(inputField.getText().length() - rightSelectionPositions);
            if(inputField.getSelectionStart() == 0)
                inputField.setSelection(input.length());
        } catch (Exception ex) {
            inputField.setSelection(input.length());
        }

    }

    public static boolean isFraction(EditText inputField) {
        StringBuilder input = new StringBuilder(inputField.getText());
        int selection = inputField.getSelectionStart();
        input.insert(0, 'x');
        input.insert(input.length(), 'x');
        int i;
        for (i = selection; (input.charAt(i) >= '0' && input.charAt(i) <= '9') || input.charAt(i) == ' '; i--) {
        }
        int startNumber = ++i;
        if (selection == 0) //!!!
            selection++;
        for (i = selection; (input.charAt(i) >= '0' && input.charAt(i) <= '9') || input.charAt(i) == ' '; i++) {
        }
        int endNumber = i;
        Log.i(TAG, String.valueOf(input.charAt(startNumber - 1) == ',' || input.charAt(endNumber) == ','));
        return input.charAt(startNumber - 1) == ',' || input.charAt(endNumber) == ',';
    }

    public static void appendArithmeticSign(String operation, EditText inputField) {
        StringBuilder input = new StringBuilder(inputField.getText());
        int selection = inputField.getSelectionStart();
        if (selection != input.length() && input.charAt(selection) == ' ')
            //Если знак ставится после пробела, сводим к случаю постановки перед пробелом. Он решается автоматом
            selection++;
        if(selection == 0 && !operation.equals("−"))
            return;
        if (input.length() == 0 && operation.equals("−")) {
            //минус можно ставить в пустоту
            input.insert(selection, operation);
            inputField.setText(input.toString());
            inputField.setSelection(selection + 1);
            return;
        } else if (selection == 0 && operation.equals("−")) {
                input.insert(selection, operation);
                inputField.setText(input.toString());
                inputField.setSelection(selection + 1);
                return;
        }  else if (input.length() == 0 || ((!operation.equals("−")) && !numeral.contains(input.charAt(selection - 1)) && input.charAt(selection - 1) != ' ') || input.charAt(selection - 1) == ',')
            //не минус нельзя ставть в пустоту и не минус после знака арифметической операции
            //последнее условние для несрабатывания на ситуации 1 +234. Условие не считает пробел за число и return.
            return;
         else if (selection < input.length() && !numeral.contains(input.charAt(selection)))
            //не допускает ситуацию, когда знак операци можно было поствить 55здесь+464
            return;
        else if (input.charAt(selection - 1) != '−' && numeral.contains(input.charAt(selection - 1)) && operation.equals("−")) {
            //позволяет поставить минус после знака арифметической операции
            input.insert(selection, operation);
            inputField.setText(input.toString());
            inputField.setSelection(selection + 1);
            StringUtil.serapationForArithmeticOperatoin(inputField);
            return;
        } else if (input.charAt(selection - 1) == '−')
            //два минуса нельзя
            return;
        input.insert(selection, operation);
        inputField.setText(input.toString());
        inputField.setSelection(selection + 1);
        StringUtil.serapationForArithmeticOperatoin(inputField);
    }

    public static void serapationForArithmeticOperatoin(EditText inputField) {
        //Арифметическая операция может разбить какое-то число правая его часть будет нетронута, а левая отправится на сепарацию
        StringBuilder input = new StringBuilder(inputField.getText());
        int selection = inputField.getSelectionStart();
        //if(input.charAt(selection - 2) ==  || input.charAt(selection))
        int rightSelectionPositions = inputField.getText().length() - selection;
        inputField.setSelection(selection - 1);
        if (input.length() == 1)
            return;
        StringUtil.separation(inputField);
        inputField.setSelection(inputField.getText().length() - rightSelectionPositions);
    }

    public static void checkTextSize(EditText inputField) {
        if (inputField.length() < 13
        ) {
            inputField.setTextSize(50);
        }
        else if (inputField.length() < 14 && inputField.length() >= 12) {
            inputField.setTextSize(47);
        }
        else if (inputField.length() < 17 && inputField.length() >= 14)
            inputField.setTextSize(38);
        else if (inputField.length() < 19 && inputField.length() >= 17)
            inputField.setTextSize(34);
        else inputField.setTextSize(30);
    }
}
