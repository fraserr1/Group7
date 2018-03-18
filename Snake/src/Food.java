import javafx.scene.image.Image;

public class Food extends Sprite{
    private int foodXPos;
    private int foodYPos;
    private Image food = new Image("food.png");
    public Food(int boardXPositions, int boardYPositions){
        super(boardXPositions,boardYPositions);
        newFoodPos();
    }
    public void newFoodPos(){
        super.newPosition();
        this.foodXPos = super.x;
        this.foodYPos = super.y;
    }

    public int getFoodXPos() {
        return foodXPos;
    }

    public int getFoodYPos() {
        return foodYPos;
    }

    public Image getFood() {
        return food;
    }
}
