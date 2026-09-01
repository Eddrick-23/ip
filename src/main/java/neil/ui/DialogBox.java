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

    private DialogBox(String text, String placeholder) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DialogBox.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load the dialog box view", e);
        }

        dialog.setText(text);
        displayPicture.setText(placeholder);
    }

    private void flip() {
        ObservableList<Node> children = FXCollections.observableArrayList(getChildren());
        Collections.reverse(children);
        getChildren().setAll(children);
        setAlignment(Pos.TOP_LEFT);
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
}
