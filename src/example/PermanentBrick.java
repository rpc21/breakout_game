package example;

import javafx.scene.image.Image;

/**
 * PermanentBrick is a type of Brick that is does not get removed via collision with Bouncer
 * PermanentBricks can only be removed using powerups
 */
public class PermanentBrick extends GenericBrick {

    public static final String PERMANENT_BRICK_IMAGE = "brick4.gif";
    private static final int PERMANENT_BRICK_POINT_VALUE = 0;
    private int myPointValue;

    /**
     * PermanentBrick constructor
     * @param xPos
     * @param yPos
     * @param brickLength
     */
    public PermanentBrick(double xPos, double yPos, double brickLength){
        super(xPos, yPos, brickLength, new Image(PERMANENT_BRICK_IMAGE));
        myPointValue = PERMANENT_BRICK_POINT_VALUE;
    }

    @Override
    public int getMyPointValue() {
        return myPointValue;
    }

    @Override
    public GenericBrick getReplacementBrick() {
        return this;
    }

}
