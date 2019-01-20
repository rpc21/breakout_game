package example;

import javafx.scene.image.Image;

public class DangerBrick extends GenericBrick {

    public static final String DANGER_BRICK_IMAGE = "brick10.gif";

    private int myPointValue;

    public DangerBrick(double xPos, double yPos, double brickLength){
        super(xPos, yPos, brickLength, new Image(DANGER_BRICK_IMAGE));
        myPointValue = 0;
    }

    @Override
    public int getMyPointValue() {
        return myPointValue;
    }
}
