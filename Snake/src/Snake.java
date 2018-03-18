import com.sun.javafx.scene.traversal.Direction;
import javafx.scene.image.Image;

public class Snake extends Sprite{
    private Image headLeft;
    private Image headRight;
    private Image headUp;
    private Image headDown;
    private Image body;
    private int[] snakeXLength;
    private int[] snakeYLength;
    private int snakeLength;

    public Snake(int boardXPositions, int boardYPositions){
        super(boardXPositions,boardYPositions);
        this.headLeft = new Image("headLeft.png");
        this.headRight = new Image("headRight.png");
        this.headUp = new Image("headUp.png");
        this.headDown = new Image("headDown.png");
        this.body = new Image("body.png");
        this.snakeLength = 1;
        this.snakeXLength = new int[boardXPositions * boardYPositions];
        this.snakeYLength = new int[boardXPositions * boardYPositions];
        newSnakePos();
    }
    public void newSnakePos(){
        super.newPosition();
        this.snakeXLength[0] = super.x;
        this.snakeYLength[0] = super.y;
    }

    public boolean updateSnakePos(Direction direction){
        if (direction == Direction.UP) {
            for (int j = snakeLength - 1; j >= 0; j--) {
                snakeXLength[j + 1] = snakeXLength[j];
            }
            for (int j = snakeLength - 1; j >= 0; j--) {
                if (j == 0)
                    if (snakeYLength[j] > 0)
                        snakeYLength[j] -= 32;
                    else return true;
                else
                    snakeYLength[j] = snakeYLength[j - 1];
            }
        }
        if (direction == Direction.DOWN){
            for (int j = snakeLength - 1; j >= 0; j--) {
                snakeXLength[j + 1] = snakeXLength[j];
            }
            for (int j = snakeLength - 1; j >= 0; j--) {
                if (j == 0)
                    if (snakeYLength[j] + 32 < super.H)
                        snakeYLength[j] += 32;
                    else return true;
                else
                    snakeYLength[j] = snakeYLength[j - 1];
            }
        }
        if (direction == Direction.RIGHT) {
            for (int j = snakeLength - 1; j >= 0; j--) {
                snakeYLength[j + 1] = snakeYLength[j];
            }
            for (int j = snakeLength - 1; j >= 0; j--) {
                if (j == 0)
                    if (snakeXLength[j] + 32 < super.W)
                        snakeXLength[j] += 32;
                    else return true;
                else
                    snakeXLength[j] = snakeXLength[j - 1];
            }
        }
        if (direction == Direction.LEFT){
            for (int j = snakeLength - 1; j >= 0; j--) {
                snakeYLength[j + 1] = snakeYLength[j];
            }
            for (int j = snakeLength - 1; j >= 0; j--) {
                if (j == 0)
                    if (snakeXLength[j] > 0)
                        snakeXLength[j] -= 32;
                    else return true;
                else
                    snakeXLength[j] = snakeXLength[j - 1];
            }
        }
        for (int k = 1; k < snakeLength - 1; k++) {
            if (snakeXLength[k] == snakeXLength[0] && snakeYLength[k] == snakeYLength[0]) {
                return true;
            }
        }
        return false;
    }

    public int getSnakeHeadXPos(){
        return snakeXLength[0];
    }
    public int getSnakeHeadYPos(){
        return snakeYLength[0];
    }

    public int[] getSnakeXLength() {
        return snakeXLength;
    }

    public int[] getSnakeYLength() {
        return snakeYLength;
    }

    public Image getHeadLeft() {
        return headLeft;
    }

    public Image getHeadRight() {
        return headRight;
    }

    public Image getHeadUp() {
        return headUp;
    }

    public Image getHeadDown() {
        return headDown;
    }

    public Image getBody() {
        return body;
    }

    public int getSnakeLength() {
        return snakeLength;
    }

    public void setSnakeLength(int snakeLength) {
        this.snakeLength = snakeLength;
    }
}
