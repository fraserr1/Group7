//original idea by Almas Baimagambetov was completely redesigned by COIS2240.GROUP7
//https://www.youtube.com/watch?v=nK6l1uVlunc

import javafx.scene.media.AudioClip;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
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

public class Main extends Application {
	
	//*********************************************//
	//               CONSTANTS                     //
	//*********************************************//
	public static final int BLOCK_SIZE = 40;
	public static final int APP_W = 20 * BLOCK_SIZE;
	public static final int APP_H = 15 * BLOCK_SIZE;
	final static Image BACKGROUND_IMAGE = new Image(Main.class.getResource("BG.png").toString());

	
	public static int gameLevel=1;
	public static int segmentsToAdd = 0;
	public static int score = 0; 
	public static int pause = 0;
	public static Stage primaryStage;
	
	public static double X0;
	public static double Y0;
	public static double X1;
	public static double Y1;
	
	public Direction direction = new Direction();
	public static char dir = 'R';
	
	public static Snake snake;
	public static Food food;
	public static Node tempSegment; //holds ImageView node
	
	//decision making variables for timeline
	private boolean moved = false;
	private boolean running = false;
	private boolean isSoundOn = true;
	
	private Timeline timeline = new Timeline();
	
	Scene intro, game;

	
	//*****************************************//
	//         layout for intro scene          //
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
		
    	Text keys = new Text("Use arrow keys to direct the snake \n spacebar to pause the game");
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
	//        layout for game scene          //
	//***************************************//	
	
	private Parent layout_game() {
		
		Pane root = new Pane();
		root.setPrefSize(APP_W, APP_H+40);
		
	//background imageView
		
        final ImageView background = new ImageView(BACKGROUND_IMAGE);
        background.setLayoutY(40);
    
    //text showing score and game level
        
    	Text text = new Text("Level: " + gameLevel + "   Score: " + score);
    	text.setFont(Font.font("Calibri", FontWeight.BOLD, 30));
    	text.setFill(Color.WHITE);
    	text.setLayoutX(30);
    	text.setLayoutY(30);
    	
    	DropShadow ds = new DropShadow();
    	ds.setOffsetY(3.0f);
    	ds.setColor(Color.color(0.5f, 0.5f, 0.5f));
    	
    	text.setEffect(ds);
    	
    	// --- end of text
    	// on click I want to turn off sound -----------------------------------------------------------------
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
    	//
    	
    	
        Group snakeBody = new Group();
        
        snake = new Snake(snakeBody);   
        
		food = new Food();
		
	//------------- KEYFRAME -------------------//
		
		KeyFrame frame = new KeyFrame(Duration.seconds(setTime(gameLevel)), event -> {
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
			
			
			
			//******************** Collision Detection **********************//
			
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
			
			//***************** End of Collision Detection *******************//			
			
			if (food.foodView.getTranslateX() == X0 && food.foodView.getTranslateY() == Y0) {
				food.randomMove(snake);
				score ++;
				segmentsToAdd += gameLevel;
				playSound('B');
			}
			if (score > 40) {
				gameLevel = 5;
			} if (score <= 40) {
				gameLevel = 4;
			} if (score <= 30) {
				gameLevel = 3;
			} if (score <= 20) {
				gameLevel = 2;
			} if (score <= 10) {
				gameLevel = 1;
			}
			  
			
			
			
		});	
		//-----------END of KEYFRAME -------------------//

		timeline.getKeyFrames().add(frame);
		timeline.setCycleCount(Timeline.INDEFINITE);
			
		root.getChildren().addAll(background,text,soundView,food.foodView,snakeBody);
		
		return root;
	}
	

	//*********************************************//
	//               the START method              //
	//*********************************************//
	@Override
	public void start(Stage primaryStage) throws Exception {
		

		
		Scene game = new Scene(layout_game());
		game.setFill(Color.DEEPSKYBLUE);
		
		Scene intro = new Scene(layout_intro());
		
		intro.setOnMouseClicked(event -> {
			primaryStage.setScene(game);
			primaryStage.show();
			running = true;
		});
		
		
		game.setOnKeyPressed(event -> {
			if (!moved && pause == 0)
				return;
			
			switch (event.getCode()) {
			  	case UP: 
			  		if (direction.getDirection() != Direction.Directions.DOWN ) {
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
			  		if (direction.getDirection() != Direction.Directions.UP ) {
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
			  		if (direction.getDirection() != Direction.Directions.RIGHT ) {
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
			  		if (direction.getDirection() != Direction.Directions.LEFT ) {
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
			  			pause = 1;
			  			pauseGame(snake);
			  			System.out.println(pause);
			  		}
			  		else {
			  			pause = 0;
			  			resumeGame(snake);
			  			System.out.println(pause);
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
		//primaryStage.set
		
		primaryStage.setScene(intro);
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
	public void startGame(Snake snake) {
		direction.setDirection(Direction.Directions.RIGHT);
		
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
		primaryStage.setScene(intro);
		primaryStage.show();	
	}	
	
	public void pauseGame(Snake snake) {
		running = false;
		timeline.pause();
	}
	
	public void resumeGame(Snake snake) {
		running = true;
		timeline.play();
	}	
	
	public void restartGame(Snake snake) {
		stopGame(snake);
		gameLevel = 1;
		segmentsToAdd = 0;
		score = 0;
		dir = 'R';
		running = true;
		startGame(snake);	
	}
	
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
	
	// i need to figure out why this doesn't work
	public static double setTime(int gameLevel) {
		double time;
		switch (gameLevel) {
			case 1:
				time = 0.5f;
				return time;
			case 2:
				time = 0.4f;
				return time;
			case 3:
				time = 0.3f;
				return time;
			case 4:
				time = 0.2f;
				return time;
			case 5:
				time = 0.1f;
				return time;
			default:
				return 0;
		}		
	}
}