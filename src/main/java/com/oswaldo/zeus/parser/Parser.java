package com.oswaldo.zeus.parser;

import com.oswaldo.zeus.ast.BinaryExpr;
import com.oswaldo.zeus.ast.Expr;
import com.oswaldo.zeus.ast.NumberExpr;
import com.oswaldo.zeus.lexer.Token;
import com.oswaldo.zeus.lexer.TokenType;

import java.util.List;

public class Parser {

    private final List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Expr parseExpression() {
        Expr expr = expression();
        consume(TokenType.EOF, "Expected end of input");
        return expr;
    }

    // expression -> term ( ( "+" | "-" ) term )*
    private Expr expression() {
        Expr expr = term();

        while (match(TokenType.PLUS, TokenType.MINUS)) {
            TokenType op = previous().type;
            Expr right = term();
            expr = new BinaryExpr(expr, op, right);
        }

        return expr;
    }

    // term -> factor ( ( "*" | "/" ) factor )*
    private Expr term() {
        Expr expr = factor();

        while (match(TokenType.STAR, TokenType.SLASH)) {
            TokenType op = previous().type;
            Expr right = factor();
            expr = new BinaryExpr(expr, op, right);
        }

        return expr;
    }

    // factor -> NUMBER | "(" expression ")"
    private Expr factor() {
        if (match(TokenType.NUMBER)) {
            int value = Integer.parseInt(previous().lexeme);
            return new NumberExpr(value);
        }

        if (match(TokenType.LPAREN)) {
            Expr expr = expression();
            consume(TokenType.RPAREN, "Expected ')'");
            return expr;
        }

        Token t = peek();
        throw error(t, "Expected a number or '('");
    }

    // ---- helpers ----

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private Token consume(TokenType type, String message) {
        if (check(type)) return advance();
        throw error(peek(), message);
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) return type == TokenType.EOF;
        return peek().type == type;
    }

    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().type == TokenType.EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    private RuntimeException error(Token token, String message) {
        return new RuntimeException(message + " at " + token.line + ":" + token.column + " (found " + token.type + ")");
    }
}
