package nl.windesheim.codeparser.analyzers.observer.components;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.resolution.declarations.ResolvedReferenceTypeDeclaration;

public class ConcreteObservable extends ObservableClass {
    public ConcreteObservable(final ClassOrInterfaceDeclaration classDeclaration) {
        this(classDeclaration, null);
    }

    public ConcreteObservable(final ClassOrInterfaceDeclaration classDeclaration, final ResolvedReferenceTypeDeclaration resolvedTypeDeclaration) {
        super(classDeclaration, resolvedTypeDeclaration);
    }
}