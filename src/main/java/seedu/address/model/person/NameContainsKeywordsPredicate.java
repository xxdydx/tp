package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Matches if a {@code Person}'s name OR (for CLIENTs) their partner's name
 * contains any of the given keyword substrings (case-insensitive).
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        final String selfName = person.getName() == null ? "" : person.getName().toString();

        final String partnerName =
                person.getType() == PersonType.CLIENT
                        ? person.getPartner().map(Partner::toString).orElse("")
                        : "";

        return keywords.stream()
                .map(String::trim)
                .filter(k -> !k.isEmpty())
                .anyMatch(k -> containsSubstringIgnoreCase(selfName, k)
                        || containsSubstringIgnoreCase(partnerName, k));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof NameContainsKeywordsPredicate
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }

    private static boolean containsSubstringIgnoreCase(String haystack, String needle) {
        if (haystack == null || needle == null) {
            return false;
        }
        return haystack.toLowerCase().contains(needle.toLowerCase());
    }
}
