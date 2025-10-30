package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel; // Initializes JavaFX toolkit in headless envs
import javafx.scene.Scene;
import seedu.address.model.date.WeddingDate;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonType;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Price;
import seedu.address.model.tag.Tag;

public class UiSmokeTest {

    @BeforeAll
    public static void initFx() throws Exception {
        new JFXPanel();
        if (!Platform.isFxApplicationThread()) {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.runLater(latch::countDown);
            latch.await(5, TimeUnit.SECONDS);
        }
    }

    @Test
    public void loadHelpWindow_constructsAndBuildsAccordion() {
        assertDoesNotThrow(() -> {
            Platform.runLater(() -> {
                HelpWindow hw = new HelpWindow();
                assertNotNull(hw.getRoot());
                hw.getRoot().setScene(new Scene(new javafx.scene.layout.VBox()));
            });
        });
    }

    @Test
    public void loadPersonCard_forClientAndVendor() throws Exception {
        // Client (use constructor with linked persons + budget + partner)
        java.util.Set<seedu.address.model.person.Person> links = new java.util.HashSet<>();
        Person client = new Person(
            new Name("Alice Client"),
            new Phone("81234567"),
            new Email("alice@example.com"),
            new Address("1 Client Rd"),
            WeddingDate.parse("2026-01-01"),
            PersonType.CLIENT,
            java.util.Collections.emptySet(),
            links,
            /* price */ null,
            /* budget */ null,
            java.util.Optional.of(new seedu.address.model.person.Partner("Bob"))
        );

        // Vendor with price and tag (use vendor constructor)
        java.util.Set<Tag> tags = new java.util.HashSet<>();
        tags.add(new Tag("Photography"));
        Person vendor = new Person(
            new Name("Vendor V"),
            new Phone("91234567"),
            new Email("vendor@example.com"),
            new Address("2 Vendor Rd"),
            PersonType.VENDOR,
            tags,
            new Price("500-1000")
        );

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            PersonCard clientCard = new PersonCard(client, 1);
            assertNotNull(clientCard.getRoot());
            PersonCard vendorCard = new PersonCard(vendor, 2);
            assertNotNull(vendorCard.getRoot());
            latch.countDown();
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void loadResultDisplayAndCommandBox_fxmlLoads() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            ResultDisplay rd = new ResultDisplay();
            assertNotNull(rd.getRoot());
            CommandBox cb = new CommandBox(commandText -> null); // command executor not used
            assertNotNull(cb.getRoot());
            latch.countDown();
        });
        latch.await(5, TimeUnit.SECONDS);
    }
}
