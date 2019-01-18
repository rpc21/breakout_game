package example;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class GameLevel {

    public static final int BEGINNING_MODE = 13789;
    public static final int INTERMEDIATE_MODE = 13790;
    public static final int ADVANCED_MODE = 13791;
    public static final String BOUNCER_IMAGE = "ball.gif";
    public static final Paint BACKGROUND = Color.AZURE;
    public static final int SIZE = 500;



    private int myNumberOfLivesRemaining;
    private int playerScore;
    private int levelNumber;
    private String bricksSpecificationFile;
    private int timeRemaining;
    private int currentMode;
    private Bouncer myBouncer;
    private Paddle myPaddle;
    private StageManager myStageManager;
    private Scene myScene;
    private ArrayList<Brick> myBricks;

    public GameLevel(StageManager stageManager){
        myStageManager = stageManager;
        currentMode = BEGINNING_MODE;
        setUpLevel();
    }

    private void setUpLevel() {
        // create one top level collection to organize the things in the scene
        var root = new Group();
        // create a place to see the shapes
        var scene = new Scene(root, SIZE, SIZE, BACKGROUND);
        // make some shapes and set their properties
        var image = new Image(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));


        myBouncer = initializeBouncer();
        myPaddle = initializePaddle();

        root.getChildren().add(myBouncer);
        root.getChildren().add(myPaddle);
        myBouncer = new Bouncer(image);
        // x and y represent the top left corner, so center it
        myBouncer.setX(scene.getWidth()/ 2 - myBouncer.getBoundsInLocal().getWidth() / 2);
        myBouncer.setY(scene.getHeight() / 2 - myBouncer.getBoundsInLocal().getHeight() / 2);


        // order added to the group is the order in which they are drawn
//        root.getChildren().add(myBouncers);

//        root.getChildren().add(myPaddle);
//        root.getChildren().add(myGrower);
        root.getChildren().add(myPaddle);
        // respond to input, the e gives a name to the event that happened so you can get the info
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        return scene;

    }


}
