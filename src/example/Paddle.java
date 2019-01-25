package example;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Paddle extends Sprite {


    public static final int MAX_PADDLE_VELOCITY = 120;
    private static final int PADDLE_ACCELERATION = 30;
    public static final int MIN_PADDLE_VELOCITY = -120;
    private static final String PADDLE_IMAGE = "paddle.gif";

    private int myXSpeed;

    /**
     * Getter for paddle velocity
     * @return myVelocity
     */
    public int getMyXSpeed() {
        return myXSpeed;
    }

    /**
     * Paddle constructor - initializes paddle in the middle bottom of the screen with 0 initial velocity
     * @param scene
     */
    public Paddle(Scene scene){
        super(new Image(PADDLE_IMAGE));
        setX(scene.getWidth() / 2 - this.getBoundsInLocal().getWidth() / 2);
        setY(scene.getHeight() * 0.9);
        myXSpeed = 0;
    }

    /**
     * increases the Paddle velocity by the Paddle's acceleration
     */
    public void increaseVelocity(){
        if (myXSpeed < MAX_PADDLE_VELOCITY){
            myXSpeed += PADDLE_ACCELERATION;
        }

    }

    /**
     * decreases the Paddle velocity by the Paddle's acceleration
     */
    public void decreaseVelocity(){
        if (myXSpeed > MIN_PADDLE_VELOCITY){
            myXSpeed -= PADDLE_ACCELERATION;
        }
    }

    /**
     * Updates the Paddle's position based on current position, velocity and elapsed time
     * Paddle will wrap from one side of the screen to the other
     * @param elapsedTime
     * @param scene
     */
    public void updatePaddlePosition(double elapsedTime, Scene scene){
        setX(this.getX() + myXSpeed * elapsedTime);
        if(this.getX() > scene.getWidth()){
            setX(this.getX() % scene.getWidth());
        }
        else if(this.getX() < 0){
            setX(this.getX() + scene.getWidth());
        }
    }

    /**
     * Setter for Paddle velocity
     * @param myVelocity
     */
    public void setMyXSpeed(int myVelocity) {
        this.myXSpeed = myVelocity;
    }
}
