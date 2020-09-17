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
            removeEdgeSymbol(true);
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
        if ((selection == input.length() || selection == input.length() - 1) && input.charAt(input.length() - 1) == COMMA && input.charAt(input.length() - 2) == '0') {
            removeFractionWithLeftNumber(selection);
            return;
        }
        if(selection == FIRST_POSITION && input.length() > 1 &&  StringUtil.isArithmeticOperation(input.charAt(selection))){
            removeEdgeSymbol(false);
            return;
        }
        if (selection >= 1 && selection != input.length()
                && ((input.charAt(selection) == COMMA
                && input.charAt(selection - 1) == '0'
                && (selection - 1 == 0
                || !StringUtil.isNumeral(input.charAt(selection - 2))))
            ||(input.charAt(selection - 1) == COMMA
                && input.charAt(selection - 2) == '0'
                && (selection - 2 == 0 || !StringUtil.isNumeral(input.charAt(selection - 3)))))) {
            removeFractionWithLeftNumber(selection);
            return;
        }
        if (selection == FIRST_POSITION) {
            removeEdgeSymbol(true);
            return;
        }
        if (input.charAt(selection - 1) == COMMA && selection != input.length()) {
            removeCommaInMiddle(selection);
            return;
        }
        defaultBackspace(selection);
    }

    private void removeEdgeSymbol(boolean moveSelection) {
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
        if (StringUtil.isFraction(inputField))
            return;
        if(selection == ZERO_POSITION && input.length() > 1 && !StringUtil.isNumeral(input.charAt(selection))) {
            createFractionOnEdgeOfNumber(selection);
            return;
        }
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

    public void insertZero() {
        StringBuilder input = new StringBuilder(inputField.getText());
        ComplexOperations complexOperations = new ComplexOperations(inputField, input);
        int selection = inputField.getSelectionStart();
        //TODO
//        if(input.length() == 0){
//            complexOperations.onClickFraction();
//            return;
//        }
//        if(selection == 0){
////            if(StringUtil.isArithmeticOperation(input.charAt(selection)) || input.charAt(selection) == '('){
////                defaultInsertZero(selection);
////                return;
////            }
////            if(input.length() > 1 && input.charAt(selection + 1) != ',')
////                onClickFraction();
//            if(onClickFraction())
//                return;
//        }
//        if(selection == input.length()){
//            if(StringUtil.isArithmeticOperation(input.charAt(selection - 1))){
//                createFractionOnEdgeOfNumber(selection);
//                return;
//            }
//            defaultInsertZero(selection);
//            return;
//        }
//        if((StringUtil.isArithmeticOperation(input.charAt(selection - 1)) || input.charAt(selection) == ')')
//                && StringUtil.isNumeral(input.charAt(selection))){
//            createFractionOnEdgeOfNumber(selection);
//            return;
//        }
//        try{
//            int commaIndex = input.substring(ZERO_POSITION, selection + 2).indexOf(",");
//            if(commaIndex > selection - 2  && input.charAt(commaIndex - 1) == '0'
//                    && (commaIndex == 1 || !StringUtil.isNumeral(input.charAt(commaIndex - 2))))
//                return;
//        } catch (Exception e) {
//            int commaIndex = input.substring(ZERO_POSITION, selection + 1).indexOf(",");
//            if(commaIndex > selection - 2 && input.charAt(commaIndex - 1) == '0'
//                    && (commaIndex == 1 || !StringUtil.isNumeral(input.charAt(commaIndex - 1))))
//                return;
//        }


        if(canCreateFractionOnEdge(selection)){
            createFractionOnEdgeOfNumber(selection);
            return;
        }
        defaultInsertZero(selection);
    }

    private boolean canCreateFractionOnEdge(int selection) {
        if(input.length() == 0)
            return  true;
        if (StringUtil.isFraction(inputField)
                || (selection == ZERO_POSITION && input.length() > 2 && !StringUtil.isNumeral(input.charAt(selection)))) {
            return false;
        }
        try{
            if(StringUtil.isArithmeticOperation(input.charAt(selection - 1)) && StringUtil.isArithmeticOperation(input.charAt(selection)))
                return false;
        } catch (Exception ignored){
        }
        if(selection == ZERO_POSITION && input.length() > 1 && !StringUtil.isNumeral(input.charAt(selection))){
            return false;
        }
        if (input.charAt(selection - 1) != ',' &&(input.length() == 0 || (selection == ZERO_POSITION && StringUtil.isNumeral(input.charAt(selection))))
                || (!StringUtil.isNumeral(input.charAt(selection - 1)) && input.charAt(selection - 1) != ' ')) {
            return true;
        }
        if (((selection <= input.length() && StringUtil.isNumeral(input.charAt(selection - 1))))) {
            return false;
        }
            return true;
    }

    private void defaultInsertZero(int selection) {
        input.insert(selection, '0');
        inputField.setText(input);
        inputField.setSelection(selection + 1);
        StringUtil.separation(inputField);
    }
}
