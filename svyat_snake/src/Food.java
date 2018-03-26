import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;

public class Food{

    public Image food =  new Image(Main.class.getResource("apple.png").toString());
    public ImageView foodView;

    int x, y;

    public Food() {
        this.foodView = new ImageView(food);
        this.x = ((int)(Math.random()*(Main.APP_W-Main.BLOCK_SIZE))/Main.BLOCK_SIZE * Main.BLOCK_SIZE);
        this.y = ((int)(Math.random()*(Main.APP_H-Main.BLOCK_SIZE))/Main.BLOCK_SIZE * Main.BLOCK_SIZE);
        if (this.y == 0) this.y = y+40;
        this.foodView.setFitHeight(40);
        this.foodView.setPreserveRatio(true);
        this.foodView.setTranslateX(x);
        this.foodView.setTranslateY(y);

    }
    public void randomMove(Snake snake) {
        this.x = ((int)(Math.random()*(Main.APP_W-Main.BLOCK_SIZE))/Main.BLOCK_SIZE * Main.BLOCK_SIZE);
        this.y = ((int)(Math.random()*(Main.APP_H-Main.BLOCK_SIZE))/Main.BLOCK_SIZE * Main.BLOCK_SIZE);
        if (this.y == 0) this.y = y+40;
        Main.food.foodView.setFitHeight(40);
        Main.food.foodView.setPreserveRatio(true);
        Main.food.foodView.setTranslateX(x);
        Main.food.foodView.setTranslateY(y);
        if (ifOverlap(snake)) {
            randomMove(snake);
        }
    }

    public boolean ifOverlap(Snake snake) {
        for (Node segment : snake.getSnake()) {
            if (Main.food.foodView.getTranslateX() == segment.getTranslateX()
                    && Main.food.foodView.getTranslateY() == segment.getTranslateY()) {
                return true;
            }
        }
        return false;
    }



}	