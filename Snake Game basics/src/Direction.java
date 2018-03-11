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

	public static void moveInDirection(Directions direction, Snake snake, Node newSegment) {
		switch (direction) {
			case UP:
				newSegment.setTranslateX(snake.getSnake().get(0).getTranslateX());
				newSegment.setTranslateY(snake.getSnake().get(0).getTranslateY() - Main.BLOCK_SIZE);
				break;
			case DOWN:
				newSegment.setTranslateX(snake.getSnake().get(0).getTranslateX());
				newSegment.setTranslateY(snake.getSnake().get(0).getTranslateY() + Main.BLOCK_SIZE);
				break;
			case LEFT:
				newSegment.setTranslateX(snake.getSnake().get(0).getTranslateX() - Main.BLOCK_SIZE);
				newSegment.setTranslateY(snake.getSnake().get(0).getTranslateY());
				break;
			case RIGHT:
				newSegment.setTranslateX(snake.getSnake().get(0).getTranslateX() + Main.BLOCK_SIZE);
				newSegment.setTranslateY(snake.getSnake().get(0).getTranslateY());
				break;
			}
	}
}