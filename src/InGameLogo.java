import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class InGameLogo extends Obstacle implements Drawable{
    int sideWidth;
    public InGameLogo(int x, int y){
        super(x, y);
        sideLength = 600;
        sideWidth = 343;

    }
    @Override
    public void update(World w, double time){
        position = position.add(velocity.times(time));
        leftWall.setRect(position.x - wallWidth, position.y + wallWidth, wallWidth, sideWidth);
        rightWall.setRect(position.x + sideLength, position.y + wallWidth, wallWidth, sideWidth);
        topWall.setRect(position.x, position.y, sideWidth, wallWidth);
        bottomWall.setRect(position.x,position.y + sideWidth - wallWidth, sideLength, wallWidth);
        velocity = velocity.add(acceleration.times(time));
    }
    @Override
    public void draw(Graphics g) {
        BufferedImage inGameLogo = null;
        try {
            inGameLogo = ImageIO.read(getClass().getResourceAsStream("inGameLogo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(inGameLogo, (int)position.x, (int)position.y,sideLength,sideWidth, null);
    }
}
