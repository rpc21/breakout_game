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
    private double timeRemaining;
    private GameDifficulty currentMode;
    private Bouncer myBouncer;
    private Paddle myPaddle;
    private StageManager myStageManager;
    private Scene myScene;
    private ArrayList<GenericBrick> myBricks;
    private Group root;
    private int myLevelNumber;
    private BrickManager myBrickManager;

    @Override
    public Scene getMyScene() {
        return myScene;
    }

    public GameLevel(){
        super();
    }

    public GameLevel(GameLevel currentLevel) {
        this(currentLevel.getMyStageManager(),currentLevel.getMyLevelNumber());

    }



    public GameLevel(StageManager stageManager, int levelNumber){
        myStageManager = stageManager;
        System.out.println(myStageManager + "is the stageManager in GameLevel");
        currentMode = new GameDifficulty(GameDifficulty.ADVANCED_MODE);
        myLevelNumber = levelNumber;
        myBrickManager = new BrickManager(myLevelNumber,currentMode);
        playerScore = 0;
        timeRemaining = currentMode.getStartTime();
        myNumberOfLivesRemaining = currentMode.getStartLives();
        setUpLevel();
    }

    protected void setUpLevel() {
        // create one top level collection to organize the things in the scene
        root = new Group();
        // create a place to see the shapes
        var scene = new Scene(root, SIZE, SIZE, BACKGROUND);

        timeRemaining = currentMode.getStartTime();

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
        myBricks = myBrickManager.getMyBricks();

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
        myBouncer.updateBouncer(elapsedTime,myScene, myPaddle, myBricks, root);
        timeRemaining -= elapsedTime;
        handleLifeLoss();
        handleEndOfGame();
    }

    private void handleEndOfGame() {
        if (myBricks.size() == 0){
            System.out.println("Congratulations you won!");
            displayWinningScreen();
        }
        if (myNumberOfLivesRemaining <= 0  || timeRemaining < 0.0){
            System.out.println("Sorry you lost!");
            displayLosingScreen();
        }
    }

    private void displayLosingScreen() {
    }

    private void displayWinningScreen() {
    }

    private void handleLifeLoss() {
        if (myBouncer.getY() > myPaddle .getY()){
            myNumberOfLivesRemaining -= 1;
            root.getChildren().removeAll(myBouncer);
            root.getChildren().removeAll(myPaddle);
            initializeBouncer(myScene);
            initializePaddle(myScene);
            root.getChildren().addAll(myPaddle,myBouncer);
            System.out.println(myNumberOfLivesRemaining);
        }
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
            myStageManager.switchScene(new PauseScreen(myStageManager));
        }
        else if (code == KeyCode.ENTER){
            myBouncer.setMyXSpeed(currentMode.getMaxBouncerXSpeed()/2);
            myBouncer.setMyYSpeed(currentMode.getBouncerYSpeed());
        }
    }

    public int getMyNumberOfLivesRemaining() {
        return myNumberOfLivesRemaining;
    }

    public void setMyNumberOfLivesRemaining(int myNumberOfLivesRemaining) {
        this.myNumberOfLivesRemaining = myNumberOfLivesRemaining;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public double getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(double timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public GameDifficulty getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(GameDifficulty currentMode) {
        this.currentMode = currentMode;
    }

    public Bouncer getMyBouncer() {
        return myBouncer;
    }

    public void setMyBouncer(Bouncer myBouncer) {
        this.myBouncer = myBouncer;
    }

    public Paddle getMyPaddle() {
        return myPaddle;
    }

    public void setMyPaddle(Paddle myPaddle) {
        this.myPaddle = myPaddle;
    }

    public StageManager getMyStageManager() {
        return myStageManager;
    }

    public void setMyStageManager(StageManager myStageManager) {
        this.myStageManager = myStageManager;
    }

    public void setMyScene(Scene myScene) {
        this.myScene = myScene;
    }

    public ArrayList<GenericBrick> getMyBricks() {
        return myBricks;
    }

    public void setMyBricks(ArrayList<GenericBrick> myBricks) {
        this.myBricks = myBricks;
    }

    public int getMyLevelNumber() {
        return myLevelNumber;
    }


}
