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

        allObstacles.append(new Brick(650, 425));
        allObstacles.append(new Brick(750, 300));
        allObstacles.append(new Brick(900, 200));
        allObstacles.append(new Brick(1100, 180));
        allObstacles.append(new Brick(1140, 180));
        allCoins.append(new Coin(1140, 100));
        allObstacles.append(new Brick(1180, 180));
        allObstacles.append(new Brick(1220, 180));
        allCoins.append(new Coin(1220, 100));
        allObstacles.append(new Brick(1260, 180));
        allObstacles.append(new Brick(1300, 180));
        allCoins.append(new Coin(1300, 100));
        allObstacles.append(new Brick(1340, 180));

        allObstacles.append(new Pipe(756, Main.HEIGHT - 100 - 100, 70, 100));

        allObstacles.append(new Brick(1250, 500));
        allObstacles.append(new Block(1100, 500));
        allObstacles.append(new Brick(950, 500));
        allObstacles.append(new Block(1350, 628));
        allObstacles.append(new StairBlock(1400, 588));

        allObstacles.append(new Pipe(1600, Main.HEIGHT - 100 - 130, 70, 130));

        for (int i = 4; i > 0; i--){
            for (int j = 0; j < i; j++){
                allObstacles.append(new StairBlock(1800 + (i * 40), 628 - (j * 40)));
            }
        }

        allObstacles.append(new Pipe(2250, Main.HEIGHT - 100 - 140,70, 140));

        for (int i = 0; i < 9; i++){
            for (int j = 0; j < i; j++){
                allObstacles.append(new StairBlock(2400 + (i * 40), 628 - (j * 40)));
            }
        }
        for (int i = 0; i < 8; i++){
            allObstacles.append(new StairBlock(2400 + (9 * 40), 628 - (i * 40)));
        }

        allCoins.append(new Coin(2270, 350));
        allCoins.append(new Coin(1100, 400));
        allCoins.append(new Coin(1622, 360));
        allNPCs.append(new NPC(2100, 300, this));
        allNPCs.append(new NPC(2270, 300, this));
        allNPCs.append(new NPC(1200, 500, this));

        special.append(new FlagStaff(3070, Main.HEIGHT - 100 - 320, 320));
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
