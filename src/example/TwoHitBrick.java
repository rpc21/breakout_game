package example;

import javafx.scene.image.Image;

public class TwoHitBrick extends GenericBrick {

    public static final String TWO_HIT_BRICK_IMAGE = "brick2.gif";
    public static final int TWO_HIT_BRICK_POINT_VALUE= 10;

    private int myPointValue;

    public TwoHitBrick(double xPos, double yPos, double brickLength){
        super(xPos, yPos, brickLength, new Image(TWO_HIT_BRICK_IMAGE));
        myPointValue = TWO_HIT_BRICK_POINT_VALUE;
    }

}
