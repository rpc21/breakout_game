package example;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Paddle extends ImageView {


    public static final int MAX_PADDLE_VELOCITY = 90;
    public static final int PADDLE_ACCELERATION = 30;
    public static final int MIN_PADDLE_VELOCITY = -90;
    private int myVelocity;

    public int getMyVelocity() {
        return myVelocity;
    }

    public Paddle(Image image){
        super(image);
        myVelocity = 0;
    }

    public void increaseVelocity(){
        if (myVelocity < MAX_PADDLE_VELOCITY){
            myVelocity += PADDLE_ACCELERATION;
        }

    }

    public void decreaseVelocity(){
        if (myVelocity > MIN_PADDLE_VELOCITY){
            myVelocity -= PADDLE_ACCELERATION;
        }
    }

    public void updatePaddlePosition(double elapsedTime, Scene scene){
        setX(this.getX() + myVelocity * elapsedTime);
        if(this.getX() > scene.getWidth()){
            setX(this.getX() % scene.getWidth());
        }
        else if(this.getX() < 0){
            setX(this.getX() + scene.getWidth());
        }
    }
}
