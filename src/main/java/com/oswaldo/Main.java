package com.zeus;

import com.zeus.lexer.Lexer;

public class Main {
    public static void main(String[] args) {
        String code = "int x = 3 + 4;";
        Lexer lexer = new Lexer(code);

        lexer.tokenize().forEach(System.out::println);
    }
}