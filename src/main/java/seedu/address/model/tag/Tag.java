package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in
 * {@link #isValidTagName(String)}
 */
public class Tag {

    public static final int MAX_LENGTH = 30;
    public static final String MESSAGE_CONSTRAINTS =
            "Categories must only contain alphanumeric characters and can contain "
            + "spaces, hyphens, ampersands, periods, apostrophes, slashes, and parentheses. "
            + "Categories should start with an alphanumeric character and be at most " + MAX_LENGTH + " characters. "
            + "Enter a blank category to remove a category.";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} \\-&.'()/\\u0020]+";

    public final String tagName;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        this.tagName = tagName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX) && test.length() <= MAX_LENGTH;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Tag)) {
            return false;
        }

        Tag otherTag = (Tag) other;
        return tagName.equals(otherTag.tagName);
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }

}
