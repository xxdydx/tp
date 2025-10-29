package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.CategoryMatchesPredicate;

/**
 * Finds and lists all persons in address book whose tags contain the specified category.
 * Category matching is case insensitive.
 */
public class CatCommand extends Command {

    public static final String COMMAND_WORD = "cat";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose tags contain "
            + "the specified category (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: CATEGORY\n"
            + "Example: " + COMMAND_WORD + " florist";

    private final CategoryMatchesPredicate predicate;

    public CatCommand(CategoryMatchesPredicate predicate) {
        this.predicate = requireNonNull(predicate);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(Messages.getPersonsListedMessage(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CatCommand)) {
            return false;
        }

        CatCommand otherCatCommand = (CatCommand) other;
        return predicate.equals(otherCatCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}

