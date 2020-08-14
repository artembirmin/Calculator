package com.example.calculator;

import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class StringUtil {

    private static final int ZERO_POSITION = 0;
    private static ArrayList<Character> numeral = new ArrayList<>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));

    public static int getNumberOf(char character, String thisString) {
        int amt = 0;
        for (char ch : thisString.toCharArray()) {
            if (ch == character)
                amt++;
        }
        return amt;
    }

    public static void separation(EditText inputField) {
        if (inputField.getText().length() == 0) return;
        int selection = inputField.getSelectionStart();
        int rightSelectionPositions = inputField.getText().length() - selection; //запомнил позицию курсора
        StringBuilder input = new StringBuilder(inputField.getText());
        if (selection != 0 && !numeral.contains(input.charAt(selection - 1)) && input.charAt(selection - 1) != ',' && input.charAt(selection - 1) != ' ')
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
        if (selection == ZERO_POSITION || input.charAt(selection) == ',')
            selection++;
        i = selection;
        while ((input.charAt(i) >= '0' && input.charAt(i) <= '9') || input.charAt(i) == ' ') {
            i++;
        }
        int endNumber = i;
        if (input.charAt(startNumber - 1) == ',') {
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

    public static boolean isFraction(EditText inputField) {
        StringBuilder input = new StringBuilder(inputField.getText());
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
        i = selection;
        while ((input.charAt(i) >= '0' && input.charAt(i) <= '9') || input.charAt(i) == ' ') {
            i++;
        }
        int endNumber = i;
        return input.charAt(startNumber - 1) == ',' || input.charAt(endNumber) == ',';
    }

    public static void insertArithmeticSign(String operation, EditText inputField) {
        StringBuilder input = new StringBuilder(inputField.getText());
        int selection = inputField.getSelectionStart();
        if (selection != input.length() && input.charAt(selection) == ' ')
            //Если знак ставится после пробела, сводим к случаю постановки перед пробелом. Он решается автоматом
            selection++;
        if (selection == ZERO_POSITION && !operation.equals("−"))
            return;
        if (selection == ZERO_POSITION) {
            addMinusToStart(operation, inputField, input, selection);
            return;
        }
        if (input.length() == 0
                || ((!operation.equals("−")) && !numeral.contains(input.charAt(selection - 1)) && input.charAt(selection - 1) != ' ')
                || input.charAt(selection - 1) == ',')
            //не минус нельзя ставть в пустоту и не минус после знака арифметической операции
            //последнее условние для несрабатывания на ситуации 1 +234. Условие не считает пробел за число и return.
            return;
        if (selection < input.length() && !numeral.contains(input.charAt(selection)))
            //не допускает ситуацию, когда знак операци можно было поствить 55здесь+464
            return;
        if (input.charAt(selection - 1) != '−' && numeral.contains(input.charAt(selection - 1)) && operation.equals("−")) {
            //позволяет поставить минус после знака арифметической операции
            defaultInsertArithmeticSign(operation, inputField, input, selection);
            return;
        }
        if (input.charAt(selection - 1) == '−')
            //два минуса нельзя
            return;
        defaultInsertArithmeticSign(operation, inputField, input, selection);
    }

    private static void addMinusToStart(String operation, EditText inputField, StringBuilder input, int selection) {
        input.insert(selection, operation);
        inputField.setText(input.toString());
        inputField.setSelection(selection + 1);
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
        inputField.setSelection(inputField.getText().length() - rightSelectionPositions);
    }

    private static void defaultInsertArithmeticSign(String operation, EditText inputField, StringBuilder input, int selection) {
        input.insert(selection, operation);
        inputField.setText(input.toString());
        inputField.setSelection(selection + 1);
        StringUtil.separationForArithmeticOperation(inputField);
    }

    public static void checkTextSize(EditText inputField) {
        int maxLengthValue = 19 + StringUtil.getNumberOf(',', inputField.getText().toString()) / 2;
        int minSizeValue = 30;
        if (inputField.length() >= maxLengthValue) {
            inputField.setTextSize(minSizeValue);
            return;
        }
        if (inputField.length() >= maxLengthValue - 3) {
            inputField.setTextSize(minSizeValue + 4);
            return;
        }
        if (inputField.length() >= maxLengthValue - 5) {
            inputField.setTextSize(minSizeValue + 10);
            return;
        }
        if (inputField.length() >= maxLengthValue - 7) {
            inputField.setTextSize(minSizeValue + 16);
            return;
        }
        inputField.setTextSize(minSizeValue + 24);
    }
}
