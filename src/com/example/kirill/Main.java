package com.example.kirill;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    
    final static String checkString = "11+(exp(2.010635+sin(PI/2)*3)+50)/2";
    final static String checkString2 = "3+(-30)/(-30)";

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        String str = /*checkString2;//*/in.nextLine().replace(" ", "");

        checkInput(str);


        Parser parser = new Parser(str);

        Calculator calc = new Calculator(parser);

        System.out.print(str + " = " + calc.eval());

    }

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
