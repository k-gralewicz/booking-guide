package pl.gralewicz.kamil.java.app.bookingguide.controller.model;

public enum DayOfWeek {
    MONDAY(1L, "Monday"),
    TUESDAY(2L, "Tuesday"),
    WEDNESDAY(3L, "Wednesday"),
    THURSDAY(4L, "Thursday"),
    FRIDAY(5L, "Friday"),
    SATURDAY(6L, "Saturday"),
    SUNDAY(7L, "Sunday");

    private final Long id;
    private final String name;

    DayOfWeek(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "DayOfWeek{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}