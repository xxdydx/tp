package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Budget;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonType;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Price;
import seedu.address.model.tag.Tag;

/**
 * Covers error paths in {@link EditCommand#execute} to lift branch coverage.
 */
public class EditCommandEdgeCasesTest {

    private static class ModelStub implements Model {
        private final AddressBook addressBook = new AddressBook();
        private ObservableList<Person> list = FXCollections.observableArrayList();

        ModelStub(Person... persons)
        {
            list.addAll(Arrays.asList(persons));
            for (Person p : persons)
            {
                addressBook.addPerson(p);
            }
        }

        @Override public void setUserPrefs(ReadOnlyUserPrefs userPrefs)
        {
        }
        @Override public ReadOnlyUserPrefs getUserPrefs()
        {
            return null;
        }
        @Override public GuiSettings getGuiSettings()
        {
            return null;
        }
        @Override public void setGuiSettings(GuiSettings guiSettings)
        {
        }
        @Override public java.nio.file.Path getAddressBookFilePath()
        {
            return null;
        }
        @Override public void setAddressBookFilePath(java.nio.file.Path addressBookFilePath)
        {
        }
        @Override public void setAddressBook(ReadOnlyAddressBook addressBook)
        {
        }
        @Override public ReadOnlyAddressBook getAddressBook()
        {
            return addressBook;
        }
        @Override public boolean hasPerson(Person person)
        {
            return addressBook.hasPerson(person);
        }
        @Override public void deletePerson(Person target)
        {
            addressBook.removePerson(target);
        }
        @Override public void addPerson(Person person)
        {
            addressBook.addPerson(person);
            list.add(person);
        }
        @Override public void setPerson(Person target, Person editedPerson)
        {
            addressBook.setPerson(target, editedPerson);
        }
        @Override public ObservableList<Person> getFilteredPersonList()
        {
            return FXCollections.unmodifiableObservableList(list);
        }
        @Override public void updateFilteredPersonList(Predicate<Person> predicate)
        {
        }
    }

    @Test
    public void execute_clientAddingPrice_throws() {
        ModelStub model = new ModelStub(ALICE); // ALICE is a client
        EditCommand.EditPersonDescriptor d = new EditCommand.EditPersonDescriptor();
        d.setPrice(new Price("100-200"));
        EditCommand cmd = new EditCommand(Index.fromOneBased(1), d);
        assertThrows(CommandException.class, () -> cmd.execute(model));
    }

    @Test
    public void execute_vendorAddingBudgetOrWeddingDate_throws() {
        // Make a vendor
        Person vendor = new Person(
                ALICE.getName(),
                new Phone("99999999"),
                ALICE.getEmail(),
                ALICE.getAddress(),
                PersonType.VENDOR,
                new HashSet<>(Arrays.asList(new Tag("Decor"))),
                null);

        ModelStub model = new ModelStub(vendor);

        // budget not allowed for vendors
        EditCommand.EditPersonDescriptor d1 = new EditCommand.EditPersonDescriptor();
        d1.setBudget(new Budget("1000-2000"));
        EditCommand cmd1 = new EditCommand(Index.fromOneBased(1), d1);
        assertThrows(CommandException.class, () -> cmd1.execute(model));

        // wedding date not allowed for vendors
        EditCommand.EditPersonDescriptor d2 = new EditCommand.EditPersonDescriptor();
        d2.setName(new Name("Vendor Name")); // ensure some change
        d2.setWeddingDate(seedu.address.model.date.WeddingDate.parse("01-01-2025"));
        EditCommand cmd2 = new EditCommand(Index.fromOneBased(1), d2);
        assertThrows(CommandException.class, () -> cmd2.execute(model));
    }

    @Test
    public void execute_clientWithTags_throws() {
        ModelStub model = new ModelStub(ALICE); // client
        EditCommand.EditPersonDescriptor d = new EditCommand.EditPersonDescriptor();
        d.setTags(Set.of(new seedu.address.model.tag.Tag("Photography")));
        EditCommand cmd = new EditCommand(Index.fromOneBased(1), d);
        assertThrows(CommandException.class, () -> cmd.execute(model));
    }
}


