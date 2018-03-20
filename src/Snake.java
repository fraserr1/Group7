
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;

public class Snake {
	
	private ObservableList<Node> snake;
	
	public ObservableList<Node> getSnake() {
		return snake;
	}

	public void setSnake(ObservableList<Node> snake) {
		this.snake = snake;
	}

	public Snake(Group snakeBody) {
		this.snake = snakeBody.getChildren();
	}
	
}
