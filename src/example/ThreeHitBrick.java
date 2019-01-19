package example;

import javafx.scene.image.Image;

public class ThreeHitBrick extends GenericBrick{

    public static final String THREE_HIT_BRICK_IMAGE = "brick3.gif";
    public static final int THREE_HIT_BRICK_POINT_VALUE= 10;

    private int myPointValue;

    public ThreeHitBrick(double xPos, double yPos, double brickLength){
        super(xPos, yPos, brickLength, new Image(THREE_HIT_BRICK_IMAGE));
        myPointValue = THREE_HIT_BRICK_POINT_VALUE;
    }
}
