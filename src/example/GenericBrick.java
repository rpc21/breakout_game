package example;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Abstract class that provides the basic methods for the various brick types
 * Subclasses: OneHitBrick, TwoHitBrick, ThreeHitBrick, DangerBrick, PermanentBrick
 */
public abstract class GenericBrick extends ImageView {

    public static final int GENERIC_BRICK_POINT_VALUE = 10;
    public static final String ONE_HIT_BRICK_IMAGE = "brick1.gif";

    private int myPointValue;

    /**
     * GenericBrick default constructor
     */
    public GenericBrick(){
        super(new Image(ONE_HIT_BRICK_IMAGE));
        myPointValue = GENERIC_BRICK_POINT_VALUE;
    }

    /**
     * GenericBrick constructor
     * @param xPos
     * @param yPos
     * @param brickLength
     * @param image
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
}
