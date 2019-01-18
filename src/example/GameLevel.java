package example;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;

public class GameLevel extends GenericScreen{

    public static final int BEGINNING_MODE = 13789;
    public static final int INTERMEDIATE_MODE = 13790;
    public static final int ADVANCED_MODE = 13791;
    public static final String BOUNCER_IMAGE = "ball.gif";
    public static final String PADDLE_IMAGE = "paddle.gif";
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
    private ArrayList<GenericBrick> myBricks;

    public GameLevel(StageManager stageManager){
        myStageManager = stageManager;
        currentMode = BEGINNING_MODE;
        setUpLevel();
    }

    protected void setUpLevel() {
        // create one top level collection to organize the things in the scene
        var root = new Group();
        // create a place to see the shapes
        var scene = new Scene(root, SIZE, SIZE, BACKGROUND);

        generateBricks();

        initializeBouncer();
        initializePaddle();

        root.getChildren().add(myBouncer);
        root.getChildren().add(myPaddle);
        root.getChildren().addAll(myBricks);


        // respond to input, the e gives a name to the event that happened so you can get the info
//        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
//        scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        myScene = scene;

    }

    protected void generateBricks() {
        myBricks = new ArrayList<>();
    }

    protected void initializeBouncer(){
        var bouncerImage = new Image(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));
        myBouncer = new Bouncer(bouncerImage);
        myBouncer.setX(myScene.getWidth()/ 2 - myBouncer.getBoundsInLocal().getWidth() / 2);
        myBouncer.setY(myScene.getHeight() / 2 - myBouncer.getBoundsInLocal().getHeight() / 2);
    }

    protected void initializePaddle(){
        var paddleImage = new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
        myPaddle = new Paddle(paddleImage);
        myPaddle.setX(myScene.getWidth() / 2 - myPaddle.getBoundsInLocal().getWidth() / 2);
        myPaddle.setY(myScene.getHeight() * 0.9);
    }


}
