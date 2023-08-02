package pl.gralewicz.kamil.java.app.bookingguide.controller.model;

public enum DurationType {
    MINUTES("Minuty", 30),
    HOURS("Godziny", 1),
    DAYS("Dni", 0);

    private String label;
    private int defaultValue;

    DurationType() {
    }

    DurationType(String label, int defaultValue) {
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
