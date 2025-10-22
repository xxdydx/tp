package seedu.address.testutil;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {
    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_WEDDING_DATE = "01/01/2020";
    public static final PersonType DEFAULT_TYPE = PersonType.CLIENT;
    public static final String DEFAULT_PARTNER = "Clara Chia";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private WeddingDate weddingDate;
    private Set<Tag> tags;
    private PersonType type;
    private Price price;
    private Budget budget;
    private Optional<Partner> partner;


    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        weddingDate = WeddingDate.parse(DEFAULT_WEDDING_DATE);
        tags = new HashSet<>();
        type = DEFAULT_TYPE;
        price = null;
        budget = null;
        partner = Optional.of(new Partner(DEFAULT_PARTNER));
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        weddingDate = personToCopy.getWeddingDate();
        tags = new HashSet<>(personToCopy.getTags());
        type = personToCopy.getType();
        price = personToCopy.getPrice().orElse(null);
        budget = personToCopy.getBudget().orElse(null);
        partner = personToCopy.getPartner();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code WeddingDate} of the {@code Person} that we are building.
     */
    public PersonBuilder withWeddingDate(String weddingDate) {
        this.weddingDate = WeddingDate.parse(weddingDate);
        return this;
    }

    /**
     * Sets the {@code PersonType} of the {@code Person} that we are building.
     */
    public PersonBuilder withType(PersonType type) {
        this.type = type;
        if (type == PersonType.VENDOR) {
            this.partner = Optional.empty();
            this.budget = null;
        } else {
            this.price = null;
            if (this.partner == null || this.partner.isEmpty()) {
                this.partner = Optional.of(new Partner(DEFAULT_PARTNER));
            }
        }
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Person} that we are building.
     */
    public PersonBuilder withPrice(String price) {
        this.price = new Price(price);
        return this;
    }

    /**
     * Sets the {@code Price} to null for the {@code Person} that we are building.
     */
    public PersonBuilder withoutPrice() {
        this.price = null;
        return this;
    }

    /**
     * Sets the {@code Budget} of the {@code Person} that we are building.
     */
    public PersonBuilder withBudget(String budget) {
        this.budget = new Budget(budget);
        return this;
    }

    /**
     * Sets the {@code Budget} to null for the {@code Person} that we are building.
     */
    public PersonBuilder withoutBudget() {
        this.budget = null;
        return this;
    }

    /**
     * Sets the {@code Partner} of the {@code Person} that we are building.
     */
    public PersonBuilder withPartner(String partnerName) {
        this.partner = Optional.of(new Partner(partnerName));
        this.type = PersonType.CLIENT;
        this.price = null;
        return this;
    }

    /**
     * Sets the {@code Partner} to Optional.empty() for the {@code Person} that we are building.
     */
    public PersonBuilder withoutPartner() {
        this.partner = Optional.empty();
        return this;
    }

    /**
     * Builds a {@link Person} from the values set on this builder, enforcing type/partner rules.
     * */
    public Person build() {
        if (type == PersonType.CLIENT) {
            if (partner == null || partner.isEmpty()) {
                throw new IllegalArgumentException("Clients must have a partner");
            }
            return new Person(name, phone, email, address, weddingDate, type, tags,
                    null, budget, partner);
        } else {
            if (partner != null && partner.isPresent()) {
                throw new IllegalArgumentException("Vendors cannot have a partner");
            }
            return new Person(name, phone, email, address, weddingDate, type, tags,
                    price, null, Optional.empty());
        }
    }

}
