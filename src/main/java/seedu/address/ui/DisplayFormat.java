package seedu.address.ui;

import seedu.address.model.person.Person;

/**
 * UI-facing formatting helpers.
 * */
public final class DisplayFormat {
    private DisplayFormat() {}

    /**
     * Returns a display string for a person in the form
     * <pre>{@code "Name & Partner"}</pre>
     * when a partner is present, or just
     * <pre>{@code "Name"}</pre>
     * when no partner is set.
     * <p>
     */
    public static String nameAndPartner(Person person) {
        return person.getPartner().map(partner -> person.getName() + " & " + partner)
                .orElse(person.getName().toString());
    }
}
