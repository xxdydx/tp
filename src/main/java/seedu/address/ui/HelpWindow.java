package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CatCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.LinkCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.UnlinkCommand;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private VBox helpMessageContainer;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        initializeAccordion();
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Initializes the accordion with all command help sections.
     */
    private void initializeAccordion() {
        Label welcomeLabel = new Label(
            "Welcome to KnotBook! Try out these commands to get started!\n");
        welcomeLabel.setWrapText(true);
        welcomeLabel.setStyle("-fx-padding: 10; -fx-font-size: 14px;");

        Accordion accordion = new Accordion();
        accordion.setMaxWidth(Double.MAX_VALUE);
        accordion.getPanes().addAll(
            createCommandPane("Add", AddCommand.MESSAGE_USAGE),
            createCommandPane("Cat", CatCommand.MESSAGE_USAGE),
            createCommandPane("Clear", ClearCommand.MESSAGE_USAGE),
            createCommandPane("Delete", DeleteCommand.MESSAGE_USAGE),
            createCommandPane("Edit", EditCommand.MESSAGE_USAGE),
            createCommandPane("Exit", ExitCommand.MESSAGE_USAGE),
            createCommandPane("Find", FindCommand.MESSAGE_USAGE),
            createCommandPane("Help", HelpCommand.MESSAGE_USAGE),
            createCommandPane("Link", LinkCommand.MESSAGE_USAGE),
            createCommandPane("List", ListCommand.MESSAGE_USAGE),
            createCommandPane("Unlink", UnlinkCommand.MESSAGE_USAGE)
        );

        helpMessageContainer.getChildren().addAll(welcomeLabel, accordion);
    }

    /**
     * Creates a TitledPane for a command with its usage information.
     */
    private TitledPane createCommandPane(String commandName, String commandUsage) {
        Label contentLabel = new Label(commandUsage);
        contentLabel.setWrapText(true);
        contentLabel.setMaxWidth(Double.MAX_VALUE);
        contentLabel.setPrefWidth(400);
        contentLabel.setMinHeight(Label.USE_PREF_SIZE);
        contentLabel.setStyle("-fx-padding: 10; -fx-text-alignment: left;");

        VBox contentBox = new VBox(contentLabel);
        contentBox.setMaxWidth(Double.MAX_VALUE);

        TitledPane pane = new TitledPane(commandName, contentBox);
        pane.setAnimated(true);
        pane.setMaxWidth(Double.MAX_VALUE);
        return pane;
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
