package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.LinkCommand;

/**
 * Contains unit tests for LinkCommandParser.
 */
public class LinkCommandParserTest {

    private LinkCommandParser parser = new LinkCommandParser();

    @Test
    public void parse_validArgs_returnsLinkCommand() {
        // Standard format
        assertParseSuccess(parser, " client/1 vendor/2",
                new LinkCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));

        // Extra whitespace
        assertParseSuccess(parser, "  client/1  vendor/2  ",
                new LinkCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingClientParameter_throwsParseException() {
        assertParseFailure(parser, " vendor/2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingVendorParameter_throwsParseException() {
        assertParseFailure(parser, " client/1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidFormat_throwsParseException() {
        // Preamble present
        assertParseFailure(parser, " extra client/1 vendor/2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicatePrefix_throwsParseException() {
        // Duplicate client prefix
        assertParseFailure(parser, " client/1 client/2 vendor/3",
                "Multiple values specified for the following single-valued field(s): client/");

        // Duplicate vendor prefix
        assertParseFailure(parser, " client/1 vendor/2 vendor/3",
                "Multiple values specified for the following single-valued field(s): vendor/");
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        // Negative index
        assertParseFailure(parser, " client/-1 vendor/2",
                MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // Zero index
        assertParseFailure(parser, " client/0 vendor/2",
                MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // Non-numeric index
        assertParseFailure(parser, " client/a vendor/2",
                MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
}
