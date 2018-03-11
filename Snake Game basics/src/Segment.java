import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Segment extends Rectangle {
	
	public Rectangle segment;
	public double prevCoordX;
	public double prevCoordY;
	public Direction direct;
	
	public Segment() {
		this.segment = new Rectangle(Main.BLOCK_SIZE, Main.BLOCK_SIZE);
		this.segment.setFill(Color.BLUE);
		this.prevCoordX = segment.getTranslateX();
		this.prevCoordY = segment.getTranslateY();
	}
}
