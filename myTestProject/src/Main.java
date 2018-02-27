import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    Stage window;
    Scene scene1, scene2, scene3;
    Button myButton;

    public static void main(String[] args) {
        launch(args); //setup main as a JavaFX app
    }

    @Override //inherited method from Application class
    public void start(Stage primaryStage) throws Exception {
        /** stage */
        window = primaryStage;
        window.setTitle("The Title of My Java FX Application");

        /** My Button */
        Image redButton = new Image(getClass().getResourceAsStream("X_BUTTON.png"));
        ImageView imageView = new ImageView(redButton);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        myButton = new Button();
        myButton.setText("Big Red");
        myButton.setGraphic(imageView);

        /** Scene 1 */
        Label label1 = new Label("Welcome to the first scene.");
            /** Buttons */
        Button sceneButton = new Button("Go to scene 2");
        Button popupButton = new Button("Open a new window");
        Button boolButton = new Button("Do you like Booleans?");
        Button closeButton = new Button("Close");
            /** Button actions */
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
        sceneButton.setOnAction(e -> window.setScene(scene2));
        popupButton.setOnAction(e -> new AlertBox().display("Popup", "Close me"));
        boolButton.setOnAction(e -> {
            boolean answer = new ConfirmBox().display("Yes or No?", "Are you sure?");
            System.out.println("answer: " + answer);
        });
        closeButton.setOnAction(e -> closeProgram());
        /** Layout */
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1, sceneButton, popupButton, boolButton, closeButton);
        scene1 = new Scene(layout1, 600, 400);

        /** Scene 2 */
        Label label2 = new Label("Welcome to the second scene.");

        Button button2 = new Button("Go to border pane");
        button2.setOnAction(e-> window.setScene(scene3));

        myButton.setOnAction(e -> window.setScene(scene1));
        VBox layout2 = new VBox(20);
        layout2.getChildren().addAll(label2, myButton, button2);
        scene2 = new Scene(layout2, 600, 400);

        /** Embedded layouts */
        HBox topMenu = new HBox(20);
        topMenu.setAlignment(Pos.CENTER);
        topMenu.setPadding(new Insets(20,0,20,0));
        topMenu.setStyle("-fx-background-color: dodgerblue;");
        Button buttonA = new Button("A");
        Button buttonB = new Button("B");
        Button buttonC = new Button("C");
        topMenu.getChildren().addAll(buttonA,buttonB,buttonC);

        VBox leftMenu = new VBox(20);
        leftMenu.setStyle("-fx-background-color: dodgerblue;");
        leftMenu.setPadding(new Insets(20,20,0,20));
        Button buttonD = new Button("D");
        Button buttonE = new Button("E");
        Button buttonF = new Button("F");
        Button backButton = new Button("Back");
        backButton.setOnAction(e-> window.setScene(scene2));
        leftMenu.getChildren().addAll(buttonD,buttonE,buttonF, backButton);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topMenu);
        borderPane.setLeft(leftMenu);

        scene3 = new Scene(borderPane, 600,400);

        /** Gridpane */
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(8);

        Label nameLabel = new Label("username");
        GridPane.setConstraints(nameLabel,0,0);

        TextField nameInput = new TextField("username");
        GridPane.setConstraints(nameInput,1,0);

        Label passLabel = new Label("password");
        GridPane.setConstraints(passLabel,0,1);

        TextField passInput = new TextField();
        passInput.setPromptText("password");
        GridPane.setConstraints(passInput,1,1);

        Button login = new Button("Log In");
        GridPane.setConstraints(login, 1,2);

        grid.getChildren().addAll(nameLabel,nameInput,passLabel,passInput,login);
        grid.setAlignment(Pos.CENTER);
        
        borderPane.setCenter(grid);

        /** Initialize window */
        window.setScene(scene1);
        window.show();
    }

    /** Close program method */
    private void closeProgram(){
        Boolean really = new ConfirmBox().display("Exit program", "Are you sure you want to exit?");
        if (really) {
            System.out.println("save to a file");
            window.close();
        }
    }
}
