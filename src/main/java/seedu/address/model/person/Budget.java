package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Client's budget in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBudget(String)}
 */
public class Budget {

    public static final String MESSAGE_CONSTRAINTS = "Invalid amount. Provide a number or a range like 800-1500.";

    /*
     * Accepts:
     * - Single number: "1200", "$1200"
     * - Range with hyphen: "800-1500", "$800-$1500"
     * - Range with en-dash: "800–1500"
     * Numbers can have optional $ prefix and optional commas
     */
    public static final String VALIDATION_REGEX = "^\\$?\\d{1,10}(,\\d{3})*"
            + "([–\\-]\\$?\\d{1,10}(,\\d{3})*)?$";

    public final String value;

    /**
     * Constructs a {@code Budget}.
     *
     * @param budget A valid budget.
     */
    public Budget(String budget) {
        requireNonNull(budget);
        String trimmed = budget.trim();
        checkArgument(isValidBudget(trimmed), MESSAGE_CONSTRAINTS);
        value = trimmed;
    }

    /**
     * Returns true if a given string is a valid budget.
     */
    public static boolean isValidBudget(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }

        // If it's a range, validate that min <= max
        if (test.contains("-") || test.contains("–")) {
            String[] parts = test.split("[–\\-]");
            if (parts.length == 2) {
                try {
                    // Remove $ and commas to parse numbers
                    long min = Long.parseLong(parts[0].replaceAll("[\\$,]", ""));
                    long max = Long.parseLong(parts[1].replaceAll("[\\$,]", ""));
                    return min <= max;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        }

        return true;
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
        if (!(other instanceof Budget)) {
            return false;
        }

        Budget otherBudget = (Budget) other;
        return value.equals(otherBudget.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
