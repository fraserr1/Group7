import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.AudioClip;

import java.util.Random;


public class Driver extends Application{
    Stage window;
    Scene scene;

    final private int BLOCK_SIZE = 32;
    final private int XPOS = 20;
    final private int YPOS = 15;

    private int W = BLOCK_SIZE * XPOS;
    private int H = BLOCK_SIZE * YPOS;

    private int score;

    private Random random;

    private int foodXPos;
    private  int foodYPos;

    private int[] snakeXLength;
    private int[] snakeYLength;

    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;

    private int moves = 0;

    private Label lblScore = new Label("Score: " + score);

    private int lengthOfSnake = 3;

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


        Pane root = new Pane();
        Canvas canvas = new Canvas(W,H);
        root.getChildren().add(canvas);
        root.setBackground(new Background(new BackgroundFill(Color.FORESTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));

        parent.setCenter(root);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image headLeft = new Image("headLeft.png");
        Image headRight = new Image("headRight.png");
        Image headUp = new Image("headUp.png");
        Image headDown = new Image("headDown.png");
        Image body = new Image("body.png");
        Image food = new Image("food.png");

        random = new Random();

        newGame();

        scene = new Scene(parent);

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
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
        });


        primaryStage.setScene(scene);
        primaryStage.show();


        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        KeyFrame frame = new KeyFrame(Duration.seconds(0.3), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //getMove(scene);
                gc.clearRect(0,0,W,H);
                for (int i = 0; i < lengthOfSnake; i++){
                    if (i == 0) {
                        if (left) gc.drawImage(headLeft, snakeXLength[i], snakeYLength[i]);
                        else if (right) gc.drawImage(headRight, snakeXLength[i], snakeYLength[i]);
                        else if (up) gc.drawImage(headUp, snakeXLength[i], snakeYLength[i]);
                        else gc.drawImage(headDown, snakeXLength[i], snakeYLength[i]);
                    }
                    else{
                        gc.drawImage(body, snakeXLength[i],snakeYLength[i]);
                    }
                }

                if (foodXPos == snakeXLength[0] && foodYPos == snakeYLength[0]){
                    eatSound();
                    newFood();
                    score++;
                    lblScore.setText("Score: " + score);
                    lengthOfSnake++;
                }
                gc.drawImage(food,foodXPos,foodYPos);

                for (int k = 1; k < lengthOfSnake -1; k++){
                    if (snakeXLength[k] == snakeXLength[0] && snakeYLength[k] == snakeYLength[0]) {
                        newGame();
                    }
                }

                if (up) {
                    for (int j = lengthOfSnake - 1; j >= 0; j--) {
                        snakeXLength[j + 1] = snakeXLength[j];
                    }
                    for (int j = lengthOfSnake - 1; j >= 0; j--) {
                        if (j == 0)
                            if (snakeYLength[j] > 0)
                                snakeYLength[j] -= 32;
                            else newGame();
                        else
                            snakeYLength[j] = snakeYLength[j - 1];
                    }
                }
                if (down){
                    for (int j = lengthOfSnake - 1; j >= 0; j--) {
                        snakeXLength[j + 1] = snakeXLength[j];
                    }
                    for (int j = lengthOfSnake - 1; j >= 0; j--) {
                        if (j == 0)
                            if (snakeYLength[j] + 32 < H)
                                snakeYLength[j] += 32;
                            else newGame();
                        else
                            snakeYLength[j] = snakeYLength[j - 1];
                    }
                }
                if (right) {
                    for (int j = lengthOfSnake - 1; j >= 0; j--) {
                        snakeYLength[j + 1] = snakeYLength[j];
                    }
                    for (int j = lengthOfSnake - 1; j >= 0; j--) {
                        if (j == 0)
                            if (snakeXLength[j] + 32 < W)
                                snakeXLength[j] += 32;
                            else newGame();
                        else
                            snakeXLength[j] = snakeXLength[j - 1];
                    }
                }
                if (left){
                    for (int j = lengthOfSnake - 1; j >= 0; j--) {
                        snakeYLength[j + 1] = snakeYLength[j];
                    }
                    for (int j = lengthOfSnake - 1; j >= 0; j--) {
                        if (j == 0)
                            if (snakeXLength[j] > 0)
                                snakeXLength[j] -= 32;
                            else newGame();
                        else
                            snakeXLength[j] = snakeXLength[j - 1];
                    }
                }
            }
        });
        gameLoop.getKeyFrames().add(frame);
        gameLoop.play();

        window = primaryStage;
        window.setTitle("Snake");
        window.setResizable(false);

    }


    private void newGame(){
        this.moves = 0;
        this.lengthOfSnake = 1;
        this.left = false;
        this.right = false;
        this.up = false;
        this.down = false;
        this.snakeXLength = new int[50];
        this.snakeYLength = new int[50];
        this.score = 0;
        this.snakeXLength[0]=320;
        this.snakeYLength[0]=192;
        this.lblScore.setText("Score: " + score);
        this.newFood();
    }

    private void newFood(){
        boolean overlap = true;
        int x,y;

        do
        {
            x = random.nextInt(XPOS -1)*32;
            y = random.nextInt(YPOS -1)*32;

            for(int i = 0; i < lengthOfSnake; i++)
            {
                if(snakeXLength[i] == x && snakeYLength[i] == y)
                {
                    break;
                }

                if( i == lengthOfSnake -1 && snakeXLength[i] != x && snakeYLength[i] != y)
                {
                    overlap = false;
                }
            }

        }while (overlap);

        this.foodXPos = x;
        this.foodYPos = y;
    }

    private void eatSound()
    {
        AudioClip u = new AudioClip(this.getClass().getResource("eat.mp3").toString());
        u.play();
    }


//    private void getMove(Scene scene){
//        scene.setOnKeyPressed(new EventHandler<javafx.scene.input.KeyEvent>() {
//            @Override
//            public void handle(javafx.scene.input.KeyEvent event) {
//                switch (event.getCode()) {
//                    case LEFT:
//                        if (right != true)
//                            left = true;
//                        else
//                            right = true;
//                        up = false;
//                        down = false;
//                        break;
//                    case RIGHT:
//                        if (left != true)
//                            right = true;
//                        else
//                            left = true;
//                        up = false;
//                        down = false;
//                        break;
//                    case UP:
//                        left = false;
//                        right = false;
//                        if (down != true)
//                            up = true;
//                        else
//                            down = true;
//                        break;
//                    case DOWN:
//                        left = false;
//                        right = false;
//                        if (up != true)
//                            down = true;
//                        else
//                            up = true;
//                        break;
//                }
//            }
//        });
//    }
}
