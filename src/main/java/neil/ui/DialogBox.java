package neil.ui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Represents one message and speaker placeholder in the chat window.
 */
public class DialogBox extends HBox {
    private static final String NEIL_PLACEHOLDER = "N";
    private static final String USER_PLACEHOLDER = "You";

    @FXML
    private Label dialog;
    @FXML
    private Label displayPicture;
    @FXML
    private Label welcomeBanner;

    private DialogBox(String text, String placeholder) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DialogBox.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load the dialog box view", e);
        }

        assert dialog != null : "FXML must inject dialog";
        assert displayPicture != null : "FXML must inject displayPicture";
        assert welcomeBanner != null : "FXML must inject welcomeBanner";

        dialog.setText(text);
        displayPicture.setText(placeholder);
    }

    private void flip() {
        ObservableList<Node> children = FXCollections.observableArrayList(getChildren());
        Collections.reverse(children);
        getChildren().setAll(children);
        setAlignment(Pos.TOP_LEFT);
    }

    private void formatWelcomeMessage() {
        String[] welcomeParts = dialog.getText().split("\\n\\n", 2);
        if (welcomeParts.length != 2) {
            return;
        }

        welcomeBanner.setText(welcomeParts[0]);
        welcomeBanner.setManaged(true);
        welcomeBanner.setVisible(true);
        dialog.setText(welcomeParts[1]);
    }

    /**
     * Returns a right-aligned dialog containing a user message.
     *
     * @param text message entered by the user.
     * @return user dialog box.
     */
    public static DialogBox getUserDialog(String text) {
        DialogBox dialogBox = new DialogBox(text, USER_PLACEHOLDER);
        dialogBox.getStyleClass().add("user-dialog");
        return dialogBox;
    }

    /**
     * Returns a left-aligned dialog containing a Neil response.
     *
     * @param text response produced by Neil.
     * @return Neil dialog box.
     */
    public static DialogBox getNeilDialog(String text) {
        DialogBox dialogBox = new DialogBox(text, NEIL_PLACEHOLDER);
        dialogBox.flip();
        dialogBox.getStyleClass().add("neil-dialog");
        return dialogBox;
    }

    /**
     * Returns a Neil dialog with a monospaced banner and regular greeting text.
     *
     * @param text welcome message containing the banner and greeting.
     * @return Neil welcome dialog box.
     */
    public static DialogBox getNeilWelcomeDialog(String text) {
        DialogBox dialogBox = getNeilDialog(text);
        dialogBox.formatWelcomeMessage();
        return dialogBox;
    }
}
