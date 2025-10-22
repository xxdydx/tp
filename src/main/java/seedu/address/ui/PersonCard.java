package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonType;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved
     * keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The
     *      issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label weddingDate;
    @FXML
    private Label type;
    @FXML
    private Label price;
    @FXML
    private Label budget;
    @FXML
    private FlowPane tags;
    @FXML
    private Label typeChip;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to
     * display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(DisplayFormat.nameAndPartner(person));
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        weddingDate.setText(person.getWeddingDate().toString());

        // Display price only for vendors with price
        if (person.getPrice().isPresent()) {
            price.setText("Price: " + person.getPrice().get().toString());
            price.setVisible(true);
            price.setManaged(true);
        } else {
            price.setVisible(false);
            price.setManaged(false);
        }

        // Display budget only for clients with budget
        if (person.getBudget().isPresent()) {
            budget.setText("Budget: " + person.getBudget().get().toString());
            budget.setVisible(true);
            budget.setManaged(true);
        } else {
            budget.setVisible(false);
            budget.setManaged(false);
        }

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        PersonType type = person.getType();
        typeChip.setText(person.getType().display());

        typeChip.getStyleClass().removeAll("type-chip", "type-client", "type-vendor");
        typeChip.getStyleClass().add("type-chip");
        if (type == PersonType.CLIENT) {
            typeChip.getStyleClass().add("type-client");
        } else if (type == PersonType.VENDOR) {
            typeChip.getStyleClass().add("type-vendor");
        }
    }
}
