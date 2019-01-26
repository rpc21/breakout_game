package example;

import javafx.scene.Group;
import javafx.scene.Scene;

import java.util.*;

public class SpriteManager {

    private int myLevelNumber;
    private GameDifficulty myCurrentMode;
    private Bouncer myBouncer;
    private Paddle myPaddle;
    private BrickManager myBrickManager;
    private Group myRoot;
    private Set<GenericBrick> bricksToHandle;
    private boolean lostLifeUpdate;
    private int scoreUpdate;
    private int newPowerUp;

    public SpriteManager(GameDifficulty currentMode, Group root, int levelNumber){
        myLevelNumber = levelNumber;
        myCurrentMode = currentMode;
        myBouncer = new Bouncer(root.getScene(), currentMode);
        myPaddle = new Paddle(root.getScene());
        myBrickManager = new BrickManager(levelNumber, currentMode);
        myRoot = root;
        bricksToHandle = new HashSet<>();
        lostLifeUpdate = false;
        scoreUpdate = 0;
        newPowerUp = PowerUpManager.INVALID_POWER_UP_NUMBER;

    }

    public void updateSprites(double elapsedTime){
        bricksToHandle.clear();
        myPaddle.updatePaddlePosition(elapsedTime, myRoot.getScene());
        handleBouncerCollisions(elapsedTime);

    }


    private void handlePaddleCollisions() {
        if (myBouncer.spriteCollidesWithTop(myPaddle)){
            myBouncer.setMyXSpeed(myBouncer.getMyXSpeed() + myPaddle.getMyXSpeed() / 2);
            myBouncer.setMyYSpeed(myBouncer.getMyYSpeed() * -1);
        }
        if (myBouncer.getMyXSpeed() > myCurrentMode.getMaxBouncerXSpeed()){
            myBouncer.setMyYSpeed(myCurrentMode.getMaxBouncerXSpeed());
        }
        if (myBouncer.getMyXSpeed() < myCurrentMode.getMinBouncerXSpeed()){
            myBouncer.setMyYSpeed(myCurrentMode.getMinBouncerXSpeed());
        }
    }

    private void handleBouncerCollisions(double elapsedTime) {
        myBouncer.updatePosition(elapsedTime);
        handleWallCollisions(myRoot.getScene());
        handlePaddleCollisions();
        bricksToHandle = checkBouncerAndBrickCollisions(myBrickManager.getMyBricks());
    }

    private Set<GenericBrick> checkBouncerAndBrickCollisions(Set<GenericBrick> bricks) {
        Set<GenericBrick> effectedBricks = new HashSet<>();
        boolean bounceInXDirection = false;
        boolean bounceInYDirection = false;
        // https://stackoverflow.com/questions/8104692/how-to-avoid-java-util-concurrentmodificationexception-when-iterating-through-an
        for (Iterator<GenericBrick> iterator = bricks.iterator(); iterator.hasNext(); ) {
            GenericBrick brick = iterator.next();
            if (myBouncer.spriteCollidesWithTop(brick) || myBouncer.spriteCollidesWithBottom(brick)){
                effectedBricks.add(brick);
                bounceInYDirection = true;
            }
            if (myBouncer.spriteCollidesWithLeft(brick) || myBouncer.spriteCollidesWithRight(brick)){
                effectedBricks.add(brick);
                bounceInXDirection = true;
            }
        }
        if(bounceInXDirection){
            myBouncer.setMyXSpeed(myBouncer.getMyXSpeed() * -1);
        }
        if (bounceInYDirection){
            myBouncer.setMyYSpeed(myBouncer.getMyYSpeed() * -1);
        }
        return effectedBricks;
    }

    private void handleWallCollisions(Scene scene) {
        if (myBouncer.getX() <= 0 || myBouncer.getX() + myBouncer.getBoundsInLocal().getWidth() >= scene.getWidth()) {
            myBouncer.setMyXSpeed(myBouncer.getMyXSpeed() * -1);
        }
        if (myBouncer.getY() <= 0 || myBouncer.getY() + myBouncer.getBoundsInLocal().getHeight() >= scene.getHeight()) {
            myBouncer.setMyYSpeed(myBouncer.getMyYSpeed() * -1);
        }
    }
}
