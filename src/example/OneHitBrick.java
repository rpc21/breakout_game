package example;

import javafx.scene.image.Image;

public class OneHitBrick extends GenericBrick {
    public static final int GENERIC_BRICK_POINT_VALUE = 10;
    public static final String ONE_HIT_BRICK_IMAGE = "brick1.gif";

    private int myPointValue;


    public OneHitBrick(GenericBrick brick){
        super(brick.getX(), brick.getY(), brick.getFitWidth(), new Image(ONE_HIT_BRICK_IMAGE));
        myPointValue = 10;
    }

    public OneHitBrick(double xPos, double yPos, double brickLength){
        super(xPos, yPos, brickLength, new Image(ONE_HIT_BRICK_IMAGE));
    }
}
