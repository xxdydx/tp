package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonType;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * LinkCommand.
 */
public class LinkCommandTest {

    private Model model;
    private Person client;
    private Person vendor;

    @BeforeEach
    public void setUp() {
        // Create test persons with specific types
        client = new PersonBuilder().withName("Alice Client")
                .withPhone("11111111")
                .withEmail("alice@example.com")
                .withAddress("123 Client Street")
                .withWeddingDate("01/01/2024")
                .withType(PersonType.CLIENT)
                .build();

        vendor = new PersonBuilder().withName("Bob Vendor")
                .withPhone("22222222")
                .withEmail("bob@example.com")
                .withAddress("456 Vendor Avenue")
                .withWeddingDate("02/02/2024")
                .withType(PersonType.VENDOR)
                .build();

        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(client);
        addressBook.addPerson(vendor);

        model = new ModelManager(addressBook, new UserPrefs());
    }

    @Test
    public void execute_validIndexes_success() {
        LinkCommand linkCommand = new LinkCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        String expectedMessage = LinkCommand.MESSAGE_LINK_SUCCESS;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Person linkedClient = new PersonBuilder(client).build();
        Person linkedVendor = new PersonBuilder(vendor).build();

        // Manually link them in expected model
        Person updatedClient = new Person(client.getName(), client.getPhone(), client.getEmail(),
                client.getAddress(), client.getWeddingDate(), client.getType(), client.getTags(),
                java.util.Set.of(vendor), null, client.getPartner());
        Person updatedVendor = new Person(vendor.getName(), vendor.getPhone(), vendor.getEmail(),
                vendor.getAddress(), vendor.getWeddingDate(), vendor.getType(), vendor.getTags(),
                java.util.Set.of(client), null, vendor.getPartner());
        expectedModel.setPerson(client, updatedClient);
        expectedModel.setPerson(vendor, updatedVendor);

        assertCommandSuccess(linkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidClientIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        LinkCommand linkCommand = new LinkCommand(outOfBoundIndex, INDEX_SECOND_PERSON);

        assertCommandFailure(linkCommand, model, LinkCommand.MESSAGE_INVALID_CLIENT_INDEX);
    }

    @Test
    public void execute_invalidVendorIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        LinkCommand linkCommand = new LinkCommand(INDEX_FIRST_PERSON, outOfBoundIndex);

        assertCommandFailure(linkCommand, model, LinkCommand.MESSAGE_INVALID_VENDOR_INDEX);
    }

    @Test
    public void execute_clientNotClient_throwsCommandException() {
        // Create a vendor at index 1 and client at index 2
        AddressBook addressBook = new AddressBook();
        Person vendorFirst = new PersonBuilder().withName("Vendor First")
                .withType(PersonType.VENDOR).build();
        Person clientSecond = new PersonBuilder().withName("Client Second")
                .withType(PersonType.CLIENT).build();
        addressBook.addPerson(vendorFirst);
        addressBook.addPerson(clientSecond);
        Model testModel = new ModelManager(addressBook, new UserPrefs());

        // Try to link with first index (vendor) as client
        LinkCommand linkCommand = new LinkCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        assertCommandFailure(linkCommand, testModel, LinkCommand.MESSAGE_INVALID_CLIENT_INDEX);
    }

    @Test
    public void execute_vendorNotVendor_throwsCommandException() {
        // Create client at index 1 and another client at index 2
        AddressBook addressBook = new AddressBook();
        Person clientFirst = new PersonBuilder().withName("Client First")
                .withType(PersonType.CLIENT).build();
        Person clientSecond = new PersonBuilder().withName("Client Second")
                .withType(PersonType.CLIENT).build();
        addressBook.addPerson(clientFirst);
        addressBook.addPerson(clientSecond);
        Model testModel = new ModelManager(addressBook, new UserPrefs());

        // Try to link with second index (client) as vendor
        LinkCommand linkCommand = new LinkCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        assertCommandFailure(linkCommand, testModel, LinkCommand.MESSAGE_INVALID_VENDOR_INDEX);
    }

    @Test
    public void execute_alreadyLinked_throwsCommandException() {
        // First link them
        LinkCommand firstLinkCommand = new LinkCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        try {
            firstLinkCommand.execute(model);
        } catch (Exception e) {
            // Should not fail
        }

        // Try to link again
        LinkCommand secondLinkCommand = new LinkCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        assertCommandFailure(secondLinkCommand, model, LinkCommand.MESSAGE_LINK_ALREADY_EXISTS);
    }

    @Test
    public void equals() {
        LinkCommand linkFirstSecondCommand = new LinkCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        LinkCommand linkSecondThirdCommand = new LinkCommand(INDEX_SECOND_PERSON, INDEX_THIRD_PERSON);

        // same object -> returns true
        assertTrue(linkFirstSecondCommand.equals(linkFirstSecondCommand));

        // same values -> returns true
        LinkCommand linkFirstSecondCommandCopy = new LinkCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        assertTrue(linkFirstSecondCommand.equals(linkFirstSecondCommandCopy));

        // different types -> returns false
        assertFalse(linkFirstSecondCommand.equals(1));

        // null -> returns false
        assertFalse(linkFirstSecondCommand.equals(null));

        // different indexes -> returns false
        assertFalse(linkFirstSecondCommand.equals(linkSecondThirdCommand));
    }

    @Test
    public void toStringMethod() {
        Index clientIndex = Index.fromOneBased(1);
        Index vendorIndex = Index.fromOneBased(2);
        LinkCommand linkCommand = new LinkCommand(clientIndex, vendorIndex);
        String expected = LinkCommand.class.getCanonicalName()
                + "{clientIndex=" + clientIndex + ", vendorIndex=" + vendorIndex + "}";
        assertEquals(expected, linkCommand.toString());
    }
}
