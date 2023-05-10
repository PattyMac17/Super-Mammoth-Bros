import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Brick extends Obstacle implements Drawable{

    public Brick(int x, int y){
        super(x, y);

    }
    @Override
    public void draw(Graphics g){
        BufferedImage brick = null;
        try {
            brick = ImageIO.read(getClass().getResourceAsStream("brick.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(brick, (int)position.x, (int)position.y,sideLength,sideLength, null);
    }
}
