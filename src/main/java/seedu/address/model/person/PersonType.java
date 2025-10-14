package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

public enum PersonType {
    CLIENT, VENDOR;

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

    public String display() {
        String s = toString();
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}
