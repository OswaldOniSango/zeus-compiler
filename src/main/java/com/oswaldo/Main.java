package com.oswaldo;

import com.oswaldo.zeus.ast.Expr;
import com.oswaldo.zeus.interpreter.Interpreter;
import com.oswaldo.zeus.lexer.Lexer;
import com.oswaldo.zeus.parser.Parser;

public class Main {
    public static void main(String[] args) {
        String code = "1 + 2 * 3";

        var tokens = new Lexer(code).tokenize();
        Expr expr = new Parser(tokens).parseExpression();

        int result = new Interpreter().evaluate(expr);
        System.out.println(result);
    }
}