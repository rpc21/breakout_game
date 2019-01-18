package example;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Bouncer extends ImageView {
    private static final String BOUNCER_IMAGE = "ball.gif";
    private int myXDirection;
    private int myYDirection;
    private int myXSpeed;
    private int myYSpeed;
    private int mySpeed;


    public Bouncer(Image image, int currentMode) {
        super(image);
        if (currentMode == GameLevel.INTERMEDIATE_MODE){
            myXSpeed = INTERMEDIATE_MODE_MAX_X_SPEED / 2;
            myYSpeed = INTERMEDIATE_MODE_Y_SPEED / 2;
        }
        else if (currentMode == GameLevel.ADVANCED_MODE){
            myXSpeed = ADVANCED_MODE_MAX_X_SPEED / 2;
            myYSpeed = ADVANCED_MODE_Y_SPEED / 2;
        }
        else {
            myXSpeed = BEGINNING_MODE_MAX_X_SPEED / 2;
            myYSpeed = BEGINNING_MODE_Y_SPEED / 2;
        }
    }

    public void updateBouncer(double elapsedTime, Scene scene, Paddle paddle, ArrayList<GenericBrick> bricks){
        updatePosition(elapsedTime);
        handleWallCollisions(scene);
        handlePaddleCollisions(paddle);
        handleBrickCollisions(bricks);


    }

    private void handleBrickCollisions(ArrayList<GenericBrick> bricks) {
    }

    private void handlePaddleCollisions(Paddle paddle) {
        if (collisionBetween(this, paddle)){
            this.myXSpeed += paddle.getMyVelocity() / 10;
        }
        if (myXSpeed > )
    }

    private boolean collisionBetween(ImageView bouncer, ImageView otherObject) {
    }

    private void updatePosition(double elapsedTime) {
        this.setX(this.getX() + myXSpeed * elapsedTime);
        this.setY(this.getY() + myYSpeed * elapsedTime);
    }

    private void handleWallCollisions(Scene scene) {
        if (this.getX() <= 0 || this.getX() >= scene.getWidth()) {
            myXDirection *= -1;
        }
        if (this.getY() <= 0 || this.getY() >= scene.getHeight()) {
            myYDirection *= -1;
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
