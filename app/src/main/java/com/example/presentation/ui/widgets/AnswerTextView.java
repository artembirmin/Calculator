package com.example.presentation.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class AnswerTextView extends androidx.appcompat.widget.AppCompatTextView {

    public AnswerTextView(Context context) {
        super(context);
    }

    public AnswerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnswerTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setAnswerText(String text){

        if(text.charAt(text.length() - 1) == '0' && text.charAt(text.length() - 2) == '.')
            if(text.charAt(0) == '-')
                super.setText(new StringBuilder(text).delete(text.length() - 2, text.length()).replace(0,1,"−"));
            else
                super.setText(new StringBuilder(text).delete(text.length() - 2, text.length()));
        else super.setText(text);
    }
}
