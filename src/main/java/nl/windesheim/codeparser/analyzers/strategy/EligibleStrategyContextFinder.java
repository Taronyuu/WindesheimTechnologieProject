package nl.windesheim.codeparser.analyzers.strategy;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.resolution.declarations.ResolvedReferenceTypeDeclaration;
import com.github.javaparser.resolution.types.ResolvedReferenceType;
import com.github.javaparser.resolution.types.ResolvedType;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserInterfaceDeclaration;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import nl.windesheim.codeparser.analyzers.util.visitor.SetterFinder;

import java.util.ArrayList;
import java.util.List;

/**
 * Visitor which finds all classes which can be 'context' classes.
 */
public class EligibleStrategyContextFinder
        extends VoidVisitorAdapter<TypeSolver> {

    /**
     * The context, interface pairs found in the last visit.
     */
    private final List<EligibleStrategyContext> classes = new ArrayList<>();

    /**
     * A list of errors which were encountered.
     */
    private final List<Exception> errors = new ArrayList<>();

    /**
     * A visitor which is used to tell if there exists a setter for a field.
     */
    private final SetterFinder setterFinder;

    /**
     * Make a new EligibleStrategyContextFinder.
     */
    public EligibleStrategyContextFinder() {
        super();
        setterFinder = new SetterFinder();
    }

    /**
     * @return A list of context, interface pairs which were found in the last visit
     */
    public List<EligibleStrategyContext> getClasses() {
        return classes;
    }

    /**
     * @return a list of errors which were encountered
     */
    public List<Exception> getErrors() {
        return errors;
    }

    /**
     * Reset the list of classes.
     */
    public void reset() {
        errors.clear();
        classes.clear();
    }

    @Override
    public void visit(final ClassOrInterfaceDeclaration declaration, final TypeSolver typeSolver) {
        //If this is a interface don't process further
        if (declaration.isInterface()) {
            return;
        }

        //Get all fields in the class
        for (FieldDeclaration field : declaration.getFields()) {

            //For every variable in a field declaration
            for (VariableDeclarator variable : field.getVariables()) {
                //Get the type of the field
                Type variableType = variable.getType();

                //Try to resolve the type
                ResolvedType resolvedType;
                try {
                    resolvedType = JavaParserFacade.get(typeSolver).convertToUsage(variableType);
                } catch (UnsolvedSymbolException e) {
                    errors.add(new Exception("Can't resolve symbol: " + variableType.asString()
                            + ", can be caused by missing dependencies, "
                            + "invalid java code or selection of invalid source root", e));
                    continue;
                }

                //if the type is a reference, not a primitive type
                if (!(resolvedType instanceof ResolvedReferenceType)) {
                    continue;
                }

                ResolvedReferenceTypeDeclaration typeDeclaration
                        = ((ResolvedReferenceType) resolvedType).getTypeDeclaration();

                //If the type is a interface
                if (!(typeDeclaration instanceof JavaParserInterfaceDeclaration)) {
                    continue;
                }

                ClassOrInterfaceDeclaration resolvedInterface =
                        ((JavaParserInterfaceDeclaration) typeDeclaration).getWrappedNode();


                //If the interface has at least one method it is eligible as strategy interface
                boolean hasMethods = !resolvedInterface.getMethods().isEmpty();

                //Check if there is a setter for the field
                boolean hasSetter = setterFinder.visit(declaration, variable) != null;

                classes.add(
                    new EligibleStrategyContext()
                        .setInterfaceAttr(variable)
                        .setStrategyClass(resolvedInterface)
                        .setHasMethods(hasMethods)
                        .setHasSetter(hasSetter)
                );
            }
        }
    }
}
