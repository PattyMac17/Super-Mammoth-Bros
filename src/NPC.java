import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class NPC extends Obstacle implements Drawable{
    BufferedImage left1,left2,right1,right2;
    String direc;
    int spriteCounter = 0;
    int spriteNum = 1;
    int bounceCount;
    Color color;
    boolean engaged = false;
    boolean leftCollision;
    boolean rightCollision;
    boolean alive = true;
    int imageSize;
    int sideHeight;

    public NPC(int x, int y, World w) {
        super(x, y);
        sideLength = 50;
        sideHeight = 35;
        imageSize = 50;
        topWall = new Rectangle2D.Double(position.x, position.y,
                sideLength, wallWidth);
        leftWall = new Rectangle2D.Double(position.x - wallWidth, position.y + wallWidth,
                wallWidth, sideHeight - (2 * wallWidth));
        rightWall = new Rectangle2D.Double(position.x + sideLength, position.y + wallWidth,
                wallWidth, sideHeight - (2 * wallWidth));
        bottomWall = new Rectangle2D.Double(position.x, position.y + sideHeight - wallWidth,
                sideLength, wallWidth);
        getImage();
        direc = "left";
        color = Color.YELLOW;
        acceleration = new Pair(acceleration.x, 700);

    }
    public void updateDirection(){
        if(bounceCount%2 == 0){
            direc = "left";
        }
        else{
            direc = "right";
        }
    }

    @Override
    public void draw(Graphics g) {
        if(alive){
            BufferedImage image = null;
            switch (direc) {
                case "right":
                    if (spriteNum == 1){
                        image = right1;
                    }
                    if (spriteNum == 2){
                        image = right2;
                    }
                    break;
                case "left":
                    if (spriteNum == 1){
                        image = left1;
                    }
                    if (spriteNum == 2){
                        image = left2;
                    }
                    break;
            }
            g.drawImage(image,(int)position.x,(int)position.y - 5, imageSize, imageSize,null);
        }
    }

    @Override
    public void update(World w, double time) {
        position = position.add(velocity.times(time));
        leftWall.setRect(position.x - wallWidth, position.y + wallWidth,
                wallWidth, sideHeight - (2 * wallWidth));
        rightWall.setRect(position.x + sideLength, position.y + wallWidth,
                wallWidth, sideHeight - (2 * wallWidth));
        topWall.setRect(position.x, position.y,
                sideLength, wallWidth);
        bottomWall.setRect(position.x, position.y + sideHeight - wallWidth,
                sideLength, wallWidth);
        velocity = velocity.add(acceleration.times(time));

        jumpStop(w);
        scrollAdjust(w);
        updateDirection();
        playerCheck(w);
        spriteCounter++;
        if (velocity.x != 0 || w.ground.velocity.x != 0){
            if (spriteCounter > 100) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }
    public void playerCheck(World w){
        if(topWall.intersects(w.player.hitBox) && w.player.alive && alive){
            alive = false;
            w.score += 100;
        }
    }

    @Override
    public void setVelocity(Pair a) {
        super.setVelocity(a);
    }
    public void jumpStop(World w){
        if(bottomWall.intersects(w.ground.groundLevel)){
            velocity = new Pair(velocity.x,0);
        }
    }
    public void scrollAdjust(World w){
        if(engaged){
            if(w.isScrolling && w.ground.velocity.x != 0){
                if(bounceCount%2 ==0){
                    velocity = new Pair(-300, velocity.y);

                }
                else{
                    velocity = new Pair(-100, velocity.y);
                }
            }
            else{
                if(bounceCount%2 ==0){
                    velocity = new Pair(-100, velocity.y);
                }
                else{
                    velocity = new Pair(100, velocity.y);
                }
            }
        }
    }
    public void getImage(){
        try{
            right1 = ImageIO.read(getClass().getResourceAsStream("eph_right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("eph_right2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("eph_left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("eph_left2.png"));

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
