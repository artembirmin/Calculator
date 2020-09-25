package com.example.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Calculator implements Parcelable {

    public String name = "";
    public String content = "";
    public String answer = "";

    public Calculator(String name, String content, String answer) {
        if(name != null)
            this.name = name;
        if(content != null)
            this.content = content;
        if(answer!= null)
            this.answer = answer;
    }

    protected Calculator(Parcel in) {
        name = in.readString();
        content = in.readString();
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

    public String getContent() {
        return content;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        return "Calculator{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
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
        parcel.writeString(content);
        parcel.writeString(answer);
    }
}
