package com.example;

public class CalcToken {

    /**
     * Enumeration of token types
     */
    public enum Type {
        NULL,
        NUM,
        FUNC,
        ADD,
        SUBTRACT,
        MULTIPLY,
        DIVIDE,
        OPEN_BRACKET,
        CLOSE_BRACKET
    }

    public String content;

    public Type type;

    /**
     * Constructor
     *
     * @param str
     */
    public CalcToken(String str) {
        content = str;

        defineType();
    }

    private void defineType() {
        if (isInteger()) {
            castConstToNum();
            type = Type.NUM;
        } else if (isAddition()) {
            type = Type.ADD;
        } else if (isFunc()) {
            type = Type.FUNC;
        } else if (isSubstraction()) {
            type = Type.SUBTRACT;
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

    /**
     * Defines if token is starting expression.
     * Needed for negative numbers
     *
     * @return boolean
     */
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

        if (content.equals("PI") || content.equals("E")) {
            return true;
        }

        try {
            Double.parseDouble(content);
        } catch(Exception e) {
            return false;
        }

        return true;
    }

    /**
     * Converts constants to numbers
     */
    private void castConstToNum() {
        if (content.equals("PI")) {
            content = "3.14159265359";
        } else if (content.equals("E")) {
            content = "2.71828182846";
        }
    }

    private boolean isFunc() {
        return content.equals("cos") || content.equals("sin") || content.equals("exp");
    }

    private boolean isAddition() {
        return content.equals("+");
    }

    private boolean isSubstraction() {
        return content.equals("-");
    }

    private boolean isMultiplication() {
        return content.equals("*");
    }

    private boolean isDivision() {
        return content.equals("/");
    }

    private boolean isOpenBracket() {
        return content.equals("(");
    }

    private boolean isClosedBracket() {
        return content.equals(")");
    }

    /**
     * Overrides to compare by value, not reference
     *
     * @param object object (CalcToken) to be compared to
     * @return boolean
     */
    @Override
    public boolean equals (Object object) {

        try {
            CalcToken calcToken = (CalcToken) object;

            if (this.getType().equals(calcToken.getType()) && this.content.equals(calcToken.content)) {
                return true;
            } else {
                return false;
            }

        } catch (ClassCastException e) {
            return false;
        }


    }



}
