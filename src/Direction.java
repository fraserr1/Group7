import javafx.scene.Node;

public class Direction {

	public static enum Directions {
		UP, DOWN, LEFT, RIGHT
	}
	
	private Directions direction = Directions.RIGHT;
	
	public Directions getDirection() {
		return direction;
	}

	public void setDirection(Directions direction) {
		this.direction = direction;
	}

	public static void moveInDirection(Directions direction, Snake snake, Node tempSegment) {
		switch (direction) {
			case UP:
				tempSegment.setTranslateX(snake.getSnake().get(0).getTranslateX());
				tempSegment.setTranslateY(snake.getSnake().get(0).getTranslateY() - Main.BLOCK_SIZE);
				break;
			case DOWN:
				tempSegment.setTranslateX(snake.getSnake().get(0).getTranslateX());
				tempSegment.setTranslateY(snake.getSnake().get(0).getTranslateY() + Main.BLOCK_SIZE);
				break;
			case LEFT:
				tempSegment.setTranslateX(snake.getSnake().get(0).getTranslateX() - Main.BLOCK_SIZE);
				tempSegment.setTranslateY(snake.getSnake().get(0).getTranslateY());
				break;
			case RIGHT:
				tempSegment.setTranslateX(snake.getSnake().get(0).getTranslateX() + Main.BLOCK_SIZE);
				tempSegment.setTranslateY(snake.getSnake().get(0).getTranslateY());
				break;
			}
	}
}