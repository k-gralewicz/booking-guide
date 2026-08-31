package pl.gralewicz.kamil.java.app.bookingguide.api;

public enum VisitStatusType {
    NEW("nowa", 0),
    CONFIRMED("potwierdzona", 1),
    COMPLETED("zakończona", 2);

    private String label;
    private int defaultValue;

    VisitStatusType(String label, int defaultValue) {
        this.label = label;
        this.defaultValue = defaultValue;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(int defaultValue) {
        this.defaultValue = defaultValue;
    }
}
