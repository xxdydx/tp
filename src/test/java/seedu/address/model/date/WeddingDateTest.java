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
        // Invalid format - should throw MESSAGE_CONSTRAINTS
        IllegalArgumentException formatException1 = assertThrows(
                IllegalArgumentException.class, () -> WeddingDate.parse("12/10/2025"));
        assertEquals(WeddingDate.MESSAGE_CONSTRAINTS, formatException1.getMessage());

        IllegalArgumentException formatException2 = assertThrows(
                IllegalArgumentException.class, () -> WeddingDate.parse("12 Oct 2025"));
        assertEquals(WeddingDate.MESSAGE_CONSTRAINTS, formatException2.getMessage());

        IllegalArgumentException formatException3 = assertThrows(
                IllegalArgumentException.class, () -> WeddingDate.parse("not a date"));
        assertEquals(WeddingDate.MESSAGE_CONSTRAINTS, formatException3.getMessage());
    }

    @Test
    public void parse_invalidDateValues_throws() {
        // Invalid date values - should throw MESSAGE_INVALID_DATE_VALUES
        IllegalArgumentException valueException1 = assertThrows(
                IllegalArgumentException.class, () -> WeddingDate.parse("32-01-2025"));
        assertEquals(WeddingDate.MESSAGE_INVALID_DATE_VALUES, valueException1.getMessage());

        IllegalArgumentException valueException2 = assertThrows(
                IllegalArgumentException.class, () -> WeddingDate.parse("2025-13-01"));
        assertEquals(WeddingDate.MESSAGE_INVALID_DATE_VALUES, valueException2.getMessage());

        // February 30th doesn't exist
        IllegalArgumentException valueException3 = assertThrows(
                IllegalArgumentException.class, () -> WeddingDate.parse("30-02-2025"));
        assertEquals(WeddingDate.MESSAGE_INVALID_DATE_VALUES, valueException3.getMessage());

        // February 29th on non-leap year
        IllegalArgumentException valueException4 = assertThrows(
                IllegalArgumentException.class, () -> WeddingDate.parse("29-02-2023"));
        assertEquals(WeddingDate.MESSAGE_INVALID_DATE_VALUES, valueException4.getMessage());
    }

    @Test
    public void equality_hash_success() {
        WeddingDate a = WeddingDate.parse("2025-10-12");
        WeddingDate b = new WeddingDate(LocalDate.of(2025, 10, 12));
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}
