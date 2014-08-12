package com.example.kirill;

/**
 * Created by kirill on 12.08.14.
 */
public class CalcToken {

    public enum Type {
        NUM,
        FUNC,
        ADD,
        MULTIPLY,
        DIVIDE,
        OPEN_BRACKET,
        CLOSE_BRACKET
    }

    public String content = new String();

    public CalcToken(String str) {
        content = str;
    }

    public Type getType() {
        return Type.OPEN_BRACKET;
    }

    private boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c <= '/' || c >= ':') {
                return false;
            }
        }
        return true;
    }

}
