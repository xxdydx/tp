package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PriceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Price(null));
    }

    @Test
    public void constructor_invalidPrice_throwsIllegalArgumentException() {
        String invalidPrice = "";
        assertThrows(IllegalArgumentException.class, () -> new Price(invalidPrice));
    }

    @Test
    public void isValidPrice() {
        // null price
        assertThrows(NullPointerException.class, () -> Price.isValidPrice(null));

        // invalid prices
        assertFalse(Price.isValidPrice("")); // empty string
        assertFalse(Price.isValidPrice(" ")); // spaces only
        assertFalse(Price.isValidPrice("abc")); // non-numeric
        assertFalse(Price.isValidPrice("12.50")); // decimal not allowed
        assertFalse(Price.isValidPrice("-500")); // negative number
        assertFalse(Price.isValidPrice("100-")); // incomplete range
        assertFalse(Price.isValidPrice("-100")); // incomplete range
        assertFalse(Price.isValidPrice("100--200")); // double hyphen
        assertFalse(Price.isValidPrice("100-200-300")); // multiple ranges
        assertFalse(Price.isValidPrice("12345678901")); // too many digits (>10)

        // valid prices - single numbers
        assertTrue(Price.isValidPrice("1000")); // simple number
        assertTrue(Price.isValidPrice("0")); // zero
        assertTrue(Price.isValidPrice("1")); // single digit
        assertTrue(Price.isValidPrice("1234567890")); // 10 digits (max)
        assertTrue(Price.isValidPrice("$1000")); // with dollar sign
        assertTrue(Price.isValidPrice("1,000")); // with comma
        assertTrue(Price.isValidPrice("$1,000")); // with dollar and comma
        assertTrue(Price.isValidPrice("10,000,000")); // multiple commas

        // valid prices - ranges
        assertTrue(Price.isValidPrice("800-1500")); // simple range
        assertTrue(Price.isValidPrice("800–1500")); // en-dash
        assertTrue(Price.isValidPrice("$800-$1500")); // with dollar signs
        assertTrue(Price.isValidPrice("$800-1500")); // mixed dollar
        assertTrue(Price.isValidPrice("800-$1500")); // mixed dollar
        assertTrue(Price.isValidPrice("1,000-2,000")); // with commas
        assertTrue(Price.isValidPrice("$1,000-$2,000")); // with dollar and commas
    }

    @Test
    public void equals() {
        Price price = new Price("1000");

        // same values -> returns true
        assertTrue(price.equals(new Price("1000")));

        // same object -> returns true
        assertTrue(price.equals(price));

        // null -> returns false
        assertFalse(price.equals(null));

        // different types -> returns false
        assertFalse(price.equals(5.0f));

        // different values -> returns false
        assertFalse(price.equals(new Price("2000")));
        assertFalse(price.equals(new Price("1000-2000")));
    }

    @Test
    public void hashCode_samePrice_sameHashCode() {
        Price price1 = new Price("1500");
        Price price2 = new Price("1500");
        assertTrue(price1.hashCode() == price2.hashCode());
    }

    @Test
    public void toString_returnsCorrectString() {
        Price price = new Price("1000-2000");
        assertTrue(price.toString().equals("1000-2000"));
    }
}
