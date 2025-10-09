package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Finds and lists all persons in address book whose category matches the specified category.
 * Category matching is case insensitive.
 */
public class CatCommand extends Command {

    public static final String COMMAND_WORD = "cat";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose category matches "
            + "the specified category (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: CATEGORY\n"
            + "Example: " + COMMAND_WORD + " florist";

    public static final String MESSAGE_SUCCESS = "Category filter applied: %1$s";

    private final String category;

    public CatCommand(String category) {
        this.category = category;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        // Placeholder implementation - actual filtering will be implemented when Category field is added
        return new CommandResult(String.format(MESSAGE_SUCCESS, category));
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
        return category.equals(otherCatCommand.category);
    }
}

