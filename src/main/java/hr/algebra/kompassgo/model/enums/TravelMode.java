package hr.algebra.kompassgo.model.enums;

public enum TravelMode {
    WALKING("Walking"),
    BICYCLE("Bicycle"),
    PUBLIC_TRANSPORT("Public transport"),
    CAR("Car");

    private final String displayName;

    TravelMode(String displayName) {
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
