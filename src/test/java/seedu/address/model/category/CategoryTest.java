package seedu.address.model.category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    public void constructor_invalid_throws() {
        assertThrows(NullPointerException.class, () -> new Category(null));
        assertThrows(IllegalArgumentException.class, () -> new Category(""));
        assertThrows(IllegalArgumentException.class, () -> new Category(" "));
        assertThrows(IllegalArgumentException.class, () -> new Category("#Invalid"));
    }

    @Test
    public void isValidCategoryName_various() {
        // valid
        assertTrue(Category.isValidCategoryName("Photography"));
        assertTrue(Category.isValidCategoryName("Wedding Planning & Events"));
        assertTrue(Category.isValidCategoryName("Cakes/Bakery"));
        assertTrue(Category.isValidCategoryName("Make-up (Bridal)"));
        assertTrue(Category.isValidCategoryName("John's Decor."));

        // invalid
        assertFalse(Category.isValidCategoryName(""));
        assertFalse(Category.isValidCategoryName("#Tag"));
    }

    @Test
    public void equalityAndHash() {
        Category a = new Category("Photography");
        Category b = new Category("Photography");
        Category c = new Category("Decor");

        assertTrue(a.equals(a));
        assertTrue(a.equals(b));
        assertEquals(a.hashCode(), b.hashCode());
        assertFalse(a.equals(c));
        assertFalse(a.equals(null));
        assertFalse(a.equals("Photography"));
    }
}


