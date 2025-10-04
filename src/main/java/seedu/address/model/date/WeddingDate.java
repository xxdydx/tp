package seedu.address.model.date;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.util.Locale;

/**
 * Represents a wedding date in the address book.
 */
public class WeddingDate {

    public static final String MESSAGE_CONSTRAINTS =
            "Invalid wedding date. Use DD/MM/YYYY (e.g., 12/10/2025) or YYYY-MM-DD (e.g., 2025-10-12).";

    private static final DateTimeFormatter DMY_SLASH = new DateTimeFormatterBuilder()
            .parseStrict()
            .appendPattern("dd/MM/uuuu")
            .toFormatter(Locale.ENGLISH)
            .withResolverStyle(ResolverStyle.STRICT);

    private static final DateTimeFormatter ISO = DateTimeFormatter.ISO_LOCAL_DATE
            .withResolverStyle(ResolverStyle.STRICT)
            .withLocale(Locale.ENGLISH);

    private final LocalDate value;

    /**
     * Constructs a {@code WeddingDate} from a {@link LocalDate}.
     */
    public WeddingDate(LocalDate date) {
        requireNonNull(date);
        this.value = date;
    }

    /** Parses a {@code WeddingDate} from a string in any accepted format. */
    public static WeddingDate parse(String input) {
        requireNonNull(input);
        String s = input.trim().replaceAll("\\s+", " ");
        LocalDate parsed = tryParse(s);
        if (parsed == null) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        return new WeddingDate(parsed);
    }

    private static LocalDate tryParse(String s) {
        try {
            return LocalDate.parse(s, DMY_SLASH);
        } catch (Exception ignore) {
            /* try next accepted format */
        }

        try {
            return LocalDate.parse(s, ISO);
        } catch (Exception ignore) {
            /* no more formats to try */
        }

        return null;
    }


    public LocalDate getDate() {
        return value;
    }

    @Override
    public String toString() {
        return value.format(ISO);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof WeddingDate)
                && value.equals(((WeddingDate) other).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
