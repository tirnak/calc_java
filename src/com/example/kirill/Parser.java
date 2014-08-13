package com.example.kirill;

import java.util.*;

public class Parser {

    public LinkedList<CalcToken> tokens = new LinkedList<CalcToken>();

    public Parser(String str) {

        tokens.push(new CalcToken(""));

        StringTokenizer st = new StringTokenizer(str, "+-*/()", true);

        while (st.hasMoreTokens()) {
            String tString = st.nextToken();

            if (tString == "-" && tokens.peekLast().isStartOfSubExpr()) {

            }

            tokens.add(new CalcToken(tString));
        }

        tokens.add(new CalcToken(""));

        correctNegativeNumbers();

        for (CalcToken token: tokens) {
            System.out.println(
                    token.content
            );
        }
    }

    public LinkedList<CalcToken> getTokensInRPNotation() {

        LinkedList<CalcToken> rpnTokens = new LinkedList<CalcToken>();
        LinkedList<CalcToken> tmpStack = new LinkedList<CalcToken>();

        tmpStack.push(
                tokens.pop()
        );

        Mapper.Action action;

        do {
            action = Mapper.map(tokens.peek(), tmpStack.peek());

            switch (action) {
                case MOVE_TO_TMP_STACK:
                    break;
                case MOVE_TO_RPN_STACK:
                    break;
                case ERASE_BOTH_TOKENS:
                    break;
                case RETURN_ERROR:
                    break;
            }
        } while (action != Mapper.Action.FINISH);

        return rpnTokens;
    }

    private static class Mapper {
        public enum Action {
            MOVE_TO_TMP_STACK,
            MOVE_TO_RPN_STACK,
            ERASE_BOTH_TOKENS,
            FINISH,
            RETURN_ERROR
        }

        public static Action map(CalcToken fromInfix, CalcToken fromTmpStack) {

            switch (fromInfix.getType()) {

                case OPEN_BRACKET:
                    return Action.MOVE_TO_TMP_STACK;
                case NULL:
                    if (fromTmpStack.getType() == CalcToken.Type.NULL) {
                        return Action.FINISH;
                    } else if (fromTmpStack.getType() == CalcToken.Type.OPEN_BRACKET) {
                        return Action.RETURN_ERROR;
                    } else {
                        return Action.MOVE_TO_RPN_STACK;
                    }
                case ADD:
                case SUBSTRACT:
                case NUM:
                    if (fromTmpStack.getType() == CalcToken.Type.NULL || fromTmpStack.getType() == CalcToken.Type.OPEN_BRACKET) {
                        return Action.MOVE_TO_TMP_STACK;
                    } else {
                        return Action.MOVE_TO_RPN_STACK;
                    }
                case MULTIPLY:
                case DIVIDE:
                    if (fromTmpStack.getType() == CalcToken.Type.MULTIPLY || fromTmpStack.getType() == CalcToken.Type.DIVIDE) {
                        return Action.MOVE_TO_RPN_STACK;
                    } else {
                        return Action.MOVE_TO_TMP_STACK;
                    }
                case CLOSE_BRACKET:
                    if (fromTmpStack.getType() == CalcToken.Type.NULL) {
                        return Action.RETURN_ERROR;
                    } else if (fromTmpStack.getType() == CalcToken.Type.OPEN_BRACKET) {
                        return Action.ERASE_BOTH_TOKENS;
                    } else {
                        return Action.MOVE_TO_RPN_STACK;
                    }
            }

            return Action.MOVE_TO_RPN_STACK;

        }
    }

    private void correctNegativeNumbers() {
        LinkedList<Integer> indexesToRemove = new LinkedList<Integer>();

        for (int i = 0, length = tokens.size(); i < length; i++) {

            if (tokens.get(i).getType() == CalcToken.Type.SUBSTRACT &&
                    tokens.get(i-1).isStartOfSubExpr()) {

                tokens.set(
                        i+1,
                        new CalcToken("-" + tokens.get(i+1))
                );

                indexesToRemove.push(i);
            }
        }

        while (indexesToRemove.size() > 0) {
            tokens.remove(
                    indexesToRemove.pop()
            );
        }
    }
}
