package nl.windesheim.codeparser.patterns.properties;

public class ObserverPatternProperties {
    private boolean obsableHasDetach;

    private boolean obsHasObsable;

    private boolean obsHasAttachCall;

    private boolean obsHasDetachCall;

    private boolean updateHasArgs;

    public ObserverPatternProperties () {
        obsableHasDetach = false;
        obsHasObsable = false;
        obsHasDetachCall = false;
        obsHasAttachCall = false;
    }

    public boolean isSubjectHasDetach() {
        return obsableHasDetach;
    }

    public ObserverPatternProperties setSubjectHasDetach(final boolean obsableHasDetach) {
        if (!this.obsableHasDetach) {
            this.obsableHasDetach = obsableHasDetach;
        }

        return this;
    }

    public boolean isObserverHasSubject() {
        return obsHasObsable;
    }

    public ObserverPatternProperties setObserverHasSubject(final boolean obsHasObsable) {
        if (!this.obsHasObsable) {
            this.obsHasObsable = obsHasObsable;
        }

        return this;
    }

    public boolean isObserverHasAttachCall () {
        return obsHasAttachCall;
    }

    public ObserverPatternProperties setObserverHasAttachCall (final boolean obsHasAttachCall) {
        if (!this.obsHasAttachCall) {
            this.obsHasAttachCall = obsHasAttachCall;
        }

        return this;
    }

    public boolean isObserverHasDetachCall () {
        return obsHasDetachCall;
    }

    public ObserverPatternProperties setObserverHasDetachCall (final boolean obsHasDetachCall) {
        if (!this.obsHasDetachCall) {
            this.obsHasDetachCall = obsHasDetachCall;
        }

        return this;
    }

    public boolean isUpdateHasArguments() {
        return updateHasArgs;
    }

    public ObserverPatternProperties setUpdateHasArguments(final boolean updateHasArgs) {
        this.updateHasArgs = updateHasArgs;
        return this;
    }
}
