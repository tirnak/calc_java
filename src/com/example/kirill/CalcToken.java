package com.example.kirill;

/**
 * Created by kirill on 12.08.14.
 */
public class CalcToken {

    public enum Type {
        NULL,
        NUM,
        FUNC,
        ADD,
        SUBSTRACT,
        MULTIPLY,
        DIVIDE,
        OPEN_BRACKET,
        CLOSE_BRACKET
    }

    public enum Group {
        NULL,

    }

    public String content = new String();

    public Type type;

    public CalcToken(String str) {
        content = str;

        if (isInteger()) {
            type = Type.NUM;
        } else if (isAddition()) {
            type = Type.ADD;
        } else if (isFunc()) {
            type = Type.FUNC;
        } else if (isSubstraction()) {
            type = Type.SUBSTRACT;
        } else if (isMultiplication()) {
            type = Type.MULTIPLY;
        } else if (isDivision()) {
            type = Type.DIVIDE;
        } else if (isOpenBracket()) {
            type = Type.OPEN_BRACKET;
        } else if (isClosedBracket()) {
            type = Type.CLOSE_BRACKET;
        } else {
            type = Type.NULL;
        }
    }

    public Type getType() {
        return type;
    }


    public boolean isStartOfSubExpr () {
        switch (getType()) {
            case OPEN_BRACKET:
            case NULL:
                return true;
            default:
                return false;
        }
    }

    private boolean isInteger() {

        if (content == "PI" || content == "E") {
            return true;
        }

        try {
            Double.parseDouble(content);
        } catch(Exception e) {
            return false;
        }

        return true;
    }

    private boolean isFunc() {
        return content == "cos" || content == "sin" || content == "exp";
    }

    private boolean isAddition() {
        return content == "+";
    }

    private boolean isSubstraction() {
        return content == "-";
    }

    private boolean isMultiplication() {
        return content == "*";
    }

    private boolean isDivision() {
        return content == "/";
    }

    private boolean isOpenBracket() {
        return content == "(";
    }

    private boolean isClosedBracket() {
        return content == ")";
    }





}
