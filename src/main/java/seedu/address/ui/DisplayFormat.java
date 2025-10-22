package seedu.address.ui;

import seedu.address.model.person.Person;

public final class DisplayFormat {
    private DisplayFormat() {}
    public static String nameAndPartner(Person person) {
        return person.getPartner().map(partner -> person.getName() + " & " + partner)
                .orElse(person.getName().toString());
    }
}
