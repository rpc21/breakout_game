package example;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bouncer extends ImageView {
    private static final String BOUNCER_IMAGE = "ball.gif";
    private int myXDirection;
    private int myYDirection;
    private int mySpeed;


    public Bouncer(Image image) {
        super(image);
        if(Math.random() >= 0.5){
            myXDirection = 1;
        }else{
            myXDirection = -1;
        }
        if(Math.random() >= 0.5){
            myYDirection = 1;
        }else{
            myYDirection = -1;
        }
        mySpeed = (int) (Math.random() * 100);
    }

    public void updateBouncer(double elapsedTime, Scene myScene){
        this.setX(this.getX() - myXDirection * mySpeed * elapsedTime);
        this.setY(this.getY() - myYDirection * mySpeed * elapsedTime);

        if (this.getX() <= 0 || this.getX() >= myScene.getWidth()) {
            myXDirection *= -1;
        }
        if (this.getY() <= 0 || this.getY() >= myScene.getHeight()) {
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

    public int getMySpeed() {
        return mySpeed;
    }

    public void setMySpeed(int mySpeed) {
        this.mySpeed = mySpeed;
    }
}
