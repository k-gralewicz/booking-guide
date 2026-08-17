package pl.gralewicz.kamil.java.app.bookingguide.api;

public enum ServiceStatusType {
    INACTIVE("nieaktywna", 0),
    ACTIVE("aktywna", 1);
//    PROMOTION,IN_PREPARATION,SUSPENDED,ARCHIVED;

//    ACTIVE- Usługa widoczna dla klientów i możliwa do rezerwacji.
//    INACTIVE- Usługa jest tymczasowo niedostępna, np. sezonowa. Może być ponownie aktywowana.
//    PROMOTION- Usługa aktywna, ale w specjalnej, promocyjnej ofercie.
//    IN_PREPARATION- Nowa usługa, jeszcze niedostępna dla klientów.
//    SUSPENDED- Usługa niedostępna z powodu braku specjalisty lub sprzętu.
//    ARCHIVED- Usługa historyczna, która nie będzie już oferowana. Nie można jej przywrócić.

    private String label;
    private int defaultValue;

    ServiceStatusType(String label, int defaultValue) {
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
