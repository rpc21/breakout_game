package example;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Iterator;

public class Bouncer extends ImageView {
    private static final String BOUNCER_IMAGE = "ball.gif";
    private int myXDirection;
    private int myYDirection;
    private int myXSpeed;
    private int myYSpeed;
    private int mySpeed;
    private GameDifficulty myCurrentMode;


    public Bouncer(Image image, GameDifficulty currentMode) {
        super(image);
        myCurrentMode = currentMode;
        myXSpeed = myCurrentMode.getMaxBouncerXSpeed() / 2;
        myYSpeed = myCurrentMode.getBouncerYSpeed();
    }

    public void updateBouncer(double elapsedTime, Scene scene, Paddle paddle, ArrayList<GenericBrick> bricks){
        updatePosition(elapsedTime);
        handleWallCollisions(scene);
        handlePaddleCollisions(paddle);
        handleBrickCollisions(bricks, scene);

    }

    private void handleBrickCollisions(ArrayList<GenericBrick> bricks, Scene scene) {
        // https://stackoverflow.com/questions/8104692/how-to-avoid-java-util-concurrentmodificationexception-when-iterating-through-an
        for (Iterator<GenericBrick> iterator = bricks.iterator(); iterator.hasNext(); ) {
            GenericBrick brick = iterator.next();
            if (bouncerCollidesWithTop(this,brick) || bouncerCollidesWithBottom(this,brick)){
                iterator.remove();
                this.myYSpeed *= -1;
            }
        }
    }

    private boolean bouncerCollidesWithBottom(ImageView bouncer, ImageView otherObject) {
        return (bouncer.getX() <= otherObject.getX() + otherObject.getBoundsInParent().getWidth() &&
                bouncer.getX() + bouncer.getBoundsInParent().getWidth() >= otherObject.getX() &&
                bouncer.getY() <= otherObject.getY() + otherObject.getBoundsInParent().getWidth() + 1 &&
                bouncer.getY() >= otherObject.getY() + otherObject.getBoundsInParent().getHeight() - 1);
    }


    private void handlePaddleCollisions(Paddle paddle) {
        if (bouncerCollidesWithTop(this, paddle)){
            this.myXSpeed += paddle.getMyVelocity() / 2;
            this.myYSpeed *= -1;
        }
        if (myXSpeed > myCurrentMode.getMaxBouncerXSpeed()){
            myXSpeed = myCurrentMode.getMaxBouncerXSpeed();
        }
        if (myXSpeed < myCurrentMode.getMinBouncerXSpeed()){
            myXSpeed = myCurrentMode.getMinBouncerXSpeed();
        }
    }

    private boolean bouncerCollidesWithTop(ImageView bouncer, ImageView otherObject) {
        return (bouncer.getX() <= otherObject.getX() + otherObject.getBoundsInParent().getWidth() &&
                bouncer.getX() + bouncer.getBoundsInParent().getWidth() >= otherObject.getX() &&
                bouncer.getY() + bouncer.getBoundsInParent().getHeight() <= otherObject.getY() + 1 &&
                bouncer.getY() + bouncer.getBoundsInParent().getHeight() >= otherObject.getY() - 1);
    }

    private void updatePosition(double elapsedTime) {
        this.setX(this.getX() + myXSpeed * elapsedTime);
        this.setY(this.getY() + myYSpeed * elapsedTime);
    }

    private void handleWallCollisions(Scene scene) {
        if (this.getX() <= 0 || this.getX() + this.getBoundsInLocal().getWidth() >= scene.getWidth()) {
            this.myXSpeed *= -1;
        }
        if (this.getY() <= 0 || this.getY() + this.getBoundsInLocal().getHeight() >= scene.getHeight()) {
            this.myYSpeed *= -1;
        }
    }

    public int getMyXDirection() {
        return myXDirection;
    }

    public void setMyXDirection(int myXDirection) {
        this.myXDirection = myXDirection;
    }

    public int getMyYDirection() {
        return myYDirection;
    }

    public void setMyYDirection(int myYDirection) {
        this.myYDirection = myYDirection;
    }

}
