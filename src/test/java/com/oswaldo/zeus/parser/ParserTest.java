package com.oswaldo.zeus.parser;

import com.oswaldo.zeus.lexer.Lexer;
import com.oswaldo.zeus.ast.AstPrinter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserTest {

    private final AstPrinter printer = new AstPrinter();

    @Test
    void parsesPrecedence() {
        var tokens = new Lexer("1 + 2 * 3").tokenize();
        var expr = new Parser(tokens).parseExpression();
        assertEquals("(PLUS 1 (STAR 2 3))", printer.print(expr));
    }

    @Test
    void parsesParentheses() {
        var tokens = new Lexer("(1 + 2) * 3").tokenize();
        var expr = new Parser(tokens).parseExpression();
        assertEquals("(STAR (PLUS 1 2) 3)", printer.print(expr));
    }
}
