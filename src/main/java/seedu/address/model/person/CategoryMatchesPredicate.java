package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s tags contain the specified category.
 * Category matching is case insensitive.
 */
public class CategoryMatchesPredicate implements Predicate<Person> {
    private final String category;

    public CategoryMatchesPredicate(String category) {
        this.category = category;
    }

    @Override
    public boolean test(Person person) {
        return person.getCategories().stream()
                .anyMatch(category -> category.categoryName.equalsIgnoreCase(this.category));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CategoryMatchesPredicate)) {
            return false;
        }

        CategoryMatchesPredicate otherPredicate = (CategoryMatchesPredicate) other;
        return category.equals(otherPredicate.category);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("category", category).toString();
    }
}
