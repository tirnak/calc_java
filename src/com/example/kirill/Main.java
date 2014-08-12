package com.example.kirill;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        int i, n;
        Scanner in = new Scanner(System.in);

        String str = in.nextLine().replace(" ", "");

        checkInput(str);

        Parser parser = new Parser(str);

        System.out.print(str);
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
