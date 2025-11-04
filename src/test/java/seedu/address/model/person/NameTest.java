package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("*Peter")); // starts with invalid character
        assertFalse(Name.isValidName(".John")); // starts with period
        assertFalse(Name.isValidName("'Mary")); // starts with apostrophe
        assertFalse(Name.isValidName("-Jane")); // starts with hyphen
        assertFalse(Name.isValidName("&Events")); // starts with ampersand
        assertFalse(Name.isValidName("/Slash")); // starts with slash
        assertFalse(Name.isValidName("Peter+")); // contains invalid character
        assertFalse(Name.isValidName("John@Doe")); // contains invalid character
        assertFalse(Name.isValidName("Events$")); // contains invalid character

        // valid name
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long names

        // valid names with special characters
        assertTrue(Name.isValidName("John's Photography")); // with apostrophe
        assertTrue(Name.isValidName("Mary-Jane's Events")); // with hyphen and apostrophe
        assertTrue(Name.isValidName("A.B. Events")); // with periods
        assertTrue(Name.isValidName("Flowers & More")); // with ampersand
        assertTrue(Name.isValidName("A/B Photography")); // with slash
        assertTrue(Name.isValidName("Sarah's Wedding & Events")); // multiple special chars
        assertTrue(Name.isValidName("J.R. Smith's Photography")); // multiple special chars
        assertTrue(Name.isValidName("First-Class Events & Planning")); // multiple special chars
        assertTrue(Name.isValidName("Mr. & Mrs. Smith")); // periods and ampersand
        assertTrue(Name.isValidName("Pat's Party-Planning Services")); // multiple special chars
    }

    @Test
    public void equals() {
        Name name = new Name("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new Name("Valid Name")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new Name("Other Valid Name")));
    }
}
