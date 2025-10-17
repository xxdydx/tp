package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all contacts in the address book.\n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Listed all your contacts!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        String message = getSuccessMessageWithCount(model);
        return new CommandResult(message);
    }

    /**
     * Returns the appropriate success message with contact count.
     * Shows "No contacts yet." for empty lists, otherwise includes the count.
     */
    private String getSuccessMessageWithCount(Model model) {
        int contactCount = getContactCount(model);

        if (contactCount == 0) {
            return "No contacts yet.";
        }

        String countText = contactCount == 1 ? "contact" : "contacts";
        return String.format("%s (%d %s)", MESSAGE_SUCCESS, contactCount, countText);
    }

    /**
     * Returns the number of contacts in the filtered person list.
     */
    private int getContactCount(Model model) {
        return model.getFilteredPersonList().size();
    }
}
