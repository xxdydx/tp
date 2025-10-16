package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CatCommand;
import seedu.address.model.person.CategoryMatchesPredicate;

public class CatCommandParserTest {

    private CatCommandParser parser = new CatCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, CatCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsCatCommand() {
        // no leading and trailing whitespaces
        CatCommand expectedCatCommand = new CatCommand(new CategoryMatchesPredicate("florist"));
        assertParseSuccess(parser, "florist", expectedCatCommand);

        // with leading and trailing whitespaces
        assertParseSuccess(parser, "  florist  ", expectedCatCommand);
    }

    @Test
    public void parse_multipleWords_returnsCatCommand() {
        // Category with multiple words (e.g., "makeup artist")
        CatCommand expectedCatCommand = new CatCommand(new CategoryMatchesPredicate("makeup artist"));
        assertParseSuccess(parser, "makeup artist", expectedCatCommand);

        // with extra whitespaces
        assertParseSuccess(parser, "  makeup artist  ", expectedCatCommand);
    }
}

