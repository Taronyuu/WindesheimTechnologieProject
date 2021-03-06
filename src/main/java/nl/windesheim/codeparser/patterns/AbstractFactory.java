package nl.windesheim.codeparser.patterns;

import nl.windesheim.codeparser.ClassOrInterface;

import java.util.List;
import java.util.Map;

/**
 * The Abstract Factory pattern class.
 */
@SuppressWarnings("PMD.DataClass")
public class AbstractFactory implements IDesignPattern {

    /**
     * The interface that indicates the factory.
     */
    private ClassOrInterface factoryInterface;

    /**
     * List of all the implementations for this factory.
     */
    private List<ClassOrInterface> implementations;

    /**
     * List of all the used interfaces by the concrete factories.
     */
    private Map<ClassOrInterface, List<ClassOrInterface>> concreteInter;

    /**
     * Get the factory interface.
     * @return the factory interface.
     */
    public ClassOrInterface getFactoryInterface() {
        return factoryInterface;
    }

    /**
     * Get the implementations.
     * @return The implementations.
     */
    public List<ClassOrInterface> getImplementations() {
        return implementations;
    }

    /**
     * Get the concrete interfaces.
     * @return the conrete interfaces.
     */
    public Map<ClassOrInterface, List<ClassOrInterface>> getConcreteImplementations() {
        return concreteInter;
    }

    /**
     * Set the factory interface.
     * @param factoryInterface the factory interface.
     */
    public void setFactoryInterface(final ClassOrInterface factoryInterface) {
        this.factoryInterface = factoryInterface;
    }

    /**
     * Set the implementations of this factory.
     * @param implementations The implementations to set.
     */
    public void setImplementations(final List<ClassOrInterface> implementations) {
        this.implementations = implementations;
    }

    /**
     * Set the list of concrete interfaces.
     * @param implInterfaces the concrete interfaces
     */
    public void setConcreteInterfaces(final Map<ClassOrInterface, List<ClassOrInterface>> implInterfaces) {
        this.concreteInter = implInterfaces;
    }
}
