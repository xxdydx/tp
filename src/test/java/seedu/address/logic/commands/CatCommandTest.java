package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.CategoryMatchesPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code CatCommand}.
 */
public class CatCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        CategoryMatchesPredicate firstPredicate = new CategoryMatchesPredicate("florist");
        CategoryMatchesPredicate secondPredicate = new CategoryMatchesPredicate("caterer");

        CatCommand catFirstCommand = new CatCommand(firstPredicate);
        CatCommand catSecondCommand = new CatCommand(secondPredicate);

        // same object -> returns true
        assertTrue(catFirstCommand.equals(catFirstCommand));

        // same values -> returns true
        CatCommand catFirstCommandCopy = new CatCommand(firstPredicate);
        assertTrue(catFirstCommand.equals(catFirstCommandCopy));

        // different types -> returns false
        assertFalse(catFirstCommand.equals(1));

        // null -> returns false
        assertFalse(catFirstCommand.equals(null));

        // different category -> returns false
        assertFalse(catFirstCommand.equals(catSecondCommand));
    }

    @Test
    public void execute_categoryNotFound_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        CategoryMatchesPredicate predicate = new CategoryMatchesPredicate("nonexistent");
        CatCommand command = new CatCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_categoryFound_multiplePersonsFound() {
        // Add some test persons with category tags (vendors only can have tags)
        Person florist1 = new PersonBuilder().withName("John Flowers").withPhone("91234567")
                .withEmail("john@flowers.com").withAddress("123 Street")
                .withType(seedu.address.model.person.PersonType.VENDOR)
                .withTags("florist").build();
        Person florist2 = new PersonBuilder().withName("Mary Blooms").withPhone("91234568")
                .withEmail("mary@blooms.com").withAddress("456 Avenue")
                .withType(seedu.address.model.person.PersonType.VENDOR)
                .withTags("florist", "premium").build();
        Person caterer = new PersonBuilder().withName("Best Catering").withPhone("91234569")
                .withEmail("best@catering.com").withAddress("789 Road")
                .withType(seedu.address.model.person.PersonType.VENDOR)
                .withTags("caterer").build();

        model.addPerson(florist1);
        model.addPerson(florist2);
        model.addPerson(caterer);
        expectedModel.addPerson(florist1);
        expectedModel.addPerson(florist2);
        expectedModel.addPerson(caterer);

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        CategoryMatchesPredicate predicate = new CategoryMatchesPredicate("florist");
        CatCommand command = new CatCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(florist1, florist2), model.getFilteredPersonList());
    }

    @Test
    public void execute_categoryFoundCaseInsensitive_personsFound() {
        // Add test person with lowercase category tag (vendors only can have tags)
        Person florist = new PersonBuilder().withName("John Flowers").withPhone("91234567")
                .withEmail("john@flowers.com").withAddress("123 Street")
                .withType(seedu.address.model.person.PersonType.VENDOR)
                .withTags("florist").build();

        model.addPerson(florist);
        expectedModel.addPerson(florist);

        // Search with uppercase
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        CategoryMatchesPredicate predicate = new CategoryMatchesPredicate("FLORIST");
        CatCommand command = new CatCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(florist), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        CategoryMatchesPredicate predicate = new CategoryMatchesPredicate("florist");
        CatCommand catCommand = new CatCommand(predicate);
        String expected = CatCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, catCommand.toString());
    }
}

