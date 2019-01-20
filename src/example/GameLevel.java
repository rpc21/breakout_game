package example;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;

public class GameLevel extends GenericScreen{

    public static final String BOUNCER_IMAGE = "ball.gif";
    public static final Paint BACKGROUND = Color.AZURE;
    public static final int SIZE = 500;
    public static final double BOTTOM_LINE_DISPLAY_LOCATION = 0.95;
    public static final double TOP_LINE_DISPLAY_LOCATION = 0.03;


    private int myNumberOfLivesRemaining;
    private int playerScore;
    private double timeRemaining;
    private GameDifficulty currentMode;
    protected Bouncer myBouncer;
    private Paddle myPaddle;
    private StageManager myStageManager;
    private Scene myScene;
    private ArrayList<GenericBrick> myBricks;
    private Group root;
    private int myLevelNumber;
    private BrickManager myBrickManager;
    protected HBox topLineDisplay;
    protected HBox bottomLineDisplay;
    protected Text importantMessages;
    protected Text scoreText;
    protected Text timeText;
    protected Text livesRemainingText;
    private boolean activeGameMode;
    private PowerUpManager myPowerUpManager;

    @Override
    public Scene getMyScene() {
        return myScene;
    }

    public GameLevel(){
        super();
    }

    public GameLevel(GameLevel currentLevel) {
        this(currentLevel.getMyStageManager(),currentLevel.getMyLevelNumber(), currentLevel.getCurrentMode().getMyCurrentMode());
    }



    public GameLevel(StageManager stageManager, int levelNumber, int gameDifficulty){
        myStageManager = stageManager;
        System.out.println(myStageManager + "is the stageManager in GameLevel");
        currentMode = new GameDifficulty(gameDifficulty);
        myLevelNumber = levelNumber;
        myBrickManager = new BrickManager(myLevelNumber,currentMode);
        playerScore = 0;
        activeGameMode = false;
        timeRemaining = currentMode.getStartTime();
        myNumberOfLivesRemaining = currentMode.getStartLives();
        setUpLevel();
        myPowerUpManager = new PowerUpManager(root, myPaddle, myBouncer, myBrickManager);
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
        initializeTopLineDisplay(scene);
        initializeBottomLine(scene);

        root.getChildren().add(myBouncer);
        root.getChildren().add(myPaddle);
        root.getChildren().addAll(myBricks);
        root.getChildren().add(bottomLineDisplay);
        root.getChildren().add(topLineDisplay);

        System.out.println("Reached past adding the children");

        createKeyBindings(scene);


        // respond to input, the e gives a name to the event that happened so you can get the info
//        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
//        scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        myScene = scene;

    }

    protected void initializeTopLineDisplay(Scene scene) {
        topLineDisplay = new HBox(10.0D);
        scoreText = new Text("Score: "+playerScore);
        timeText = new Text("Time Remaining: "+ ((int) timeRemaining)+" seconds");
        livesRemainingText = new Text("Lives Remaining: "+myNumberOfLivesRemaining);
        topLineDisplay.getChildren().addAll(timeText,scoreText,livesRemainingText);
        centerHBoxText(topLineDisplay, scene.getHeight()* TOP_LINE_DISPLAY_LOCATION, scene);
    }

    protected void initializeBottomLine(Scene scene) {
        bottomLineDisplay = new HBox(10.0D);
        importantMessages = new Text("Press ENTER to Set bouncer in Motion");
        bottomLineDisplay.getChildren().add(importantMessages);
        importantMessages.setTextAlignment(TextAlignment.CENTER);
        centerHBoxText(bottomLineDisplay, scene.getHeight()*BOTTOM_LINE_DISPLAY_LOCATION, scene);
    }

    private void createKeyBindings(Scene scene) {
        System.out.println(myStageManager+"is the stageManager when keyBindings get assigned");
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    }

    protected void generateBricks(Scene scene) {
        System.out.println("Reach generate Bricks");
        myBricks = myBrickManager.getMyBricks();

    }

    protected void initializeBouncer(Scene scene){
        System.out.println("Raeched initialize Bouncer");
        myBouncer = new Bouncer(scene, currentMode);
    }

    protected void initializePaddle(Scene scene){
        System.out.println("Reached initialize Paddle");
        myPaddle = new Paddle(scene);
    }

    @Override
    protected void step (double elapsedTime) {
        // update attributes
        if (activeGameMode && !myPowerUpManager.isInDestroyMode() && !myPowerUpManager.isInBallDropperMode()) {
            timeRemaining -= elapsedTime;
            myPaddle.updatePaddlePosition(elapsedTime,myScene);
            List<GenericBrick> effectedBricks = myBouncer.handleBouncerCollisions(elapsedTime,myScene, myPaddle, myBricks,
                    root);
            myBrickManager.handleEffectedBricks(effectedBricks, root);
            myPowerUpManager.updatePowerUpStatus(elapsedTime);

            handleLifeLoss();
            handleEndOfGame();
        }
        if (myPowerUpManager.isInBallDropperMode()){
            if (!myPowerUpManager.getMyBouncerDrop().isEmpty()){
                myPowerUpManager.updateBouncerDropBouncers(elapsedTime);
            }
            else {
                myPowerUpManager.setInBallDropperMode(false);
            }

        }
        playerScore = myBrickManager.getMyScore();
        centerHBoxText(bottomLineDisplay, myScene.getHeight()* BOTTOM_LINE_DISPLAY_LOCATION, myScene);
        updateTopLine();
        myPowerUpManager.displayStateOfPowerUps();
    }

    private void updateTopLine() {
        scoreText.setText("Score: "+playerScore);
        timeText.setText("Time Remaining: "+((int) timeRemaining) + " seconds");
        livesRemainingText.setText("Lives Remaining: "+myNumberOfLivesRemaining);
    }

    private void handleEndOfGame() {
        if (onlyPermanentAndDangerBricksRemain()){
//            System.out.println("Congratulations you won!");
            displayWinningScreen();
            activeGameMode = false;
        }
        if (gameIsOver()){
            System.out.println("Sorry you lost!");
            displayLosingScreen();
            activeGameMode = false;
        }
    }

    private boolean gameIsOver() {
        return myNumberOfLivesRemaining <= 0  || timeRemaining < 0.0;
    }

    private boolean onlyPermanentAndDangerBricksRemain() {
        for (GenericBrick brick: myBrickManager.getMyBricks()){
            if (brick instanceof OneHitBrick || brick instanceof TwoHitBrick || brick instanceof ThreeHitBrick){
                return false;
            }
        }
        return true;
    }

    private void displayLosingScreen() {
        resetBouncerAndPaddle();
        importantMessages.setText("Bummer, you lost! Press BACKSPACE to go back to the main screen or R to retry");
        centerHBoxText(bottomLineDisplay, myScene.getHeight()* BOTTOM_LINE_DISPLAY_LOCATION, myScene);

    }

    private void displayWinningScreen() {
        resetBouncerAndPaddle();
        importantMessages.setText("Congratulations, you won!!! Press SPACE to try a new level.");
        centerHBoxText(bottomLineDisplay, myScene.getHeight()* BOTTOM_LINE_DISPLAY_LOCATION, myScene);
    }

    private void handleLifeLoss() {
        if (myBouncer.getY() > myPaddle.getY() || myBrickManager.isLoseLifeDueToDangerBrick()){
            myNumberOfLivesRemaining -= 1;
            resetBouncerAndPaddle();
            activeGameMode = false;
            System.out.println(myNumberOfLivesRemaining);
            myBrickManager.setLoseLifeDueToDangerBrick(false);
            importantMessages.setText("Lost a life!!! Press ENTER to set bouncer in motion");
            centerHBoxText(bottomLineDisplay, myScene.getHeight()* BOTTOM_LINE_DISPLAY_LOCATION, myScene);
        }
    }

    private void resetBouncerAndPaddle() {
        root.getChildren().removeAll(myBouncer);
        root.getChildren().removeAll(myPaddle);
        initializeBouncer(myScene);
        initializePaddle(myScene);
        myPowerUpManager.resetPowerUpManager(myBouncer, myPaddle);
        root.getChildren().addAll(myPaddle,myBouncer);
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
        else if (code == KeyCode.R && gameIsOver()) {
            myStageManager.switchScene(new GameLevel(myStageManager, myLevelNumber,
                    currentMode.getMyCurrentMode()));
        }
        else if (code == KeyCode.ENTER){
            importantMessages.setText("Press SPACE to pause the game");
            activeGameMode = true;
            myBouncer.setMyXSpeed(currentMode.getMaxBouncerXSpeed()/2);
            myBouncer.setMyYSpeed(currentMode.getBouncerYSpeed());
            centerHBoxText(bottomLineDisplay, myScene.getHeight()* BOTTOM_LINE_DISPLAY_LOCATION, myScene);
        }
        else if (code == KeyCode.P){
            for (int j = 0; j<10; j++){
                for (int i = 0; i<4; i++){
                    myPowerUpManager.addPowerUp(i);
                }
            }
        }
        else if (code == KeyCode.E){
            myPowerUpManager.usePowerUp(PowerUpManager.EXTRA_PADDLE_POWERUP_NUMBER);
        }
        else if (code == KeyCode.D){
            myPowerUpManager.usePowerUp(PowerUpManager.SELECT_AND_DESTROY_POWERUP_NUMBER);

        }
        else if (code == KeyCode.B){
            myPowerUpManager.usePowerUp(PowerUpManager.BALL_DROPPER_POWERUP_NUMBER);
        }
        else if (code == KeyCode.L){
            myPowerUpManager.usePowerUp(PowerUpManager.LONG_PADDLE_POWERUP_NUMBER);
        }
        myPowerUpManager.handleKeyInput(code);

    }

    private void exitDestroyMode() {
        if (myPowerUpManager.isInDestroyMode()){
            myPowerUpManager.setInDestroyMode(false);
            activeGameMode = true;
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
