package nl.windesheim.reporting.builders;

import nl.windesheim.codeparser.patterns.Strategy;
import nl.windesheim.reporting.DesignPatternType;
import nl.windesheim.reporting.components.AbstractFoundPatternBuilder;
import nl.windesheim.reporting.components.FoundPatternReport;
import nl.windesheim.reporting.components.IFoundPatternReport;
import nl.windesheim.reporting.components.Result;
import nl.windesheim.reporting.decorators.HasClassList;
import nl.windesheim.reporting.decorators.HasClassOrInterface;

/**
 * Strategy pattern found builder.
 */
public class StrategyFoundPatternBuilder extends AbstractFoundPatternBuilder {

    /**
     * Strategy pattern.
     */
    private final Strategy pattern;

    /**
     * Set the required parameters for the builder.
     *
     * @param pattern the pattern for which we want to build the report
     */
    public StrategyFoundPatternBuilder(
            final Strategy pattern
    ) {
        super();
        this.pattern = pattern;
    }


    @Override
    public IFoundPatternReport buildReport() {
        FoundPatternReport patternReport = new FoundPatternReport();
        patternReport.setDesignPatternType(DesignPatternType.STRATEGY);

        int errors = 0;

        if (!pattern.isContextHasCaller()) {
            errors++;
            patternReport.addPatternError("The context doesn't call the strategy");
        }

        if (!pattern.isContextHasSetter()) {
            errors++;
            patternReport.addPatternError("The context has no setter for the strategy");
        }

        if (!pattern.isStrategyMethods()) {
            errors++;
            patternReport.addPatternError("The strategy interface has no methods declared");
        }

        if (errors > 0) {
            patternReport.setCertainty(Result.Certainty.LIKELY);
        }

        if (errors > 1) {
            patternReport.setCertainty(Result.Certainty.UNLIKELY);
        }

        HasClassList strategies = new HasClassList(patternReport);
        strategies.setName("Strategies");
        strategies.setClasses(pattern.getStrategies());

        HasClassOrInterface hasContext = new HasClassOrInterface(strategies);
        hasContext.setName("Context");
        hasContext.setClassOrInterface(pattern.getContext());

        HasClassOrInterface hasInterface = new HasClassOrInterface(hasContext);
        hasInterface.setName("Strategy interface");
        hasInterface.setClassOrInterface(pattern.getStrategyInterface());

        return hasInterface;
    }
}
