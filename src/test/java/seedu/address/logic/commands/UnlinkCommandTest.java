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
 * UnlinkCommand.
 */
public class UnlinkCommandTest {

    private Model model;
    private Person client;
    private Person vendor;

    @BeforeEach
    public void setUp() {
        // Create test persons with specific types and links
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

        // Create linked persons
        Person linkedClient = new Person(client.getName(), client.getPhone(), client.getEmail(),
                client.getAddress(), client.getWeddingDate(), client.getType(), client.getTags(),
                java.util.Set.of(vendor), null, client.getPartner());
        Person linkedVendor = new Person(vendor.getName(), vendor.getPhone(), vendor.getEmail(),
                vendor.getAddress(), vendor.getWeddingDate(), vendor.getType(), vendor.getTags(),
                java.util.Set.of(client), null, vendor.getPartner());

        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(linkedClient);
        addressBook.addPerson(linkedVendor);

        model = new ModelManager(addressBook, new UserPrefs());
    }

    @Test
    public void execute_validIndexes_success() {
        UnlinkCommand unlinkCommand = new UnlinkCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        String expectedMessage = UnlinkCommand.MESSAGE_UNLINK_SUCCESS;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Person currentClient = expectedModel.getFilteredPersonList().get(0);
        Person currentVendor = expectedModel.getFilteredPersonList().get(1);

        // Unlink them in expected model
        Person updatedClient = new Person(currentClient.getName(), currentClient.getPhone(),
                currentClient.getEmail(), currentClient.getAddress(), currentClient.getWeddingDate(),
                currentClient.getType(), currentClient.getTags(), java.util.Set.of(), null, currentClient.getPartner());
        Person updatedVendor = new Person(currentVendor.getName(), currentVendor.getPhone(),
                currentVendor.getEmail(), currentVendor.getAddress(), currentVendor.getWeddingDate(),
                currentVendor.getType(), currentVendor.getTags(), java.util.Set.of(), null, currentVendor.getPartner());
        expectedModel.setPerson(currentClient, updatedClient);
        expectedModel.setPerson(currentVendor, updatedVendor);

        assertCommandSuccess(unlinkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidClientIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnlinkCommand unlinkCommand = new UnlinkCommand(outOfBoundIndex, INDEX_SECOND_PERSON);

        assertCommandFailure(unlinkCommand, model, UnlinkCommand.MESSAGE_INVALID_CLIENT_INDEX);
    }

    @Test
    public void execute_invalidVendorIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnlinkCommand unlinkCommand = new UnlinkCommand(INDEX_FIRST_PERSON, outOfBoundIndex);

        assertCommandFailure(unlinkCommand, model, UnlinkCommand.MESSAGE_INVALID_VENDOR_INDEX);
    }

    @Test
    public void execute_clientNotClient_throwsCommandException() {
        // Create a vendor at index 1 and client at index 2, already linked
        Person vendorFirst = new PersonBuilder().withName("Vendor First")
                .withType(PersonType.VENDOR).build();
        Person clientSecond = new PersonBuilder().withName("Client Second")
                .withType(PersonType.CLIENT).build();

        Person linkedVendor = new Person(vendorFirst.getName(), vendorFirst.getPhone(),
                vendorFirst.getEmail(), vendorFirst.getAddress(), vendorFirst.getWeddingDate(),
                vendorFirst.getType(), vendorFirst.getTags(), java.util.Set.of(clientSecond), null,
                vendorFirst.getPartner());
        Person linkedClient = new Person(clientSecond.getName(), clientSecond.getPhone(),
                clientSecond.getEmail(), clientSecond.getAddress(), clientSecond.getWeddingDate(),
                clientSecond.getType(), clientSecond.getTags(), java.util.Set.of(vendorFirst), null,
                clientSecond.getPartner());

        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(linkedVendor);
        addressBook.addPerson(linkedClient);
        Model testModel = new ModelManager(addressBook, new UserPrefs());

        // Try to unlink with first index (vendor) as client
        UnlinkCommand unlinkCommand = new UnlinkCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        assertCommandFailure(unlinkCommand, testModel, UnlinkCommand.MESSAGE_INVALID_CLIENT_INDEX);
    }

    @Test
    public void execute_vendorNotVendor_throwsCommandException() {
        // Create client at index 1 and another client at index 2, already linked
        Person clientFirst = new PersonBuilder().withName("Client First")
                .withType(PersonType.CLIENT).build();
        Person clientSecond = new PersonBuilder().withName("Client Second")
                .withType(PersonType.CLIENT).build();

        Person linkedClient1 = new Person(clientFirst.getName(), clientFirst.getPhone(),
                clientFirst.getEmail(), clientFirst.getAddress(), clientFirst.getWeddingDate(),
                clientFirst.getType(), clientFirst.getTags(), java.util.Set.of(clientSecond), null,
                clientFirst.getPartner());
        Person linkedClient2 = new Person(clientSecond.getName(), clientSecond.getPhone(),
                clientSecond.getEmail(), clientSecond.getAddress(), clientSecond.getWeddingDate(),
                clientSecond.getType(), clientSecond.getTags(), java.util.Set.of(clientFirst), null,
                clientSecond.getPartner());

        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(linkedClient1);
        addressBook.addPerson(linkedClient2);
        Model testModel = new ModelManager(addressBook, new UserPrefs());

        // Try to unlink with second index (client) as vendor
        UnlinkCommand unlinkCommand = new UnlinkCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        assertCommandFailure(unlinkCommand, testModel, UnlinkCommand.MESSAGE_INVALID_VENDOR_INDEX);
    }

    @Test
    public void execute_notLinked_throwsCommandException() {
        // Create unlinked client and vendor
        Person unlinkClient = new PersonBuilder().withName("Unlinked Client")
                .withType(PersonType.CLIENT).build();
        Person unlinkVendor = new PersonBuilder().withName("Unlinked Vendor")
                .withType(PersonType.VENDOR).build();

        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(unlinkClient);
        addressBook.addPerson(unlinkVendor);
        Model testModel = new ModelManager(addressBook, new UserPrefs());

        // Try to unlink when they're not linked
        UnlinkCommand unlinkCommand = new UnlinkCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        assertCommandFailure(unlinkCommand, testModel, UnlinkCommand.MESSAGE_LINK_DOES_NOT_EXIST);
    }

    @Test
    public void equals() {
        UnlinkCommand unlinkFirstSecondCommand = new UnlinkCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        UnlinkCommand unlinkSecondThirdCommand = new UnlinkCommand(INDEX_SECOND_PERSON, INDEX_THIRD_PERSON);

        // same object -> returns true
        assertTrue(unlinkFirstSecondCommand.equals(unlinkFirstSecondCommand));

        // same values -> returns true
        UnlinkCommand unlinkFirstSecondCommandCopy = new UnlinkCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        assertTrue(unlinkFirstSecondCommand.equals(unlinkFirstSecondCommandCopy));

        // different types -> returns false
        assertFalse(unlinkFirstSecondCommand.equals(1));

        // null -> returns false
        assertFalse(unlinkFirstSecondCommand.equals(null));

        // different indexes -> returns false
        assertFalse(unlinkFirstSecondCommand.equals(unlinkSecondThirdCommand));
    }

    @Test
    public void toStringMethod() {
        Index clientIndex = Index.fromOneBased(1);
        Index vendorIndex = Index.fromOneBased(2);
        UnlinkCommand unlinkCommand = new UnlinkCommand(clientIndex, vendorIndex);
        String expected = UnlinkCommand.class.getCanonicalName()
                + "{clientIndex=" + clientIndex + ", vendorIndex=" + vendorIndex + "}";
        assertEquals(expected, unlinkCommand.toString());
    }
}
