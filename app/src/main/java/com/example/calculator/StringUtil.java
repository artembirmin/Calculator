package com.example.calculator;

import android.util.Log;

import java.text.NumberFormat;
import java.util.Locale;

public class StringUtil {
    public static String separation(String inputStr, int selection) {
       // if(inputStr.length() == 1)
        //    return inputStr;
        int startNumber;
        int endNumber;
        int i;
        StringBuilder input = new StringBuilder(inputStr);
        input.insert(0,'x');
        input.insert(input.length(), 'x');
        Log.i("qwert-2", input.toString());
        for(i = selection; (input.charAt(i) >= '0' && input.charAt(i) <= '9') || input.charAt(i) == ' '; i--) {
        }
        startNumber = ++i;
        for(i = selection; (input.charAt(i) >= '0' && input.charAt(i) <= '9') || input.charAt(i) == ' '; i++) {
        }
        endNumber = i;
        Log.i("qwert-1", input.toString());
        StringBuilder number = new StringBuilder(input.substring(startNumber,endNumber).replaceAll("\\s","")).reverse();
        Log.i("qwert0", number.toString());
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
        Log.i("qwert1", number.toString());
        input.deleteCharAt(0);
        input.deleteCharAt(input.length()-1);
        input.replace(startNumber-1,endNumber-1, number.toString());

        Log.i("qwert2", input.toString());
        return input.toString();
    }

}
