package seedu.address.model.util;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.date.WeddingDate;
import seedu.address.model.person.Address;
import seedu.address.model.person.Budget;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Partner;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonType;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Price;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                        new Address("Blk 30 Geylang Street 29, #06-40"),
                        WeddingDate.parse("15/06/2020"), PersonType.CLIENT, getTagSet("friends"), null,
                        new Budget("5000-10000"), Optional.of(new Partner("Cierra Wang"))),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                        new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                        WeddingDate.parse("22/03/2019"), PersonType.CLIENT, getTagSet("colleagues", "friends"), null,
                        new Budget("8000-15000"), Optional.of(new Partner("Jackson Wang"))),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                        new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                        WeddingDate.parse("10/12/2021"), PersonType.VENDOR, getTagSet("neighbours"),
                        new Price("500-1000"), null, Optional.empty()),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                        new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                        WeddingDate.parse("05/08/2018"), PersonType.CLIENT, getTagSet("family"), null,
                        new Budget("3000-6000"), Optional.of(new Partner("Jia Min"))),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                        new Address("Blk 47 Tampines Street 20, #17-35"),
                        WeddingDate.parse("18/11/2022"), PersonType.CLIENT, getTagSet("classmates"), null,
                        new Budget("10000-15000"), Optional.of(new Partner("Siti Nur"))),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                        new Address("Blk 45 Aljunied Street 85, #11-31"),
                        WeddingDate.parse("25/09/2020"), PersonType.VENDOR, getTagSet("colleagues"),
                        new Price("2000-3500"), null, Optional.empty())
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
