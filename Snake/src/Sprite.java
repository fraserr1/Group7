import javafx.scene.image.Image;

import java.util.Random;

public class Sprite {
    final protected int BLOCK_SIZE = 32;
    protected int W,H;
    protected int x,y;
    private int xPositions, yPositions;
    private Random random = new Random();

    public Sprite(int xPositions, int yPositions){
        this.xPositions = xPositions;
        this.yPositions = yPositions;
        this.W = xPositions * BLOCK_SIZE;
        this.H = yPositions * BLOCK_SIZE;
    }
    public void newPosition(){
        this.x = random.nextInt(this.xPositions -1)*BLOCK_SIZE;
        this.y = random.nextInt(this.yPositions -1)*BLOCK_SIZE;
    }
}
