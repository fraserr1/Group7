//original idea by Almas Baimagambetov was completely redesigned by COIS2240.GROUP7
//https://www.youtube.com/watch?v=nK6l1uVlunc

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.media.AudioClip;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

public class Main extends Application {

    //board
    public static final int BLOCK_SIZE = 40;
    public static final int APP_W = 20 * BLOCK_SIZE;
    public static final int APP_H = 15 * BLOCK_SIZE;

    //background image
    public final static Image BACKGROUND_IMAGE = new Image(Main.class.getResource("BG.png").toString());

    //game control variables
    public static int gameLevel = 1;
    public static int segmentsToAdd = 0;
    public static int score = 0;
    public static int pause = 0;
    public static boolean moved = false;
    public static boolean running = false;
    public static boolean isSoundOn = true;
    public static boolean highScoreBoxClosed = false;

    //segment position variables
    public static double X0;
    public static double Y0;
    public static double X1;
    public static double Y1;
    public Direction direction = new Direction();
    public static char dir = 'R';

    //nodes
    public static Snake snake;
    public static Food food;
    public static Node tempSegment;
    public static Text text;

    //scene related
    static Scene intro, game;
    private Timeline timeline = new Timeline();

    //db
    private Pair<String[],int[]> data;


    //*****************************************//
    //            INTRO SCENE LAYOUT           //
    //*****************************************//
    private Parent layout_intro() {
        Pane root = new Pane();
        root.setPrefSize(APP_W, APP_H);

        Image logo =  new Image(Main.class.getResource("snake.png").toString());
        ImageView logoView = new ImageView(logo);
        logoView.setFitHeight(500);
        logoView.setPreserveRatio(true);
        logoView.setLayoutX(0);

        Image controls =  new Image(Main.class.getResource("controls.png").toString());
        ImageView controlsView = new ImageView(controls);
        controlsView.setFitHeight(100);
        controlsView.setPreserveRatio(true);
        controlsView.setLayoutX(350);
        controlsView.setLayoutY(20);

        Text keys = new Text("Use arrow keys to direct the snake \nspacebar to pause/restart the game");
        keys.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
        keys.setFill(Color.BLUE);
        keys.setLayoutX(470);
        keys.setLayoutY(50);

        Image play =  new Image(Main.class.getResource("play.png").toString());
        ImageView playView = new ImageView(play);
        playView.setFitHeight(150);
        playView.setPreserveRatio(true);
        playView.setLayoutX(330);
        playView.setLayoutY(170);

        final ImageView background = new ImageView(BACKGROUND_IMAGE);

        root.getChildren().setAll(background, logoView, controlsView, keys, playView);

        return root;
    }


    //***************************************//
    //            GAME SCENE LAYOUT          //
    //***************************************//

    private Parent layout_game() {

        Pane root = new Pane();
        root.setPrefSize(APP_W, APP_H+40);

        //background imageView
        final ImageView background = new ImageView(BACKGROUND_IMAGE);
        background.setLayoutY(40);

        // ************* text showing score and game level *********** //
        text = new Text("Level: " + gameLevel + "   Score: " + score);
        text.setFont(Font.font("Calibri", FontWeight.BOLD, 30));
        text.setFill(Color.WHITE);
        text.setLayoutX(30);
        text.setLayoutY(30);

        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0.5f, 0.5f, 0.5f));

        text.setEffect(ds);
        // --- end of text showing score and game level --- //


        // ************ MUTE *************** //
        Image soundOn =  new Image(Main.class.getResource("sound_on.png").toString());
        Image soundOff =  new Image(Main.class.getResource("sound_off.png").toString());
        ImageView soundView = new ImageView(soundOn);
        soundView.setFitHeight(30);
        soundView.setPreserveRatio(true);
        soundView.setLayoutX(760);
        soundView.setLayoutY(5);
        soundView.setOnMouseClicked(event -> {
            if(isSoundOn == true) {
                isSoundOn = false;
                soundView.setImage(soundOff);
            }else {
                isSoundOn = true;
                soundView.setImage(soundOn);
            }
        });
        //------------ end of MUTE ------------ //


        Group snakeBody = new Group();

        snake = new Snake(snakeBody);

        food = new Food();


        // ******************** KEYFRAME *********************//
        KeyFrame frame = new KeyFrame(Duration.seconds(0.3), event -> {
            if(!running)
                return;

            if (snake.getSnake().size() <= 1) {
                tempSegment = snake.getSnake().get(0);
            }

            X1 = tempSegment.getTranslateX();
            Y1 = tempSegment.getTranslateY();

            //position the tempSegment in front of the current head
            Direction.moveInDirection(direction.getDirection(), snake, tempSegment);

            X0 = tempSegment.getTranslateX();
            Y0 = tempSegment.getTranslateY();

            moved = true;

            if (snake.getSnake().size() == 1) {
                if (segmentsToAdd == 0) {
                    playSound('M');
                    snake.getSnake().get(0).setTranslateX(X0);
                    snake.getSnake().get(0).setTranslateY(Y0);
                } else {
                    ImageView head = Segment.changeSegmentView(dir);
                    head.setTranslateX(X0);
                    head.setTranslateY(Y0);
                    snake.getSnake().add(0, head);
                    playSound('M');

                    ImageView neck = Segment.changeSegmentView('S');
                    neck.setTranslateX(X1);
                    neck.setTranslateY(Y1);
                    snake.getSnake().set(1, neck);
                    segmentsToAdd --;
                }
            } else if (snake.getSnake().size() > 1) {
                if (segmentsToAdd == 0) {
                    playSound('M');
                    ImageView head = Segment.changeSegmentView(dir);
                    head.setTranslateX(X0);
                    head.setTranslateY(Y0);

                    ImageView neck = Segment.changeSegmentView('S');
                    neck.setTranslateX(X1);
                    neck.setTranslateY(Y1);
                    snake.getSnake().add(0, head);
                    snake.getSnake().set(1, neck);
                    snake.getSnake().remove(snake.getSnake().size()-1);
                } else {
                    playSound('M');
                    ImageView head = Segment.changeSegmentView(dir);
                    head.setTranslateX(X0);
                    head.setTranslateY(Y0);
                    ImageView neck = Segment.changeSegmentView('S');
                    neck.setTranslateX(X1);
                    neck.setTranslateY(Y1);
                    snake.getSnake().add(0, head);
                    snake.getSnake().set(1, neck);
                    segmentsToAdd --;
                }
            }

            text.setText("Level: " + gameLevel + "   Score: " + score);



            // ******************** COLLISION DETECTION ********************** //

            // if the head of the snake collides with any of its segments then restart the game
            for (Node segment : snake.getSnake()) {
                if (segment != snake.getSnake().get(0) && X0 == segment.getTranslateX()
                        && Y0 == segment.getTranslateY()) {
                    playSound('C');
                    playSound('E');
                    stopGame(snake);
                    break;
                }
            }

            // if the head of the snake moves outside the board bounds then restart the game
            if (X0 < 0 || X0 >= APP_W || Y0 < 40 || Y0 >= APP_H) {
                playSound('C');
                playSound('E');
                stopGame(snake);
            }
            // ---------------- End of COLLISION DETECTION ----------------- //


            // ************ snake eating food scenario ***************** //
            if (food.foodView.getTranslateX() == X0 && food.foodView.getTranslateY() == Y0) {
                food.randomMove(snake);
                score ++;
                segmentsToAdd += gameLevel;
                playSound('B');
            } // update game level (and amount of segments to add consequentially) based on score
            if (score > 40 && gameLevel != 5) {
                gameLevel = 5;
            } if (score <= 40 && gameLevel != 4) {
                gameLevel = 4;
            } if (score <= 30 && gameLevel != 3) {
                gameLevel = 3;
            } if (score <= 20 && gameLevel != 2) {
                gameLevel = 2;
            } if (score <= 10 && gameLevel != 1) {
                gameLevel = 1;
            }


        });
        // ----------- end of KEYFRAME ----------------- //


        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);


        Pane p = new Pane();
        p.setBackground(new Background(new BackgroundFill(Color.DEEPSKYBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        p.getChildren().addAll(background,text,soundView);
        root.getChildren().addAll(p,food.foodView,snakeBody);

        return root;
    }


    //*********************************************//
    //               the START method              //
    //*********************************************//
    @Override
    public void start(Stage primaryStage) throws Exception {

        game = new Scene(layout_game());

        intro = new Scene(layout_intro());

        intro.setOnMouseClicked(event -> {
            primaryStage.setScene(game);
            primaryStage.show();
            running = true;
            timeline.play();
        });

        game.setOnKeyPressed(event -> {
            if (!moved && pause == 0)
                return;

            switch (event.getCode()) {
                case UP:
                    if (direction.getDirection() != Direction.Directions.DOWN && pause != 1) {
                        direction.setDirection(Direction.Directions.UP);
                        playSound('U');
                        dir = 'U';
                        ImageView head = Segment.changeSegmentView(dir);
                        head.setTranslateX(X0);
                        head.setTranslateY(Y0);
                        snake.getSnake().set(0, head);
                    }
                    break;
                case DOWN:
                    if (direction.getDirection() != Direction.Directions.UP && pause != 1) {
                        direction.setDirection(Direction.Directions.DOWN);
                        playSound('D');
                        dir = 'D';
                        ImageView head = Segment.changeSegmentView(dir);
                        head.setTranslateX(X0);
                        head.setTranslateY(Y0);
                        snake.getSnake().set(0, head);
                    }
                    break;
                case LEFT:
                    if (direction.getDirection() != Direction.Directions.RIGHT && pause != 1) {
                        direction.setDirection(Direction.Directions.LEFT);
                        playSound('L');
                        dir = 'L';
                        ImageView head = Segment.changeSegmentView(dir);
                        head.setTranslateX(X0);
                        head.setTranslateY(Y0);
                        snake.getSnake().set(0, head);
                    }
                    break;
                case RIGHT:
                    if (direction.getDirection() != Direction.Directions.LEFT && pause != 1) {
                        direction.setDirection(Direction.Directions.RIGHT);
                        playSound('R');
                        dir = 'R';
                        ImageView head = Segment.changeSegmentView(dir);
                        head.setTranslateX(X0);
                        head.setTranslateY(Y0);
                        snake.getSnake().set(0, head);
                    }
                    break;
                case SPACE:
                    if (pause == 0) {
                        pauseGame(snake);
                    }
                    else {
                        resumeGame(snake);
                    }
                    break;
                default:
                    break;
            }

            moved = false;
        });




        primaryStage.setTitle("SNAKE GAME");
        primaryStage.setResizable(false);
        primaryStage.setMaxWidth(APP_W+6);
        primaryStage.setMaxHeight(APP_H+36);

        primaryStage.setScene(intro);
        primaryStage.show();

        startGame(snake);

        // ******* LISTENER TO CATCH CHANGES IN PRIMARYSTAGE PROPERTIES ********
        primaryStage.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {

                if (highScoreBoxClosed == true) {
                    running = false;
                    //pause = 0;
                    primaryStage.setScene(intro);
                    primaryStage.show();
                    System.out.println(highScoreBoxClosed);
                }
            }
        });
        // ---------- end of LISTENER ------------ //

    }



    //*********************************************//
    //  the main function that launches JavaFx     //
    //*********************************************//
    public static void main(String[] args) {
        launch(args);
    }



    //*****************************************************//
    //    functions that control start/end of the game     //
    //*****************************************************//
    public void startGame(Snake snake) {
        direction.setDirection(Direction.Directions.RIGHT);
        dir = 'R';

        ImageView segment = Segment.changeSegmentView(dir);
        segment.setTranslateX(0);
        segment.setTranslateY(280);
        snake.getSnake().add(segment);

        gameLevel = 1;
        segmentsToAdd = 0;
        score = 0;
        timeline.play();
    }

    public void stopGame(Snake snake) {
        running = false;
        snake.getSnake().clear();
        timeline.stop();
        text.setText("                                           GAME OVER");
        checkScore(score);
        gameLevel = 1;
        segmentsToAdd = 0;
        score = 0;
        dir = 'R';
        running = true;
        startGame(snake);
        pauseGame(snake);
        pause = 0;
        X0 += 40; //reset snake//
    }

    public void pauseGame(Snake snake) {
        pause = 1;
        running = false;
        timeline.pause();
    }

    public void resumeGame(Snake snake) {
        pause = 0;
        running = true;
        timeline.play();

    }

    //@SuppressWarnings("static-access")
    private void checkScore(int score){

        boolean high = false;
        data = DBManager.getScores();
        for(int i = 0; i< 5; i++)
            if (score > data.getValue()[i])
                high = true;
        if (high){
            new InputBox().display("Enter Name", "Enter Initials", score);
        }
        else
            new HighScoreBox().display(data.getKey(),data.getValue());
    }


    // **************** SOUND EFFECTS ****************** //
    public void playSound(char dir) {
        if (isSoundOn) {
            switch (dir) {
                case 'U':
                    AudioClip u = new AudioClip(this.getClass().getResource("up.wav").toString());
                    u.play();
                    break;
                case 'D':
                    AudioClip d = new AudioClip(this.getClass().getResource("down.wav").toString());
                    d.play();
                    break;
                case 'L':
                    AudioClip l = new AudioClip(this.getClass().getResource("left.wav").toString());
                    l.play();
                    break;
                case 'R':
                    AudioClip r = new AudioClip(this.getClass().getResource("right.wav").toString());
                    r.play();
                    break;
                case 'B':
                    AudioClip b = new AudioClip(this.getClass().getResource("bite.wav").toString());
                    b.play();
                    break;
                case 'E':
                    AudioClip e = new AudioClip(this.getClass().getResource("ending.wav").toString());
                    e.play();
                    break;
                case 'C':
                    AudioClip c = new AudioClip(this.getClass().getResource("crash.wav").toString());
                    c.play();
                    break;
                case 'M':
                    AudioClip m = new AudioClip(this.getClass().getResource("move.wav").toString());
                    m.play();
                    break;
                case 'W':
                    AudioClip w = new AudioClip(this.getClass().getResource("win.wav").toString());
                    w.play();
                    break;
                default:
                    break;
            }
        }
    }
    // --------- end of SOUND EFECTS ---------- //

}