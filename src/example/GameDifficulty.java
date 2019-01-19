package example;

public class GameDifficulty {
    public static final int BEGINNING_MODE = 13789;
    public static final int INTERMEDIATE_MODE = 13790;
    public static final int ADVANCED_MODE = 13791;

    public static final int BEGINNING_MODE_MAX_X_SPEED = 60;
    public static final int BEGINNING_MODE_MIN_X_SPEED = -60;
    public static final int BEGINNING_MODE_Y_SPEED = 60;
    public static final int BEGINNING_START_TIME = 240000;
    public static final int BEGINNING_START_LIVES = 5;
    public static final int INTERMEDIATE_MODE_MAX_X_SPEED = 90;
    public static final int INTERMEDIATE_MODE_MIN_X_SPEED = -90;
    public static final int INTERMEDIATE_MODE_Y_SPEED = 90;
    public static final int INTERMEDIATE_START_TIME = 180000;
    public static final int INTERMEDIATE_START_LIVES = 4;
    public static final int ADVANCED_MODE_MAX_X_SPEED = 120;
    public static final int ADVANCED_MODE_MIN_X_SPEED = -120;
    public static final int ADVANCED_MODE_Y_SPEED = 120;
    public static final int ADVANCED_START_TIME = 120000;
    public static final int ADVANCED_START_LIVES = 3;


    private int maxBouncerXSpeed;
    private int minBouncerXSpeed;
    private int bouncerYSpeed;
    private double startTime;
    private int startLives;
    private String currentDifficultyString;
    private int myCurrentMode;

    public double getStartTime() {
        return startTime;
    }

    public GameDifficulty(int currentMode){
        myCurrentMode = currentMode;
        if (currentMode == INTERMEDIATE_MODE){
            maxBouncerXSpeed = INTERMEDIATE_MODE_MAX_X_SPEED;
            minBouncerXSpeed = INTERMEDIATE_MODE_MIN_X_SPEED;
            bouncerYSpeed = INTERMEDIATE_MODE_Y_SPEED;
            startTime = INTERMEDIATE_START_TIME;
            startLives = INTERMEDIATE_START_LIVES;
            currentDifficultyString = "Intermediate";
        }
        else if (currentMode == ADVANCED_MODE){
            maxBouncerXSpeed = ADVANCED_MODE_MAX_X_SPEED;
            minBouncerXSpeed = ADVANCED_MODE_MIN_X_SPEED;
            bouncerYSpeed = ADVANCED_MODE_Y_SPEED;
            startTime = ADVANCED_START_TIME;
            startLives = ADVANCED_START_LIVES;
            currentDifficultyString = "Advanced";
        }
        else {
            maxBouncerXSpeed = BEGINNING_MODE_MAX_X_SPEED;
            minBouncerXSpeed = BEGINNING_MODE_MIN_X_SPEED;
            bouncerYSpeed = BEGINNING_MODE_Y_SPEED;
            startTime = BEGINNING_START_TIME;
            startLives = BEGINNING_START_LIVES;
            currentDifficultyString = "Beginner";
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

    public int getStartLives() {
        return startLives;
    }

    public String getCurrentDifficultyString() {
        return currentDifficultyString;
    }

    public int getMyCurrentMode() {
        return myCurrentMode;
    }
}
