package com.oswaldo.zeus.interpreter;

import com.oswaldo.zeus.ast.BinaryExpr;
import com.oswaldo.zeus.ast.Expr;
import com.oswaldo.zeus.ast.NumberExpr;
import com.oswaldo.zeus.lexer.TokenType;

public class Interpreter {

    public int evaluate(Expr expr) {
        if (expr instanceof NumberExpr n) {
            return n.value();
        }

        if (expr instanceof BinaryExpr b) {
            int left = evaluate(b.left());
            int right = evaluate(b.right());

            return switch (b.op()) {
                case PLUS -> left + right;
                case MINUS -> left - right;
                case STAR -> left * right;
                case SLASH -> left / right;
                default -> throw new RuntimeException("Unknown operator: " + b.op());
            };
        }

        throw new RuntimeException("Unknown expression: " + expr);
    }
}
