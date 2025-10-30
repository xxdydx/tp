package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonType;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command."
            + "\n\nType 'help' for information on valid commands.";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_DUPLICATE_FIELDS =
            "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns a message indicating the number of persons listed with correct
     * pluralization.
     */
    public static String getPersonsListedMessage(int count) {
        return count == 1 ? "1 person listed!" : String.format("%d persons listed!", count);
    }

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields = Stream.of(duplicatePrefixes).map(Prefix::toString)
                .collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Type: ")
                .append(person.getType())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress());

        // Add wedding date if present (clients only)
        person.getWeddingDate()
                .ifPresent(weddingDate -> builder.append("; Wedding Date: ").append(weddingDate));

        // Add price if present (vendors only)
        person.getPrice().ifPresent(price -> builder.append("; Price: ").append(price));

        // Add budget if present (clients only)
        person.getBudget().ifPresent(budget -> builder.append("; Budget: ").append(budget));

        // Add partner if present (clients only)
        person.getPartner().ifPresent(partner -> builder.append("; Partner: ").append(partner));

        // Add categories if person is a vendor and has categories
        if (person.getType() == PersonType.VENDOR && !person.getCategories().isEmpty()) {
            builder.append("; Categories: ");
            String categories = person.getCategories().stream()
                    .map(category -> category.categoryName)
                    .collect(Collectors.joining(", "));
            builder.append(categories);
        }

        return builder.toString();
    }

}
