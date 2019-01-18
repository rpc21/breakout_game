package example;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Paddle extends ImageView {

    private int myVelocity;

    public Paddle(Image image){
        super(image);
        myVelocity = 0;
    }

    public void increaseVelocity(){
        if (myVelocity < 60){
            myVelocity += 20;
        }

    }

    public void decreaseVelocity(){
        if (myVelocity > -60){
            myVelocity -= 20;
        }
    }

    public void updatePaddlePosition(Scene scene){
//        if(this.getX() > scene.getWidth()){
//            setX(this.getX() % );
//        }
    }
}
