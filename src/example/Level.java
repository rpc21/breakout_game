package example;

import java.io.*;
import java.util.ArrayList;

public class Level {

    public static final String LEVEL_ONE = "resources/level_one_layout.txt";
    public static final String LEVEL_TWO = "resources/level_two_layout.txt";
    public static final String LEVEL_THREE = "resources/level_three_layout.txt";

    private BrickManager myBrickManager;
    private File pathToBrickLayout;
    private ArrayList<GenericBrick> myBricks;

    public ArrayList<GenericBrick> getMyBricks() {
        return myBricks;
    }

    public Level(){
        pathToBrickLayout = new File(LEVEL_ONE);
        myBrickManager = new BrickManager(new GameDifficulty(GameDifficulty.BEGINNING_MODE));
        myBricks = new ArrayList<>();
        initializeBlocksFromFile();
    }

    public Level(int levelNumber, GameDifficulty currentMode){
        if (levelNumber == 1){
            pathToBrickLayout = new File(LEVEL_ONE);
        }
        else if (levelNumber == 2){
            pathToBrickLayout = new File(LEVEL_TWO);
        }
        else if (levelNumber == 3){
            pathToBrickLayout = new File(LEVEL_THREE);
        }
        System.out.println("Level Number passed: " + levelNumber);
        System.out.println("Path to brick layout:"+ pathToBrickLayout);
        myBrickManager = new BrickManager(currentMode);
        myBricks = new ArrayList<>();
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
                        myBricks.add(myBrickManager.generateBrick((double) i*GamePlayer.SIZE/lineLength + 0.1*brickSize,0.1*counter*GamePlayer.SIZE, brickSize));
                    }
                }
                System.out.println(currentLine);
                counter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
