import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Food extends Rectangle{

	public Rectangle food;

	public Food() {
		this.food = new Rectangle(Main.BLOCK_SIZE, Main.BLOCK_SIZE);
		this.food.setFill(Color.RED);
		this.food.setTranslateX((int)(Math.random()*(Main.APP_W-Main.BLOCK_SIZE))/Main.BLOCK_SIZE * Main.BLOCK_SIZE);
		this.food.setTranslateY((int)(Math.random()*(Main.APP_H-Main.BLOCK_SIZE))/Main.BLOCK_SIZE * Main.BLOCK_SIZE);
	}
	
}
