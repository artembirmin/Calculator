package com.example.calculatormain;

import android.widget.EditText;

public class StringUtil {

    private static final int ZERO_POSITION = 0;
    private static final int FIRST_POSITION = 1;
    private static final int SECOND_POSITION = 2;
    private static final char MINUS = '−';
    private static final char PLUS = '+';

    public static void separation(EditText inputField) {
        if (inputField.getText().length() == 0) return;
        int selection = inputField.getSelectionStart();
        int rightSelectionPositions = inputField.getText().length() - selection; //запомнил позицию курсора
        StringBuilder input = new StringBuilder(inputField.getText());
        if (selection != 0 && !StringUtil.isNumeral(input.charAt(selection - 1)) && input.charAt(selection - 1) != ' ')
            return;
        //Для случая, когда сепарация вызывается между знаками операций
        input.insert(ZERO_POSITION, 'x');
        input.insert(input.length(), 'x');
        int i;
        i = selection;
        while ((input.charAt(i) >= '0' && input.charAt(i) <= '9') || input.charAt(i) == ' ') {
            i--;
        }
        int startNumber = ++i;
        if (selection == ZERO_POSITION || input.charAt(selection) == ',' || input.charAt(selection) == '.')
            selection++;
        i = selection;
        while ((input.charAt(i) >= '0' && input.charAt(i) <= '9') || input.charAt(i) == ' ') {
            i++;
        }
        int endNumber = i;
        if (input.charAt(startNumber - 1) == ',' || input.charAt(startNumber - 1) == '.') {
            fractionalPart(inputField, rightSelectionPositions, input, startNumber, endNumber);
            return;
        }
        integerPart(inputField, rightSelectionPositions, input, startNumber, endNumber);
    }

    private static void fractionalPart(EditText inputField, int rightSelectionPositions, StringBuilder input, int startNumber, int endNumber) {
        StringBuilder number = new StringBuilder(input.substring(startNumber, endNumber).replaceAll("\\s", ""));
        input.deleteCharAt(0);
        input.deleteCharAt(input.length() - 1);
        input.replace(startNumber - 1, endNumber - 1, number.toString());
        inputField.setText(input);
        inputField.setSelection(inputField.getText().length() - rightSelectionPositions);
    }

    private static void integerPart(EditText inputField, int rightSelectionPositions, StringBuilder input, int startNumber, int endNumber) {
        int i;
        StringBuilder number = new StringBuilder(input.substring(startNumber, endNumber).replaceAll("\\s", "")).reverse();
        //убрали пробелы в нужном промежутке и перевернули
        i = 0;
        for (int j = ZERO_POSITION; j < number.length(); j++) {
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
            if (inputField.getSelectionStart() == ZERO_POSITION)
                inputField.setSelection(input.length());
        } catch (Exception ex) {
            inputField.setSelection(input.length());
        }
    }

    public static String format(String expression){
        return expression.replace('.',',').replace('-',MINUS);
    }

    public static int getCountOf(char character, String thisString) {
        int amt = 0;
        for (char ch : thisString.toCharArray()) {
            if (ch == character)
                amt++;
        }
        return amt;
    }

    public static boolean isNumeral(char item) {
        return item >= '0' && item <= '9' || item == ' ';
    }

    public static boolean isPartOfNumber(char item) {
        return item >= '0' && item <= '9' || item == ' ' || item == ',' || item == '(' || item == ')';
    }

    public static boolean isFraction(EditText inputField) {
        StringBuilder input = new StringBuilder(inputField.getText());
        if(input.length() == 0)
            return false;
        int selection = inputField.getSelectionStart();
        input.insert(ZERO_POSITION, 'x');
        input.insert(input.length(), 'x');
        int i;
        i = selection;
        while ((input.charAt(i) >= '0' && input.charAt(i) <= '9') || input.charAt(i) == ' ') {
            i--;
        }
        int startNumber = ++i;
        if (selection == ZERO_POSITION)
            selection++;
        i = ++selection;
        while ((input.charAt(i) >= '0' && input.charAt(i) <= '9') || input.charAt(i) == ' ') {
            i++;
        }
        int endNumber = i;
        return input.charAt(startNumber - 1) == ',' || input.charAt(endNumber) == ',';
    }

    public static boolean isArithmeticOperation(char item) {
        return item == '+' || item == '−' || item == '×' || item == '÷' || item == '^';
    }

    public static boolean isNumberOnly(String str){
        int i;
        for( i = 0; i < str.length(); i++){
            if (!isPartOfNumber(str.charAt(i))){
                i = -1;
                break;
            }
        }
        return i == str.length();
    }

    public static void insertArithmeticSign(String operation, EditText inputField) {
        StringBuilder input = new StringBuilder(inputField.getText());
        ArithmeticOperations arithmeticOperations = new ArithmeticOperations(inputField, input);
        int selection = inputField.getSelectionStart();
        if (selection != input.length() && input.charAt(selection) == ' ')
            //Если знак ставится после пробела, сводим к случаю постановки перед пробелом. Он решается автоматом
            selection++;
        if(operation.equals(String.valueOf(MINUS))) {
            arithmeticOperations.insertMinus(selection);
            return;
        }
        if(operation.equals(String.valueOf(PLUS))) {
            arithmeticOperations.insertSign(operation, selection);
            return;
        }
        if(StringUtil.isArithmeticOperation(operation.charAt(ZERO_POSITION)))
            arithmeticOperations.insertSign(operation, selection);
    }

    public static void separationForArithmeticOperation(EditText inputField) {
        //Арифметическая операция может разбить какое-то число правая его часть будет нетронута, а левая отправится на сепарацию
        StringBuilder input = new StringBuilder(inputField.getText());
        int selection = inputField.getSelectionStart();
        int rightSelectionPositions = inputField.getText().length() - selection;
        inputField.setSelection(selection - 1);
        if (input.length() == 1)
            return;
        StringUtil.separation(inputField);
        try {
            inputField.setSelection(selection + 1);
            StringUtil.separation(inputField);
        } catch (Exception ignored) {
        }
        inputField.setSelection(inputField.getText().length() - rightSelectionPositions);
    }

    public static void checkTextSize(EditText inputField) {
        int maxLengthValue = 19 + StringUtil.getCountOf(',', inputField.getText().toString()) / 2;
        int minSizeValue = 30;
        if (inputField.length() >= maxLengthValue) {
            inputField.setTextSize(minSizeValue);
            return;
        }
        if (inputField.length() >= maxLengthValue - 4) {
            inputField.setTextSize(minSizeValue + 4);
            return;
        }
        if (inputField.length() >= maxLengthValue - 6) {
            inputField.setTextSize(minSizeValue + 10);
            return;
        }
        if (inputField.length() >= maxLengthValue - 8) {
            inputField.setTextSize(minSizeValue + 16);
            return;
        }
        inputField.setTextSize(minSizeValue + 24);
    }

}
