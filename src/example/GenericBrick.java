package example;

import javafx.scene.image.Image;
/**
 * Abstract class that provides the basic methods for the various brick types
 * Constructs a brick based on its location as specified by top left corner, and an image to represent brick
 * Can handle what happens to itself when it collides with a ball in terms of whether it is replaced or removed
 * Has a point value associated with a collision between the brick and the bouncer
 * Subclasses: OneHitBrick, TwoHitBrick, ThreeHitBrick, DangerBrick, PermanentBrick
 */
public abstract class GenericBrick extends Sprite {

    public static final int GENERIC_BRICK_POINT_VALUE = 10;
    public static final String ONE_HIT_BRICK_IMAGE = "brick1.gif";

    private int myPointValue;

    /**
     * GenericBrick default constructor, constructs a brick with the ONE_HIT_BRICK_IMAGE
     */
    public GenericBrick(){
        super(new Image(ONE_HIT_BRICK_IMAGE));
        myPointValue = GENERIC_BRICK_POINT_VALUE;
    }

    /**
     * GenericBrick constructor
     * @param xPos x position of top left corner
     * @param yPos y position of top left corner
     * @param brickLength length of the brick to generate
     * @param image image that will display for the brick
     */
    public GenericBrick(double xPos, double yPos, double brickLength, Image image){
        super(image);
        setFitWidth(brickLength);
        setX(xPos);
        setY(yPos);
        myPointValue = GENERIC_BRICK_POINT_VALUE;
    }

    /**
     * Getter for brick's point value
     * @return myPointValue
     */
    public int getMyPointValue() {
        return myPointValue;
    }

    /**
     * Generates the brick meant to take the spot of the brick if it is involved in a collision, default is to remove
     * @return null if brick is to be removed from the scene, otherwise return replacement brick
     */
    public GenericBrick getReplacementBrick(){
        return null;
    }

    /**
     * @return whether or not a collision with this brick costs the player a life - default is false
     */
    public boolean costsLife(){
        return false;
    }
}
