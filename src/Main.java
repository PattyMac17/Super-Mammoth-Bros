import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
public class Main extends JPanel implements KeyListener{
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final int FPS = 1000;
    World world;
    class Runner implements Runnable{
        public void run() {
            while(true){
                if(!world.startSequence){
                    world.update(1.0 / (double)FPS);
                }
                repaint();
                try{
                    Thread.sleep(1000/FPS);
                }
                catch(InterruptedException e){}
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE && !world.getHelp){
            world.startSequence = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_B){
            world.getHelp = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            System.exit(0);
        }
        if(e.getKeyCode() == KeyEvent.VK_V && world.viewHighScore == false){
            world.getHelp = true;
        }
        if(world.player.alive && world.player.inControl){
            if(e.getKeyCode() == KeyEvent.VK_W && world.player.ableToJump){
                world.player.setAcceleration(new Pair(world.player.acceleration.x, 670));
                world.player.setVelocity(new Pair(world.player.velocity.x, -470));
            }
            if(e.getKeyCode() == KeyEvent.VK_A && !world.player.atStart && !world.player.leftCollision){
                Pair a = new Pair(-world.playerVelocity, world.player.velocity.y);
                world.player.setVelocity(a);
                world.player.direction = "left";
            }
            if (e.getKeyCode() == KeyEvent.VK_D && !world.player.rightCollision){
                world.player.direction = "right";
                Pair a = new Pair(world.playerVelocity, world.player.velocity.y);
                if (!world.player.atMiddle){
                    world.player.setVelocity(a);
                }
                else{
                    a = new Pair(-world.playerVelocity, 0);
                    world.ground.setVelocity(a);
                    world.allObstacles.setVelocity(a);
                    world.allCoins.setVelocity(a);
                    world.special.setVelocity(a);
                    world.isScrolling = true;
                }
            }
        }

    }

    public void keyReleased(KeyEvent e) {
        if( (e.getKeyCode() == KeyEvent.VK_A) || (e.getKeyCode() == KeyEvent.VK_D)){
            Pair a = new Pair(0, world.player.velocity.y);
            world.player.setVelocity(a);
            world.ground.setVelocity(new Pair(0, 0));
            world.isScrolling = false;
            world.allObstacles.setVelocity(new Pair(0, 0));
            world.allCoins.setVelocity(new Pair(0, 0));
            world.special.setVelocity(new Pair(0, 0));
        }
    }


    public void keyTyped(KeyEvent e) {

    }

    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    public Main(){
        world = new World(WIDTH, HEIGHT);
        addKeyListener(this);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        Thread mainThread = new Thread(new Runner());
        mainThread.start();
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Super Mammoth Bros");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Main mainInstance = new Main();
        frame.setContentPane(mainInstance);
        frame.pack();
        frame.setVisible(true);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(world.startSequence){
            if(world.getHelp){
                BufferedImage ground = null;
                BufferedImage controls = null;
                BufferedImage w = null;
                BufferedImage d = null;
                BufferedImage a = null;
                BufferedImage esc = null;
                BufferedImage b = null;
                try {
                    w = ImageIO.read(getClass().getResourceAsStream("W.png"));
                    b = ImageIO.read(getClass().getResourceAsStream("B.png"));
                    d = ImageIO.read(getClass().getResourceAsStream("D.png"));
                    a = ImageIO.read(getClass().getResourceAsStream("A.png"));
                    esc = ImageIO.read(getClass().getResourceAsStream("ESC.png"));
                    ground = ImageIO.read(getClass().getResourceAsStream("ground.png"));
                    controls = ImageIO.read(getClass().getResourceAsStream("Controls.png"));
                } catch (IOException e) {
                        e.printStackTrace();
                }
                g.setColor(new Color(63,191,255));
                g.fillRect(0,0,WIDTH,HEIGHT);
                g.drawImage(ground, 0,Main.HEIGHT - 100,8000,100, null);
                g.drawImage(controls, 300,20, null);
                g.drawImage(d, 212,200, 600, 75,  null);
                g.drawImage(a, 212,300,600, 57, null);
                g.drawImage(w, 212,375, 600, 75,null);
                g.drawImage(esc, 212,475, 600, 75,null);
                g.drawImage(b, 212,575, 600, 75,null);
            }
            else{
                BufferedImage menu = null;
                BufferedImage title = null;
                BufferedImage ground = null;
                BufferedImage V = null;
                BufferedImage Space = null;
                try {
                    menu = ImageIO.read(getClass().getResourceAsStream("background.png"));
                    ground = ImageIO.read(getClass().getResourceAsStream("ground.png"));
                    title = ImageIO.read(getClass().getResourceAsStream("title.png"));
                    V = ImageIO.read(getClass().getResourceAsStream("V.png"));
                    Space = ImageIO.read(getClass().getResourceAsStream("SPACE.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                g.drawImage(menu, 0,0,10000,885, null);
                g.drawImage(ground, 0,Main.HEIGHT - 100,8000,100, null);
                g.drawImage(title, 100,100, null);
                g.drawImage(V, 212,350,600, 75, null);
                g.drawImage(Space, 212,450, 600, 75,null);
            }
        }
        else{
            if(!world.player.sixFeetUnder && !world.winner){
                BufferedImage background = null;
                try {
                    background = ImageIO.read(getClass().getResourceAsStream("background.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                g.drawImage(background, (int)world.ground.position.x,0,10000,885, null);

                world.draw(g);
            }
            else{
                Rectangle rect = new Rectangle(0, 0, WIDTH, HEIGHT);
                Font f;
                if(world.winner){
                    BufferedImage ground = null;
                    try {
                        ground = ImageIO.read(getClass().getResourceAsStream("ground.png"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    g.setColor(new Color(63,191,255));
                    g.fillRect(0,0,WIDTH,HEIGHT);
                    g.drawImage(ground, 0,Main.HEIGHT - 100,8000,100, null);
                    g.setColor(new Color(215, 17, 255));
                    String score = "Final Score: " + world.score;
                    f = new Font("TimesRoman", Font.BOLD, 120);
                    drawCenteredString(g, score,rect, f);
                }
                if(world.player.sixFeetUnder){
                    g.setColor(Color.BLACK);
                    g.fillRect(0, 0, WIDTH, HEIGHT);
                    g.setColor(Color.white);
                    String fail = "You Died";
                    f = new Font("TimesRoman", Font.PLAIN, 100);
                    drawCenteredString(g,fail, rect, f);
                }
            }
        }
    }
    //this method was sourced from StackExchange
    public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setFont(font);
        g.drawString(text, x, y);
    }
}