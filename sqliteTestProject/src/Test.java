import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Pair;

public class Test extends Application {
    private Pair<String[],int[]> data;
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        int score = 2;

        data = DBManager.getScores();
        checkScore(score);
        new HighScoreBox().display(data.getKey(), data.getValue());
        primaryStage.show();
    }

    private void checkScore(int score){
        boolean high = false;
        for(int i = 0; i< 5; i++)
            if (score >= data.getValue()[i])
                high = true;
        if (high){
            String name = new InputBox().display("Enter Name", "Enter Initials");
            DBManager.insertHighScore(name, score);
            data = DBManager.getScores();
        }
    }
}
