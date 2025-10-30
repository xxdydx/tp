package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommand = new DeleteCommand(targetIndex);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    @Test
    public void execute_deletePersonWithLinkedPersons_removesFromLinkedPersons() {
        // Create a fresh model with linked persons
        AddressBook ab = new AddressBook();
        Model model = new ModelManager(ab, new UserPrefs());

        // Create a client
        Person client = new PersonBuilder().withName("John Client")
                .withPhone("91234567").withEmail("john@client.com")
                .withAddress("123 Client St")
                .withWeddingDate("01-01-2025")
                .withType(seedu.address.model.person.PersonType.CLIENT)
                .withPartner("Jane Smith")
                .build();

        // Create a vendor
        Person vendor = new PersonBuilder().withName("Alice Vendor")
                .withPhone("98765432").withEmail("alice@vendor.com")
                .withAddress("456 Vendor Ave")
                .withType(seedu.address.model.person.PersonType.VENDOR)
                .withPrice("1000-2000")
                .build();

        // Add both to the address book first
        model.addPerson(client);
        model.addPerson(vendor);

        // Now create linked versions
        Set<Person> clientLinks = new HashSet<>();
        clientLinks.add(vendor);

        Set<Person> vendorLinks = new HashSet<>();
        vendorLinks.add(client);

        // Create updated persons with links
        Person linkedClient = new PersonBuilder(client).withLinkedPersons(clientLinks).build();
        Person linkedVendor = new PersonBuilder(vendor).withLinkedPersons(vendorLinks).build();

        // Update both in the model
        model.setPerson(client, linkedClient);
        model.setPerson(vendor, linkedVendor);

        // Verify the link exists before deletion
        Person clientInModel = model.getFilteredPersonList().stream()
                .filter(p -> p.getPhone().equals(linkedClient.getPhone()))
                .findFirst().get();
        assertTrue(clientInModel.isLinkedTo(linkedVendor));

        // Delete the vendor
        DeleteCommand deleteCommand = new DeleteCommand(Index.fromOneBased(2));
        try {
            deleteCommand.execute(model);
        } catch (Exception e) {
            throw new AssertionError("Execution of command should not fail.", e);
        }

        // Verify the client no longer has the vendor in linked persons
        Person updatedClient = model.getFilteredPersonList().stream()
                .filter(p -> p.getPhone().equals(linkedClient.getPhone()))
                .findFirst().get();
        assertFalse(updatedClient.isLinkedTo(linkedVendor));
        assertEquals(0, updatedClient.getLinkedPersons().size());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
