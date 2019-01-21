package example;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import java.util.ArrayList;

public class TutorialMode extends GenericScreen{
    public static final int FRAMES_PER_SECOND = 60;
    private static final double TOP_HBOX_DISPLAY_HEIGHT = 0.2;
    private static final double BOTTOM_HBOX_DISPLAY_HEIGHT = 0.8;
    private static final double CENTER_HBOX_DISPLAY_HEIGHT = 0.5;
    private static final int TUTORIAL_BOUNCER_Y_SPEED = 40;
    private static final int TUTORIAL_BOUNCER_X_SPEED = 20;
    private static final double TUTORIAL_BOUNCER_STARTING_HEIGHT = 0.45;


    private Text tutorialInstructionsText;
    private Text navigationInstructionsText;
    private Text centerInstructionsText;
    private Group root;
    private StageManager myStageManager;
    private int currentTutorial;
    private int myTutorialNumber;
    private HBox myHBoxTop;
    private HBox myHBoxBottom;
    private HBox myHBoxCenter;
    private Paddle myPaddle;
    private Bouncer myBouncer;
    private DangerBrick myDangerBrick;
    private PermanentBrick myPermanentBrick;
    private ThreeHitBrick myThreeHitBrick;
    private TwoHitBrick myTwoHitBrick;
    private OneHitBrick myOneHitBrick;
    private PowerUpManager myPowerUpManager;


    /**
     * TutorialMode Constructor - initializes the possible objects and loads tutorial scene 0
     * @param stageManager
     */
    public TutorialMode(StageManager stageManager){
        this.myStageManager = stageManager;
        tutorialInstructionsText = new Text("");
        myTutorialNumber = 0;
        setUpScene(GenericScreen.SIZE,GenericScreen.SIZE,GenericScreen.BACKGROUND);
        initializePossibleObjects();
        initializeObjectsForTutorialNumber();
        myPowerUpManager = new PowerUpManager(root,myPaddle,myBouncer,new BrickManager(1));
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        myScene.setOnMouseClicked(e -> handleMouseClicked());
    }

    private void initializePossibleObjects() {
        myHBoxTop = new HBox(20.0D);
        tutorialInstructionsText = new Text("");
        myHBoxTop.getChildren().addAll(tutorialInstructionsText);
        root.getChildren().add(myHBoxTop);

        myHBoxBottom = new HBox(20.0D);
        navigationInstructionsText = new Text("");
        myHBoxBottom.getChildren().add(navigationInstructionsText);
        root.getChildren().add(myHBoxBottom);

        myHBoxCenter = new HBox(20.0D);
        centerInstructionsText = new Text("");
        myHBoxCenter.getChildren().add(centerInstructionsText);
        root.getChildren().add(myHBoxCenter);

        myPaddle = new Paddle(myScene);
        myBouncer = new Bouncer(myScene);

        initializeBricks();
    }

    private void initializeBricks() {
        double brickLength = 50.0D;
        double xPos = myScene.getWidth()/2 - brickLength/2;
        double yPos= myScene.getHeight()/2;

        myDangerBrick = new DangerBrick(xPos, yPos,brickLength);
        myOneHitBrick = new OneHitBrick(xPos, yPos,brickLength);
        myTwoHitBrick = new TwoHitBrick(xPos, yPos,brickLength);
        myThreeHitBrick = new ThreeHitBrick(xPos, yPos,brickLength);
        myPermanentBrick = new PermanentBrick(xPos, yPos,brickLength);
    }

    private void initializeObjectsForTutorialNumber(){
        if(myTutorialNumber == 0){
            loadTutorialZero();
        }
        else if (myTutorialNumber == 1){
            loadTutorialOne();
        }
        else if (myTutorialNumber == 2){
            loadTutorialTwo();
        }
        else if (myTutorialNumber == 3){
            loadTutorialThree();
        }
        else if (myTutorialNumber == 4){
            loadTutorialFour();
        }
        else if (myTutorialNumber == 5){
            loadTutorialFive();
        }
        else if (myTutorialNumber == 6){
            loadTutorialSix();
        }
        else if (myTutorialNumber == 7){
            loadTutorialSeven();
        }
        else if (myTutorialNumber == 8){
            loadTutorialEight();
        }
        else if (myTutorialNumber == 9){
            loadTutorialNine();
        }
        else if (myTutorialNumber == 10){
            loadTutorialTen();
        }
        else if (myTutorialNumber == 11){
            loadTutorialEleven();
        }
    }

    private void loadTutorialEleven() {
        tutorialInstructionsText.setText("Before starting a level a splash screen will appear.\n"+
                "The splash screen will also appear if you press SPACE during a level.\n"+
                "From the splash screen a player can use cheat keys to change levels, add lives, etc.\n"+
                "Press SPACE to see what the splash screen looks like and start playing!");
        navigationInstructionsText.setText("Press SPACE to exit tutorial and start playing!");
    }

    private void loadTutorialTen() {
        tutorialInstructionsText.setText("For other power-ups, you decide when to use them.\n"+
                "'E' will let you control two paddles at once for 10 seconds.\n"+
                "'L' will let you control an extra long paddle for 10 seconds.\n"+
                "'B' will drop bouncers from the top of the screen and destroy the top row of bricks.\n"+
                "'D' will pause the game and let you click on any brick to destroy it.\n"+
                "Bricks destroyed using power-ups will not cause you to lose any lives.\n"+
                "You can see your available power-ups and how to access them in the top left corner of a level.");
    }

    private void loadTutorialNine() {
        tutorialInstructionsText.setText("You may get a power-up that will add an extra life or more time.\n"+
                "These power-ups will be automatically handled.");
    }

    private void loadTutorialEight() {
        root.getChildren().remove(myDangerBrick);
        tutorialInstructionsText.setText("You will also encounter various power-ups when you play.\n"+
                "When you destroy a brick there is a chance you will receive a power-up.\n"+
                "Messages regarding power-ups will be displayed at the bottom of the screen.\n"+
                "Let's explore some of the power-ups you may encounter!");
    }

    private void loadTutorialSeven() {
        root.getChildren().remove(myPermanentBrick);
        tutorialInstructionsText.setText("As you play there are different types of bricks you will encounter.\n"+
                "Be careful! A collision with this brick will cost you a life but will then disappear.\n"+
                "This brick adds 0 points to your score when it is hit.\n"+
                "This brick does not have to be cleared to " +
                "complete the level." +
                ".\n"+
                "This kind of brick can only be found when playing in the Intermediate and Advanced modes.");
        root.getChildren().add(myDangerBrick);
    }

    private void loadTutorialSix() {
        root.getChildren().remove(myThreeHitBrick);
        tutorialInstructionsText.setText("As you play there are different types of bricks you will encounter.\n"+
                "This one is a brick that is cannot be cleared by collisions with the bouncer.\n"+
                "This brick adds 0 points to your score when it is hit.\n"+
                "This brick does not have to be cleared to " +
                "complete the level." +
                ".\n"+
                "This kind of brick can only be found when playing in the Intermediate and Advanced modes.");
        root.getChildren().add(myPermanentBrick);
    }

    private void loadTutorialFive() {
        root.getChildren().remove(myTwoHitBrick);
        tutorialInstructionsText.setText("As you play there are different types of bricks you will encounter.\n"+
                "This one is a brick that takes three collisions with the bouncer to clear.\n"+
                "This brick adds 10 points to your score each time it is hit.");
        root.getChildren().add(myThreeHitBrick);
    }

    private void loadTutorialFour() {
        root.getChildren().remove(myOneHitBrick);
        tutorialInstructionsText.setText("As you play there are different types of bricks you will encounter.\n"+
                "This one is a brick that takes two collisions with the bouncer to clear.\n"+
                "This brick adds 10 points to your score each time it is hit.");
        root.getChildren().add(myTwoHitBrick);
    }

    private void loadTutorialThree() {
        root.getChildren().remove(myBouncer);
        root.getChildren().remove(myPaddle);
        tutorialInstructionsText.setText("As you play there are different types of bricks you will encounter.\n"+
                "This one is a generic brick that can be cleared after one collision with the bouncer.\n"+
                "This brick adds 10 points to your score each time it is hit.");
        root.getChildren().add(myOneHitBrick);
    }

    private void loadTutorialTwo() {
        tutorialInstructionsText.setText("Move the paddle to be under the bouncer when it falls.\n"+
                "If the bouncer goes below the paddle you lose a life.\n"+
                "See how the paddle's speed changes how the bouncer bounces off the paddle.\n"+
                "Press ENTER to set the bouncer in motion.");
        root.getChildren().add(myBouncer);
        resetBouncerAndPaddle();
    }

    private void loadTutorialOne() {
        tutorialInstructionsText.setText("You can change the paddle's speed with the left or right arrows, try " +
                "it!\n"+
                "The paddle can also wrap around the screen.");
        centerInstructionsText.setText("");
        root.getChildren().add(myPaddle);
        resetPaddle();
    }

    private void loadTutorialZero() {
        tutorialInstructionsText.setText("Welcome to the tutorial for Ryan's Breakout Implementation!");
        navigationInstructionsText.setText("Click to advance to next tutorial");
        centerInstructionsText.setText("The objective of the game is to clear all clearable bricks before you run" +
                " out of time or lives.\n"+
                "You lose a life if the bouncer goes below your paddle");
    }

    private void handleMouseClicked() {
        myTutorialNumber += 1;
        initializeObjectsForTutorialNumber();
    }

    @Override
    protected void setUpScene (int width, int height, Paint background) {
        root = new Group();
        myScene = new Scene(root, width, height, background);

    }

    @Override
    protected void step (double elapsedTime) {
        if (!myPowerUpManager.isInBallDropperMode()) {
            centerHBoxText(myHBoxTop, myScene.getHeight() * TOP_HBOX_DISPLAY_HEIGHT, myScene);
            centerHBoxText(myHBoxBottom, myScene.getHeight() * BOTTOM_HBOX_DISPLAY_HEIGHT, myScene);
            centerHBoxText(myHBoxCenter, myScene.getHeight() * CENTER_HBOX_DISPLAY_HEIGHT, myScene);
            myPaddle.updatePaddlePosition(elapsedTime, myScene);
            handleTutorialOneAnimation();
            myBouncer.handleBouncerCollisions(elapsedTime, myScene, myPaddle, new ArrayList<>(), root);
            handleLossCondition();
            myPowerUpManager.updatePowerUpStatus(elapsedTime);
        }
        myPowerUpManager.handleBallDropperMode(elapsedTime);
    }

    private void handleLossCondition() {
        if (myBouncer.getY() >= myPaddle.getY()) {
            resetBouncerAndPaddle();
        }
    }

    private void handleTutorialOneAnimation() {
        if (myTutorialNumber == 1 &&
                (myPaddle.getMyVelocity() == Paddle.MAX_PADDLE_VELOCITY || myPaddle.getMyVelocity() == Paddle.MIN_PADDLE_VELOCITY)) {
            tutorialInstructionsText.setText("That is as fast as the paddle can go!");
        } else if (myTutorialNumber == 1) {
            tutorialInstructionsText.setText("You can change the paddle's speed with the left or right arrows, try " +
                    "it!");
        }
    }

    private void resetBouncerAndPaddle() {
        resetPaddle();
        resetBouncer();
    }

    private void resetPaddle() {
        myPaddle.setY(myScene.getHeight() * (BOTTOM_HBOX_DISPLAY_HEIGHT - 0.05));
        myPaddle.setX(myScene.getWidth()/2 - myPaddle.getBoundsInLocal().getWidth()/2);
        myPaddle.setMyVelocity(0);
    }

    private void resetBouncer() {
        myBouncer.setMyXSpeed(0);
        myBouncer.setMyYSpeed(0);
        myBouncer.setX(myScene.getWidth()/2 - myBouncer.getBoundsInLocal().getWidth()/2);
        myBouncer.setY(myScene.getHeight() * TUTORIAL_BOUNCER_STARTING_HEIGHT);
    }

    @Override
    protected void handleKeyInput(KeyCode code) {
        if (code == KeyCode.BACK_SPACE){
            System.out.println(myStageManager+"is the stageManager on line 47");
            myStageManager.switchScene(myStageManager.getMainScreen());
        }
        if (code == KeyCode.RIGHT) {
            myPaddle.increaseVelocity();
        }
        else if (code == KeyCode.LEFT) {
            myPaddle.decreaseVelocity();
        }
        else if (code == KeyCode.ENTER){
            myBouncer.setMyYSpeed(TUTORIAL_BOUNCER_Y_SPEED);
            myBouncer.setMyXSpeed(TUTORIAL_BOUNCER_X_SPEED);
        }
        else if (code == KeyCode.SPACE){
            myStageManager.switchScene(myStageManager.getPauseScreen());
        }

    }

    /**
     * Getter for myScene
     * @return myScene
     */
    public Scene getMyScene() {
        return myScene;
    }
}
