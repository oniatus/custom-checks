package org.terasology.customchecks;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class LibsClassVisibilityCheck extends Check {

    @Override
    public int[] getDefaultTokens() {
        return new int[]{TokenTypes.CLASS_DEF};
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (isPublic(ast)) {
            if (!allowedToBePublic(ast)) {
                String className = ast.findFirstToken(TokenTypes.IDENT).getText();
                log(ast.getLineNo(), className + " is not allowed to be public");
            }
        }
    }

    private boolean allowedToBePublic(DetailAST ast) {
        return implementsComponent(ast) ||
                isFactory(ast) ||
                isAnnotatedWithRegisterSystem(ast) ||
                isEvent(ast) ||
                isAbstract(ast);
    }

    private boolean isAbstract(DetailAST ast) {
        return ast.findFirstToken(TokenTypes.MODIFIERS).findFirstToken(TokenTypes.ABSTRACT) != null;
    }

    private boolean isEvent(DetailAST ast) {
        return ast.findFirstToken(TokenTypes.IDENT).getText().endsWith("Event");
    }

    private boolean isAnnotatedWithRegisterSystem(DetailAST ast) {
        DetailAST annotation = ast.findFirstToken(TokenTypes.MODIFIERS).findFirstToken(TokenTypes.ANNOTATION);
        return annotation != null && "RegisterSystem".equals(annotation.findFirstToken(TokenTypes.IDENT).getText());
    }

    private boolean isFactory(DetailAST ast) {
        return ast.findFirstToken(TokenTypes.IDENT).getText().endsWith("Factory");
    }

    private boolean implementsComponent(DetailAST ast) {
        DetailAST implementsClause = ast.findFirstToken(TokenTypes.IMPLEMENTS_CLAUSE);
        return implementsClause != null && "Component".equals(implementsClause.findFirstToken(TokenTypes.IDENT).getText());
    }

    private boolean isPublic(DetailAST ast) {
        return ast.findFirstToken(TokenTypes.MODIFIERS).findFirstToken(TokenTypes.LITERAL_PUBLIC) != null;
    }
}


