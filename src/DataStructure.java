import java.awt.*;
public class DataStructure<E extends Obstacle> {
    Node<E> end;
    public DataStructure(){
        end = null;
    }
    public void append(E toAppend){
        Node<E> toAdd = new Node<E>(toAppend);
        toAdd.prev = end;
        end = toAdd;
    }

    public void drawAll( Graphics g){
        Node<E> elem = this.end;
        elem.element.draw(g);
        while(elem.prev != null){
            elem = elem.prev;
            elem.element.draw(g);
        }
    }
    public void updateAll(World w, double time){
        Node<E> node = this.end;
        E elem = node.element;
        elem.update(w, time);
        while(node.prev != null){
            node = node.prev;
            elem = node.element;
            elem.update(w, time);
        }
    }
    public void setVelocity(Pair a){
        Node<E> node = this.end;
        E elem = node.element;
        elem.setVelocity(a);
        while (node.prev != null){
            node = node.prev;
            elem = node.element;
            elem.setVelocity(a);
        }
    }
    public void collisionCheck(World w){
        if(w.player.alive){
            rightCheck(w);
            leftCheck(w);
            jumpStopCheck(w);
            bottomCheck(w);
        }
    }
    public void rightCheck(World w){
        Node<E> node = this.end;
        E elem = node.element;
        if(w.player.hitBox.intersects(elem.rightWall)){
            w.player.leftCollision = true;
            w.player.velocity = new Pair(0, w.player.velocity.y);
            w.ground.setVelocity(new Pair(0, 0));
            w.allCoins.setVelocity(new Pair(0, 0));
            w.special.setVelocity(new Pair(0,0));
            this.setVelocity(new Pair(0, 0));
        }
        else{
            w.player.leftCollision = false;
        }
        while (node.prev != null){
            node = node.prev;
            elem = node.element;
            if(w.player.hitBox.intersects(elem.rightWall)){
                w.player.leftCollision = true;
                w.player.velocity = new Pair(0, w.player.velocity.y);
                w.ground.setVelocity(new Pair(0, 0));
                w.allCoins.setVelocity(new Pair(0, 0));
                w.special.setVelocity(new Pair(0,0));
                this.setVelocity(new Pair(0, 0));
            }
            else{
                w.player.leftCollision = false;
            }
        }
    }
    public void leftCheck(World w){
        Node<E> node = this.end;
        E elem = node.element;
        if(w.player.hitBox.intersects(elem.leftWall)){
            w.player.rightCollision = true;
            w.player.velocity = new Pair(0, w.player.velocity.y);
            w.ground.setVelocity(new Pair(0, 0));
            w.allCoins.setVelocity(new Pair(0, 0));
            w.special.setVelocity(new Pair(0,0));
            this.setVelocity(new Pair(0, 0));
        }
        else{
            w.player.rightCollision = false;
        }
        while (node.prev != null){
            node = node.prev;
            elem = node.element;
            if(w.player.hitBox.intersects(elem.leftWall)){
                w.player.rightCollision = true;
                w.player.velocity = new Pair(0, w.player.velocity.y);
                w.ground.setVelocity(new Pair(0, 0));
                w.allCoins.setVelocity(new Pair(0, 0));
                w.special.setVelocity(new Pair(0,0));
                this.setVelocity(new Pair(0, 0));
            }
            else{
                w.player.rightCollision = false;
            }
        }
    }
    public boolean jumpStartCheck(World w){
        boolean contact = false;
        Node<E> node = this.end;
        E elem = node.element;
        if (w.player.hitBox.intersects(elem.topWall)){
            contact = true;
        }
        while(node.prev != null){
            node = node.prev;
            elem = node.element;
            if (w.player.hitBox.intersects(elem.topWall)){
                contact = true;
            }
        }
        return contact;
    }
    public void jumpStopCheck(World w){
        Node<E> node = this.end;
        E elem = node.element;
        if(w.player.hitBox.intersects(elem.topWall) && w.player.velocity.y > 0){
            w.player.velocity = new Pair(w.player.velocity.x,0);
        }
        while (node.prev != null){
            node = node.prev;
            elem = node.element;
            if(w.player.hitBox.intersects(elem.topWall) && w.player.velocity.y > 0){
                w.player.velocity = new Pair(w.player.velocity.x,0);
            }
        }
    }
    public void bottomCheck(World w){
        Node<E> node = this.end;
        E elem = node.element;
        if (w.player.hitBox.intersects(elem.bottomWall)){
            w.player.velocity = new Pair(w.player.velocity.x, -w.player.velocity.y);
        }
        while(node.prev != null){
            node = node.prev;
            elem = node.element;
            if (w.player.hitBox.intersects(elem.bottomWall)){
                w.player.velocity = new Pair(w.player.velocity.x, -w.player.velocity.y);
            }
        }
    }







    //player-NPC interaction ONLY USE FOR DS allNPCs
    public void allNPCEngage(World w){
        Node<E> node = this.end;
        NPC elem = (NPC) node.element;
        if(elem.position.x - w.player.position.x < 1000 && elem.engaged == false){
            elem.setVelocity(new Pair(-100, 0));
            elem.engaged = true;
        }
        while (node.prev != null){
            node = node.prev;
            elem = (NPC) node.element;
            if(elem.position.x - w.player.position.x < 1000 && elem.engaged == false){
                elem.setVelocity(new Pair(-100, 0));
                elem.engaged = true;
            }
        }
    }
    public void playerNPCLeftCollision(World w){
        Node<E> node = this.end;
        NPC elem = (NPC) node.element;
        left(w, elem);
        while(node.prev != null){
            node = node.prev;
            elem = (NPC) node.element;
            left(w, elem);
        }
    }
    public void left(World w, NPC a){
        if(w.player.hitBox.intersects(a.leftWall) && a.alive && w.player.alive){
            w.player.alive = false;
            w.player.setVelocity(new Pair(0, -470));
            w.player.setAcceleration(new Pair(0, 670));
        }
    }
    public void playerNPCRightCollision(World w){
        Node<E> node = this.end;
        NPC elem = (NPC) node.element;
        right(w, elem);
        while(node.prev != null){
            node = node.prev;
            elem = (NPC) node.element;
            right(w, elem);
        }
    }
    public void right(World w, NPC a){
        if(w.player.hitBox.intersects(a.rightWall) && a.alive && w.player.alive){
            w.player.alive = false;
            w.player.setVelocity(new Pair(0, -470));
            w.player.setAcceleration(new Pair(0, 670));
        }
    }
    public void playerNPCBottomCollision(World w){
        Node<E> node = this.end;
        NPC elem = (NPC) node.element;
        bottom(w, elem);
        while(node.prev != null){
            node = node.prev;
            elem = (NPC) node.element;
            bottom(w, elem);
        }
    }
    public void bottom(World w, NPC a){
        if(w.player.hitBox.intersects(a.bottomWall) && a.alive && w.player.alive){
            w.player.alive = false;
            w.player.setVelocity(new Pair(0, -470));
            w.player.setAcceleration(new Pair(0, 670));
        }
    }
    //this method takes the form allNPCs.playerNPCCollision - goes in player.update
    public void playerNPCCollision(World w){
        playerNPCLeftCollision(w);
        playerNPCRightCollision(w);
        playerNPCBottomCollision(w);
    }
    public void allCheckNPC(World w, DataStructure<Obstacle> DS){
        allCheckLeftNPC(w, DS);
        allCheckRightNPC(w, DS);
    }
    public void allCheckLeftNPC(World w, DataStructure<Obstacle> DS){
        Node<E> node = end;
        NPC elem = (NPC) node.element;
        leftCheckNPC(w, elem, DS);
        while (node.prev != null){
            node = node.prev;
            elem = (NPC) node.element;
            leftCheckNPC(w, elem, DS);
        }
    }
    public void allCheckRightNPC(World w, DataStructure<Obstacle> DS){
        Node<E> node = end;
        NPC elem = (NPC) node.element;
        rightCheckNPC(w, elem, DS);
        while (node.prev != null){
            node = node.prev;
            elem = (NPC) node.element;
            rightCheckNPC(w, elem, DS);
        }
    }
    public void leftCheckNPC(World w, NPC a, DataStructure<Obstacle> DS){
        Node<Obstacle> node = DS.end;
        Obstacle elem = node.element;
        if(a.leftWall.intersects(elem.rightWall)){
            a.leftCollision = true;
            a.bounceCount++;
            if(w.isScrolling){
                a.velocity = new Pair(-100,0);
            }
            else{
                a. velocity = new Pair (100, 0);
            }
        }
        else{
            a. leftCollision = false;
        }
        while (node.prev != null){
            node = node.prev;
            elem = node.element;
            if(a.leftWall.intersects(elem.rightWall)){
                a.leftCollision = true;
                a.bounceCount++;
                if(w.isScrolling){
                    a.velocity = new Pair(-100,0);
                }
                else{
                    a. velocity = new Pair (100, 0);
                }
            }
            else{
                a. leftCollision = false;
            }
        }
    }
    public void rightCheckNPC(World w, NPC a, DataStructure<Obstacle> DS){
        Node<Obstacle> node = DS.end;
        Obstacle elem = node.element;
        if(a.rightWall.intersects(elem.leftWall)){
            a.rightCollision = true;
            a.bounceCount++;
            if(w.isScrolling){
                a.velocity = new Pair(-300,0);
            }
            else{
                a.velocity = new Pair (-100, 0);
            }
        }
        else{
            a.rightCollision = false;
        }
        while (node.prev != null){
            node = node.prev;
            elem = node.element;
            if(a.rightWall.intersects(elem.leftWall)){
                a.rightCollision = true;
                a.bounceCount++;
                if(w.isScrolling){
                    a.velocity = new Pair(-300,0);
                }
                else{
                    a.velocity = new Pair (-100, 0);
                }
            }
            else{
                a.rightCollision = false;
            }
        }
    }
    public void specialDSRightCheck(World w){
        Node<E> node = this.end;
        E elem = node.element;
        if(w.player.hitBox.intersects(elem.rightWall)){
            double h = Main.HEIGHT - 100 - w.player.position.y;
            double flagH = Main.HEIGHT - 100 - elem.position.y;
            int addScore = (int)(h/flagH*100);
            w.score += addScore;
            w.player.leftCollision = true;
            w.player.velocity = new Pair(0, 0); //this is where it is special
            w.player.inControl = false;
            w.ground.setVelocity(new Pair(0, 0));
            w.allObstacles.setVelocity(new Pair(0, 0));
            w.allCoins.setVelocity(new Pair(0, 0));
            this.setVelocity(new Pair(0, 0));
        }
        else{
            w.player.leftCollision = false;
        }
        while (node.prev != null){
            node = node.prev;
            elem = node.element;
            if(w.player.hitBox.intersects(elem.rightWall)){
                double h = Main.HEIGHT - 100 - w.player.position.y;
                double flagH = Main.HEIGHT - 100 - elem.position.y;
                int addScore = (int)(h/flagH*100);
                w.score += addScore;
                w.player.leftCollision = true;
                w.player.velocity = new Pair(0, 0); //this is where it is special
                w.player.inControl = false;
                w.ground.setVelocity(new Pair(0, 0));
                w.allCoins.setVelocity(new Pair(0, 0));
                this.setVelocity(new Pair(0, 0));
            }
            else{
                w.player.leftCollision = false;
            }
        }
    }
    public void ending(World w){
        Node<E> node = this.end;
        E elem = node.element;
        if(w.player.hitBox.intersects(elem.bottomWall)){
            w.player.walkOff = true;
        }
        while(node.prev != null){
            node = node.prev;
            elem = node.element;
            if(w.player.hitBox.intersects(elem.bottomWall)){
                w.player.walkOff = true;
            }
        }
    }

}
