package nl.windesheim.reporting;

import nl.windesheim.codeparser.ClassOrInterface;
import nl.windesheim.codeparser.FilePart;
import nl.windesheim.codeparser.patterns.Singleton;
import nl.windesheim.codeparser.patterns.Strategy;
import nl.windesheim.codeparser.patterns.ChainOfResponsibility;
import nl.windesheim.codeparser.patterns.AbstractFactory;
import nl.windesheim.codeparser.patterns.IDesignPattern;
import nl.windesheim.reporting.builders.AbstractFactoryFoundPatternBuilder;
import nl.windesheim.reporting.builders.ChainOfResponsibilityFoundPatternBuilder;
import nl.windesheim.reporting.builders.SingletonFoundPatternBuilder;
import nl.windesheim.reporting.builders.StrategyFoundPatternBuilder;
import nl.windesheim.reporting.components.AbstractFoundPatternBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Map the result from analyzers and return the correct builder.
 */
public class CodeReportDesignPatternMapper {
    /**
     * Get the correct builder.
     * @param pattern IDesignPattern result
     * @return AbstractFoundPatternBuilder builder of matched result
     */
    public AbstractFoundPatternBuilder getBuilder(final IDesignPattern pattern) {
        // Singletons
        if (pattern instanceof Singleton) {
            return buildSingletonBuilder((Singleton) pattern);
        }

        // Chain of responsibility
        if (pattern instanceof ChainOfResponsibility) {
            return buildChainOfResponsibilityBuilder((ChainOfResponsibility) pattern);
        }

        // Strategy
        if (pattern instanceof Strategy) {
            return buildStrategyBuilder((Strategy) pattern);
        }

        // Abstract factory
        if (pattern instanceof AbstractFactory) {
            return buildAbstractFactory((AbstractFactory) pattern);
        }

        return null;
    }

    /**
     * Build the abstract factory pattern builder class.
     * @param pattern the pattern.
     * @return the abstract factory pattern builder.
     */
    private AbstractFoundPatternBuilder buildAbstractFactory(final AbstractFactory pattern) {
        return new AbstractFactoryFoundPatternBuilder(pattern.getFactoryInterface().getNameAsString());
    }

    /**
     * Build the strategy builder.
     * @param pattern strategy pattern
     * @return Strategy builder
     */
    private AbstractFoundPatternBuilder buildStrategyBuilder(final Strategy pattern) {
        String interfaceName = pattern.getStrategyInterface().getName();
        String context = pattern.getContext().getName();
        List<String> files = new ArrayList<>();
        List<String> strategies = new ArrayList<>();

        for (ClassOrInterface strategy : pattern.getStrategies()) {
            strategies.add(strategy.getName());
        }

        return new StrategyFoundPatternBuilder(files, context, interfaceName, strategies);
    }

    /**
     * Build the singleton builder.
     * @param pattern singleton pattern
     * @return Singleton builder
     */
    private AbstractFoundPatternBuilder buildSingletonBuilder(final Singleton pattern) {
        FilePart filePart = pattern.getFilePart();
        String fileName = filePart.getFile().getName();
        return new SingletonFoundPatternBuilder(fileName);
    }

    /**
     * Build the ChainOfResponsbilityBuilder.
     * @param pattern the pattern
     * @return ChainOfResponsibilityBuilder
     */
    private AbstractFoundPatternBuilder buildChainOfResponsibilityBuilder(final ChainOfResponsibility pattern) {

        List<String> links = new ArrayList<>();


        for (ClassOrInterface link : pattern.getChainLinks()) {
            links.add(link.getName());
        }

        return new ChainOfResponsibilityFoundPatternBuilder(pattern.getCommonParent().getName(), links);
    }
}
