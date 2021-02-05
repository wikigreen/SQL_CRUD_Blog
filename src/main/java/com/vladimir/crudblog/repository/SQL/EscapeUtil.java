package com.vladimir.crudblog.repository.SQL;

public class EscapeUtil {
    public static String addEscapeCharacters(String input){
        return input.replaceAll("\\\\", "\\\\\\\\").replaceAll("'", "\\\\'");
    }
}
