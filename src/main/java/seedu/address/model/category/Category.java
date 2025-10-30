package seedu.address.model.category;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Category in the address book.
 * Guarantees: immutable; name is valid as declared in
 * {@link #isValidCategoryName(String)}
 */
public class Category {

    public static final String MESSAGE_CONSTRAINTS =
            "Category must start with an alphanumeric character, can contain alphanumeric "
            + "characters, spaces, hyphens, ampersands, periods, apostrophes, slashes, "
            + "and parentheses, and must be between 2 and 30 characters long.";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} \\-&.'/()\u0020]+";

    public final String categoryName;

    /**
     * Constructs a {@code Category}.
     *
     * @param categoryName A valid category name.
     */
    public Category(String categoryName) {
        requireNonNull(categoryName);
        checkArgument(isValidCategoryName(categoryName), MESSAGE_CONSTRAINTS);
        this.categoryName = categoryName;
    }

    /**
     * Returns true if a given string is a valid category name.
     */
    public static boolean isValidCategoryName(String test) {
        requireNonNull(test);
        return test.length() >= 2 && test.length() <= 30 && test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Category)) {
            return false;
        }

        Category otherCategory = (Category) other;
        return categoryName.equals(otherCategory.categoryName);
    }

    @Override
    public int hashCode() {
        return categoryName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return categoryName;
    }

}
