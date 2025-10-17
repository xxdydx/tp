package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.date.WeddingDate;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final WeddingDate weddingDate;
    private final Set<Tag> tags = new HashSet<>();
    private final PersonType type;
    private final Price price; // only for vendors
    private final Set<Person> linkedPersons = new HashSet<>();
    private final Budget budget; // only for clients
    private final Remark remark;

    /**
     * Every field must be present and not null, except price and budget which are
     * optional.
     */
    public Person(Name name, Phone phone, Email email, Address address, WeddingDate weddingDate, PersonType type,
            Set<Tag> tags, Price price, Budget budget) {
        requireAllNonNull(name, phone, email, address, weddingDate, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.weddingDate = weddingDate;
        this.type = type;
        this.tags.addAll(tags);
        this.price = price;
        this.budget = budget;
        this.remark = new Remark(""); // default empty remark
    }

    /**
     * Every field must be present and not null, except price, budget and remark which are
     * optional.
     */
    public Person(Name name, Phone phone, Email email, Address address, WeddingDate weddingDate, PersonType type,
            Set<Tag> tags, Price price, Budget budget, Remark remark) {
        requireAllNonNull(name, phone, email, address, weddingDate, tags, remark);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.weddingDate = weddingDate;
        this.type = type;
        this.tags.addAll(tags);
        this.price = price;
        this.budget = budget;
        this.remark = remark;
    }

    /**
     * Constructor with linked persons.
     * Every field must be present and not null, except price which is optional.
     */
    public Person(Name name, Phone phone, Email email, Address address, WeddingDate weddingDate, PersonType type,
            Set<Tag> tags, Set<Person> linkedPersons, Price price) {
        requireAllNonNull(name, phone, email, address, weddingDate, tags, linkedPersons);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.weddingDate = weddingDate;
        this.type = type;
        this.tags.addAll(tags);
        this.price = price;
        this.linkedPersons.addAll(linkedPersons);
        this.budget = null;
        this.remark = new Remark(""); // default empty remark
    }

    /**
     * Constructor with linked persons and remark.
     * Every field must be present and not null, except price and remark which are optional.
     */
    public Person(Name name, Phone phone, Email email, Address address, WeddingDate weddingDate, PersonType type,
            Set<Tag> tags, Set<Person> linkedPersons, Price price, Remark remark) {
        requireAllNonNull(name, phone, email, address, weddingDate, tags, linkedPersons, remark);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.weddingDate = weddingDate;
        this.type = type;
        this.tags.addAll(tags);
        this.price = price;
        this.linkedPersons.addAll(linkedPersons);
        this.budget = null;
        this.remark = remark;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public WeddingDate getWeddingDate() {
        return weddingDate;
    }

    public PersonType getType() {
        return type;
    }

    public Optional<Price> getPrice() {
        return Optional.ofNullable(price);
    }

    public Optional<Budget> getBudget() {
        return Optional.ofNullable(budget);
    }

    public Remark getRemark() {
        return remark;
    }

    /**
     * Returns an immutable set of linked persons, which throws
     * {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Person> getLinkedPersons() {
        return Collections.unmodifiableSet(linkedPersons);
    }

    /**
     * Returns true if this person is linked to the specified person.
     */
    public boolean isLinkedTo(Person other) {
        return linkedPersons.contains(other);
    }

    /**
     * Returns an immutable tag set, which throws
     * {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && weddingDate.equals(otherPerson.weddingDate)
                && type == otherPerson.type
                && tags.equals(otherPerson.tags)
                && Objects.equals(price, otherPerson.price)
                && Objects.equals(budget, otherPerson.budget)
                && remark.equals(otherPerson.remark);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        // linkedPersons intentionally excluded from hashCode to avoid circular
        // references
        return Objects.hash(name, phone, email, address, weddingDate, type, tags, price, budget, remark);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("weddingDate", weddingDate)
                .add("tags", tags)
                .add("type", type)
                .add("price", price)
                .add("linkedPersons", linkedPersons.size() + " link(s)")
                .add("remark", remark)
                .toString();
    }

}
