package example;

import javafx.scene.Scene;

import java.util.HashSet;
import java.util.Set;

public class BouncerDropBouncer extends Bouncer {

    public BouncerDropBouncer(Scene scene){
        super(scene);
    }

    public Set<GenericBrick> handleBouncerDropCollisions(double elapsedTime, BrickManager brickManager){
        Set<GenericBrick> bricksToBeRemoved = new HashSet<>();
        this.updatePosition(elapsedTime);
        boolean changeYSpeed = false;
        for (GenericBrick brick : brickManager.getMyBricks()){
            if (this.spriteCollidesWithTop(brick)){
                changeYSpeed = true;
                bricksToBeRemoved.add(brick);
            }
        }
        if (changeYSpeed){
            this.setMyYSpeed(-1);
        }
        return bricksToBeRemoved;
    }
}
