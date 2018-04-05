The Snake Readme File
April 2018
© Software Design & Modelling Group 7, 2018. All rights reserved.

Welcome to The Snake game!

This file contains information to help you install "The Snake".
 
The Snake game is a JavaFX implementation of the classic arcade game, Snake. 

[CONTENTS]

1.1 SYSTEM REQUIREMENTS
1.2 INSTALLATION
1.3 SOURCE CODE
2.1 GAMEPLAY
3.1 AUTHORS
3.2 LICENSE
3.3 ACKNOWLEDGMENTS

[1.1 SYSTEM REQUIREMENTS] 

The game requires Java SE 8 or higher to run. Java Runtime Environment 8 (JRE 8) can be downloaded at http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html

[1.2 INSTALLATION] 

Double-click the executable zip file and select the destination folder. The game can be run by double-clicking the SnakeGame.jar file in the game folder. The scores.db file needs to be present in the same folder or the game will not run properly.

[1.3 SOURCE CODE] 

The source code is found in the source folder. It is run through the main function of the Main class which extends Application and launches JavaFX. Snake, Food and Direction classes provide logical support for the game objects and the Segment class handles the snake’s images. The introductory Scene is provided by an instance of the StartScreen class and the database for the highest scores is managed by static methods in the DBManager class. High scores are displayed by an instance of the HighScoreBox class. Input and validation are provided by instances of the InputBox and AlertBox classes.

Once the snake has a tail, it moves by positioning the last Node in the snake’s ObservableList to the front of the snake: 

public class Directions {
    
    ...
     
    /** moveInDirection method 
     * Moves the given Node to the head of the snake */ 
    public static void moveInDirection(Directions direction, Snake snake, Node tempSegment) { 
	switch (direction) { 
	    case UP: 
		tempSegment.setTranslateX(snake.getSnake().get(0).getTranslateX()); 		
		tempSegment.setTranslateY(snake.getSnake().get(0).getTranslateY() - Main.BLOCK_SIZE); // Move up 
		break;

	    ...

	}
    }

    ...
    
} 

[2.1 GAMEPLAY] 

Upon running for the first time an introductory scene is displayed with the gameplay instructions, the scene switches to the game with a mouse click.
 
The player chooses the direction of the snake using the arrow keys on the keyboard. The player may pause the current game using the spacebar. An apple appears on at random positions in the game and the player’s score increases when the snake’s head is successfully navigated to the apple.
 
The length of the snake increases after scoring. If the snake runs past the edge of the screen or into itself, the game ends. There are 5 difficulty levels and the length that the snake increases per point increase with each level. When the game ends, the high scores are displayed and the game returns to the introductory scene. If the player has beat a high score they are prompted to enter their initials.

[3.1 AUTHORS]

(Svyatoslav Loboda, Fraser Raney, Shivam Parekh, and Jiatao Wen)

[3.2 LICENSE]

This program is free for use with modification. If it can help others learn more about JavaFX, JDBC, and programming in general, we are all happy.

[3.3 ACKNOWLEDGMENTS]

The graphics used in the game were provided under free-for-use-with-modification license. The original game algorithm by Almas Baimagambetov (https://www.youtube.com/watch?v=nK6l1uVlunc) was completely redesigned. Tutorials Point is a great resource for learning about JDBC (https://www.tutorialspoint.com/sqlite/sqlite_java.htm). There are great other great tutorials for JavaFX available on YouTube.com from thenewboston at https://www.youtube.com/channel/UCJbPGzawDH1njbqV-D5HqKw.