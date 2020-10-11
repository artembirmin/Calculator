package com.example.util;

import android.widget.EditText;

import com.example.util.StringUtil;

public class ArithmeticOperations {

    private EditText inputField;
    private StringBuilder input;
    private final char MINUS = '−';
    private final char COMMA = ',';

    public ArithmeticOperations(EditText inputField, StringBuilder input){
        this.inputField = inputField;
        this.input = input;
    }

    protected void insertMinus(int selection) {
        if(input.length() == 0){
            addMinusToStart(selection);
            return;
        }
        if(selection == 0 && !StringUtil.isArithmeticOperation(input.charAt(selection))){
            addMinusToStart(selection);
            return;
        }
        if(selection == 0 && StringUtil.isArithmeticOperation(input.charAt(selection)))
            return;
        if(input.charAt(selection - 1) == COMMA || (selection != input.length() && input.charAt(selection) == COMMA))
            return;
        if(input.length() == selection && input.charAt(selection - 1) != MINUS  && input.charAt(selection - 1) != '+'){
            defaultInsertArithmeticSign("−", selection);
            return;
        }
        if(input.charAt(selection - 1) == '+' || (selection != input.length() && input.charAt(selection) == '+')){
            replacingOperationSigns("−", selection);
            return;
        }
        if(input.charAt(selection - 1) == MINUS || (selection != input.length() && input.charAt(selection) == MINUS)) {
            return;
        }
        if((selection != input.length() && StringUtil.isArithmeticOperation(input.charAt(selection))))
            return;
        defaultInsertArithmeticSign("−", selection);
    }

    protected void insertSign(String operation, int selection) {
        if(input.length() == 0)
            return;
        if(selection == 0)
            return;
        if(selection != input.length() && input.charAt(selection) == operation.charAt(0))
            return;
        if(input.charAt(selection - 1) == COMMA ||(selection != input.length() && input.charAt(selection) == COMMA))
            return;
        if(input.length() == selection && !StringUtil.isArithmeticOperation(input.charAt(selection - 1))){
            defaultInsertArithmeticSign(operation, selection);
            return;
        }
        if(selection == 1 && input.charAt(selection - 1) == MINUS) {
            return;
        }
        if(StringUtil.isArithmeticOperation(input.charAt(selection - 1))
                || (selection != input.length() && StringUtil.isArithmeticOperation(input.charAt(selection)))){
            replacingOperationSigns(operation , selection);
            return;
        }
        if(StringUtil.isArithmeticOperation(input.charAt(selection - 1))
                || (selection != input.length()
                && StringUtil.isArithmeticOperation(input.charAt(selection)))) {
            return;
        }
        defaultInsertArithmeticSign(operation, selection);
    }

    private void replacingOperationSigns(String newOperation, int selection) {
        int shift = 1;
        try {
            if (StringUtil.isArithmeticOperation(input.charAt(selection)) && !StringUtil.isArithmeticOperation(input.charAt(selection - 1))) {
                selection++;
            }
        }
        catch (Exception ignored){
        }
        if(selection != input.length() && String.valueOf(input.charAt(selection)).equals(newOperation))
            return;
        try {
            if (StringUtil.isArithmeticOperation(input.charAt(selection - 1)) && StringUtil.isArithmeticOperation(input.charAt(selection - 2))) {
                shift++;
            }
        }
        catch (Exception ignored){
        }
        input.replace(selection - shift, selection, newOperation);
        inputField.setText(input.toString());
        if(shift == 2) selection--;
        inputField.setSelection(selection);
    }

    private void addMinusToStart(int selection) {
        input.insert(selection, "−");
        inputField.setText(input.toString());
        inputField.setSelection(selection + 1);
    }

    private void defaultInsertArithmeticSign(String operation, int selection) {
        input.insert(selection, operation);
        inputField.setText(input.toString());
        inputField.setSelection(selection + operation.length());
        StringUtil.separationForArithmeticOperation(inputField);
    }
}
