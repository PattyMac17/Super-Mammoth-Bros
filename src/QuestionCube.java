import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class QuestionCube extends Obstacle implements Drawable{

    public QuestionCube(int x, int y){
        super(x, y);
    }

    public void draw(Graphics g){
        BufferedImage questionCube = null;
        try {
            questionCube = ImageIO.read(getClass().getResourceAsStream("questionCube.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(questionCube, (int)position.x, (int)position.y,sideLength,sideLength, null);
    }
}