package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;

public class SampleDataUtilTest {
    @Test
    public void getTagSet_deduplicatesAndPreservesValues() {
        Set<Tag> tags = SampleDataUtil.getTagSet("Photography & Videography", "Photography & Videography", "Decor");
        assertEquals(2, tags.size());
        assertTrue(tags.stream().anyMatch(t -> t.tagName.equals("Photography & Videography")));
        assertTrue(tags.stream().anyMatch(t -> t.tagName.equals("Decor")));
    }
}


