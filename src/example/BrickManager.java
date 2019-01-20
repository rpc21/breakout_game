package example;

import javafx.scene.Group;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BrickManager {

    public static final String LEVEL_ONE = "resources/level_one_layout.txt";
    public static final String LEVEL_TWO = "resources/level_two_layout.txt";
    public static final String LEVEL_THREE = "resources/level_three_layout.txt";

    private File pathToBrickLayout;
    private ArrayList<GenericBrick> myBricks;


    private int myCurrentMode;
    private int myScore;
    private boolean loseLifeDueToDangerBrick;

    public BrickManager(GameDifficulty currentMode){
        this(1,currentMode);
    }

    public BrickManager(int levelNumber){
        this(levelNumber,new GameDifficulty(GameDifficulty.BEGINNING_MODE));
    }

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
            return new OneHitBrick(xPos,yPos,brickLength);
        }
        else if (randomNumber <= 0.9D){
            return new TwoHitBrick(xPos,yPos,brickLength);
        }
        else{
            return new ThreeHitBrick(xPos,yPos,brickLength);
        }
    }

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

    public void handleEffectedBricks(List<GenericBrick> effectedBricks, Group root) {
        for (Iterator<GenericBrick> iterator = effectedBricks.iterator(); iterator.hasNext(); ) {
            GenericBrick brick = iterator.next();
            if (effectedBricks.contains(brick)){
                myScore += brick.getMyPointValue();
                removeOrReplaceBrick(brick, root);
            }
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

    public ArrayList<GenericBrick> getMyBricks() {
        return myBricks;
    }

    public int getMyScore() {
        return myScore;
    }

    public boolean isLoseLifeDueToDangerBrick() {
        return loseLifeDueToDangerBrick;
    }

    public void setLoseLifeDueToDangerBrick(boolean loseLifeDueToDangerBrick) {
        this.loseLifeDueToDangerBrick = loseLifeDueToDangerBrick;
    }
}
