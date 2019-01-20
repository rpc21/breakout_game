package example;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class TutorialMode extends GenericScreen{
    public static final String TITLE = "Ryan Culhane Game";
    public static final int SIZE = 500;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.AZURE;
    public static final Paint HIGHLIGHT = Color.OLIVEDRAB;
    public static final String BOUNCER_IMAGE = "ball.gif";
    public static final int BOUNCER_SPEED = 40;
    public static final String PADDLE_IMAGE = "paddle.gif";
    //    public static final String WELCOME_PAGE_BACKGROUND = "welcome_page_background.gif";
    public static final String WELCOME_PAGE_BACKGROUND = "ball.gif";
    public static final double TOP_HBOX_DISPLAY_HEIGHT = 0.2;
    public static final double BOTTOM_HBOX_DISPLAY_HEIGHT = 0.8;
    public static final double CENTER_HBOX_DISPLAY_HEIGHT = 0.5;


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



    public TutorialMode(StageManager stageManager){
        this.myStageManager = stageManager;
        tutorialInstructionsText = new Text("");
        myTutorialNumber = 0;
        setUpScene(GenericScreen.SIZE,GenericScreen.SIZE,GenericScreen.BACKGROUND);
        initializePossibleObjects();
        initializeObjectsForTutorialNumber();
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
            tutorialInstructionsText.setText("Welcome to the tutorial for Ryan's Breakout Implementation!");
            navigationInstructionsText.setText("Click to advance to next tutorial");
            centerInstructionsText.setText("The objective of the game is to clear all clearable bricks before you run" +
                    " out of time or lives.\n"+
                    "You lose a life if the ball goes below your paddle");
        }
        else if (myTutorialNumber == 1){
            tutorialInstructionsText.setText("You can change the paddle's speed with the left or right arrows, try " +
                    "it!\n"+
                    "The paddle can also wrap around the screen.");
            centerInstructionsText.setText("");
            root.getChildren().add(myPaddle);
            myPaddle.setX(myScene.getWidth()/2 - myPaddle.getBoundsInLocal().getWidth()/2);
            myPaddle.setY(myScene.getHeight()/2);

        }
        else if (myTutorialNumber == 2){
            

        }
        else if (myTutorialNumber == 3){

        }
        else if (myTutorialNumber == 4){

        }
        else if (myTutorialNumber == 5){

        }
        else if (myTutorialNumber == 6){

        }
        else if (myTutorialNumber == 7){

        }
        else if (myTutorialNumber == 8){

        }
        else if (myTutorialNumber == 9){

        }
        else if (myTutorialNumber == 10){

        }

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
        centerHBoxText(myHBoxTop, myScene.getHeight()* TOP_HBOX_DISPLAY_HEIGHT, myScene);
        centerHBoxText(myHBoxBottom, myScene.getHeight()* BOTTOM_HBOX_DISPLAY_HEIGHT, myScene);
        centerHBoxText(myHBoxCenter, myScene.getHeight()* CENTER_HBOX_DISPLAY_HEIGHT, myScene);
        myPaddle.updatePaddlePosition(elapsedTime, myScene);
        if (myTutorialNumber == 1 &&
                (myPaddle.getMyVelocity() == Paddle.MAX_PADDLE_VELOCITY || myPaddle.getMyVelocity() == Paddle.MIN_PADDLE_VELOCITY)){
            tutorialInstructionsText.setText("That is as fast as the paddle can go!");
        }else if (myTutorialNumber == 1){
            tutorialInstructionsText.setText("You can change the paddle's speed with the left or right arrows, try " +
                    "it!");
        }
    }

    @Override
    protected void handleKeyInput(KeyCode code) {
        if (code == KeyCode.RIGHT) {
            myPaddle.increaseVelocity();
        }
        else if (code == KeyCode.LEFT) {
            myPaddle.decreaseVelocity();
        }
    }

    public Scene getMyScene() {
        return myScene;
    }
}
