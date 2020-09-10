package com.example.calculatormain;

import android.widget.EditText;

public class ComplexOperations {

    private EditText inputField;
    private StringBuilder input;
    private final int ZERO_POSITION = 0;
    private final int FIRST_POSITION = 1;
    private final int SECOND_POSITION = 2;
    private final char COMMA = ',';

    /*
        Индексу курсора соответствует символ справа от него
    */

    ComplexOperations(EditText inputField, StringBuilder input){
        this.inputField = inputField;
        this.input = input;
    }

    public void onClickBackspace() {
        StringBuilder input = new StringBuilder(inputField.getText());
        int selection = inputField.getSelectionStart();
        if (input.length() == 1) {
            removeEdgeSymbol(inputField, input, true);
            return;
        }
        if (selection == ZERO_POSITION) {
            inputField.setSelection(input.length());
            return;
        }
        if (input.charAt(selection - 1) == ' ') {
            removeAfterSpaceLeft(selection);
            return;
        }
        if ((selection == FIRST_POSITION || selection == SECOND_POSITION) && input.charAt(1) == COMMA && StringUtil.isNumeral(input.charAt(ZERO_POSITION))) {
            removeFractionWithLeftNumber(selection);
            return;
        }
        if(selection == FIRST_POSITION && input.length() > 1 &&  StringUtil.isArithmeticOperation(input.charAt(selection))){
            removeEdgeSymbol(inputField,input, false);
            return;
        }
        if (selection == FIRST_POSITION) {
            removeEdgeSymbol(inputField, input, true);
            return;
        }
        if (selection > SECOND_POSITION && selection != input.length()
                && ((input.charAt(selection - 1) == COMMA && StringUtil.isNumeral(input.charAt(selection - 2))  && !StringUtil.isNumeral(input.charAt(selection - 3)))
                || (input.charAt(selection) == COMMA && StringUtil.isNumeral(input.charAt(selection - 1)) && !StringUtil.isNumeral(input.charAt(selection - 2))))) {
            removeFractionWithLeftNumber(selection);
            return;
        }
        if (input.charAt(selection - 1) == COMMA && selection != input.length()) {
            removeCommaInMiddle(selection);
            return;
        }
        defaultBackspace(selection);
    }

    private void removeEdgeSymbol(EditText inputField, StringBuilder input, boolean moveSelection) {
        input.deleteCharAt(ZERO_POSITION);
        inputField.setText(input.toString());
        if(moveSelection)
            inputField.setSelection(inputField.length());
        else
            inputField.setSelection(ZERO_POSITION);
    }

    private void removeAfterSpaceLeft(int selection) {
        input.deleteCharAt(selection - 2);
        inputField.setText(input.toString());
        inputField.setSelection(selection - 1);
        StringUtil.separation(inputField);
    }

    private void removeFractionWithLeftNumber(int selection) {
        if (StringUtil.isNumeral(input.charAt(selection - 1)))
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

    private void removeCommaInMiddle(int selection) {
        int oldNumberSpaces = StringUtil.getCountOf(' ', inputField.getText().toString().substring(ZERO_POSITION, selection));
        defaultBackspace(selection);
        int newNumberSpaces = StringUtil.getCountOf(' ', inputField.getText().toString().substring(ZERO_POSITION, selection));
        inputField.setSelection(selection - 1 - oldNumberSpaces + newNumberSpaces);
    }

    private void defaultBackspace(int selection) {
        input.deleteCharAt(selection - 1);
        inputField.setText(input.toString());
        inputField.setSelection(selection - 1);
        try {
            if(input.charAt(selection - 1) == ' '){
                input.deleteCharAt(selection - 1);
                inputField.setText(input.toString());
                inputField.setSelection(selection - 1);
            }
        } catch (Exception ignored){
        }
        StringUtil.separation(inputField);
    }

    public void onClickFraction() {
        StringBuilder input = new StringBuilder(inputField.getText());
        int selection = inputField.getSelectionStart();
        if (StringUtil.isFraction(inputField)
                || (selection == ZERO_POSITION && input.length() > 2 && !StringUtil.isNumeral(input.charAt(selection))))
            return;
        if ((input.length() == 0 || (selection == ZERO_POSITION && StringUtil.isNumeral(input.charAt(selection))))
                || (!StringUtil.isNumeral(input.charAt(selection - 1)) && input.charAt(selection - 1) != ' ')) {
            createFractionOnEdgeOfNumber(selection);
            return;
        }
        if (((selection <= input.length() && StringUtil.isNumeral(input.charAt(selection - 1))))) {
            createFraction(selection);
        }
    }

    private void createFractionOnEdgeOfNumber(int selection) {
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

    private void createFraction(int selection) {
        if (input.charAt(selection - 1) == ' ') {
            inputField.setSelection(--selection);
        }
        input.insert(selection, ",");
        inputField.setText(input.toString());
        inputField.setSelection(selection);
        int oldNumberSpaces = StringUtil.getCountOf(' ', inputField.getText().toString().substring(ZERO_POSITION, selection));
        try {
            StringUtil.separation(inputField);
            int newNumberSpaces = StringUtil.getCountOf(' ', inputField.getText().toString().substring(ZERO_POSITION, selection));
            inputField.setSelection(selection + 3);
            StringUtil.separation(inputField);
            inputField.setSelection(selection + 1 - oldNumberSpaces + newNumberSpaces);
        } catch (Exception ex) {
            inputField.setSelection(selection + 1);
        }
    }
}
