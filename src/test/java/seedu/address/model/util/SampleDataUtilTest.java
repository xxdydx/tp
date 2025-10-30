package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

public class SampleDataUtilTest {
    @Test
    public void getTagSet_deduplicatesAndPreservesValues() {
        Set<seedu.address.model.tag.Tag> tags =
            seedu.address.model.util.SampleDataUtil.getTagSet(
                "Photography", "Photography", "Decor"
            );
        assertEquals(2, tags.size());
        assertTrue(tags.stream().anyMatch(t -> t.tagName.equals("Photography")));
        assertTrue(tags.stream().anyMatch(t -> t.tagName.equals("Decor")));
    }
}


