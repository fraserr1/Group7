/**
 * references
 * https://www.youtube.com/watch?v=_SqnzvJuKiA
 * https://www.w3schools.com/colors/colors_hex.asp
 * https://www.javatips.net/api/javafx.scene.layout.backgroundfill
 *
 * */


import com.sun.javafx.scene.traversal.Direction;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.Random;


public class Driver extends Application{
    Stage window;
    Scene scene;
    Timeline gameLoop;
    long time;
    boolean waiter = false;

    final private int BLOCK_SIZE = 32;
    final private int XPOS = 20;
    final private int YPOS = 15;

    private int W = BLOCK_SIZE * XPOS;
    private int H = BLOCK_SIZE * YPOS;

    private int score;

    private Pair<String[],int[]> data;

    private Snake s;
    private Food f;

    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;


    private Label lblScore = new Label("Score: " + score);;

    public static void main(String[]args){
        launch(args);
    }
    public void start(Stage primaryStage) throws Exception {

        BorderPane parent = new BorderPane();
        HBox top = new HBox(20);
        top.setAlignment(Pos.CENTER);
        top.setBackground(new Background(new BackgroundFill(Color.KHAKI, CornerRadii.EMPTY, Insets.EMPTY)));
        lblScore.setFont(new Font("Impact", 20));
        top.getChildren().addAll(lblScore);
        parent.setTop(top);

        data = DBManager.getScores();

        Pane root = new Pane();
        Canvas canvas = new Canvas(W,H);
        root.getChildren().add(canvas);
        root.setBackground(new Background(new BackgroundFill(Color.FORESTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));

        parent.setCenter(root);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        s = new Snake(XPOS,YPOS);
        f = new Food(XPOS,YPOS);
        newGame();

        scene = new Scene(parent);

//        scene.setOnKeyPressed(e -> {
//            switch (e.getCode()) {
//                case LEFT:
//                    if (right != true)
//                        left = true;
//                    else
//                        right = true;
//                    up = false;
//                    down = false;
//                    break;
//                case RIGHT:
//                    if (left != true)
//                        right = true;
//                    else
//                        left = true;
//                    up = false;
//                    down = false;
//                    break;
//                case UP:
//                    left = false;
//                    right = false;
//                    if (down != true)
//                        up = true;
//                    else
//                        down = true;
//                    break;
//                case DOWN:
//                    left = false;
//                    right = false;
//                    if (up != true)
//                        down = true;
//                    else
//                        up = true;
//                    break;
//            }
//        });


        primaryStage.setScene(scene);
        primaryStage.show();


        gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        KeyFrame frame = new KeyFrame(Duration.seconds(0.3), e-> {
                time = System.currentTimeMillis();
                getMove(scene, System.currentTimeMillis()); // Handle KeYEvents
                gc.clearRect(0,0,W,H);
                for (int i = 0; i < s.getSnakeLength(); i++){
                    if (i == 0) {
                        if (left) gc.drawImage(s.getHeadLeft(), s.getSnakeXLength()[i], s.getSnakeYLength()[i]);
                        else if (right) gc.drawImage(s.getHeadRight(), s.getSnakeXLength()[i], s.getSnakeYLength()[i]);
                        else if (up) gc.drawImage(s.getHeadUp(), s.getSnakeXLength()[i], s.getSnakeYLength()[i]);
                        else gc.drawImage(s.getHeadDown(), s.getSnakeXLength()[i], s.getSnakeYLength()[i]);
                    }
                    else{
                        gc.drawImage(s.getBody(), s.getSnakeXLength()[i], s.getSnakeYLength()[i]);
                    }
                }

                if (f.getFoodXPos() == s.getSnakeHeadXPos() && f.getFoodYPos() == s.getSnakeHeadYPos()){
                    newFood();
                    score++;
                    lblScore.setText("Score: " + score);
                    s.setSnakeLength(s.getSnakeLength() + 1);
                }
                gc.drawImage(f.getFood(),f.getFoodXPos(),f.getFoodYPos());

                // Move the snake according to the user's direction
                if (up){
                    if(s.updateSnakePos(Direction.UP)) restart();
                }

                if (down){
                    if(s.updateSnakePos(Direction.DOWN)) restart();
                }
                if (left){
                    if(s.updateSnakePos(Direction.LEFT)) restart();
                }
                if (right){
                    if(s.updateSnakePos(Direction.RIGHT)) restart();
                }
        });
        gameLoop.getKeyFrames().add(frame);
        gameLoop.play();

        window = primaryStage;
        window.setTitle("Snake");
        window.setResizable(false);

    }
    private void newGame(){
        s.setSnakeLength(1);
        s.newSnakePos();
        this.left = false;
        this.right = false;
        this.up = false;
        this.down = false;
        this.score = 0;
        this.lblScore.setText("Score: " + score);
    }

    private void restart(){
        gameLoop.stop();
        checkScore(score);
        s.setSnakeLength(1);
        s.newSnakePos();
        this.left = false;
        this.right = false;
        this.up = false;
        this.down = false;
        this.score = 0;
        this.lblScore.setText("Score: " + score);
        gameLoop.play();
    }
    /** newFood method
     * Checks if the new food position is on top of the snake and moves it somewhere else*/
    private void newFood(){
        boolean overlap;
        do {
        f.newFoodPos();
        overlap = false;
        for (int k = 0; k < s.getSnakeLength() - 1; k++) {
            if (s.getSnakeXLength()[k] == f.getFoodXPos() && s.getSnakeYLength()[k] == f.getFoodYPos()) {
                overlap = true;
                break;
            }
        }}while(overlap);
    }
    private void checkScore(int score){
        boolean high = false;
        data = DBManager.getScores();
        for(int i = 0; i< 5; i++)
            if (score >= data.getValue()[i])
                high = true;
        if (high){
            new InputBox().display("Enter Name", "Enter Initials", score);
        }
        else
            new HighScoreBox().display(data.getKey(),data.getValue());
    }

    private void waitForIt(){
        Timeline timer = new Timeline(new KeyFrame(Duration.seconds(0.3)));
        timer.play();
    }

    /** getMove method
     * Handles KeyEvents*/
    private void getMove(Scene scene, long time){

        scene.onKeyPressedProperty().addListener(new ChangeListener<EventHandler<? super KeyEvent>>() {
            @Override
            public void changed(ObservableValue<? extends EventHandler<? super KeyEvent>> observable, EventHandler<? super KeyEvent> oldValue, EventHandler<? super KeyEvent> newValue) {
                long newTime = System.currentTimeMillis();
                if (newTime - time < 10) waiter = false;
                else waiter = true;
            }
        });
        if (!waiter){
        scene.setOnKeyPressed(new EventHandler<javafx.scene.input.KeyEvent>() {
            @Override
            public void handle(javafx.scene.input.KeyEvent event) {
                waiter = true;
                switch (event.getCode()) {
                    case LEFT:
                        if (right != true)
                            left = true;
                        else
                            right = true;
                        up = false;
                        down = false;
                        break;
                    case RIGHT:
                        if (left != true)
                            right = true;
                        else
                            left = true;
                        up = false;
                        down = false;
                        break;
                    case UP:
                        left = false;
                        right = false;
                        if (down != true)
                            up = true;
                        else
                            down = true;
                        break;
                    case DOWN:
                        left = false;
                        right = false;
                        if (up != true)
                            down = true;
                        else
                            up = true;
                        break;
                }
            }
        });}
    }
}
