package com.example.calculator;

import android.widget.EditText;
import java.util.ArrayList;
import java.util.Arrays;

public class ArithmeticOperations {

    private static final ArrayList<Character> arithmeticOperations = new ArrayList<>(Arrays.asList('+', '−', '×', '÷'));
    private static final char MINUS = '−';
    private static final char PLUS = '+';
    private static final char COMMA = ',';

    protected static void insertMinus(EditText inputField, StringBuilder input, int selection) {
        if(input.length() == 0){
            addMinusToStart(inputField,input,selection);
            return;
        }
        if(selection == 0 && !arithmeticOperations.contains(input.charAt(selection))){
            addMinusToStart(inputField,input,selection);
            return;
        }
        if(selection == 0 && arithmeticOperations.contains(input.charAt(selection)))
            return;
        if(input.charAt(selection - 1) == COMMA || (selection != input.length() && input.charAt(selection) == COMMA))
            return;
        if(input.length() == selection && input.charAt(selection - 1) != MINUS  && input.charAt(selection - 1) != PLUS){
            defaultInsertArithmeticSign("−", inputField,input,selection);
            return;
        }
        if(input.charAt(selection - 1) == PLUS || (selection != input.length() && input.charAt(selection) == PLUS)){
            replacingOperationSigns("−",inputField, input, selection);
            return;
        }
        if(input.charAt(selection - 1) == MINUS || (selection != input.length() && input.charAt(selection) == MINUS)) {
            return;
        }
        if((selection != input.length() && arithmeticOperations.contains(input.charAt(selection))))
            return;
        defaultInsertArithmeticSign("−", inputField, input, selection);
    }

    protected static void insertSign(String operation, EditText inputField, StringBuilder input, int selection) {
        if(input.length() == 0)
            return;
        if(selection == 0)
            return;
        if(selection != input.length() && input.charAt(selection) == operation.charAt(0))
            return;
        if(input.charAt(selection - 1) == COMMA ||(selection != input.length() && input.charAt(selection) == COMMA))
            return;
        if(input.length() == selection && !arithmeticOperations.contains(input.charAt(selection - 1))){
            defaultInsertArithmeticSign(operation, inputField,input,selection);
            return;
        }
        if(selection == 1 && input.charAt(selection - 1) == MINUS) {
            return;
        }
        if(arithmeticOperations.contains(input.charAt(selection - 1))
                || (selection != input.length() && arithmeticOperations.contains(input.charAt(selection)))){
            replacingOperationSigns(operation ,inputField, input, selection);
            return;
        }
        if(arithmeticOperations.contains(input.charAt(selection - 1)) || (selection != input.length() && arithmeticOperations.contains(input.charAt(selection)))) {
            return;
        }
        defaultInsertArithmeticSign(operation, inputField, input, selection);
    }

    private static void replacingOperationSigns(String newOperation, EditText inputField, StringBuilder input, int selection) {
        int shift = 1;
        try {
            if (arithmeticOperations.contains(input.charAt(selection)) && !arithmeticOperations.contains(input.charAt(selection - 1))) {
                selection++;
            }
        }
        catch (Exception ignored){
        }
        if(selection != input.length() && String.valueOf(input.charAt(selection)).equals(newOperation))
            return;
        try {
            if (arithmeticOperations.contains(input.charAt(selection - 1)) && arithmeticOperations.contains(input.charAt(selection - 2))) {
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

    private static void addMinusToStart(EditText inputField, StringBuilder input, int selection) {
        input.insert(selection, "−");
        inputField.setText(input.toString());
        inputField.setSelection(selection + 1);
    }

    private static void defaultInsertArithmeticSign(String operation, EditText inputField, StringBuilder input, int selection) {
        input.insert(selection, operation);
        inputField.setText(input.toString());
        inputField.setSelection(selection + operation.length());
        StringUtil.separationForArithmeticOperation(inputField);
    }
}
