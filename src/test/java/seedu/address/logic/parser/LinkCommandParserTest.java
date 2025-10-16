package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
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
        assertParseSuccess(parser, " client:1, vendor:2",
                new LinkCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));

        // Extra whitespace
        assertParseSuccess(parser, "  client:1  ,  vendor:2  ",
                new LinkCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));

        // No spaces around colon
        assertParseSuccess(parser, "client:1,vendor:2",
                new LinkCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingClientParameter_throwsParseException() {
        assertParseFailure(parser, " vendor:2", "client is required.");
    }

    @Test
    public void parse_missingVendorParameter_throwsParseException() {
        assertParseFailure(parser, " client:1", "vendor is required.");
    }

    @Test
    public void parse_invalidFormat_throwsParseException() {
        // Missing colon
        assertParseFailure(parser, " client 1, vendor 2",
                "client is required.");

        // Missing comma
        assertParseFailure(parser, " client:1 vendor:2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE));

        // Wrong order
        assertParseFailure(parser, " vendor:2, client:1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        // Negative index
        assertParseFailure(parser, " client:-1, vendor:2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE));

        // Zero index
        assertParseFailure(parser, " client:0, vendor:2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE));

        // Non-numeric index
        assertParseFailure(parser, " client:a, vendor:2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE));
    }
}
