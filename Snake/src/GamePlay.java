import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.Timer;

public class GamePlay {
    private Pane board;
    private int[] snakeXLength = new int[600];
    private int[] snakeYLength = new int[400];

    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;

    private Timer timer;
    private int delay = 100;

    private Image snakeSheet = new Image(getClass().getResourceAsStream("snakeset.png"));
    private ImageView head = new ImageView(snakeSheet);
    private ImageView body = new ImageView(snakeSheet);


    GamePlay(){
        board = new Pane();
        board.setBackground(new Background(new BackgroundFill(Color.KHAKI,
                CornerRadii.EMPTY, Insets.EMPTY)));
        board.setPrefHeight(400);
        board.setPrefWidth(600);

        head.setViewport(new Rectangle2D(78,150,32,32));
        body.setViewport(new Rectangle2D(176,90,32,32));

        board.getChildren().addAll(head);


    }

    public Pane getBoard() {
        return board;
    }
}
