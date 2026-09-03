package neil.ui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import neil.Neil;

/**
 * Controls Neil's main chat window.
 */
public class MainWindow {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Neil neil;

    @FXML
    private void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Supplies the command processor and displays its welcome message.
     *
     * @param neil command processor used by this window.
     */
    public void setNeil(Neil neil) {
        this.neil = neil;
        dialogContainer.getChildren().add(DialogBox.getNeilWelcomeDialog(neil.getWelcomeMessage()));
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = neil.getResponse(input);

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input),
                DialogBox.getNeilDialog(response)
        );
        userInput.clear();

        if (neil.isExitCommand(input)) {
            Platform.exit();
        }
    }
}
