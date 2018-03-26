import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class HighScoreBox {
    public static void display(String[] names, int[] scores) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("High Scores");
        window.setMinWidth(250);
        window.setResizable(false);

        GridPane grid = new GridPane();
        grid.setHgap(50);
        grid.setVgap(10);

        Label lblOne = new Label();
        lblOne.setText("1.");
        GridPane.setConstraints(lblOne, 0, 0);
        Label lblOneName = new Label();
        lblOneName.setText(names[0]);
        GridPane.setConstraints(lblOneName, 1, 0);
        Label lblOneScore = new Label();
        lblOneScore.setText("" + scores[0]);
        GridPane.setConstraints(lblOneScore, 2, 0);

        Label lblTwo = new Label();
        lblTwo.setText("2.");
        GridPane.setConstraints(lblTwo, 0, 1);
        Label lblTwoName = new Label();
        lblTwoName.setText(names[1]);
        GridPane.setConstraints(lblTwoName, 1, 1);
        Label lblTwoScore = new Label();
        lblTwoScore.setText("" + scores[1]);
        GridPane.setConstraints(lblTwoScore, 2, 1);

        Label lblThree = new Label();
        lblThree.setText("3.");
        GridPane.setConstraints(lblThree, 0, 2);
        Label lblThreeName = new Label();
        lblThreeName.setText(names[2]);
        GridPane.setConstraints(lblThreeName, 1, 2);
        Label lblThreeScore = new Label();
        lblThreeScore.setText("" + scores[2]);
        GridPane.setConstraints(lblThreeScore, 2, 2);

        Label lblFour = new Label();
        lblFour.setText("4.");
        GridPane.setConstraints(lblFour, 0, 3);
        Label lblFourName = new Label();
        lblFourName.setText(names[3]);
        GridPane.setConstraints(lblFourName, 1, 3);
        Label lblFourScore = new Label();
        lblFourScore.setText("" + scores[3]);
        GridPane.setConstraints(lblFourScore, 2, 3);

        Label lblFive = new Label();
        lblFive.setText("5.");
        GridPane.setConstraints(lblFive, 0, 4);
        Label lblFiveName = new Label();
        lblFiveName.setText(names[4]);
        GridPane.setConstraints(lblFiveName, 1, 4);
        Label lblFiveScore = new Label();
        lblFiveScore.setText("" + scores[4]);
        GridPane.setConstraints(lblFiveScore, 2, 4);

        grid.getChildren().addAll(lblFive,lblFiveName,lblFiveScore,lblFour,lblFourName,lblFourScore,
                lblThree,lblThreeName,lblThreeScore,lblOne,lblOneName,lblOneScore,lblTwo,lblTwoName,lblTwoScore);
        grid.setAlignment(Pos.CENTER);
        Button btnClose = new Button("Close");
        btnClose.setOnAction(e -> {
            window.close();
        });

        VBox layout = new VBox(35);
        layout.getChildren().addAll(grid, btnClose);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30, 20, 40, 20));

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();
    }
}


