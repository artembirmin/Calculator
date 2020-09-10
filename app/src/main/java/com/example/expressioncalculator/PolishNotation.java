package com.example.expressioncalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import static java.lang.String.valueOf;

abstract class PolishNotation implements ExpressionCalculator {

    private final HashMap<Character, Integer> arithmeticPriority = new HashMap<>();
    private ArrayList<Object> expressionInPN = new ArrayList<>();

    {
        arithmeticPriority.put('+', 0);
        arithmeticPriority.put('-', 0);
        arithmeticPriority.put('*', 1);
        arithmeticPriority.put('/', 1);
        arithmeticPriority.put('M', 2);
        arithmeticPriority.put('^', 3);
    }

    @Override
    public double calculateExpression(String expression) {
        Stack<Double> calculationStack = new Stack<>();
        expressionInPN = translationToPN(expression);
        double buffer = 0;
        for (Object item : expressionInPN) {
            try {
                calculationStack.push((double) item);
                continue;
            } catch (Exception ignored) {
            }
            try {
                switch ((char) item) {
                    case '+':
                        buffer = calculationStack.pop();
                        calculationStack.push(calculationStack.pop() + buffer);
                        break;
                    case 'M':
                    case '-':
                        buffer = calculationStack.pop();
                        calculationStack.push(calculationStack.pop() - buffer);
                        break;
                    case '*':
                        buffer = calculationStack.pop();
                        calculationStack.push(calculationStack.pop() * buffer);
                        break;
                    case '/':
                        buffer = calculationStack.pop();
                        calculationStack.push(calculationStack.pop() / buffer);
                        break;
                    case '^':
                        buffer = calculationStack.pop();
                        calculationStack.push(Math.pow(calculationStack.pop(), buffer));
                        break;
                }
            } catch (Exception ignored) {
            }
        }
        expressionInPN.clear();
        return calculationStack.peek();
    }

    public ArrayList<Object> translationToPN(String expression) {
        expression = expressionFormatting(new StringBuilder(expression));
        Stack<Character> signStack = new Stack<>();
        String strNumber;
        double number;
        char item;
        for (int i = 0; i < expression.length(); i++) {
            item = expression.charAt(i);
            if (isPartOfNumber(item)) {
                strNumber = getNumber(expression, i);
                number = Double.parseDouble(strNumber);
                expressionInPN.add(number);
                i += strNumber.length() - 1;
                continue;
            }
            if (isArithmeticOperation(expression.charAt(i))) {
                if (arithmeticOperationProcessing(signStack, item)) continue;
            }
            if (isBracket(item)) {
                bracketProcessing(signStack, item);
            }
        }
        while (!signStack.isEmpty())
            expressionInPN.add(signStack.pop());
        return expressionInPN;
    }

    protected String expressionFormatting(StringBuilder expression) {

        if (isArithmeticOperation(expression.charAt(expression.length() - 1))){
            expression.deleteCharAt(expression.length() - 1);
        }
        if (isArithmeticOperation(expression.charAt(expression.length() - 1))){
            expression.deleteCharAt(expression.length() - 1);
        }
        if (expression.charAt(0) == '-')
            expression.insert(0, '0');
        for (int i = 0; i < expression.length(); i++) {
            if(expression.charAt(i) == '-'
                    && isArithmeticOperation(expression.charAt(i - 1))
                    && !isArithmeticOperation(expression.charAt(i + 1))){
                expression.insert(i,'0');
                expression.setCharAt(i + 1, 'M');
            }
        }
        return expression.toString();
    }

    //  private String processingDoubleSign(){
    //
    // }

    private boolean arithmeticOperationProcessing(Stack<Character> signStack, char item) {
        if (signStack.isEmpty()) {
            signStack.push(item);
            return true;
        }
        if (signStack.peek() == '(' || arithmeticPriority.get(item) > arithmeticPriority.get(signStack.peek())) {
            signStack.push(item);
            return true;
        }
        if (arithmeticPriority.get(item) == arithmeticPriority.get(signStack.peek())) {
            expressionInPN.add(signStack.pop());
            signStack.push(item);
            return true;
        } else {
            while (!signStack.isEmpty() && signStack.peek() != '(')
                expressionInPN.add(signStack.pop());
            signStack.push(item);
        }
        return false;
    }

    private void bracketProcessing(Stack<Character> signStack, char item) {
        if (item == '(') {
            signStack.push(item);
            return;
        }
        if (item == ')') {
            while (!signStack.isEmpty()) {
                if (signStack.peek() == '(') {
                    signStack.pop();
                    break;
                }
                expressionInPN.add(signStack.pop());
            }
        }
    }

    private String getNumber(String expression, int start) {
        int end;
        for (end = start; end < expression.length() && isPartOfNumber(expression.charAt(end)); end++) {
        }
        return expression.substring(start, end);
    }

    private boolean isBracket(char item) {
        return item == '(' || item == ')';
    }

    protected boolean isArithmeticOperation(char item) {
        return item == '+' || item == '-' || item == '*' || item == '/' || item == 'M' || item == '^';
    }

    private boolean isPartOfNumber(char item) {
        return item >= '0' && item <= '9' || item == '.';
    }
}

