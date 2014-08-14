package com.example.kirill;

import java.util.*;

/**
 * Created by kirill on 14.08.14.
 */
public class Calculator {

    public ArrayList<CalcToken> tokens = new ArrayList<CalcToken>();

    public LinkedList<BracketPair> bracketPairs = new LinkedList<BracketPair>();

    public Calculator(Parser p) {
        tokens = p.tokens;
        defineBrackets();
    }

    public double eval() {

        double result = 0.0;

        while (bracketPairs.size() > 0) {

            BracketPair bracketPair = bracketPairs.pop();

            List<CalcToken> expr = (List<CalcToken>) tokens.subList(bracketPair.posOpened + 1, bracketPair.posClosed);

            if (tokens.get(bracketPair.posOpened - 1).getType() == CalcToken.Type.FUNC) {
                evalSubExpr(expr, tokens.get(bracketPair.posOpened - 1));
                tokens.remove(bracketPair.posOpened - 1);
            } else {
                evalSubExpr(expr);
            }

            tokens.remove(bracketPair.posOpened - 1);
            tokens.remove(bracketPair.posOpened);

            defineBrackets();

        }

        return Double.parseDouble(tokens.get(1).content);
    }
    private void evalSubExpr(List<CalcToken> expr, CalcToken func) {

        evalSubExpr(expr);

        double tmpResult = Double.parseDouble(expr.get(0).content);

        if (func.content.equals("cos")) {
            tmpResult = Math.cos(tmpResult);
        } else if (func.content.equals("sin")) {
            tmpResult = Math.sin(tmpResult);
        } else  {
            tmpResult = Math.exp(tmpResult);
        }

        expr.set(0, new CalcToken(tmpResult + ""));
    }

    private void evalSubExpr(List<CalcToken> expr) {
        if (expr.size() > 1) {
            int operationIndex = 0;


            CalcToken mult = new CalcToken("*");
            CalcToken div = new CalcToken("/");
            CalcToken add = new CalcToken("+");
            CalcToken sub = new CalcToken("-");

            while (expr.contains(mult)) {
                operationIndex = expr.indexOf(mult);
                expr.set(operationIndex - 1, new CalcToken(
                        Double.parseDouble(expr.get(operationIndex - 1).content) *
                                Double.parseDouble(expr.get(operationIndex + 1).content) + ""
                ));
                expr.remove(operationIndex);
                expr.remove(operationIndex);
            }
            while (expr.contains(div)) {
                operationIndex = expr.indexOf(div);
                expr.set(operationIndex - 1, new CalcToken(
                        Double.parseDouble(expr.get(operationIndex - 1).content) /
                                Double.parseDouble(expr.get(operationIndex + 1).content) + ""
                ));
                expr.remove(operationIndex);
                expr.remove(operationIndex);
            }

            while (expr.contains(add)) {
                operationIndex = expr.indexOf(add);
                expr.set(operationIndex - 1, new CalcToken(
                        Double.parseDouble(expr.get(operationIndex - 1).content) +
                                Double.parseDouble(expr.get(operationIndex + 1).content) + ""
                ));
                expr.remove(operationIndex);
                expr.remove(operationIndex);
            }

            while (expr.contains(sub)) {
                operationIndex = expr.indexOf(sub);
                expr.set(operationIndex - 1, new CalcToken(
                        Double.parseDouble(expr.get(operationIndex - 1).content) -
                                Double.parseDouble(expr.get(operationIndex + 1).content) + ""
                ));
                expr.remove(operationIndex);
                expr.remove(operationIndex);
            }


        }
    }

    public void defineBrackets() {
        bracketPairs = new LinkedList<BracketPair>();
        int depth = 0, maxDepth = 0;
        LinkedList<BracketPair> bracketStack = new LinkedList<BracketPair>();

        for (int i = 0, length = tokens.size(); i < length; i++) {
            if (tokens.get(i).getType() == CalcToken.Type.OPEN_BRACKET) {
                depth++;
                bracketPairs
                        .add(new BracketPair(i, depth)
                        );
                bracketStack
                        .push(bracketPairs.getLast()
                        );
                if (depth > maxDepth) {
                    maxDepth = depth;
                }
            } else if (tokens.get(i).getType() == CalcToken.Type.CLOSE_BRACKET) {
                bracketStack
                        .peek()
                        .setPosClosed(i);
                bracketStack.pop();
                depth--;
            }
        }

        if (maxDepth != 0) {
            Collections.sort(bracketPairs, new DepthComparator());
        }

        for (BracketPair bracketPair: bracketPairs) {
            System.out.println(bracketPair.posOpened + ", " + bracketPair.posClosed);
        }

    }

    class BracketPair {
        int depth;
        int posOpened;
        int posClosed;

        public BracketPair(int posOpened, int depth) {
            this.posOpened = posOpened;
            this.depth = depth;
        }

        public void setPosClosed(int position) {
            posClosed = position;
        }

        public int getDistance() {
            return posClosed - posOpened + 1;
        }
    }

    class DepthComparator implements Comparator<BracketPair> {
        @Override
        public int compare(BracketPair a, BracketPair b) {
            return a.depth < b.depth ? 1 :
                    a.depth == b.depth ? 0 :
                            -1;
        }
    }
}
