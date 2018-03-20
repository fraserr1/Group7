
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Segment {
	
	public static Image headUp =  new Image(Main.class.getResource("SnakeHead_U.png").toString());
	public static Image headDown =  new Image(Main.class.getResource("SnakeHead_D.png").toString());
	public static Image headLeft =  new Image(Main.class.getResource("SnakeHead_L.png").toString());
	public static Image headRight =  new Image(Main.class.getResource("SnakeHead_R.png").toString());
	public static Image segment =  new Image(Main.class.getResource("segment+.png").toString());
	public static ImageView segmentView = new ImageView(headRight);
	
	public static ImageView changeSegmentView(char dir) {
		switch (dir) {
		case 'U':
			segmentView = new ImageView(headUp);
			return segmentView;
		case 'D':
			segmentView = new ImageView(headDown);
			return segmentView;
		case 'L':
			segmentView = new ImageView(headLeft);
			return segmentView;
		case 'R':
			segmentView = new ImageView(headRight);
			return segmentView;
		case 'S':
			segmentView = new ImageView(segment);
			return segmentView;
		case 'T':
			return null;
		default:
			return null;
		}	
	}
}
