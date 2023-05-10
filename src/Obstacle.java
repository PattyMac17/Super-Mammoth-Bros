import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Obstacle {
    Pair position;
    Pair velocity;
    Pair acceleration;
    Rectangle2D leftWall;
    Rectangle2D rightWall;
    Rectangle2D topWall;
    Rectangle2D bottomWall;
    int sideLength;
    int wallWidth;
    //Pair storedSpeed;
    public Obstacle(int x, int y){
        position = new Pair(x, y);
        this.velocity = new Pair(0, 0);
        this.acceleration = new Pair(0, 0);
        sideLength = 40;
        wallWidth = 1;
        topWall = new Rectangle2D.Double(position.x, position.y,
                sideLength, wallWidth);
        leftWall = new Rectangle2D.Double(position.x - wallWidth, position.y + wallWidth,
                wallWidth, sideLength - (2 * wallWidth));
        rightWall = new Rectangle2D.Double(position.x + sideLength, position.y + wallWidth,
                wallWidth, sideLength - (2 * wallWidth));
        bottomWall = new Rectangle2D.Double(position.x, position.y + sideLength - wallWidth,
                sideLength, wallWidth);
    }
    public void update(World w, double time){
        position = position.add(velocity.times(time));
        leftWall.setRect(position.x - wallWidth, position.y + wallWidth,
                wallWidth, sideLength - (2 * wallWidth));
        rightWall.setRect(position.x + sideLength, position.y + wallWidth,
                wallWidth, sideLength - (2 * wallWidth));
        topWall.setRect(position.x, position.y,
                sideLength, wallWidth);
        bottomWall.setRect(position.x, position.y + sideLength - wallWidth,
                sideLength, wallWidth);
        velocity = velocity.add(acceleration.times(time));
    }
    public void setVelocity(Pair v){
        velocity = v;
    }
    public abstract void draw(Graphics g);
}
