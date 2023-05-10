import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player {
    BufferedImage left1,left2,right1,right2;
    String direction;
    int spriteCounter = 0;
    int spriteNum = 1;
    Rectangle2D hitBox;
    Pair position;
    Pair velocity;
    Pair acceleration;
    int playerHeight;
    int playerWidth;
    Color testColor;
    boolean atMiddle = false;
    boolean atStart = false;
    boolean ableToJump = false;
    boolean leftCollision = false;
    boolean rightCollision = false;
    //boolean topCollision = false;
    boolean alive = true;
    boolean sixFeetUnder = false;
    boolean inControl = true;
    boolean walkOff;
    int walkOffCounter = 0;
    int imageSize;
    public Player(){
        getImage();
        direction = "right";
        this.playerHeight = 36;
        this.playerWidth = 60;
        this.imageSize = 60;
        this.position = new Pair(50, Main.HEIGHT - 99 - this.playerHeight);
        this.velocity = new Pair(0, 0);
        this.acceleration = new Pair(0, 0);
        this.hitBox = new Rectangle2D.Double(position.x, position.y, playerWidth, playerHeight);
        this.testColor = Color.CYAN;
    }
    public void getImage(){ //pulls images needed for the character
        //we did this by referring to a YouTube tutorial
        try{
            right1 = ImageIO.read(getClass().getResourceAsStream("bebu_right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("bebu_right2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("bebu_left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("bebu_left2.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void deathCheck(World w){
        if(position.y > w.height){
            sixFeetUnder = true;
        }
    }
    public void endSequence(World w){
        if(!inControl && !w.reachedFlag){
            w.reachedFlag = true;
            setAcceleration(new Pair(0, 670));
        }
    }
    public void walk(World w){
        if(walkOff){
            if(walkOffCounter == 0){
                setPosition(new Pair(position.x, Main.HEIGHT - 99 - this.playerHeight));
                walkOffCounter++;
            }
            setVelocity(new Pair(w.playerVelocity, 0));
        }
    }
    public void gameOverSwitch(World w){
        if(walkOff){
            if(position.x > Main.WIDTH){
                w.winner = true;
            }
        }
    }
    public void update(World w, double time){
        position = position.add(velocity.times(time));
        hitBox.setRect(position.x,position.y, playerWidth,playerHeight);
        velocity = velocity.add(acceleration.times(time));
        spriteCounter++;
        if ((hitBox.intersects(w.ground.groundLevel) || w.allObstacles.jumpStartCheck(w)) && (velocity.x != 0 || w.ground.velocity.x != 0)){
            if (spriteCounter > 100) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        midCheck();
        wallCheck();
        if(alive){
            jumpStop(w);
        }
        deathCheck(w);
        jumpStart(w);
        w.allNPCs.allNPCEngage(w);
        w.allNPCs.playerNPCCollision(w);
        w.allNPCs.allCheckNPC(w, w.allObstacles);
        w.allObstacles.collisionCheck(w);
        if(inControl){
            w.special.specialDSRightCheck(w);
        }
        endSequence(w);
        w.special.ending(w);
        walk(w);
        gameOverSwitch(w);
    }
    public void midCheck(){
        Rectangle2D midline = new Rectangle2D.Double(Main.WIDTH/2 + playerWidth, 0, Main.WIDTH/2 -
                playerWidth, Main.HEIGHT);
        if(this.hitBox.intersects(midline)){
            atMiddle = true;
            velocity = new Pair(0, velocity.y);
        }
        else{
            atMiddle = false;
        }
    }
    public void wallCheck(){
        Rectangle2D start = new Rectangle2D.Double(-10, 0, 10, Main.HEIGHT);
        if(this.hitBox.intersects(start)){
            atStart = true;
            velocity = new Pair(0, velocity.y);
        }
        else{
            atStart = false;
        }
    }
    public void draw(Graphics g){
        BufferedImage image = null;
        switch (direction) {
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
        g.drawImage(image,(int)position.x,(int)position.y - 10, imageSize, imageSize,null);
    }

    public void setPosition(Pair p){
        position = p;
    }
    public void setVelocity(Pair v){
        velocity = v;
    }

    public void setAcceleration(Pair acceleration) {
        this.acceleration = acceleration;
    }
    public void jumpStop(World w){
        if(hitBox.intersects(w.ground.groundLevel) && velocity.y > 0){
            velocity = new Pair(velocity.x,0);
        }
    }
    public void jumpStart(World w){
        if(hitBox.intersects(w.ground.groundLevel) || w.allObstacles.jumpStartCheck(w)){
            ableToJump = true;
        }
        else{
            ableToJump = false;
        }
    }
}
