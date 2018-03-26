/** References
 * https://www.youtube.com/watch?v=cwJK_WpseKQ*/

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

public class InputBox {
    private String username = "AAA";
    private Pair<String[],int[]> data;
    public String display(String title, String message, int score) {
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

        TextField inputField = new TextField("AAA");
        inputField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (inputField.getText().length() > 3){
                    inputField.setText(inputField.getText().substring(0,3));
                }
            }
        });
        inputField.setTextFormatter(new TextFormatter<Object>(e->{
            e.setText(e.getText().toUpperCase());
            return e;
        }));
        Button okButton = new Button("Ok");
        okButton.setOnAction(e -> {
            if (isText(inputField, inputField.getText())){
                username = inputField.getText();
                closeProgram(window);
                DBManager.insertHighScore(inputField.getText(), score);
                data = DBManager.getScores();
                new HighScoreBox().display(data.getKey(),data.getValue());
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, inputField, okButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20,20,20,20));

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();
        return username;
    }

    private boolean isText(TextField inputField, String text) {
        if (text.matches("[a-zA-Z]+") && text.length() == 3) return true;
        else {
            new AlertBox().display("Error", "Please Enter Three Letters Only");
            inputField.setText("AAA");
            inputField.requestFocus();
            return false;
        }
    }

    /**
     * Close program method
     */
    private void closeProgram(Stage window) {
            window.close();
        }
    }

