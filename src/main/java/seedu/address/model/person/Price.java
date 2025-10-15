package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Vendor's price in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(String)}
 */
public class Price {

    public static final String MESSAGE_CONSTRAINTS = "Invalid amount. Provide a number or a range like 800-1500.";

    /*
     * Accepts:
     * - Single number: "1200", "$1200"
     * - Range with hyphen: "800-1500", "$800-$1500"
     * - Range with en-dash: "800–1500"
     * Numbers can have optional $ prefix and optional commas
     */
    public static final String VALIDATION_REGEX = "^\\$?\\d{1,10}(,\\d{3})*([–\\-]\\$?\\d{1,10}(,\\d{3})*)?$";

    public final String value;

    /**
     * Constructs a {@code Price}.
     *
     * @param price A valid price.
     */
    public Price(String price) {
        requireNonNull(price);
        String trimmed = price.trim();
        checkArgument(isValidPrice(trimmed), MESSAGE_CONSTRAINTS);
        value = trimmed;
    }

    /**
     * Returns true if a given string is a valid price.
     */
    public static boolean isValidPrice(String test) {
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
        if (!(other instanceof Price)) {
            return false;
        }

        Price otherPrice = (Price) other;
        return value.equals(otherPrice.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
