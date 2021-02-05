package com.vladimir.crudblog.view.commands;

public class UnknownCommandException extends Exception {
    public UnknownCommandException(String error){
        super(error);
    }
}
