package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING_DATE;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.date.WeddingDate;
import seedu.address.model.person.Address;
import seedu.address.model.person.Budget;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonType;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Price;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_WEDDING_DATE,
                PREFIX_TAG, PREFIX_TYPE, PREFIX_PRICE, PREFIX_BUDGET);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_WEDDING_DATE, PREFIX_TYPE,
                PREFIX_PRICE, PREFIX_BUDGET);

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        PersonType type = PersonType.CLIENT;

        if (argMultimap.getValue(PREFIX_TYPE).isPresent()) {
            String raw = argMultimap.getValue(PREFIX_TYPE).get().trim();
            String normalised = raw.toLowerCase();

            // Exact message expected by AddCommandParserTest.parse_typeInvalid_failure
            if (!normalised.equals("client") && !normalised.equals("vendor")) {
                throw new ParseException("Invalid type. Please choose either 'client' or 'vendor'.");
            }
            type = PersonType.parse(normalised);
        }

        // Parse wedding date - required for clients, not allowed for vendors
        WeddingDate weddingDate = null;
        if (argMultimap.getValue(PREFIX_WEDDING_DATE).isPresent()) {
            // Wedding date is only applicable for clients
            if (type != PersonType.CLIENT) {
                throw new ParseException(Person.MSG_WEDDING_DATE_FORBIDDEN_FOR_VENDOR);
            }
            weddingDate = ParserUtil.parseWeddingDate(argMultimap.getValue(PREFIX_WEDDING_DATE).get());
        } else if (type == PersonType.CLIENT) {
            throw new ParseException(Person.MSG_WEDDING_DATE_REQUIRED_FOR_CLIENT);
        }

        // Parse price if present
        Price price = null;
        if (argMultimap.getValue(PREFIX_PRICE).isPresent()) {
            // Price is only applicable for vendors
            if (type != PersonType.VENDOR) {
                throw new ParseException("Price is only applicable for vendors.");
            }
            price = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE).get());
        }

        // Parse budget if present
        Budget budget = null;
        if (argMultimap.getValue(PREFIX_BUDGET).isPresent()) {
            // Budget is only applicable for clients
            if (type != PersonType.CLIENT) {
                throw new ParseException("Budget is only applicable for clients.");
            }
            budget = ParserUtil.parseBudget(argMultimap.getValue(PREFIX_BUDGET).get());
        }

        Person person;
        if (type == PersonType.VENDOR) {
            person = new Person(name, phone, email, address, type, tagList, price);
        } else {
            person = new Person(name, phone, email, address, weddingDate, type, tagList, price, budget);
        }
        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values
     * in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
