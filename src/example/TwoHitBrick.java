package example;

import javafx.scene.image.Image;

/**
 * Brick that takes two hits to destroy and is worth 10 points for every collision
 */
public class TwoHitBrick extends GenericBrick {

    private static final String TWO_HIT_BRICK_IMAGE = "brick2.gif";
    private static final int TWO_HIT_BRICK_POINT_VALUE= 10;

    private int myPointValue;

    /**
     * TwoHitBrick constructor
     * @param xPos
     * @param yPos
     * @param brickLength
     */
    public TwoHitBrick(double xPos, double yPos, double brickLength){
        super(xPos, yPos, brickLength, new Image(TWO_HIT_BRICK_IMAGE));
        myPointValue = TWO_HIT_BRICK_POINT_VALUE;
    }

    /**
     * TwoHitBrick constructor that is useful for handling a collision with a ThreeHitBrick
     * @param brick
     */
    public TwoHitBrick(GenericBrick brick){
        super(brick.getX(),brick.getY(),brick.getFitWidth(),new Image(TWO_HIT_BRICK_IMAGE));
        myPointValue = TWO_HIT_BRICK_POINT_VALUE;

    }

}
