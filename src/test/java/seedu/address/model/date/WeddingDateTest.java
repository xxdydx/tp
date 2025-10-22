package seedu.address.model.date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class WeddingDateTest {

    @Test
    public void parse_validFormats_success() {
        assertEquals("2025-10-12", WeddingDate.parse("12-10-2025").toString());
        assertEquals("2025-10-12", WeddingDate.parse("2025-10-12").toString());
    }

    @Test
    public void parse_invalidFormats_throws() {
        assertThrows(IllegalArgumentException.class, () -> WeddingDate.parse("32-01-2025"));
        assertThrows(IllegalArgumentException.class, () -> WeddingDate.parse("2025-13-01"));
        assertThrows(IllegalArgumentException.class, () -> WeddingDate.parse("12/10/2025"));
        assertThrows(IllegalArgumentException.class, () -> WeddingDate.parse("12 Oct 2025"));
        assertThrows(IllegalArgumentException.class, () -> WeddingDate.parse("not a date"));
    }

    @Test
    public void equality_hash_success() {
        WeddingDate a = WeddingDate.parse("2025-10-12");
        WeddingDate b = new WeddingDate(LocalDate.of(2025, 10, 12));
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}
