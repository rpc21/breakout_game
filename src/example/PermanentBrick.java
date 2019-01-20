package example;

import javafx.scene.image.Image;

public class PermanentBrick extends GenericBrick {

    public static final String PERMANENT_BRICK_IMAGE = "brick4.gif";
    private int myPointValue;

    public PermanentBrick(double xPos, double yPos, double brickLength){
        super(xPos, yPos, brickLength, new Image(PERMANENT_BRICK_IMAGE));
        myPointValue = 0;
    }

    @Override
    public int getMyPointValue() {
        return myPointValue;
    }
}
