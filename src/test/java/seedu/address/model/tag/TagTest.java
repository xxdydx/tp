package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));

        // Invalid tag names
        assertFalse(Tag.isValidTagName(""));
        assertFalse(Tag.isValidTagName(" "));
        assertFalse(Tag.isValidTagName("^"));
        assertFalse(Tag.isValidTagName("&Wedding"));
        assertFalse(Tag.isValidTagName("-Catering"));
        assertFalse(Tag.isValidTagName("(Event)"));
        assertFalse(Tag.isValidTagName(".Service"));
        assertFalse(Tag.isValidTagName("peter*"));
        assertFalse(Tag.isValidTagName("hans#"));
        assertFalse(Tag.isValidTagName("!@#$%^"));
        assertFalse(Tag.isValidTagName("Event???"));

        // Valid tag names
        assertTrue(Tag.isValidTagName("wedding"));
        assertTrue(Tag.isValidTagName("FLORIST"));
        assertTrue(Tag.isValidTagName("Wedding Dresses"));
        assertTrue(Tag.isValidTagName("Day-of Coordinator"));
        assertTrue(Tag.isValidTagName("Food & Beverage"));
        assertTrue(Tag.isValidTagName("A.B.C Events"));
        assertTrue(Tag.isValidTagName("John's Photography"));
        assertTrue(Tag.isValidTagName("Venue/Reception Hall"));
        assertTrue(Tag.isValidTagName("Catering (Kosher)"));
        assertTrue(Tag.isValidTagName("Photography & Videography"));
        assertTrue(Tag.isValidTagName("DJ/Emcee"));
        assertTrue(Tag.isValidTagName("Premium123"));
        assertTrue(Tag.isValidTagName("1stChoice"));
        assertTrue(Tag.isValidTagName("Venues (Outdoor)"));
        assertTrue(Tag.isValidTagName("Full-Service & Planning"));
    }
}
