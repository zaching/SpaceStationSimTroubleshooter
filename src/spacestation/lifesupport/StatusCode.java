package spacestation.lifesupport;

public enum StatusCode {
    NOMINAL("Nominal"),
    WARNING("Warning"),
    ERROR("Error"),
    CRITCAL_FAILURE("Critical Failure");


    private final String text;

    StatusCode(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public String toString() {
        return getText();
    }
}
