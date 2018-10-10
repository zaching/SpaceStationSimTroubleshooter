package spacestation.lifesupport;

public enum StatusCode {
    NOMINAL("Nominal",1),
    WARNING("Warning",2),
    ERROR("Broken",3),
    CRITICAL("Critical Failure",4);


    private final String text;
    private final int severity;

    StatusCode(String text, int severity) {
        this.text = text;
        this.severity = severity;
    }

    public String getText() {
        return this.text;
    }

    public boolean moreSevere(StatusCode otherStatus) {
        return otherStatus.severity < severity;
    }

    public boolean lessSevere(StatusCode otherStatus) {
        return otherStatus.severity > severity;
    }

    public StatusCode getWorseStatus(StatusCode otherStatus) { return moreSevere(otherStatus) ? this : otherStatus; }

    public String toString() {
        return getText();
    }
}
