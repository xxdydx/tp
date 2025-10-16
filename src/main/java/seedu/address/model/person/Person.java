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
    private final Budget budget; // only for clients

    /**
     * Every field must be present and not null, except price and budget which are optional.
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
                && Objects.equals(budget, otherPerson.budget);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, weddingDate, type, tags, price, budget);
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
                .add("budget", budget)
                .toString();
    }

}
