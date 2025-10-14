package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * A UI component that displays the details of a selected {@link Person}.
 */
public class PersonDetailsPanel extends UiPart<Region> {
    private static final String FXML = "PersonDetailsPanel.fxml";

    @FXML private Label name;
    @FXML private Label phone;
    @FXML private Label email;
    @FXML private FlowPane tags;

    /**
     * Creates a new details panel bound to the given {@link Person}.
     * <p>
     * If {@code person} is {@code null}, the panel enters an empty state (no selection).
     *
     * @param person The initial person to display; may be {@code null} to show the empty state.
     */
    public PersonDetailsPanel(Person person) {
        super(FXML);
        setPerson(person);
    }

    /**
     * Updates the panel to display the given {@link Person}.
     * <p>
     * Passing {@code null} resets the panel to an empty state.
     *
     * @param person The person to display; may be {@code null}.
     */
    public void setPerson(Person person) {
        if (person == null) {
            name.setText("No contact selected");
            phone.setText("");
            email.setText("");
            tags.getChildren().clear();
            return;
        }

        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        email.setText(person.getEmail().value);

        tags.getChildren().clear();
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
