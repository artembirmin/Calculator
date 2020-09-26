package com.example.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Calculator implements Parcelable {

    @PrimaryKey
    public String name = "";
    public String expression = "";
    public String answer = "";

    public Calculator(String name){
        this.name = name;
    }

    public Calculator(String name, String expression, String answer) {
        this.name = name;
        this.expression = expression;
        this.answer = answer;
    }

    protected Calculator(Parcel in) {
        name = in.readString();
        expression = in.readString();
        answer = in.readString();
    }

    public static final Creator<Calculator> CREATOR = new Creator<Calculator>() {
        @Override
        public Calculator createFromParcel(Parcel in) {
            return new Calculator(in);
        }

        @Override
        public Calculator[] newArray(int size) {
            return new Calculator[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getExpression() {
        return expression;
    }

    public String getAnswer() {
        return answer;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Calculator{" +
                "name='" + name + '\'' +
                ", content='" + expression + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(expression);
        parcel.writeString(answer);
    }
}
