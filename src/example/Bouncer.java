package example;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.*;

public class Bouncer extends Sprite {
    private static final String BOUNCER_IMAGE = "ball.gif";
    private int myXSpeed;
    private int myYSpeed;
    private GameDifficulty myCurrentMode;


    /**
     * Bouncer class creates an object with the ball.gif image
     * The bouncer checks for its own collisions with the other objects in the scene
     * such as the paddle, the bricks and the wall
     * This constructor initializes a Beginning Mode Bouncer
     * @param scene
     */
    public Bouncer(Scene scene){
        this(scene, new GameDifficulty(GameDifficulty.BEGINNING_MODE));
    }

    /**
     * Bouncer class creates an object with the ball.gif image
     * Bouncer checks for its own collisions with the other objects in the scene
     * The currentMode determines the Bouncer's starting and max velocities
     * More difficult modes have faster moving bouncers
     * @param scene
     * @param currentMode
     */
    public Bouncer(Scene scene, GameDifficulty currentMode) {
        super(new Image(BOUNCER_IMAGE));
        myCurrentMode = currentMode;
        setX(scene.getWidth() / 2 - this.getBoundsInLocal().getWidth() / 2);
        setY(scene.getHeight() * 0.6D);
    }

    /**
     * This method updates the Bouncer's position by computing new position from current position and velocity
     * This method also handles Bouncer's collisions with all other objects in the scene
     * These include walls, paddles, bricks
     * @param elapsedTime
     * @param scene
     * @param paddle
     * @param bricks
     * @param root
     * @return the bricks that the Bouncer collided with so they can be handled by the BrickManager
     */
    public Set<GenericBrick> handleBouncerCollisions(double elapsedTime, Scene scene, Paddle paddle,
                                                      Set<GenericBrick> bricks,
                                                      Group root){
        updatePosition(elapsedTime);
        handleWallCollisions(scene);
        handlePaddleCollisions(paddle);
        Set<GenericBrick> effectedBricks = findEffectedBricks(bricks, root);
        return effectedBricks;
    }

    private Set<GenericBrick> findEffectedBricks(Set<GenericBrick> bricks, Group root) {
        Set<GenericBrick> effectedBricks = new HashSet<>();
        boolean bounceInXDirection = false;
        boolean bounceInYDirection = false;
        // https://stackoverflow.com/questions/8104692/how-to-avoid-java-util-concurrentmodificationexception-when-iterating-through-an
        for (Iterator<GenericBrick> iterator = bricks.iterator(); iterator.hasNext(); ) {
            GenericBrick brick = iterator.next();
            if (bouncerCollidesWithTop(this,brick) || bouncerCollidesWithBottom(this,brick)){
                effectedBricks.add(brick);
                bounceInYDirection = true;
            }
            if (bouncerCollidesWithLeft(this,brick) || bouncerCollidesWithRight(this,brick)){
                effectedBricks.add(brick);
                bounceInXDirection = true;
            }
        }
        if(bounceInXDirection){
            this.myXSpeed *= -1;
        }
        if (bounceInYDirection){
            this.myYSpeed *= -1;
        }
        return effectedBricks;
    }

    private boolean bouncerCollidesWithRight(Bouncer bouncer, ImageView otherObject) {
        return ((topLeftCornerIsInYBounds(bouncer, otherObject) || bottomLeftCornerIsInYBounds(bouncer, otherObject)) &&
                bouncer.getX() <= otherObject.getX() + otherObject.getBoundsInParent().getWidth() + 1 &&
                bouncer.getX() >= otherObject.getX() + otherObject.getBoundsInParent().getWidth() - 1);
    }

    private boolean topLeftCornerIsInYBounds(Bouncer bouncer, ImageView otherObject) {
        return bouncer.getY() <= otherObject.getY() + otherObject.getBoundsInParent().getHeight() &&
                bouncer.getY() >= otherObject.getY();
    }

    private boolean bottomLeftCornerIsInYBounds(Bouncer bouncer, ImageView otherObject) {
        return bouncer.getY() + bouncer.getBoundsInParent().getHeight() <= otherObject.getY() + otherObject.getBoundsInParent().getHeight() &&
                bouncer.getY() + bouncer.getBoundsInParent().getHeight() >= otherObject.getY();
    }


    private boolean bouncerCollidesWithLeft(Bouncer bouncer, ImageView otherObject) {
        return ((topLeftCornerIsInYBounds(bouncer, otherObject) || bottomLeftCornerIsInYBounds(bouncer, otherObject)) &&
                bouncer.getX() + bouncer.getBoundsInParent().getWidth() <= otherObject.getX() + 1 &&
                bouncer.getX() + bouncer.getBoundsInParent().getWidth() >= otherObject.getX() - 1);
    }

    private boolean bouncerCollidesWithBottom(Bouncer bouncer, ImageView otherObject) {
        return ((leftSideIsInXBounds(bouncer, otherObject) || rightSideIsInXBounds(bouncer, otherObject))&&
                bouncer.getY() <= otherObject.getY() + otherObject.getBoundsInParent().getWidth() + 1 &&
                bouncer.getY() >= otherObject.getY() + otherObject.getBoundsInParent().getHeight() - 1);
    }

    private boolean leftSideIsInXBounds(Bouncer bouncer, ImageView otherObject) {
        return bouncer.getX() <= otherObject.getX() + otherObject.getBoundsInParent().getWidth() &&
                bouncer.getX() + bouncer.getBoundsInParent().getWidth() >= otherObject.getX();
    }

    private boolean rightSideIsInXBounds(Bouncer bouncer, ImageView otherObject) {
        return bouncer.getX() + bouncer.getBoundsInParent().getWidth() <= otherObject.getX() + otherObject.getBoundsInParent().getWidth() &&
                bouncer.getX() + bouncer.getBoundsInParent().getWidth() >= otherObject.getX();
    }

    private void handleWallCollisions(Scene scene) {
        if (this.getX() <= 0 || this.getX() + this.getBoundsInLocal().getWidth() >= scene.getWidth()) {
            this.myXSpeed *= -1;
        }
        if (this.getY() <= 0 || this.getY() + this.getBoundsInLocal().getHeight() >= scene.getHeight()) {
            this.myYSpeed *= -1;
        }
    }


    /**
     * Handles the Bouncer's collisions with paddles by determining if they collide and changing the
     * velocity accordingly. Implements the physics of how Bouncer's speed is affected by the Paddle's speed.
     * Ensures Bouncer's velocity does not exceed the maximum for the current difficulty mode.
     * @param paddle
     */
    public void handlePaddleCollisions(Paddle paddle) {
        if (bouncerCollidesWithTop(this, paddle)){
            this.myXSpeed += paddle.getMyXSpeed() / 2;
            this.myYSpeed *= -1;
        }
        if (myXSpeed > myCurrentMode.getMaxBouncerXSpeed()){
            myXSpeed = myCurrentMode.getMaxBouncerXSpeed();
        }
        if (myXSpeed < myCurrentMode.getMinBouncerXSpeed()){
            myXSpeed = myCurrentMode.getMinBouncerXSpeed();
        }
    }

    private boolean bouncerCollidesWithTop(Bouncer bouncer, ImageView otherObject) {
        return ((leftSideIsInXBounds(bouncer, otherObject) || rightSideIsInXBounds(bouncer, otherObject)) &&
                bouncer.getY() + bouncer.getBoundsInParent().getHeight() <= otherObject.getY() + 1 &&
                bouncer.getY() + bouncer.getBoundsInParent().getHeight() >= otherObject.getY() - 1);
    }

    /**
     * Updates the bouncers position based on current position, velocity and elapsed time
     * @param elapsedTime
     */
    public void updatePosition(double elapsedTime) {
        this.setX(this.getX() + myXSpeed * elapsedTime);
        this.setY(this.getY() + myYSpeed * elapsedTime);
    }


    /**
     * Getter for Bouncer speed in Y direction
     * @return myYSpeed
     */
    public int getMyYSpeed() {
        return myYSpeed;
    }

    /**
     * Setter for Bouncer speed in X direction
     * @param myXSpeed
     */
    public void setMyXSpeed(int myXSpeed) {
        this.myXSpeed = myXSpeed;
    }

    /**
     * Setter for Bouncer speed in Y direction
     * @param myYSpeed
     */
    public void setMyYSpeed(int myYSpeed) {
        this.myYSpeed = myYSpeed;
    }
}
