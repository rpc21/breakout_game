package example;

import javafx.scene.image.Image;

/**
 * DangerBrick is a type of Brick that is red and costs the player a life if the Bouncer collides with it
 */
public class DangerBrick extends GenericBrick {

    public static final String DANGER_BRICK_IMAGE = "brick10.gif";
    private static final int DANGER_BRICK_POINT_VALUE = 0;

    private int myPointValue;

    /**
     * DangerBrick constructor
     * @param xPos
     * @param yPos
     * @param brickLength
     */
    public DangerBrick(double xPos, double yPos, double brickLength){
        super(xPos, yPos, brickLength, new Image(DANGER_BRICK_IMAGE));
        myPointValue = DANGER_BRICK_POINT_VALUE;
    }

    @Override
    public int getMyPointValue() {
        return myPointValue;
    }

    @Override
    public GenericBrick getReplacementBrick() {
        return null;
    }

    @Override
    public boolean costsLife() {
        return true;
    }
}
