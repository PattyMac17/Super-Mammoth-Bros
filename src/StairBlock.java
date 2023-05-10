import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class StairBlock extends Obstacle implements Drawable{

    public StairBlock(int x, int y){
        super(x, y);

    }
    @Override
    public void draw(Graphics g){
        BufferedImage stairBlock = null;
        try {
            stairBlock = ImageIO.read(getClass().getResourceAsStream("stairBlock.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(stairBlock, (int)position.x, (int)position.y,sideLength,sideLength, null);
    }
}