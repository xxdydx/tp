package seedu.address.ui;

import java.util.Comparator;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    @FXML private Label type;
    @FXML private Label weddingDate;
    @FXML private Label tagsLine;

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
            type.setText("");
            weddingDate.setText("");
            if (tagsLine != null) {
                tagsLine.setText("");
                tagsLine.setVisible(false);
                tagsLine.setManaged(false);
            }
            return;
        }

        name.setText(person.getName().fullName);
        phone.setText("Phone       : " + person.getPhone().value);
        email.setText("Email         : " + person.getEmail().value);
        String typeText = person.getType().display();
        type.setText("Type          : " + typeText);

        String wdText = (person.getWeddingDate() != null)
                ? person.getWeddingDate().toString()
                : "-";
        weddingDate.setText("Wedding  : " + wdText);

        if (person.getTags().isEmpty()) {
            tagsLine.setText("");
            tagsLine.setVisible(false);
            tagsLine.setManaged(false); // remove its layout space
        } else {
            String tagsCsv = person.getTags().stream()
                    .sorted(Comparator.comparing(t -> t.tagName))
                    .map(t -> t.tagName)
                    .collect(Collectors.joining(", "));
            tagsLine.setText("Tags          : " + tagsCsv);
            tagsLine.setVisible(true);
            tagsLine.setManaged(true);
        }
    }
}
