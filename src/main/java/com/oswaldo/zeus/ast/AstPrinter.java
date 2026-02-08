package com.oswaldo.zeus.ast;

public class AstPrinter {

    public String print(Expr expr) {
        if (expr instanceof NumberExpr n) {
            return Integer.toString(n.value());
        }
        if (expr instanceof BinaryExpr b) {
            return "(" + opToString(b.op()) + " " + print(b.left()) + " " + print(b.right()) + ")";
        }
        throw new IllegalStateException("Unknown Expr: " + expr);
    }

    private String opToString(Enum<?> op) {
        return op.name();
    }
}
