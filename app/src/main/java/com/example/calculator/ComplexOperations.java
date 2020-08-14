package com.example.calculator;

import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;

public class ComplexOperations {

    private static final int ZERO_POSITION = 0;
    private static final int FIRST_POSITION = 1;
    private static final int SECOND_POSITION = 2;
    private static ArrayList<Character> numeral = new ArrayList<>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));

    /*
        Индексу курсора соответствует символ справа от него
    */

    public static void onClickMakeBackspace(EditText inputField) {
        StringBuilder input = new StringBuilder(inputField.getText());
        int selection = inputField.getSelectionStart();
        if (input.length() == 1) {
            RemoveEdgeSymbol(inputField, input);
            return;
        }
        if (selection == ZERO_POSITION) {
            inputField.setSelection(input.length());
            return;
        }
        if (input.charAt(selection - 1) == ' ') {
            RemoveAfterSpaceLeft(inputField, input, selection);
            return;
        }
        if ((selection == FIRST_POSITION || selection == SECOND_POSITION) && input.charAt(1) == ',' && input.charAt(ZERO_POSITION) == '0') {
            RemoveFractionLessThenOne(inputField, input, selection);
            return;
        }
        if (selection == FIRST_POSITION) {
            RemoveEdgeSymbol(inputField, input);
            return;
        }
        if (selection > SECOND_POSITION && selection != input.length()
                && ((input.charAt(selection - 1) == ',' && input.charAt(selection - 2) == '0' && !numeral.contains(input.charAt(selection - 3)))
                || (input.charAt(selection) == ',' && input.charAt(selection - 1) == '0' && !numeral.contains(input.charAt(selection - 2))))) {
            RemoveFractionLessThenOne(inputField, input, selection);
            return;
        }
        if (input.charAt(selection - 1) == ',' && selection != input.length()) {
            RemoveCommaInMiddle(inputField, input, selection);
            return;
        }
        defaultBackspace(inputField, input, selection);
    }

    private static void RemoveEdgeSymbol(EditText inputField, StringBuilder input) {
        input.deleteCharAt(ZERO_POSITION);
        inputField.setText(input.toString());
        inputField.setSelection(inputField.length());
    }

    private static void RemoveAfterSpaceLeft(EditText inputField, StringBuilder input, int selection) {
        input.deleteCharAt(selection - 2);
        inputField.setText(input.toString());
        inputField.setSelection(selection - 1);
        StringUtil.separation(inputField);
    }

    private static void RemoveFractionLessThenOne(EditText inputField, StringBuilder input, int selection) {
        if (input.charAt(selection - 1) == '0')
            selection++;
        input.delete(selection - 2, selection);
        inputField.setText(input.toString());
        try {
            inputField.setSelection(selection - 1);
        } catch (Exception ex) {
            inputField.setSelection(inputField.length());
        }
        StringUtil.separation(inputField);
        if (selection - 2 == ZERO_POSITION) {
            inputField.setSelection(inputField.length());
            return;
        }
        try {
            inputField.setSelection(selection - 2);
        } catch (Exception ex) {
            inputField.setSelection(inputField.length());
        }
    }

    private static void RemoveCommaInMiddle(EditText inputField, StringBuilder input, int selection) {
        int oldNumberSpaces = StringUtil.getNumberOf(' ', inputField.getText().toString().substring(0, selection));
        defaultBackspace(inputField, input, selection);
        int newNumberSpaces = StringUtil.getNumberOf(' ', inputField.getText().toString().substring(0, selection));
        inputField.setSelection(selection - 1 - oldNumberSpaces + newNumberSpaces);
    }

    private static void defaultBackspace(EditText inputField, StringBuilder input, int selection) {
        input.deleteCharAt(selection - 1);
        inputField.setText(input.toString());
        inputField.setSelection(selection - 1);
        StringUtil.separation(inputField);
    }

    public static void onClickCreateFraction(EditText inputField) {
        StringBuilder input = new StringBuilder(inputField.getText());
        int selection = inputField.getSelectionStart();
        if (StringUtil.isFraction(inputField) || (selection == ZERO_POSITION && input.length() > 2 && !numeral.contains(input.charAt(selection))))
            return;
        if ((input.length() == 0 || (selection == ZERO_POSITION && numeral.contains(input.charAt(selection))))
                || (!numeral.contains(input.charAt(selection - 1)) && input.charAt(selection - 1) != ' ')) {
            createFractionOnEdgeOfNumber(inputField, input, selection);
            return;
        }
        if (((selection == input.length() && numeral.contains(input.charAt(selection - 1))) ||
                (selection < input.length() && numeral.contains(input.charAt(selection - 1)) || input.charAt(selection - 1) == ' '))) {
            createFraction(inputField, input, selection);
        }
    }

    private static void createFractionOnEdgeOfNumber(EditText inputField, StringBuilder input, int selection) {
        input.insert(selection, "0,");
        inputField.setText(input.toString());
        selection += 2;
        try {
            inputField.setSelection(selection + 1);
            StringUtil.separation(inputField);
            inputField.setSelection(selection);
        } catch (Exception ex) {
            inputField.setSelection(selection);
        }
    }

    private static void createFraction(EditText inputField, StringBuilder input, int selection) {
        if (input.charAt(selection - 1) == ' ') {
            inputField.setSelection(--selection);
        }
        input.insert(selection, ",");
        inputField.setText(input.toString());
        inputField.setSelection(selection);
        int oldNumberSpaces = StringUtil.getNumberOf(' ', inputField.getText().toString().substring(ZERO_POSITION, selection));
        try {
            StringUtil.separation(inputField);
            int newNumberSpaces = StringUtil.getNumberOf(' ', inputField.getText().toString().substring(ZERO_POSITION, selection));
            inputField.setSelection(selection + 1 - oldNumberSpaces + newNumberSpaces);
            StringUtil.separation(inputField);
            inputField.setSelection(selection + 1 - oldNumberSpaces + newNumberSpaces);
        } catch (Exception ex) {
            inputField.setSelection(selection);
        }
    }
}
