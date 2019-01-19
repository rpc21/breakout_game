package example;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GenericBrick extends ImageView {

    public static final int GENERIC_BRICK_POINT_VALUE = 10;
    public static final String ONE_HIT_BRICK_IMAGE = "brick1.gif";

    private int myPointValue;

    public GenericBrick(){
        super(new Image(ONE_HIT_BRICK_IMAGE));
        myPointValue = GENERIC_BRICK_POINT_VALUE;
    }

    public GenericBrick(double xPos, double yPos, double brickLength, Image image){
        super(image);
        setFitWidth(brickLength);
        setX(xPos);
        setY(yPos);
        myPointValue = GENERIC_BRICK_POINT_VALUE;
    }

    public GenericBrick(double xPos, double yPos, double brickLength){
        this(xPos, yPos, brickLength, new Image(ONE_HIT_BRICK_IMAGE));
    }

    public int getMyPointValue() {
        return myPointValue;
    }
}
