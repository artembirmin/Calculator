package com.example.domain.calculator;

public class ReversePolishNotation extends PolishNotation {

    @Override
    protected String expressionFormatting(StringBuilder expression) {

        if (isArithmeticOperation(expression.charAt(expression.length() - 1))) {
            expression.deleteCharAt(expression.length() - 1);
        }
        if (isArithmeticOperation(expression.charAt(expression.length() - 1))) {
            expression.deleteCharAt(expression.length() - 1);
        }
        return super.expressionFormatting(new StringBuilder(expression.toString().replace(',', '.')
                .replace('×', '*')
                .replace('÷', '/')
                .replace('−', '-') //минус на тире
                .replaceAll("\\s", "")));
        //FIXME возможно сделать свою реализацию, ибо создается дохрена строк
    }
}
