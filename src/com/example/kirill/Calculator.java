package com.example.kirill;

import java.util.*;

/**
 * Created by kirill on 14.08.14.
 */
public class Calculator {

    public enum Operation {
        ADDITION,
        SUBSTRACTION,
        DIVISION,
        MULTIPLICATION
    }

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
                tokens.remove(bracketPair.posOpened - 1);
                tokens.remove(bracketPair.posOpened);
            } else {
                evalSubExpr(expr);
                tokens.remove(bracketPair.posOpened);
                tokens.remove(bracketPair.posOpened + 1);
            }



            defineBrackets();

        }

        evalSubExpr(tokens);

        return Double.parseDouble(tokens.get(0).content);
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
            int operationIndex = 0, i1, i2;
            Operation operation;
            double operand1, operand2;


            CalcToken mult = new CalcToken("*");
            CalcToken div = new CalcToken("/");
            CalcToken add = new CalcToken("+");
            CalcToken sub = new CalcToken("-");

            while (expr.contains(mult) || expr.contains(div)) {
                i1 = expr.indexOf(mult);
                i2 = expr.indexOf(div);
                if (i1 < i2 && i1 > 0 || i2 == -1) {
                    operationIndex = i1;
                    operation = Operation.MULTIPLICATION;
                } else {
                    operationIndex = i2;
                    operation = Operation.DIVISION;
                }

                operate(expr, operationIndex, operation);
            }

            while (expr.contains(add) || expr.contains(sub)) {
                i1 = expr.indexOf(add);
                i2 = expr.indexOf(sub);
                if (i1 < i2 && i1 > 0 || i2 == -1) {
                    operationIndex = i1;
                    operation = Operation.ADDITION;
                } else {
                    operationIndex = i2;
                    operation = Operation.SUBSTRACTION;
                }

                operate(expr, operationIndex, operation);
            }
        }
    }

    private void operate(List<CalcToken> expr, int operationIndex, Operation operation) {
        double o1 = Double.parseDouble(expr.get(operationIndex - 1).content);
        double o2 = Double.parseDouble(expr.get(operationIndex + 1).content);
        double result = 0;
        switch (operation) {
            case ADDITION:
                result = o1 + o2;
                break;
            case SUBSTRACTION:
                result = o1 - o2;
                break;
            case MULTIPLICATION:
                result = o1 * o2;
                break;
            case DIVISION:
                result = o1 / o2;
                break;
        }
        expr.set(operationIndex - 1, new CalcToken(result + ""));
        expr.remove(operationIndex);
        expr.remove(operationIndex);
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
