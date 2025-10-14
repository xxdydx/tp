package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Enumerates the canonical types for a {@link Person}: {@code CLIENT} or {@code VENDOR}.
 * */
public enum PersonType {
    CLIENT, VENDOR;

    /**
     * Parses a raw string into a {@link PersonType}.
     * */
    public static PersonType parse(String raw) {
        requireNonNull(raw);
        return switch (raw.trim().toLowerCase()) {
        case "client" -> CLIENT;
        case "vendor" -> VENDOR;
        default -> throw new IllegalArgumentException("Type must be 'client' or 'vendor'.");
        };
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    /**
     * Returns a nicely styled label for UI display.
     * */
    public String display() {
        String s = toString();
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}
