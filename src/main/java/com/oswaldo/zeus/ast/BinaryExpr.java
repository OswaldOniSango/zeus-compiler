package com.oswaldo.zeus.ast;

import com.oswaldo.zeus.lexer.TokenType;

public record BinaryExpr(Expr left, TokenType op, Expr right) implements Expr {
}
