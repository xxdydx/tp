package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARTNER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING_DATE;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.category.Category;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS, PREFIX_PRICE, PREFIX_BUDGET, PREFIX_TYPE,
                PREFIX_WEDDING_DATE, PREFIX_CATEGORY, PREFIX_PARTNER);

        Index index;

        try {
            String preamble = argMultimap.getPreamble().trim();
            if (preamble.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
            }
            String[] preambleTokens = preamble.split("\\s+");
            String indexToken = preambleTokens[0];
            if (preambleTokens.length > 1) {
                // extra text after index (likely unknown prefix) → invalid command format
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
            }
            index = ParserUtil.parseIndex(indexToken);
        } catch (ParseException pe) {
            if (ParserUtil.MESSAGE_INVALID_INDEX.equals(pe.getMessage())) {
                throw new ParseException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, pe);
            }
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_WEDDING_DATE, PREFIX_PRICE, PREFIX_BUDGET, PREFIX_PARTNER);

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editPersonDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_WEDDING_DATE).isPresent()) {
            editPersonDescriptor.setWeddingDate(ParserUtil.parseWeddingDate(argMultimap
                    .getValue(PREFIX_WEDDING_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_TYPE).isPresent()) {
            throw new ParseException(EditCommand.MESSAGE_TYPE_IMMUTABLE);
        }
        if (argMultimap.getValue(PREFIX_PRICE).isPresent()) {
            editPersonDescriptor.setPrice(ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE).get()));
        }
        if (argMultimap.getValue(PREFIX_BUDGET).isPresent()) {
            editPersonDescriptor.setBudget(ParserUtil.parseBudget(argMultimap.getValue(PREFIX_BUDGET).get()));
        }
        if (argMultimap.getValue(PREFIX_PARTNER).isPresent()) {
            editPersonDescriptor.setPartner(ParserUtil.parsePartner(argMultimap.getValue(PREFIX_PARTNER).get()));
        }

        // Parse categories (supports both t/ and c/ since PREFIX_CATEGORY = "c/" = old PREFIX_TAG)
        parseCategoriesForEdit(argMultimap.getAllValues(PREFIX_CATEGORY))
                .ifPresent(editPersonDescriptor::setCategories);

        if (argMultimap.getValue(PREFIX_WEDDING_DATE).isPresent()) {
            editPersonDescriptor.setWeddingDate(ParserUtil.parseWeddingDate(argMultimap
                    .getValue(PREFIX_WEDDING_DATE).get()));
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> categories} into a {@code Set<Category>} if
     * {@code categories} is non-empty.
     * If {@code categories} contain only one element which is an empty string, it
     * will be
     * parsed into a
     * {@code Set<Category>} containing zero categories.
     */
    private Optional<Set<Category>> parseCategoriesForEdit(Collection<String> categories) throws ParseException {
        assert categories != null;

        if (categories.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> categorySet = categories.size() == 1 && categories.contains("")
                ? Collections.emptySet()
                : categories;
        return Optional.of(ParserUtil.parseCategories(categorySet));
    }

}
