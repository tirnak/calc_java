package com.example.kirill;

import java.util.*;

public class Parser {

    public ArrayList<CalcToken> tokens = new ArrayList<CalcToken>();

    public Parser(String str) {

        tokens.add(new CalcToken(""));

        StringTokenizer st = new StringTokenizer(str, "+-*/()", true);

        while (st.hasMoreTokens()) {
            tokens.add(new CalcToken(st.nextToken()));
        }

        tokens.add(new CalcToken(""));

        correctNegativeNumbers();

        for (CalcToken token: tokens) {
            System.out.println(
                    token.content
            );
        }
    }

    private void correctNegativeNumbers() {
        LinkedList<Integer> indexesToRemove = new LinkedList<Integer>();

        for (int i = 0, length = tokens.size(); i < length; i++) {

            if (tokens.get(i).getType() == CalcToken.Type.SUBSTRACT &&
                    tokens.get(i-1).isStartOfSubExpr()) {

                tokens.set(
                        i+1,
                        new CalcToken("-" + tokens.get(i+1).content)
                );

                indexesToRemove.push(i);
            }
        }

        while (indexesToRemove.size() > 0) {
            tokens.remove(
                    (int) indexesToRemove.pop()
            );

        }
    }


}


