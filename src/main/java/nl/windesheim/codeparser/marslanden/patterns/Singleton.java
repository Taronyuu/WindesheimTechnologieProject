package nl.windesheim.codeparser.marslanden.patterns;

import nl.windesheim.codeparser.marslanden.ClassPart;

/**
 * Created by caveman on 4/19/18.
 */
public class Singleton implements IDesignPattern{
    ClassPart classPart;

    public ClassPart getClassPart() {
        return classPart;
    }

    public Singleton setClassPart(ClassPart classPart) {
        this.classPart = classPart;
        return this;
    }
}
