package seedu.address.model.person;


import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a person's partner name in the address book.
 * */
public class Partner {
    public static final String MESSAGE_CONSTRAINTS = "Partner name must be 1–100 visible chars.";
    public final String value;

    /**
     * Constructs a {@code Partner} with the given name.
     * */
    public Partner(String value) {
        requireNonNull(value);
        checkArgument(!value.isBlank() && value.strip().length() <= 100, MESSAGE_CONSTRAINTS);
        this.value = value.strip();
    }
    @Override public String toString() {
        return value;
    }

    @Override public boolean equals(Object o) {
        return o instanceof Partner p && p.value.equals(value);
    }
    @Override public int hashCode() {
        return value.hashCode();
    }
}
