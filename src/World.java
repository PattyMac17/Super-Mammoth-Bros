import java.awt.*;
public class World {
    int score = 0;
    boolean isScrolling = false;
    boolean startSequence = true;
    boolean viewHighScore = false;
    boolean getHelp = false;
    int width;
    int height;
    Player player;
    int playerVelocity;
    Ground ground;
    boolean reachedFlag = false;
    boolean winner = false;
    DataStructure<Obstacle> allObstacles;
    DataStructure<NPC> allNPCs;
    DataStructure<Coin> allCoins;
    DataStructure<Obstacle> special;

    public World(int width, int height){
        this.width = width;
        this.height = height;
        this.player = new Player();
        this.ground = new Ground();
        this.playerVelocity = 200;
        this.allObstacles = new DataStructure<Obstacle>();
        this.allNPCs = new DataStructure<NPC>();
        this.allCoins = new DataStructure<Coin>();
        this.special = new DataStructure<Obstacle>();

        allObstacles.append(new InGameLogo(50, 100));
        allObstacles.append(new Pipe(width/4 + 500, Main.HEIGHT - 100 - 100, 70, 100));
        allObstacles.append(new Brick(1250, 500));
        allObstacles.append(new Block(1100, 500));
        allObstacles.append(new Brick(950, 500));
        allObstacles.append(new Block(1350, 628));
        allObstacles.append(new StairBlock(1400, 588));
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < i; j++){
                allObstacles.append(new StairBlock(1600 + (i * 40), 628 - (j * 40)));
            }
        }
        for (int i = 4; i > 0; i--){
            for (int j = 0; j < i; j++){
                allObstacles.append(new StairBlock(1800 + (i * 40), 628 - (j * 40)));
            }
        }
        allObstacles.append(new Pipe(2250, Main.HEIGHT - 100 - 140,70, 140));
        allCoins.append(new Coin(2270, 350));
        allNPCs.append(new NPC(2100, 300, this));
        allNPCs.append(new NPC(2270, 300, this));
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < i; j++){
                allObstacles.append(new StairBlock(2400 + (i * 40), 628 - (j * 40)));
            }
        }
        for (int i = 0; i < 8; i++){
            allObstacles.append(new StairBlock(2400 + (9 * 40), 628 - (i * 40)));
        }
        special.append(new FlagStaff(3100, Main.HEIGHT - 100 - 320, 320));
        allCoins.append(new Coin(1100, 400));
        allNPCs.append(new NPC(1200, 500, this));
    }
    public void draw(Graphics g) {
        player.draw(g);
        ground.draw(g);
        allObstacles.drawAll(g);
        allCoins.drawAll(g);
        allNPCs.drawAll(g);
        special.drawAll(g);
    }

    public void update(double time) {
        player.update(this, time);
        ground.update(this, time);
        allObstacles.updateAll(this, time);
        allCoins.updateAll(this, time);
        allNPCs.updateAll(this, time);
        special.updateAll(this, time);
    }
}
