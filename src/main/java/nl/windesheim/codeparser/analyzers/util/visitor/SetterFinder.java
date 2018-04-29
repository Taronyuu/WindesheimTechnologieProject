package nl.windesheim.codeparser.analyzers.util.visitor;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.ThisExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;

import java.util.Optional;

/**
 * Tries to find a setter for a field.
 */
public class SetterFinder extends GenericVisitorAdapter<Boolean, VariableDeclarator> {

    @Override
    public Boolean visit(final MethodDeclaration method, final VariableDeclarator variable) {
        Boolean parentHasFound = super.visit(method, variable);
        if (parentHasFound != null) {
            return parentHasFound;
        }

        Type fieldType = variable.getType();

        Optional<Parameter> parameter = method.getParameterByType(fieldType.asString());
        if (!parameter.isPresent()) {
            return null;
        }

        Optional<BlockStmt> optionalBody = method.getBody();
        if (!optionalBody.isPresent()) {
            return null;
        }
        BlockStmt body = optionalBody.get();

        for (Statement statement : body.getStatements()) {
            if (statement instanceof ExpressionStmt) {
                ExpressionStmt expressionStmt = (ExpressionStmt) statement;

                Expression expression = expressionStmt.getExpression();
                if (expression instanceof AssignExpr) {
                    Expression target = ((AssignExpr) expression).getTarget();
                    if (target instanceof FieldAccessExpr) {
                        if (((FieldAccessExpr) target).getScope() instanceof ThisExpr) {
                            if (((FieldAccessExpr) target).getName().equals(variable.getName())) {
                                return true;
                            }
                        }
                    } else if (target instanceof NameExpr) {
                        if (((NameExpr) target).getName().equals(variable.getName())) {
                            return true;
                        }
                    }

                }
            }
        }
        return null;
    }

}
