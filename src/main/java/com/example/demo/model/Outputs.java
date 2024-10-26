package com.example.demo.model;

public enum Outputs {
    REGISTER("ثبت نام","register"),
    ;


    private String persian;
    private String english;

    Outputs(String persian, String english) {
        this.persian = persian;
        this.english = english;
    }

    public static String getMessage(Outputs outputs,boolean language){
        if (language)
            return outputs.english;
        return outputs.persian;
    }
}
