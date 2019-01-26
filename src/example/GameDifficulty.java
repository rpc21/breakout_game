package example;

/**
 * Class to distinguish between the different difficulty modes
 * Establishes various conditions and rules for the different difficulty modes
 */
public class GameDifficulty {
    public static final int BEGINNING_MODE = 13789;
    public static final int INTERMEDIATE_MODE = 13790;
    public static final int ADVANCED_MODE = 13791;

    private static final int BEGINNING_MODE_MAX_X_SPEED = 60;
    private static final int BEGINNING_MODE_MIN_X_SPEED = -60;
    private static final int BEGINNING_MODE_Y_SPEED = 60;
    private static final int BEGINNING_START_TIME = 240;
    private static final int BEGINNING_START_LIVES = 5;
    private static final int INTERMEDIATE_MODE_MAX_X_SPEED = 90;
    private static final int INTERMEDIATE_MODE_MIN_X_SPEED = -90;
    private static final int INTERMEDIATE_MODE_Y_SPEED = 90;
    private static final int INTERMEDIATE_START_TIME = 180;
    private static final int INTERMEDIATE_START_LIVES = 4;
    private static final int ADVANCED_MODE_MAX_X_SPEED = 120;
    private static final int ADVANCED_MODE_MIN_X_SPEED = -120;
    private static final int ADVANCED_MODE_Y_SPEED = 120;
    private static final int ADVANCED_START_TIME = 120;
    private static final int ADVANCED_START_LIVES = 3;
    private static final double ADVANCED_MODE_POWERUP_RATE = 0.15;
    private static final double INTERMEDIATE_MODE_POWERUP_RATE = 0.2;
    private static final double BEGINNING_MODE_POWERUP_RATE = 0.25;
    private static final double[] BEGINNING_BRICK_GENERATOR_CUTOFFS = {0.6, 0.9, 1, 1};
    private static final double[] INTERMEDIATE_BRICK_GENERATOR_CUTOFFS = {0.4, 0.6, 0.8, 0.95};
    private static final double[] ADVANCED_BRICK_GENERATOR_CUTOFFS = {0.3, 0.5, 0.7, 0.9};


    private int maxBouncerXSpeed;
    private int minBouncerXSpeed;
    private int bouncerYSpeed;
    private double startTime;
    private int startLives;
    private String currentDifficultyString;
    private int myCurrentMode;
    private double myPowerUpRate;
    private double[] myBrickGeneratorCutoffs;

    /**
     * Getter for the Start Time
     * @return startTime
     */
    public double getStartTime() {
        return startTime;
    }

    /**
     * GameDifficulty constructor
     * @param currentMode
     */
    public GameDifficulty(int currentMode){
        myCurrentMode = currentMode;
        if (currentMode == INTERMEDIATE_MODE){
            maxBouncerXSpeed = INTERMEDIATE_MODE_MAX_X_SPEED;
            minBouncerXSpeed = INTERMEDIATE_MODE_MIN_X_SPEED;
            bouncerYSpeed = INTERMEDIATE_MODE_Y_SPEED;
            startTime = INTERMEDIATE_START_TIME;
            startLives = INTERMEDIATE_START_LIVES;
            currentDifficultyString = "Intermediate";
            myPowerUpRate = INTERMEDIATE_MODE_POWERUP_RATE;
            myBrickGeneratorCutoffs = INTERMEDIATE_BRICK_GENERATOR_CUTOFFS;
        }
        else if (currentMode == ADVANCED_MODE){
            maxBouncerXSpeed = ADVANCED_MODE_MAX_X_SPEED;
            minBouncerXSpeed = ADVANCED_MODE_MIN_X_SPEED;
            bouncerYSpeed = ADVANCED_MODE_Y_SPEED;
            startTime = ADVANCED_START_TIME;
            startLives = ADVANCED_START_LIVES;
            currentDifficultyString = "Advanced";
            myPowerUpRate = ADVANCED_MODE_POWERUP_RATE;
            myBrickGeneratorCutoffs = ADVANCED_BRICK_GENERATOR_CUTOFFS;
        }
        else {
            maxBouncerXSpeed = BEGINNING_MODE_MAX_X_SPEED;
            minBouncerXSpeed = BEGINNING_MODE_MIN_X_SPEED;
            bouncerYSpeed = BEGINNING_MODE_Y_SPEED;
            startTime = BEGINNING_START_TIME;
            startLives = BEGINNING_START_LIVES;
            currentDifficultyString = "Beginner";
            myPowerUpRate = BEGINNING_MODE_POWERUP_RATE;
            myBrickGeneratorCutoffs = BEGINNING_BRICK_GENERATOR_CUTOFFS;
        }
    }

    public GenericBrick generateBrick(double xPos, double yPos, double brickLength){
        double randomNumber = Math.random();
        if (randomNumber < myBrickGeneratorCutoffs[0]){
            return new OneHitBrick(xPos, yPos, brickLength);
        }
        else if (randomNumber <= myBrickGeneratorCutoffs[1]){
            return new TwoHitBrick(xPos,yPos,brickLength);
        }
        else if (randomNumber <= myBrickGeneratorCutoffs[2]){
            return new ThreeHitBrick(xPos, yPos, brickLength);
        }
        else if (randomNumber <= myBrickGeneratorCutoffs[3]){
            return new PermanentBrick(xPos,yPos,brickLength);
        }
        else{
            return new DangerBrick(xPos, yPos, brickLength);
        }
    }

    public int generatePowerUp() {
        double shouldGeneratePowerUp = Math.random();
        if (shouldGeneratePowerUp < myPowerUpRate) {
            return (int) (Math.random() * PowerUpManager.NUMBER_OF_POSSIBLE_POWERUPS);
        } else {
            return PowerUpManager.INVALID_POWER_UP_NUMBER;
        }
    }

    /**
     * Getter for Max Bouncer Speed in X direction
     * @return maxBouncerXSpeed
     */
    public int getMaxBouncerXSpeed() {
        return maxBouncerXSpeed;
    }

    /**
     * Getter for Min Bouncer Speed in X direction
     * @return minBouncerSpeed
     */
    public int getMinBouncerXSpeed() {
        return minBouncerXSpeed;
    }

    /**
     * Getter for the Bouncer's Y speed in this difficulty mode
     * @return bouncerYSpeed
     */
    public int getBouncerYSpeed() {
        return bouncerYSpeed;
    }

    /**
     * Getter for number of start lives in this dificulty mode
     * @return startLives
     */
    public int getStartLives() {
        return startLives;
    }

    /**
     * Getter for the string representation of the difficulty
     * @return currentDifficultyString
     */
    public String getCurrentDifficultyString() {
        return currentDifficultyString;
    }

    /**
     * Getter for the current mode as its integer representation
     * @return myCurrentMode
     */
    public int getMyCurrentMode() {
        return myCurrentMode;
    }
}
