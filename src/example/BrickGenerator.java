package example;

import javafx.scene.image.Image;

public class BrickGenerator {

    public static final int BEGINNING_MODE = 13789;
    public static final int INTERMEDIATE_MODE = 13790;
    public static final int ADVANCED_MODE = 13791;

    public static final int BEGINNING_MODE_MAX_X_SPEED = 60;
    public static final int BEGINNING_MODE_MIN_X_SPEED = -60;
    public static final int BEGINNING_MODE_Y_SPEED = 60;
    public static final int BEGINNING_START_TIME = 240;
    public static final int INTERMEDIATE_MODE_MAX_X_SPEED = 90;
    public static final int INTERMEDIATE_MODE_MIN_X_SPEED = -90;
    public static final int INTERMEDIATE_MODE_Y_SPEED = 90;
    public static final int INTERMEDIATE_START_TIME = 180;
    public static final int ADVANCED_MODE_MAX_X_SPEED = 120;
    public static final int ADVANCED_MODE_MIN_X_SPEED = -120;
    public static final int ADVANCED_MODE_Y_SPEED = 120;
    public static final int ADVANCED_START_TIME = 120;

    public static final String ONE_HIT_BRICK_IMAGE = "brick1.gif";
    public static final String TWO_HIT_BRICK_IMAGE = "brick2.gif";
    public static final String THREE_HIT_BRICK_IMAGE = "brick3.gif";
    public static final String FOUR_HIT_BRICK_IMAGE = "brick4.gif";
    public static final String FIVE_HIT_BRICK_IMAGE = "brick5.gif";


    private GameDifficulty myCurrentMode;

    public BrickGenerator(GameDifficulty currentMode){
        myCurrentMode = currentMode;
    }

    public GenericBrick generateBrick(double xPos, double yPos, double brickLength){
        Image image = new Image(ONE_HIT_BRICK_IMAGE);
        return new GenericBrick(xPos,yPos,brickLength,image);
    }
}
