package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's wedding date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidWeddingDate(String)}
 */
public class WeddingDate {

    public static final String MESSAGE_CONSTRAINTS = "Wedding dates should be in DD-MM-YYYY format";

    /*
     * Wedding date must be in DD-MM-YYYY format.
     */
    public static final String VALIDATION_REGEX = "\\d{2}-\\d{2}-\\d{4}";

    public final String value;

    /**
     * Constructs a {@code WeddingDate}.
     *
     * @param weddingDate A valid wedding date.
     */
    public WeddingDate(String weddingDate) {
        requireNonNull(weddingDate);
        checkArgument(isValidWeddingDate(weddingDate), MESSAGE_CONSTRAINTS);
        value = weddingDate;
    }

    /**
     * Returns true if a given string is a valid wedding date.
     */
    public static boolean isValidWeddingDate(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof WeddingDate)) {
            return false;
        }

        WeddingDate otherWeddingDate = (WeddingDate) other;
        return value.equals(otherWeddingDate.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
