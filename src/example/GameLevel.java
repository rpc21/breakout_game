package example;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;

public class GameLevel extends GenericScreen{

    public static final String BOUNCER_IMAGE = "ball.gif";
    public static final String PADDLE_IMAGE = "paddle.gif";
    public static final Paint BACKGROUND = Color.AZURE;
    public static final int SIZE = 500;



    private int myNumberOfLivesRemaining;
    private int playerScore;
    private int levelNumber;
    private String bricksSpecificationFile;
    private int timeRemaining;
    private GameDifficulty currentMode;
    private Bouncer myBouncer;
    private Paddle myPaddle;
    private StageManager myStageManager;
    private Scene myScene;
    private ArrayList<GenericBrick> myBricks;

    @Override
    public Scene getMyScene() {
        return myScene;
    }

    public GameLevel(StageManager stageManager){
        myStageManager = stageManager;
        System.out.println(myStageManager + "is the stageManager in GameLevel");
        currentMode = new GameDifficulty(GameDifficulty.ADVANCED_MODE);
        setUpLevel();
    }

    protected void setUpLevel() {
        // create one top level collection to organize the things in the scene
        var root = new Group();
        // create a place to see the shapes
        var scene = new Scene(root, SIZE, SIZE, BACKGROUND);

        generateBricks(scene);

        initializeBouncer(scene);
        initializePaddle(scene);

        root.getChildren().add(myBouncer);
        root.getChildren().add(myPaddle);
        root.getChildren().addAll(myBricks);

        System.out.println("Reached past adding the children");

        createKeyBindings(scene);


        // respond to input, the e gives a name to the event that happened so you can get the info
//        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
//        scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        myScene = scene;

    }

    private void createKeyBindings(Scene scene) {
        System.out.println(myStageManager+"is the stageManager when keyBindings get assigned");
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
//        scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
    }

    protected void generateBricks(Scene scene) {
        System.out.println("Reach generate Bricks");
        myBricks = new ArrayList<>();
    }

    protected void initializeBouncer(Scene scene){
        System.out.println("Raeched initialize Bouncer");
        var bouncerImage = new Image(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));
        myBouncer = new Bouncer(bouncerImage, currentMode);
        myBouncer.setX(scene.getWidth() / 2 - myBouncer.getBoundsInLocal().getWidth() / 2);
        myBouncer.setY(scene.getHeight() / 2 - myBouncer.getBoundsInLocal().getHeight() / 2);
    }

    protected void initializePaddle(Scene scene){
        System.out.println("Reached initialize Paddle");
        var paddleImage = new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
        myPaddle = new Paddle(paddleImage);
        myPaddle.setX(scene.getWidth() / 2 - myPaddle.getBoundsInLocal().getWidth() / 2);
        myPaddle.setY(scene.getHeight() * 0.9);
    }

    @Override
    protected void step (double elapsedTime) {
        // update attributes
        myPaddle.updatePaddlePosition(elapsedTime,myScene);
        myBouncer.updateBouncer(elapsedTime,myScene, myPaddle, myBricks);

    }

    @Override
    protected void handleKeyInput (KeyCode code) {
        if (code == KeyCode.RIGHT) {
            myPaddle.increaseVelocity();
        }
        else if (code == KeyCode.LEFT) {
            myPaddle.decreaseVelocity();
        }
        else if (code == KeyCode.BACK_SPACE){
            myStageManager.switchScene(myStageManager.getMainScreen());
        }
        else if (code == KeyCode.SPACE){
            myStageManager.switchScene(myStageManager.getPauseScreen());
        }
    }

}
