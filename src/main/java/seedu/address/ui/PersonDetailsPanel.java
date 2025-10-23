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

    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label type;
    @FXML
    private Label weddingDate;
    @FXML
    private Label price;
    @FXML
    private Label budget;
    @FXML
    private Label tagsLine;
    @FXML
    private Label linkedPersonsLine;

    /**
     * Creates a new details panel bound to the given {@link Person}.
     * <p>
     * If {@code person} is {@code null}, the panel enters an empty state (no
     * selection).
     *
     * @param person The initial person to display; may be {@code null} to show the
     *               empty state.
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
            if (price != null) {
                price.setText("");
                price.setVisible(false);
                price.setManaged(false);
            }
            if (budget != null) {
                budget.setText("");
                budget.setVisible(false);
                budget.setManaged(false);
            }
            if (tagsLine != null) {
                tagsLine.setText("");
                tagsLine.setVisible(false);
                tagsLine.setManaged(false);
            }
            if (linkedPersonsLine != null) {
                linkedPersonsLine.setText("");
                linkedPersonsLine.setVisible(false);
                linkedPersonsLine.setManaged(false);
            }
            return;
        }

        name.setText(DisplayFormat.nameAndPartner(person));
        phone.setText("Phone       : " + person.getPhone().value);
        email.setText("Email         : " + person.getEmail().value);
        String typeText = person.getType().display();
        type.setText("Type          : " + typeText);

        String wdText = (person.getWeddingDate() != null)
                ? person.getWeddingDate().toString()
                : "-";
        weddingDate.setText("Wedding  : " + wdText);

        // Display price only for vendors with price
        if (person.getPrice().isPresent()) {
            price.setText("Price         : " + person.getPrice().get().toString());
            price.setVisible(true);
            price.setManaged(true);
        } else {
            price.setText("");
            price.setVisible(false);
            price.setManaged(false);
        }

        // Display budget only for clients with budget
        if (person.getBudget().isPresent()) {
            budget.setText("Budget      : " + person.getBudget().get().toString());
            budget.setVisible(true);
            budget.setManaged(true);
        } else {
            budget.setText("");
            budget.setVisible(false);
            budget.setManaged(false);
        }

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

        // Display linked persons with their type (CLIENT/VENDOR) and tags
        if (person.getLinkedPersons().isEmpty()) {
            linkedPersonsLine.setText("");
            linkedPersonsLine.setVisible(false);
            linkedPersonsLine.setManaged(false);
        } else {
            String linkedPersonsText = person.getLinkedPersons().stream()
                    .sorted(Comparator.comparing(p -> p.getName().fullName))
                    .map(p -> {
                        // Get the first tag as category label (e.g., "venue", "florist")
                        String categoryLabel = p.getTags().stream()
                                .sorted(Comparator.comparing(t -> t.tagName))
                                .findFirst()
                                .map(t -> capitalize(t.tagName))
                                .orElse(p.getType().display());
                        return "• " + categoryLabel + ": " + p.getName().fullName;
                    })
                    .collect(Collectors.joining("\n"));

            // Determine if linked persons are vendors or clients
            String label = person.getLinkedPersons().stream()
                    .findFirst()
                    .map(p -> p.getType().display() + "s")
                    .orElse("Linked");

            linkedPersonsLine.setText(label + ":\n" + linkedPersonsText);
            linkedPersonsLine.setVisible(true);
            linkedPersonsLine.setManaged(true);
        }
    }

    /**
     * Capitalizes the first letter of a string.
     */
    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
