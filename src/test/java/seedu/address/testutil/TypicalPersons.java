package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEDDING_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEDDING_DATE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonType;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {
    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253").withWeddingDate("01/01/2024")
            .withTags("friends")
            .withType(PersonType.parse("client")).withBudget("5000-10000").withPartner("Aaron Tan")
            .build();

    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25").withEmail("johnd@example.com")
            .withPhone("98765432").withWeddingDate("02/02/2024")
            .withTags("owesMoney", "friends")
            .withType(PersonType.parse("client")).withBudget("6000-12000").withPartner("Beatrice Lim")
            .build();

    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").withWeddingDate("03/03/2024")
            .withType(PersonType.parse("vendor")).withPrice("800-1500")
            .build();

    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withWeddingDate("04/04/2024")
            .withTags("friends")
            .withType(PersonType.parse("vendor")).withPrice("1200-2000")
            .build();

    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("94822241")
            .withEmail("werner@example.com").withAddress("michegan ave").withWeddingDate("05/05/2024")
            .withType(PersonType.parse("vendor")).withPrice("500-900")
            .build();

    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("94824271")
            .withEmail("lydia@example.com").withAddress("little tokyo").withWeddingDate("06/06/2024")
            .withType(PersonType.parse("vendor")).withPrice("700-1100")
            .build();

    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("94824421")
            .withEmail("anna@example.com").withAddress("4th street").withWeddingDate("07/07/2024")
            .withType(PersonType.parse("vendor")).withPrice("900-1300")
            .build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("84824240")
            .withEmail("stefan@example.com").withAddress("little india").withWeddingDate("08/08/2024")
            .withType(PersonType.parse("vendor")).withPrice("500-800")
            .build();

    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("84821310")
            .withEmail("hans@example.com").withAddress("chicago ave").withWeddingDate("09/09/2024")
            .withType(PersonType.parse("vendor")).withPrice("650-900")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withWeddingDate(VALID_WEDDING_DATE_AMY)
            .withTags(VALID_TAG_FRIEND)
            .withType(PersonType.parse("client")).withBudget("4000-9000").withPartner("Alex Tan")
            .build();

    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withWeddingDate(VALID_WEDDING_DATE_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withType(PersonType.parse("vendor")).withPrice("1000-2000")
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
