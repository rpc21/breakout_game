package example;

import javafx.scene.image.Image;

/**
 * OneHitBrick is a brick that can be destroyed by one collision with a Bouncer
 */
public class OneHitBrick extends GenericBrick {
    private static final String ONE_HIT_BRICK_IMAGE = "brick1.gif";
    private static final int ONE_HIT_BRICK_POINT_VALUE = 10;

    private int myPointValue;

    /**
     * OneHitBrick constructor from other brick
     * Useful for changing a TwoHitBrick into a OneHitBrick
     * @param brick
     */
    public OneHitBrick(GenericBrick brick){
        super(brick.getX(), brick.getY(), brick.getFitWidth(), new Image(ONE_HIT_BRICK_IMAGE));
        myPointValue = ONE_HIT_BRICK_POINT_VALUE;
    }

    /**
     * OneHitBrick constructor, specifies brick placement and brick length
     * @param xPos
     * @param yPos
     * @param brickLength
     */
    public OneHitBrick(double xPos, double yPos, double brickLength){
        super(xPos, yPos, brickLength, new Image(ONE_HIT_BRICK_IMAGE));
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
     * When OneHitBricks are hit they are removed from the screen
     * @return null (remove from the scene)
     */
    @Override
    public GenericBrick getReplacementBrick() {
        return null;
    }

}
