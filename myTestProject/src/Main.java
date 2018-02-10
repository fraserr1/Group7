import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application{

    Stage window;
    Scene scene1, scene2;
    Button myButton;

    public static void main(String[]args){
        launch(args); //setup main as a JavaFX app
    }

    @Override //inherited method from Application class
    public void start(Stage primaryStage) throws Exception {
        //stage
        window = primaryStage;
        window.setTitle("The Title of My Java FX Application");

        //My Button
        Image redButton = new Image(getClass().getResourceAsStream("X_BUTTON.png"));
        ImageView imageView = new ImageView(redButton);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        myButton = new Button();
        myButton.setText("Big Red");
        myButton.setGraphic(imageView);

        //Scene 1
        Label label1 = new Label("Welcome to the first scene.");
        Button button1 = new Button("Go to scene 2");
        button1.setOnAction(e -> window.setScene(scene2));
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1,button1);
        scene1 = new Scene(layout1, 600,400);

        //Scene 2
        Label label2 = new Label("Welcome to the second scene.");
        Button button2 = new Button("Go to scene 1");
        myButton.setOnAction(e -> window.setScene(scene1));
        VBox layout2 = new VBox(20);
        layout2.getChildren().addAll(label2,myButton);
        scene2 = new Scene(layout2, 600,400);



        window.setScene(scene1);
        window.show();
    }
}
