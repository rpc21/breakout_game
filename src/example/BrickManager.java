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

    private File pathToBrickLayout;
    private Set<GenericBrick> myBricks;


    private GameDifficulty myCurrentMode;
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
        myCurrentMode = currentMode;
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
                        GenericBrick brickToAdd =
                                myCurrentMode.generateBrick((double) i * GamePlayer.SIZE / lineLength + 0.1 * brickSize,
                                0.1 * counter * GamePlayer.SIZE, brickSize);
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
     * @param root root of the current scene
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
                powerUpToReturn = myCurrentMode.generatePowerUp();
            }
        }
        return powerUpToReturn;
    }

    private void removeOrReplaceBrick(GenericBrick brick, Group root) {
        GenericBrick replacementBrick = brick.getReplacementBrick();
        loseLifeDueToDangerBrick = brick.costsLife();
        myBricks.remove(brick);
        root.getChildren().remove(brick);
        if (replacementBrick != null){
            myBricks.add(replacementBrick);
            root.getChildren().add(replacementBrick);
        }
    }

    /**
     * Getter for bricks the BrickManager is currently managing
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
     * @param loseLifeDueToDangerBrick whether the player should lose life due to collision with danger brick
     */
    public void setLoseLifeDueToDangerBrick(boolean loseLifeDueToDangerBrick) {
        this.loseLifeDueToDangerBrick = loseLifeDueToDangerBrick;
    }
}
