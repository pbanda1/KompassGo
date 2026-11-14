package hr.algebra.kompassgo.model.enums;

public enum Priority {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High");

    private final String displayName;

    Priority(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    // ComboBox and TableView show the display name instead of ENUM_NAME
    @Override
    public String toString() {
        return displayName;
    }
}
