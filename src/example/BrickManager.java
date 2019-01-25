package example;

import javafx.scene.Group;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * BrickManager class is in charge of managing all the bricks for a level
 * The BrickManager initializes the bricks for the level by reading in a file in resources folder
 * The BrickManager also handles the effects of brick collisions such as score updates and power-up
 * The BrickManager randomly determines what kind of brick is generated and what kind of power-up is distributed
 * based on the current difficulty mode of the level.
 */
public class BrickManager {

    private static final String LEVEL_ONE = "resources/level_one_layout.txt";
    private static final String LEVEL_TWO = "resources/level_two_layout.txt";
    private static final String LEVEL_THREE = "resources/level_three_layout.txt";
    private static final double ADVANCED_MODE_POWERUP_RATE = 0.15;
    private static final double INTERMEDIATE_MODE_POWERUP_RATE = 0.2;
    private static final double BEGINNING_MODE_POWERUP_RATE = 0.25;
    private static final int NUMBER_OF_POSSIBLE_POWERUPS = 6;

    private File pathToBrickLayout;
    private Set<GenericBrick> myBricks;


    private int myCurrentMode;
    private int myScore;
    private boolean loseLifeDueToDangerBrick;

    /**
     * Creates a new BrickManager, lays out the bricks based on the level number passed to the constructor
     * Reads in the brick locations from the corresponding file of brick locations
     * Defaults the current difficulty mode to Beginning
     * Initializes all bricks for the level
     * @param levelNumber
     */
    public BrickManager(int levelNumber){
        this(levelNumber,new GameDifficulty(GameDifficulty.BEGINNING_MODE));
    }

    /**
     * Creates a new BrickManager, lays out the bricks based on the level number passed to the constructor
     * Reads in the brick locations from the corresponding file of brick locations
     * Sets the behavior according the difficulty mode that is passed to the constructor
     * Initializes all bricks for the level
     * @param levelNumber
     * @param currentMode
     */
    public BrickManager(int levelNumber, GameDifficulty currentMode){
        myScore = 0;
        loseLifeDueToDangerBrick = false;
        myCurrentMode = currentMode.getMyCurrentMode();
        if (levelNumber == 2){
            pathToBrickLayout = new File(LEVEL_TWO);
        }
        else if (levelNumber == 3){
            pathToBrickLayout = new File(LEVEL_THREE);
        }
        else{
            pathToBrickLayout = new File(LEVEL_ONE);
        }
        myBricks = new HashSet<>();
        initializeBlocksFromFile();
    }


    private GenericBrick generateBrick(double xPos, double yPos, double brickLength){
        if (myCurrentMode == GameDifficulty.ADVANCED_MODE){
            return generateAdvancedBrick(xPos, yPos, brickLength);
        }
        else if (myCurrentMode == GameDifficulty.INTERMEDIATE_MODE){
            return generateIntermediateBrick(xPos, yPos, brickLength);
        }
        else {
            return generateBeginningBrick(xPos, yPos, brickLength);
        }


    }

    // Implements boundaries to set the frequency of generating different kinds of level appropriate bricks
    private GenericBrick generateBeginningBrick(double xPos, double yPos, double brickLength) {
        double randomNumber = Math.random();
        if (randomNumber <= 0.6D){
            return new OneHitBrick(xPos,yPos,brickLength);
        }
        else if (randomNumber <= 0.9D){
            return new TwoHitBrick(xPos,yPos,brickLength);
        }
        else{
            return new ThreeHitBrick(xPos,yPos,brickLength);
        }
    }

    // Implements boundaries to set the frequency of generating different kinds of level appropriate bricks
    private GenericBrick generateIntermediateBrick(double xPos, double yPos, double brickLength) {
        double randomNumber = Math.random();
        if (randomNumber <= 0.4D){
            return new OneHitBrick(xPos,yPos,brickLength);
        }
        else if (randomNumber <= 0.6D){
            return new TwoHitBrick(xPos,yPos,brickLength);
        }
        else if (randomNumber <= 0.8D){
            return new ThreeHitBrick(xPos, yPos, brickLength);
        }
        else if (randomNumber <= 0.95D){
            return new PermanentBrick(xPos,yPos,brickLength);
        }
        else{
            return new DangerBrick(xPos, yPos, brickLength);
        }
    }

    // Implements boundaries to set the frequency of generating different kinds of level appropriate bricks
    private GenericBrick generateAdvancedBrick(double xPos, double yPos, double brickLength) {
        double randomNumber = Math.random();
        if (randomNumber <= 0.3D){
            return new OneHitBrick(xPos,yPos,brickLength);
        }
        else if (randomNumber <= 0.5D){
            return new TwoHitBrick(xPos,yPos,brickLength);
        }
        else if (randomNumber <= 0.7D){
            return new ThreeHitBrick(xPos, yPos, brickLength);
        }
        else if (randomNumber <= 0.9D){
            return new PermanentBrick(xPos,yPos,brickLength);
        }
        else{
            return new DangerBrick(xPos, yPos, brickLength);
        }
    }


    private void initializeBlocksFromFile() {
        // https://www.tutorialspoint.com/Reading-a-Text-file-in-java
        try (BufferedReader br = new BufferedReader(new FileReader(pathToBrickLayout))) {
            String currentLine;
            int counter = 1;
            while ((currentLine = br.readLine()) != null) {
                char[] brickLocations = currentLine.toCharArray();
                int lineLength = brickLocations.length;
                double brickSize = GamePlayer.SIZE * 0.8 / lineLength;
                for(int i=0; i<brickLocations.length; i++){
                    if(brickLocations[i] == '-'){
                        GenericBrick brickToAdd = generateBrick((double) i*GamePlayer.SIZE/lineLength + 0.1*brickSize,
                                0.1*counter*GamePlayer.SIZE, brickSize);
                        myBricks.add(brickToAdd);
                    }
                }
                System.out.println(currentLine);
                counter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Iterates through all the bricks involved in a collision in the time step
     * Determines if they drop a power-up and if so which power-up
     * Adjusts the score based on destroyed bricks
     * Removes brick or replaces it with a brick that takes one fewer hit to destroy
     * @param effectedBricks all the bricks that were involved in a collision in the time step
     * @param root
     * @return an integer corresponding to the power-up that is produced or an invalid power up number
     * if no power-up is release from the brick
     */
    public int handleEffectedBricks(Set<GenericBrick> effectedBricks, Group root) {
        int powerUpToReturn = PowerUpManager.INVALID_POWER_UP_NUMBER;
        for (Iterator<GenericBrick> iterator = effectedBricks.iterator(); iterator.hasNext(); ) {
            GenericBrick brick = iterator.next();
            if (effectedBricks.contains(brick)){
                myScore += brick.getMyPointValue();
                removeOrReplaceBrick(brick, root);
                powerUpToReturn = generatePowerUpToReturn();
            }
        }
        return powerUpToReturn;
    }

    private int generatePowerUpToReturn() {
        if (myCurrentMode == GameDifficulty.BEGINNING_MODE){
            return generatePowerUpUsingSpecifiedRate(BEGINNING_MODE_POWERUP_RATE);
        }
        else if (myCurrentMode == GameDifficulty.INTERMEDIATE_MODE){
            return generatePowerUpUsingSpecifiedRate(INTERMEDIATE_MODE_POWERUP_RATE);
        }
        else{
            return generatePowerUpUsingSpecifiedRate(ADVANCED_MODE_POWERUP_RATE);
        }
    }

    private int generatePowerUpUsingSpecifiedRate(double advancedModePowerupRate) {
        double shouldGeneratePowerUp = Math.random();
        if (shouldGeneratePowerUp < advancedModePowerupRate) {
            return (int) (Math.random() * NUMBER_OF_POSSIBLE_POWERUPS);
        } else {
            return PowerUpManager.INVALID_POWER_UP_NUMBER;
        }
    }

    private void removeOrReplaceBrick(GenericBrick brick, Group root) {
        if (brick instanceof DangerBrick){
            loseLifeDueToDangerBrick = true;
            myBricks.remove(brick);
            root.getChildren().removeAll(brick);
        }
        if (brick instanceof ThreeHitBrick){
            TwoHitBrick replacementBrick = new TwoHitBrick(brick);
            replaceBrick(brick, root, replacementBrick);
        }
        else if (brick instanceof TwoHitBrick){
            GenericBrick replacementBrick = new OneHitBrick(brick);
            replaceBrick(brick, root, replacementBrick);
        }
        else if (brick instanceof OneHitBrick){
            myBricks.remove(brick);
            root.getChildren().removeAll(brick);
        }
    }

    private void replaceBrick(GenericBrick brick, Group root, GenericBrick replacementBrick) {
        myBricks.add(replacementBrick);
        root.getChildren().add(replacementBrick);
        myBricks.remove(brick);
        root.getChildren().removeAll(brick);
    }

    /**
     * Getter for bricks the BrickManager is managing
     * @return myBricks
     */
    public Set<GenericBrick> getMyBricks() {
        return myBricks;
    }

    /**
     * Getter for current score
     * @return myScore
     */
    public int getMyScore() {
        return myScore;
    }

    /**
     * Getter for whether or not the player lost a life due to collision with a DangerBrick
     * @return whether or not the player lost a life due to collision with a DangerBrick
     */
    public boolean isLoseLifeDueToDangerBrick() {
        return loseLifeDueToDangerBrick;
    }

    /**
     * Setter for whether or not the player lost a life due to collision with a DangerBrick
     * @param loseLifeDueToDangerBrick
     */
    public void setLoseLifeDueToDangerBrick(boolean loseLifeDueToDangerBrick) {
        this.loseLifeDueToDangerBrick = loseLifeDueToDangerBrick;
    }
}
