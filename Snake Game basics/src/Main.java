//created by Almas Baimagambetov
//https://www.youtube.com/watch?v=nK6l1uVlunc

import javafx.scene.media.AudioClip;
/*import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.net.URL;*/

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
	
	//*********************************************//
	//               CONSTANTS                     //
	//*********************************************//
	public static final int BLOCK_SIZE = 40;
	public static final int APP_W = 25 * BLOCK_SIZE;
	public static final int APP_H = 20 * BLOCK_SIZE;
	final static Image BACKGROUND_IMAGE = new Image(Main.class.getResource("BG.png").toString());
	
	public static int gameLevel = 0;
	public static int segmentsToAdd = 0;
	
	public Direction direction = new Direction();
	
	public Snake snake;
	public Food food;
	public Node segment;
	
	//MediaPlayer mediaplayer;


	
	//decision making variables for transition
	private boolean moved = false;
	private boolean running = false;
	
	private Timeline timeline = new Timeline();
	
	//*********************************************//
	//        createContent -> Parent              //
	//*********************************************//
	private Parent createContent() {
		
		Pane root = new Pane();
		root.setPrefSize(APP_W + 200, APP_H);
		
		//background imageView
        final ImageView background = new ImageView(BACKGROUND_IMAGE);
        
        Group snakeBody = new Group();
        
        snake = new Snake(snakeBody);   
        
		food = new Food();
			
		KeyFrame frame = new KeyFrame(Duration.seconds(0.3), event -> {
			if(!running)
				return;
			
			boolean toRemove = snake.getSnake().size() > 1;
			
			Node newSegment = toRemove ? snake.getSnake().remove(snake.getSnake().size()-1) : snake.getSnake().get(0);
			
			double newSegmentX = newSegment.getTranslateX();
			double newSegmentY = newSegment.getTranslateY();
			
			
			Direction.moveInDirection(direction.getDirection(), snake, newSegment);
			
			moved = true;
			
			if(toRemove)
				snake.getSnake().add(0, newSegment);
			
			//collision detection
			
			for (Node rect : snake.getSnake()) {
				if (rect != newSegment && newSegment.getTranslateX() == rect.getTranslateX()
						&& newSegment.getTranslateY() == rect.getTranslateY()) {
					restartGame(snake);
					break;
				}
			}
			
			if (newSegment.getTranslateX() < 0 || newSegment.getTranslateX() >= APP_W ||
					newSegment.getTranslateY() < 0 || newSegment.getTranslateY() >= APP_H) {
				restartGame(snake);
			}
			
			if (newSegment.getTranslateX() == food.food.getTranslateX() 
					&& newSegment.getTranslateY() == food.food.getTranslateY()) {
				food.food.setTranslateX((int)(Math.random()*(APP_W-BLOCK_SIZE))/BLOCK_SIZE * BLOCK_SIZE);
				food.food.setTranslateY((int)(Math.random()*(APP_H-BLOCK_SIZE))/BLOCK_SIZE * BLOCK_SIZE);
				
				Rectangle rect = new Rectangle(BLOCK_SIZE,BLOCK_SIZE);
				rect.setFill(Color.BLUE);
				rect.setTranslateX(newSegmentX);
				rect.setTranslateY(newSegmentY);
				
				snake.getSnake().add(rect);
			}
		});	
			
		timeline.getKeyFrames().add(frame);
		timeline.setCycleCount(Timeline.INDEFINITE);
			
		root.getChildren().addAll(background,food.food,snakeBody);
		
		return root;
	}
	

	

	//*********************************************//
	//               the START method               //
	//*********************************************//
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Scene scene = new Scene(createContent());
		
		scene.setOnKeyPressed(event -> {
			if (!moved)
				return;
			
			switch (event.getCode()) {
			  	case UP: 
			  		if (direction.getDirection() != Direction.Directions.DOWN ) {
				  		direction.setDirection(Direction.Directions.UP);
				  		playSound('U');
			  		}
				  		break;
			  	case DOWN: 
			  		if (direction.getDirection() != Direction.Directions.UP ) {
				  		direction.setDirection(Direction.Directions.DOWN);
				  		playSound('D');
			  		}
			  		break;
			  	case LEFT: 
			  		if (direction.getDirection() != Direction.Directions.RIGHT ) {
				  		direction.setDirection(Direction.Directions.LEFT);
				  		playSound('L');
			  		}
			  		break;
			  	case RIGHT:
			  		if (direction.getDirection() != Direction.Directions.LEFT ) {
				  		direction.setDirection(Direction.Directions.RIGHT);
				  		playSound('R');
			  		}
				  	break;
			  	default:
			  		break;
			  	} 
			
			moved = false;	
		});
		
		primaryStage.setTitle("SNAKE GAME");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		startGame(snake);
		
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
	private void startGame(Snake snake) {
		direction.setDirection(Direction.Directions.RIGHT);
		Rectangle head = new Rectangle(BLOCK_SIZE,BLOCK_SIZE);
		head.setFill(Color.BLUE);
		snake.getSnake().add(head);
		timeline.play();
		running = true;
	}
	
	private void stopGame(Snake snake) {
		running = false;
		timeline.stop();
		snake.getSnake().clear();
	}	
	
	private void restartGame(Snake snake) {
		stopGame(snake);
		startGame(snake);
		
	}
	
	public void playSound(char dir) {
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
			default:
				break;
		}		
	}
	
}