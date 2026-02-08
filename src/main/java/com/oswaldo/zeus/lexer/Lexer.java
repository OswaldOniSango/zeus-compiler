package com.oswaldo.zeus.lexer;

import java.util.*;

public class Lexer {

    private final String input;
    private int pos = 0;
    private int line = 1;
    private int column = 1;

    public Lexer(String input) {
        this.input = input;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (!isAtEnd()) {
            char c = peek();

            if (c == '\n') {
                advance();
                line++;
                column = 1;
                continue;
            }

            if (Character.isWhitespace(c)) {
                advance();
                continue;
            }

            if (Character.isDigit(c)) {
                tokens.add(number());
                continue;
            }

            if (Character.isLetter(c)) {
                tokens.add(identifier());
                continue;
            }

            int startColumn = column;

            switch (c) {
                case '+': tokens.add(simple(TokenType.PLUS, startColumn)); break;
                case '-': tokens.add(simple(TokenType.MINUS, startColumn)); break;
                case '*': tokens.add(simple(TokenType.STAR, startColumn)); break;
                case '/': tokens.add(simple(TokenType.SLASH, startColumn)); break;
                case '=': tokens.add(simple(TokenType.ASSIGN, startColumn)); break;
                case '(': tokens.add(simple(TokenType.LPAREN, startColumn)); break;
                case ')': tokens.add(simple(TokenType.RPAREN, startColumn)); break;
                case '{': tokens.add(simple(TokenType.LBRACE, startColumn)); break;
                case '}': tokens.add(simple(TokenType.RBRACE, startColumn)); break;
                case ';': tokens.add(simple(TokenType.SEMICOLON, startColumn)); break;
                default:
                    throw new RuntimeException(
                            "Unexpected char '" + c + "' at " + line + ":" + column
                    );
            }
        }

        tokens.add(new Token(TokenType.EOF, "", line, column));
        return tokens;
    }

    private Token simple(TokenType type, int startColumn) {
        char c = advance();
        return new Token(type, String.valueOf(c), line, startColumn);
    }

    private Token number() {
        int startColumn = column;
        StringBuilder sb = new StringBuilder();

        while (!isAtEnd() && Character.isDigit(peek())) {
            sb.append(advance());
        }

        return new Token(TokenType.NUMBER, sb.toString(), line, startColumn);
    }

    private Token identifier() {
        int startColumn = column;
        StringBuilder sb = new StringBuilder();

        while (!isAtEnd() && Character.isLetterOrDigit(peek())) {
            sb.append(advance());
        }

        String word = sb.toString();

        return switch (word) {
            case "int" -> new Token(TokenType.INT, word, line, startColumn);
            case "if" -> new Token(TokenType.IF, word, line, startColumn);
            case "else" -> new Token(TokenType.ELSE, word, line, startColumn);
            case "while" -> new Token(TokenType.WHILE, word, line, startColumn);
            case "return" -> new Token(TokenType.RETURN, word, line, startColumn);
            default -> new Token(TokenType.IDENT, word, line, startColumn);
        };
    }

    private boolean isAtEnd() {
        return pos >= input.length();
    }

    private char peek() {
        return input.charAt(pos);
    }

    private char advance() {
        char c = input.charAt(pos++);
        column++;
        return c;
    }
}
