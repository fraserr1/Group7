import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class InputBox {
    public void display(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram(window);
        });

        Label label = new Label();
        label.setText(message);

        TextField inputField = new TextField("User");
        Button okButton = new Button("Ok");
        okButton.setOnAction(e -> {
            if (isText(inputField, inputField.getText()))
                System.out.println(inputField.getText());
                window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, inputField, okButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20,20,20,20));

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    private boolean isText(TextField inputField, String text) {
        if (text.matches("[a-zA-Z]+")) return true;
        else {
            new AlertBox().display("Error", "Please Enter Letters Only");
            inputField.setText("User");
            inputField.requestFocus();
            return false;
        }
    }

    /**
     * Close program method
     */
    private void closeProgram(Stage window) {
        Boolean really = new ConfirmBox().display("Exit program", "Are you sure you want to exit?");
        if (really) {
            System.out.println("save to a file");
            window.close();
        }
    }
}
