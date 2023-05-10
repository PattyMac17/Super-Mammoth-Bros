import java.awt.*;

public interface Drawable {
    public void draw(Graphics g);
    public void update(World w, double time);
    public void setVelocity(Pair a);
}
