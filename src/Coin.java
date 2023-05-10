import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Coin extends Obstacle implements Drawable{

    Rectangle2D hitBox;
    boolean collected = false;
    public Coin(int x, int y) {
        super(x, y);
        hitBox = new Rectangle2D.Double(x + sideLength/4, y, sideLength/2, sideLength);
    }
    public void playerCheck(World w){
        if(hitBox.intersects(w.player.hitBox) && !collected && w.player.alive){
            collected = true;
            w.score += 100;
        }
    }
    @Override
    public void update(World w, double time){
        position = position.add(velocity.times(time));
        hitBox.setRect(position.x + sideLength/4, position.y, sideLength/2, sideLength);
        velocity = velocity.add(acceleration.times(time));
        playerCheck(w);
    }

    @Override
    public void draw(Graphics g) {
        if(!collected){
            BufferedImage coin = null;
            try {
                coin = ImageIO.read(getClass().getResourceAsStream("coin.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            g.drawImage(coin, (int)position.x, (int)position.y,sideLength,sideLength, null);
        }
    }
}
