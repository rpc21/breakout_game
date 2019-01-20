package example;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class PowerUpManager {

    public static final double EXTRA_LONG_PADDLE_SIZE = 1.5;
    public static final int BOUNCERS_TO_CREATE_FOR_BOUNCER_DROP = 15;
    public static final int EXTRA_PADDLE_POWERUP_NUMBER = 0;
    public static final int SELECT_AND_DESTROY_POWERUP_NUMBER = 1;
    public static final int BALL_DROPPER_POWERUP_NUMBER = 2;
    public static final int LONG_PADDLE_POWERUP_NUMBER =3;
    public static final double POWERUP_LIFETIME = 10.0D;


    private int[] myAvailablePowerUps;
    private Paddle myExtraPaddle;
    private Paddle myLongPaddle;
    private ArrayList<Bouncer> myBouncerDrop;
    private HBox myDisplay;
    private Group root;
    private double myExtraPaddleTimeRemaining;
    private double myLongPaddleTimeRemaining;
    private double myStandardPaddleSize;
    private Paddle myGamePaddle;
    private Bouncer myGameBouncer;
    private BrickManager myBrickManager;
    private boolean inDestroyMode;
    private boolean inBallDropperMode;

    public PowerUpManager(Group root, Paddle gamePaddle, Bouncer gameBouncer, BrickManager brickManager){
        this.root = root;
        myDisplay = new HBox(10.0);
        root.getChildren().add(myDisplay);
        initializePowerUpArray();
        myGamePaddle = gamePaddle;
        myGameBouncer = gameBouncer;
        myStandardPaddleSize = myGamePaddle.getLayoutBounds().getWidth();
        myBrickManager = brickManager;
        inDestroyMode = false;
        inBallDropperMode = false;
        myExtraPaddle = new Paddle(root.getScene());
        myLongPaddle = new Paddle(root.getScene());
        myLongPaddle.setFitWidth(myExtraPaddle.getFitWidth()* EXTRA_LONG_PADDLE_SIZE);
        myBouncerDrop = new ArrayList<>();
        resetMyBouncerDrop();

    }

    private void initializePowerUpArray() {
        myAvailablePowerUps = new int[4];
        for(int i = 0; i < 4; i++){
            myAvailablePowerUps[i] = 0;
        }
    }

    private void resetMyBouncerDrop() {
        myBouncerDrop.clear();
        for (int i = 0; i < BOUNCERS_TO_CREATE_FOR_BOUNCER_DROP; i++){
            Bouncer bouncer = new Bouncer(root.getScene());
            bouncer.setY(2.0D);
            bouncer.setX(i * root.getScene().getWidth() / BOUNCERS_TO_CREATE_FOR_BOUNCER_DROP);
            bouncer.setMyYSpeed(100);
            myBouncerDrop.add(bouncer);
        }
    }

    public void displayStateOfPowerUps(){
        myDisplay.getChildren().clear();
        Text extraPaddlesRemaining = new Text("E:"+myAvailablePowerUps[EXTRA_PADDLE_POWERUP_NUMBER]);
        Text selectAndDestroyRemaining = new Text("D:"+myAvailablePowerUps[SELECT_AND_DESTROY_POWERUP_NUMBER]);
        Text ballDroppersRemaining = new Text("B:"+myAvailablePowerUps[BALL_DROPPER_POWERUP_NUMBER]);
        Text longPaddlesRemaining = new Text("L:"+myAvailablePowerUps[LONG_PADDLE_POWERUP_NUMBER]);

        myDisplay.getChildren().add(extraPaddlesRemaining);
        myDisplay.getChildren().add(selectAndDestroyRemaining);
        myDisplay.getChildren().add(ballDroppersRemaining);
        myDisplay.getChildren().add(longPaddlesRemaining);

    }

    public void addPowerUp(int powerUpNumber){
        if(powerUpNumber >= 0 && powerUpNumber <= 3){
            myAvailablePowerUps[powerUpNumber] = myAvailablePowerUps[powerUpNumber] + 1;
        }
    }

    public void usePowerUp(int powerUpNumber){
        if(powerUpNumber == EXTRA_PADDLE_POWERUP_NUMBER && myAvailablePowerUps[EXTRA_PADDLE_POWERUP_NUMBER] > 0){
            myAvailablePowerUps[EXTRA_PADDLE_POWERUP_NUMBER] = myAvailablePowerUps[EXTRA_PADDLE_POWERUP_NUMBER] - 1;
            myExtraPaddleTimeRemaining = POWERUP_LIFETIME;
//            myExtraPaddle = new Paddle(root.getScene(), myGamePaddle);
            myExtraPaddle.setX(myGamePaddle.getX() + root.getScene().getWidth()/2);
            myExtraPaddle.setMyVelocity(myGamePaddle.getMyVelocity());
            if (!root.getChildren().contains(myExtraPaddle)){
                root.getChildren().add(myExtraPaddle);
            }
        }
        else if(powerUpNumber == SELECT_AND_DESTROY_POWERUP_NUMBER && myAvailablePowerUps[SELECT_AND_DESTROY_POWERUP_NUMBER] > 0){
            myAvailablePowerUps[SELECT_AND_DESTROY_POWERUP_NUMBER] =
                    myAvailablePowerUps[SELECT_AND_DESTROY_POWERUP_NUMBER] - 1;
            inDestroyMode = true;
            root.getScene().setOnMouseClicked(e -> handleMouseClick(e.getX(), e.getY()));

        }
        else if (powerUpNumber == BALL_DROPPER_POWERUP_NUMBER && myAvailablePowerUps[BALL_DROPPER_POWERUP_NUMBER] > 0){
            myAvailablePowerUps[BALL_DROPPER_POWERUP_NUMBER] = myAvailablePowerUps[BALL_DROPPER_POWERUP_NUMBER] - 1;
            resetMyBouncerDrop();
            root.getChildren().addAll(myBouncerDrop);
            inBallDropperMode = true;

        }
        else if (powerUpNumber == LONG_PADDLE_POWERUP_NUMBER && myAvailablePowerUps[LONG_PADDLE_POWERUP_NUMBER] > 0){
            myAvailablePowerUps[LONG_PADDLE_POWERUP_NUMBER] = myAvailablePowerUps[LONG_PADDLE_POWERUP_NUMBER] - 1;
            myLongPaddleTimeRemaining = POWERUP_LIFETIME;
            myGamePaddle.setFitWidth(myStandardPaddleSize*EXTRA_LONG_PADDLE_SIZE);
        }
    }

    public void updateBouncerDropBouncers(double elapsedTime) {
        List<Bouncer> bouncersToBeRemoved = new ArrayList<>();
        List<GenericBrick> bricksToBeRemoved = new ArrayList<>();
        for (Bouncer bouncer : myBouncerDrop){
//            bouncer.setX(2.0D);
            bricksToBeRemoved.addAll(bouncer.handleBouncerCollisions(elapsedTime, root.getScene(), myGamePaddle,
                    myBrickManager.getMyBricks(),
                    root));
            System.out.println(bouncer.getMyYSpeed());
            if (bouncer.getMyYSpeed() < 0){
                bouncersToBeRemoved.add(bouncer);
            }
        }
        myBrickManager.getMyBricks().removeAll(bricksToBeRemoved);
        root.getChildren().removeAll(bricksToBeRemoved);
        myBouncerDrop.removeAll(bouncersToBeRemoved);
        root.getChildren().removeAll(bouncersToBeRemoved);
    }

    private void handleMouseClick(double x, double y) {
        if (inDestroyMode) {
            GenericBrick brickToRemove = null;
            for (GenericBrick brick : myBrickManager.getMyBricks()) {
                if (brick.contains(x, y)) {
                    brickToRemove = brick;
                    break;
                }
            }
            if (brickToRemove != null) {
                myBrickManager.getMyBricks().remove(brickToRemove);
                root.getChildren().remove(brickToRemove);
            }
        }
        inDestroyMode = false;

    }

    public void updatePowerUpStatus(double elapsedTime){
        if (root.getChildren().contains(myExtraPaddle) && myExtraPaddleTimeRemaining <= 0){
            root.getChildren().remove(myExtraPaddle);
        }
        myExtraPaddle.updatePaddlePosition(elapsedTime, root.getScene());
        myExtraPaddle.setMyVelocity(myGamePaddle.getMyVelocity());
//        System.out.println(myGamePaddle.getMyVelocity() +" "+ myExtraPaddle.getMyVelocity());
        if (root.getChildren().contains(myExtraPaddle) && myExtraPaddleTimeRemaining > 0) {
            myGameBouncer.handlePaddleCollisions(myExtraPaddle);
        }
        myExtraPaddleTimeRemaining -= elapsedTime;


        if (myGamePaddle.getLayoutBounds().getWidth() != myStandardPaddleSize && myLongPaddleTimeRemaining <= 0){
            myGamePaddle.setFitWidth((myStandardPaddleSize));
        }
        myLongPaddleTimeRemaining -= elapsedTime;


    }


    public void handleKeyInput(KeyCode code) {
        if (code == KeyCode.RIGHT) {
            myExtraPaddle.increaseVelocity();
        }
        else if (code == KeyCode.LEFT) {
            myExtraPaddle.decreaseVelocity();
        }
    }

    public void resetPowerUpManager(Bouncer myBouncer, Paddle myPaddle) {
        myGameBouncer = myBouncer;
        myGamePaddle = myPaddle;
    }

    public boolean isInDestroyMode() {
        return inDestroyMode;
    }

    public void setInDestroyMode(boolean inDestroyMode) {
        this.inDestroyMode = inDestroyMode;
    }

    public boolean isInBallDropperMode() {
        return inBallDropperMode;
    }

    public void setInBallDropperMode(boolean inBallDropperMode) {
        this.inBallDropperMode = inBallDropperMode;
    }

    public ArrayList<Bouncer> getMyBouncerDrop() {
        return myBouncerDrop;
    }
}
