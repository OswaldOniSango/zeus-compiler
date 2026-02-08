package com.oswaldo.zeus.interpreter;

import com.oswaldo.zeus.lexer.Lexer;
import com.oswaldo.zeus.parser.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InterpreterTest {

    @Test
    void evaluatesExpressionWithPrecedence() {
        var tokens = new Lexer("1 + 2 * 3").tokenize();
        var expr = new Parser(tokens).parseExpression();

        int result = new Interpreter().evaluate(expr);
        assertEquals(7, result);
    }

    @Test
    void evaluatesParentheses() {
        var tokens = new Lexer("(1 + 2) * 3").tokenize();
        var expr = new Parser(tokens).parseExpression();

        int result = new Interpreter().evaluate(expr);
        assertEquals(9, result);
    }
}
