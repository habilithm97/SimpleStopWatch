package com.example.simplestopwatch;

public class Model {
    String str;

    public Model(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return "Model{" +
                "str='" + str + '\'' +
                '}';
    }
}
