package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.date.WeddingDate;
import seedu.address.model.person.Address;
import seedu.address.model.person.Budget;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.PersonType;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Price;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_WEDDING_DATE = "32/13/2025";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_PRICE = "abc";
    private static final String INVALID_BUDGET = "abc";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "12345678";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_WEDDING_DATE = "15/06/2020";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_PRICE = "1000-2000";
    private static final String VALID_BUDGET = "3000-5000";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseWeddingDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseWeddingDate((String) null));
    }

    @Test
    public void parseWeddingDate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseWeddingDate(INVALID_WEDDING_DATE));
    }

    @Test
    public void parseWeddingDate_validValueWithoutWhitespace_returnsWeddingDate() throws Exception {
        WeddingDate expectedWeddingDate = WeddingDate.parse(VALID_WEDDING_DATE);
        assertEquals(expectedWeddingDate, ParserUtil.parseWeddingDate(VALID_WEDDING_DATE));
    }

    @Test
    public void parseWeddingDate_validValueWithWhitespace_returnsTrimmedWeddingDate() throws Exception {
        String weddingDateWithWhitespace = WHITESPACE + VALID_WEDDING_DATE + WHITESPACE;
        WeddingDate expectedWeddingDate = WeddingDate.parse(VALID_WEDDING_DATE);
        assertEquals(expectedWeddingDate, ParserUtil.parseWeddingDate(weddingDateWithWhitespace));
    }

    @Test
    public void parsePersonType_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePersonType(null));
    }

    @Test
    public void parsePersonType_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePersonType("partner"));
        assertThrows(ParseException.class, () -> ParserUtil.parsePersonType(""));
        assertThrows(ParseException.class, () -> ParserUtil.parsePersonType(" ven dor "));
    }

    @Test
    public void parsePersonType_validValueWithoutWhitespace_returnsEnum() throws Exception {
        assertEquals(PersonType.CLIENT, ParserUtil.parsePersonType("client"));
        assertEquals(PersonType.VENDOR, ParserUtil.parsePersonType("vendor"));
    }

    @Test
    public void parsePersonType_validValueWithWhitespaceAndCase_returnsEnum() throws Exception {
        assertEquals(PersonType.CLIENT, ParserUtil.parsePersonType("  Client  "));
        assertEquals(PersonType.VENDOR, ParserUtil.parsePersonType("\nVeNdOr\t"));
    }

    @Test
    public void parsePrice_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePrice((String) null));
    }

    @Test
    public void parsePrice_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePrice(INVALID_PRICE));
        assertThrows(ParseException.class, () -> ParserUtil.parsePrice("")); // empty string
        assertThrows(ParseException.class, () -> ParserUtil.parsePrice(" ")); // spaces only
        assertThrows(ParseException.class, () -> ParserUtil.parsePrice("12.50")); // decimal
        assertThrows(ParseException.class, () -> ParserUtil.parsePrice("-500")); // negative
    }

    @Test
    public void parsePrice_validValueWithoutWhitespace_returnsPrice() throws Exception {
        Price expectedPrice = new Price(VALID_PRICE);
        assertEquals(expectedPrice, ParserUtil.parsePrice(VALID_PRICE));
    }

    @Test
    public void parsePrice_validValueWithWhitespace_returnsTrimmedPrice() throws Exception {
        Price expectedPrice = new Price(VALID_PRICE);
        assertEquals(expectedPrice, ParserUtil.parsePrice(WHITESPACE + VALID_PRICE + WHITESPACE));
    }

    @Test
    public void parsePrice_validSingleNumber_returnsPrice() throws Exception {
        Price expectedPrice = new Price("1000");
        assertEquals(expectedPrice, ParserUtil.parsePrice("1000"));
    }

    @Test
    public void parsePrice_validRange_returnsPrice() throws Exception {
        Price expectedPrice = new Price("500-1500");
        assertEquals(expectedPrice, ParserUtil.parsePrice("500-1500"));
    }

    @Test
    public void parsePrice_validWithDollarSign_returnsPrice() throws Exception {
        Price expectedPrice = new Price("$1000");
        assertEquals(expectedPrice, ParserUtil.parsePrice("$1000"));
    }

    @Test
    public void parseBudget_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseBudget((String) null));
    }

    @Test
    public void parseBudget_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseBudget(INVALID_BUDGET));
        assertThrows(ParseException.class, () -> ParserUtil.parseBudget("")); // empty string
        assertThrows(ParseException.class, () -> ParserUtil.parseBudget(" ")); // spaces only
        assertThrows(ParseException.class, () -> ParserUtil.parseBudget("12.50")); // decimal
        assertThrows(ParseException.class, () -> ParserUtil.parseBudget("-500")); // negative
        assertThrows(ParseException.class, () -> ParserUtil.parseBudget("5000-1000")); // invalid range
    }

    @Test
    public void parseBudget_validValueWithoutWhitespace_returnsBudget() throws Exception {
        Budget expectedBudget = new Budget(VALID_BUDGET);
        assertEquals(expectedBudget, ParserUtil.parseBudget(VALID_BUDGET));
    }

    @Test
    public void parseBudget_validValueWithWhitespace_returnsTrimmedBudget() throws Exception {
        Budget expectedBudget = new Budget(VALID_BUDGET);
        assertEquals(expectedBudget, ParserUtil.parseBudget(WHITESPACE + VALID_BUDGET + WHITESPACE));
    }

    @Test
    public void parseBudget_validSingleNumber_returnsBudget() throws Exception {
        Budget expectedBudget = new Budget("5000");
        assertEquals(expectedBudget, ParserUtil.parseBudget("5000"));
    }

    @Test
    public void parseBudget_validRange_returnsBudget() throws Exception {
        Budget expectedBudget = new Budget("2000-8000");
        assertEquals(expectedBudget, ParserUtil.parseBudget("2000-8000"));
    }

    @Test
    public void parseBudget_validWithDollarSign_returnsBudget() throws Exception {
        Budget expectedBudget = new Budget("$5000");
        assertEquals(expectedBudget, ParserUtil.parseBudget("$5000"));
    }
}
