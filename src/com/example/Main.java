package com.example;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    
    final static String checkString = "11+(exp(2.010635+sin(PI/2)*3)+50)/2";
    final static String checkString2 = "3+(-30)/(-30)";

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        String str = in.nextLine().replace(" ", "");

        checkInput(str);

        Parser parser = new Parser(str);

        Calculator calc = new Calculator(parser);

        System.out.print(str + " = " + calc.eval() + System.lineSeparator());

    }

    /**
     * If input contains unavailable tokens/symbols - returns error
     *
     * @param str input string to be checked
     */
    private static void checkInput(String str) {

        String availableExpressions = "(PI|E|cos|\\(|\\)|sin|exp|\\+|\\-|/|\\*|\\.|\\d+|\\s+)";

        Pattern p = Pattern.compile("^.*([^" + availableExpressions+ "]).*$");

        Matcher m = p.matcher(str);
        if (m.find()) {
            System.out.println("invalid token: " + m.group());
            System.exit(65);
        }

    }
}
