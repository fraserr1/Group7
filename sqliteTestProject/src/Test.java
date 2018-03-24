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
        int[] scores = {10,20,30,40,50};
        String[] names = {"AAA","BBB","CCC", "DDD", "EEE"};

        data = DBTest.getScores();

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
            DBTest.insertHighScore(name, score);
        }
    }
}
