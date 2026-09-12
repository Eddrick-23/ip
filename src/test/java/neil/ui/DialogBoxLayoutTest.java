package neil.ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Tests the declarative layout rules for chat messages.
 */
class DialogBoxLayoutTest {
    @Test
    void dialogBoxFxml_responsiveLayout_fixedMessageWidthsNotUsed() throws IOException {
        String fxml = readResource("/view/DialogBox.fxml");

        assertTrue(fxml.contains("fx:id=\"dialogContent\""));
        assertFalse(fxml.contains("maxWidth=\"310.0\""));
        assertFalse(fxml.contains("maxWidth=\"282.0\""));
    }

    private String readResource(String resourcePath) throws IOException {
        try (InputStream input = DialogBoxLayoutTest.class.getResourceAsStream(resourcePath)) {
            assert input != null : "Test resource must exist: " + resourcePath;
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
