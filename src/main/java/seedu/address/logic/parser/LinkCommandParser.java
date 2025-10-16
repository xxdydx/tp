package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.LinkCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new LinkCommand object
 */
public class LinkCommandParser implements Parser<LinkCommand> {

    private static final Pattern LINK_COMMAND_FORMAT = Pattern
            .compile("\\s*client:\\s*(?<clientIndex>\\d+)\\s*,\\s*vendor:\\s*(?<vendorIndex>\\d+)\\s*");

    /**
     * Parses the given {@code String} of arguments in the context of the
     * LinkCommand
     * and returns a LinkCommand object for execution.
     * 
     * @throws ParseException if the user input does not conform the expected format
     */
    public LinkCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE));
        }

        // Check for missing parameters
        if (!trimmedArgs.toLowerCase().contains("client:")) {
            throw new ParseException("client is required.");
        }

        if (!trimmedArgs.toLowerCase().contains("vendor:")) {
            throw new ParseException("vendor is required.");
        }

        Matcher matcher = LINK_COMMAND_FORMAT.matcher(args);

        if (!matcher.matches()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE));
        }

        try {
            Index clientIndex = ParserUtil.parseIndex(matcher.group("clientIndex"));
            Index vendorIndex = ParserUtil.parseIndex(matcher.group("vendorIndex"));
            return new LinkCommand(clientIndex, vendorIndex);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE), pe);
        }
    }
}
