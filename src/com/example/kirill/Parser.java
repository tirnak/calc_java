package com.example.kirill;

import java.util.LinkedList;
import java.util.StringTokenizer;

public class Parser {

    public LinkedList<CalcToken> tokens = new LinkedList<CalcToken>();

    public Parser(String str) {
        StringTokenizer st = new StringTokenizer(str, "+-*/()", true);

        while (st.hasMoreTokens()) {
            tokens.add(new CalcToken(st.nextToken()));
            System.out.println(tokens.peekLast().content);
        }
    }

    public LinkedList<String> getTokensInRPNotation() {

        LinkedList<String> rpnTokens = new LinkedList<String>();
        LinkedList<String> tmpStack = new LinkedList<String>();

        for (CalcToken token : tokens) {

        }

        return rpnTokens;
    }
}
