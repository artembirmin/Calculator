package com.example.calculator;

import android.util.Log;

import java.text.NumberFormat;
import java.util.Locale;

public class StringUtil {
    public static String separation(String inputStr, int selection) {
        int startNumber;
        int endNumber;
        int i;
        StringBuilder input = new StringBuilder(inputStr);
        input.insert(0,'x');
        input.insert(input.length(), 'x');
        for(i = selection;(input.charAt(i) >= '0' && input.charAt(i) <= '9') || input.charAt(i) == ' '; i--) {
        }
        if(input.charAt(i) == ',') return inputStr;
        startNumber = ++i;
        for(i = selection;(input.charAt(i) >= '0' && input.charAt(i) <= '9') || input.charAt(i) == ' '; i++) {
        }
        endNumber = i;
        StringBuilder number = new StringBuilder(input.substring(startNumber,endNumber).replaceAll("\\s","")).reverse();
        i = 0;
        for(int j = 0; j < number.length(); j++){
            if( (i+1)%3 == 0){
                number.insert(j+1, ' ');
                j++;
                i = 0;
            }
            else i++;
        }
        if(number.charAt(number.length()-1) == ' ')
            number.deleteCharAt(number.length()-1);
        number.reverse();
        input.deleteCharAt(0);
        input.deleteCharAt(input.length()-1);
        input.replace(startNumber-1,endNumber-1, number.toString());
        return input.toString();
    }

}
