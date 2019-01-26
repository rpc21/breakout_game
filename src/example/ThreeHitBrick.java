package example;

import javafx.scene.image.Image;

/**
 * Brick that takes three hits to destroy and is worth 10 points for every collision
 */
public class ThreeHitBrick extends GenericBrick{

    public static final String THREE_HIT_BRICK_IMAGE = "brick3.gif";
    public static final int THREE_HIT_BRICK_POINT_VALUE = 10;

    private int myPointValue;

    /**
     * ThreeHitBrick constructor
     * @param xPos
     * @param yPos
     * @param brickLength
     */
    public ThreeHitBrick(double xPos, double yPos, double brickLength){
        super(xPos, yPos, brickLength, new Image(THREE_HIT_BRICK_IMAGE));
        myPointValue = THREE_HIT_BRICK_POINT_VALUE;
    }

    /**
     * Getter for the point value of the brick
     * @return myPointValue
     */
    @Override
    public int getMyPointValue() {
        return myPointValue;
    }

    /**
     * When ThreeHitBricks are hit they are turned into TwoHitBricks
     * @return new TwoHitBrick in its place
     */
    @Override
    public GenericBrick getReplacementBrick() {
        return new TwoHitBrick(this);
    }

}
