package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.CatCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.CategoryMatchesPredicate;

/**
 * Parses input arguments and creates a new CatCommand object
 */
public class CatCommandParser implements Parser<CatCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CatCommand
     * and returns a CatCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CatCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CatCommand.MESSAGE_USAGE));
        }

        return new CatCommand(new CategoryMatchesPredicate(trimmedArgs));
    }

}

