package example;

import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class BrickManager {

    public static final int BEGINNING_MODE = 13789;
    public static final int INTERMEDIATE_MODE = 13790;
    public static final int ADVANCED_MODE = 13791;

    public static final String LEVEL_ONE = "resources/level_one_layout.txt";
    public static final String LEVEL_TWO = "resources/level_two_layout.txt";
    public static final String LEVEL_THREE = "resources/level_three_layout.txt";

    private File pathToBrickLayout;
    private ArrayList<GenericBrick> myBricks;

    public static final String ONE_HIT_BRICK_IMAGE = "brick1.gif";
    public static final String TWO_HIT_BRICK_IMAGE = "brick2.gif";
    public static final String THREE_HIT_BRICK_IMAGE = "brick3.gif";
    public static final String FOUR_HIT_BRICK_IMAGE = "brick4.gif";
    public static final String FIVE_HIT_BRICK_IMAGE = "brick5.gif";


    private int myCurrentMode;

    public BrickManager(GameDifficulty currentMode){
        this(1,currentMode);
    }

    public BrickManager(int levelNumber){
        this(levelNumber,new GameDifficulty(GameDifficulty.BEGINNING_MODE));
    }

    public BrickManager(int levelNumber, GameDifficulty currentMode){
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
        System.out.println("Level Number passed: " + levelNumber);
        System.out.println("Path to brick layout:"+ pathToBrickLayout);
        myBricks = new ArrayList<>();
        initializeBlocksFromFile();
    }

    public GenericBrick generateBrick(double xPos, double yPos, double brickLength){
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

    private GenericBrick generateBeginningBrick(double xPos, double yPos, double brickLength) {
        double randomNumber = Math.random();
        if (randomNumber <= 0.6D){
            return new GenericBrick(xPos,yPos,brickLength);
        }
        else if (randomNumber <= 0.9D){
            return new TwoHitBrick(xPos,yPos,brickLength);
        }
        else{
            return new ThreeHitBrick(xPos,yPos,brickLength);
        }
    }

    private GenericBrick generateIntermediateBrick(double xPos, double yPos, double brickLength) {
        return generateBeginningBrick(xPos,yPos,brickLength);
    }

    private GenericBrick generateAdvancedBrick(double xPos, double yPos, double brickLength) {
        return generateBeginningBrick(xPos,yPos,brickLength);
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
                        myBricks.add(generateBrick((double) i*GamePlayer.SIZE/lineLength + 0.1*brickSize,
                                0.1*counter*GamePlayer.SIZE, brickSize));
                    }
                }
                System.out.println(currentLine);
                counter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<GenericBrick> getMyBricks() {
        return myBricks;
    }
}
