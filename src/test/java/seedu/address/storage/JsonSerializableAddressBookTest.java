package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonType;
import seedu.address.testutil.TypicalPersons;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAddressBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAddressBook.json");
    private static final Path SAME_NAME_DIFFERENT_PHONE_FILE =
            TEST_DATA_FOLDER.resolve("sameNameDifferentPhoneAddressBook.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalPersonsAddressBook = TypicalPersons.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalPersonsAddressBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_sameNameDifferentPhone_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(SAME_NAME_DIFFERENT_PHONE_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        // Should not throw exception as persons with same name but different phone numbers are allowed
        assertEquals(2, addressBookFromFile.getPersonList().size());
    }

    // Additional tests to improve coverage of link resolution and duplicate handling using in-memory adapters
    private JsonAdaptedPerson makeClient(String name, String phone) {
        return new JsonAdaptedPerson(
                name,
                phone,
                name.toLowerCase().replace(" ", "") + "@e.co",
                "1 Main St",
                "01-01-2025",
                PersonType.CLIENT.toString(),
                null,
                "1000-2000",
                "Pat Partner",
                Collections.emptyList(),
                Collections.emptyList());
    }

    private JsonAdaptedPerson makeVendor(String name, String phone, java.util.List<String> linkedNames) {
        return new JsonAdaptedPerson(
                name,
                phone,
                name.toLowerCase().replace(" ", "") + "@e.co",
                "2 Vendor Rd",
                null,
                PersonType.VENDOR.toString(),
                "500-1000",
                null,
                null,
                Collections.emptyList(),
                linkedNames);
    }

    @Test
    public void toModelType_duplicatePhone_inMemory_throws() {
        JsonAdaptedPerson a = makeClient("Alice A", "11111111");
        JsonAdaptedPerson b = makeVendor("Vendor V", "11111111", Collections.emptyList());
        JsonSerializableAddressBook book = new JsonSerializableAddressBook(Arrays.asList(a, b));
        org.junit.jupiter.api.Assertions.assertThrows(IllegalValueException.class, book::toModelType);
    }

    @Test
    public void toModelType_resolvesLinksWhenPresent_inMemory() throws Exception {
        JsonAdaptedPerson client = makeClient("Client C", "22222222");
        JsonAdaptedPerson vendor = makeVendor("Vendor V", "33333333", Collections.singletonList("Client C"));

        JsonSerializableAddressBook book = new JsonSerializableAddressBook(Arrays.asList(client, vendor));
        AddressBook model = book.toModelType();

        assertEquals(2, model.getPersonList().size());
        Person clientModel = model.getPersonList().stream()
                .filter(p -> p.getName().fullName.equals("Client C"))
                .findFirst().orElseThrow();
        Person vendorModel = model.getPersonList().stream()
                .filter(p -> p.getName().fullName.equals("Vendor V"))
                .findFirst().orElseThrow();
        assertTrue(vendorModel.isLinkedTo(clientModel));
    }

}
