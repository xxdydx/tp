package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class CategoryMatchesPredicateTest {

    @Test
    public void equals() {
        String firstCategory = "florist";
        String secondCategory = "caterer";

        CategoryMatchesPredicate firstPredicate = new CategoryMatchesPredicate(firstCategory);
        CategoryMatchesPredicate secondPredicate = new CategoryMatchesPredicate(secondCategory);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CategoryMatchesPredicate firstPredicateCopy = new CategoryMatchesPredicate(firstCategory);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different category -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsCategory_returnsTrue() {
        // Exact match
        CategoryMatchesPredicate predicate = new CategoryMatchesPredicate("florist");
        assertTrue(predicate.test(new PersonBuilder().withTags("florist").build()));

        // Multiple tags, one matches
        predicate = new CategoryMatchesPredicate("florist");
        assertTrue(predicate.test(new PersonBuilder().withTags("florist", "premium").build()));

        // Mixed-case category
        predicate = new CategoryMatchesPredicate("FLORIST");
        assertTrue(predicate.test(new PersonBuilder().withTags("florist").build()));

        predicate = new CategoryMatchesPredicate("florist");
        assertTrue(predicate.test(new PersonBuilder().withTags("FLORIST").build()));

        predicate = new CategoryMatchesPredicate("FLoRiSt");
        assertTrue(predicate.test(new PersonBuilder().withTags("florist").build()));
    }

    @Test
    public void test_tagDoesNotContainCategory_returnsFalse() {
        // No tags
        CategoryMatchesPredicate predicate = new CategoryMatchesPredicate("florist");
        assertFalse(predicate.test(new PersonBuilder().withTags().build()));

        // Non-matching category
        predicate = new CategoryMatchesPredicate("florist");
        assertFalse(predicate.test(new PersonBuilder().withTags("caterer").build()));

        // Multiple non-matching tags
        predicate = new CategoryMatchesPredicate("florist");
        assertFalse(predicate.test(new PersonBuilder().withTags("caterer", "photographer").build()));

        // Partial match does not count
        predicate = new CategoryMatchesPredicate("flor");
        assertFalse(predicate.test(new PersonBuilder().withTags("florist").build()));
    }

    @Test
    public void toStringMethod() {
        String category = "florist";
        CategoryMatchesPredicate predicate = new CategoryMatchesPredicate(category);

        String expected = CategoryMatchesPredicate.class.getCanonicalName() + "{category=" + category + "}";
        assertEquals(expected, predicate.toString());
    }
}

