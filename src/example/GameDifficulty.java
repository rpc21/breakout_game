package example;

public class GameDifficulty {
    public static final int BEGINNING_MODE = 13789;
    public static final int INTERMEDIATE_MODE = 13790;
    public static final int ADVANCED_MODE = 13791;
    public static final int BEGINNING_MODE_MAX_X_SPEED = 60;
    public static final int BEGINNING_MODE_MIN_X_SPEED = -60;
    public static final int BEGINNING_MODE_Y_SPEED = 60;
    public static final int INTERMEDIATE_MODE_MAX_X_SPEED = 90;
    public static final int INTERMEDIATE_MODE_MIN_X_SPEED = -90;
    public static final int INTERMEDIATE_MODE_Y_SPEED = 90;
    public static final int ADVANCED_MODE_MAX_X_SPEED = 120;
    public static final int ADVANCED_MODE_MIN_X_SPEED = -120;
    public static final int ADVANCED_MODE_Y_SPEED = 120;

    private int maxBouncerXSpeed;
    private int minBouncerXSpeed;
    private int bouncerYSpeed;

    public GameDifficulty(int currentMode){
        if (currentMode == INTERMEDIATE_MODE){
            maxBouncerXSpeed = INTERMEDIATE_MODE_MAX_X_SPEED;
            minBouncerXSpeed = INTERMEDIATE_MODE_MIN_X_SPEED;
            bouncerYSpeed = INTERMEDIATE_MODE_Y_SPEED;
        }
        else if (currentMode == ADVANCED_MODE){
            maxBouncerXSpeed = ADVANCED_MODE_MAX_X_SPEED;
            minBouncerXSpeed = ADVANCED_MODE_MIN_X_SPEED;
            bouncerYSpeed = ADVANCED_MODE_Y_SPEED;
        }
        else {
            maxBouncerXSpeed = BEGINNING_MODE_MAX_X_SPEED;
            minBouncerXSpeed = BEGINNING_MODE_MIN_X_SPEED;
            bouncerYSpeed = BEGINNING_MODE_Y_SPEED;
        }
    }

    public int getMaxBouncerXSpeed() {
        return maxBouncerXSpeed;
    }

    public int getMinBouncerXSpeed() {
        return minBouncerXSpeed;
    }

    public int getBouncerYSpeed() {
        return bouncerYSpeed;
    }
}
