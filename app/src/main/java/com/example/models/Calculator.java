package com.example.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Calculator implements Parcelable, CommonListItem {

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
    @PrimaryKey
    @NonNull
    public String id = "";
    public String expression = "";
    public String answer = "";

    public Calculator() {
        this("");
    }

    public Calculator(@NonNull String name) {
        this.id = name;
    }

    public Calculator(@NonNull String name, String expression, String answer) {
        this.id = name;
        this.expression = expression;
        this.answer = answer;
    }

    protected Calculator(Parcel in) {
        id = Objects.requireNonNull(in.readString());
        expression = in.readString();
        answer = in.readString();
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @NonNull
    @Override
    public String toString() {
        return "Calculator{" +
                "name='" + id + '\'' +
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
        parcel.writeString(id);
        parcel.writeString(expression);
        parcel.writeString(answer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Calculator)) return false;
        Calculator that = (Calculator) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getExpression(), that.getExpression()) &&
                Objects.equals(getAnswer(), that.getAnswer());
    }
}
