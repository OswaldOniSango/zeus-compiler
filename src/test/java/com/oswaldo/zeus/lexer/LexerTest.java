package com.oswaldo.zeus.lexer;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LexerTest {

    @Test
    void tokenizesSimpleDeclaration() {
        String code = "int x = 3 + 4;";

        Lexer lexer = new Lexer(code);
        List<Token> tokens = lexer.tokenize();

        assertTokenTypes(tokens,
                TokenType.INT,
                TokenType.IDENT,
                TokenType.ASSIGN,
                TokenType.NUMBER,
                TokenType.PLUS,
                TokenType.NUMBER,
                TokenType.SEMICOLON,
                TokenType.EOF
        );

        // Bonus: validamos lexemas clave
        assertEquals("int", tokens.get(0).lexeme);
        assertEquals("x", tokens.get(1).lexeme);
        assertEquals("3", tokens.get(3).lexeme);
        assertEquals("4", tokens.get(5).lexeme);
    }

    @Test
    void tracksLineAndColumnAcrossNewlines() {
        String code = """
                int x = 3;
                int y = 4;
                """;

        List<Token> tokens = new Lexer(code).tokenize();

        // "int" de la segunda línea debería estar en line=2, col=1
        Token secondInt = tokens.stream()
                .filter(t -> t.type == TokenType.INT)
                .skip(1)
                .findFirst()
                .orElseThrow();

        assertEquals(2, secondInt.line);
        assertEquals(1, secondInt.column);
    }

    @Test
    void throwsOnUnexpectedCharacterWithLocation() {
        String code = "int x = 3 @ 4;";

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> new Lexer(code).tokenize()
        );

        assertTrue(ex.getMessage().contains("Unexpected char '@' at 1:11"));
    }

    @Test
    void handlesEmptyInput() {
        String code = "";

        List<Token> tokens = new Lexer(code).tokenize();

        assertEquals(1, tokens.size());
        assertEquals(TokenType.EOF, tokens.get(0).type);
    }



    private static void assertTokenTypes(List<Token> tokens, TokenType... expected) {
        assertEquals(expected.length, tokens.size(),
                "Token count mismatch. Tokens: " + tokens);

        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], tokens.get(i).type,
                    "Mismatch at index " + i + ". Tokens: " + tokens);
        }
    }
}
