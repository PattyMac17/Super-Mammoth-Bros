import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Block extends Obstacle implements Drawable{

    public Block(int x, int y){
        super(x, y);
    }
    @Override
    public void draw(Graphics g){
        BufferedImage block = null;
        try {
            block = ImageIO.read(getClass().getResourceAsStream("block.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(block, (int)position.x, (int)position.y,sideLength,sideLength, null);
    }
}