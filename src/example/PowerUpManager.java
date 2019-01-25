package example;

import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class to handle the power-ups
 * This includes the power-up functionality and how they are invoked
 * This class also include animation surrounding power-ups
 */
public class PowerUpManager {

    public static final int INVALID_POWER_UP_NUMBER = 589302;
    private static final double EXTRA_LONG_PADDLE_SIZE = 1.5;
    private static final int BOUNCERS_TO_CREATE_FOR_BOUNCER_DROP = 15;
    public static final int EXTRA_PADDLE_POWERUP_NUMBER = 0;
    public static final int SELECT_AND_DESTROY_POWERUP_NUMBER = 1;
    public static final int BALL_DROPPER_POWERUP_NUMBER = 2;
    public static final int LONG_PADDLE_POWERUP_NUMBER =3;
    private static final double POWERUP_LIFETIME = 10.0D;


    private int[] myAvailablePowerUps;
    private Paddle myExtraPaddle;
    private Paddle myLongPaddle;
    private ArrayList<BouncerDropBouncer> myBouncerDrop;
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

    /**
     * PowerUpManager Constructor
     * Initializes possible power-ups and gain information about current game objects
     * @param root
     * @param gamePaddle
     * @param gameBouncer
     * @param brickManager
     */
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
            BouncerDropBouncer bouncer = new BouncerDropBouncer(root.getScene());
            bouncer.setY(2.0D);
            bouncer.setX(i * root.getScene().getWidth() / BOUNCERS_TO_CREATE_FOR_BOUNCER_DROP);
            bouncer.setMyYSpeed(100);
            myBouncerDrop.add(bouncer);
        }
    }

    /**
     * Displays the keys used to trigger power ups and the number of power-ups of each kind
     * that the player has available to them
     */
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

    /**
     * Adds another power-up of the specified kind to the power-ups the user has
     * @param powerUpNumber
     */
    public void addPowerUp(int powerUpNumber){
        if(powerUpNumber >= 0 && powerUpNumber <= 3){
            myAvailablePowerUps[powerUpNumber] = myAvailablePowerUps[powerUpNumber] + 1;
        }
    }

    /**
     * Invokes the power-up
     * @param powerUpNumber
     */
    public void usePowerUp(int powerUpNumber){
        if(powerUpNumber == EXTRA_PADDLE_POWERUP_NUMBER && myAvailablePowerUps[EXTRA_PADDLE_POWERUP_NUMBER] > 0){
            useExtraPaddlePowerUp();
        }
        else if(powerUpNumber == SELECT_AND_DESTROY_POWERUP_NUMBER && myAvailablePowerUps[SELECT_AND_DESTROY_POWERUP_NUMBER] > 0){
            useSelectAndDestroyPowerUp();
        }
        else if (powerUpNumber == BALL_DROPPER_POWERUP_NUMBER && myAvailablePowerUps[BALL_DROPPER_POWERUP_NUMBER] > 0){
            useBouncerDropperPowerUp();
        }
        else if (powerUpNumber == LONG_PADDLE_POWERUP_NUMBER && myAvailablePowerUps[LONG_PADDLE_POWERUP_NUMBER] > 0){
            useLongPaddlePowerUp();
        }
    }

    private void useLongPaddlePowerUp() {
        myAvailablePowerUps[LONG_PADDLE_POWERUP_NUMBER] = myAvailablePowerUps[LONG_PADDLE_POWERUP_NUMBER] - 1;
        myLongPaddleTimeRemaining = POWERUP_LIFETIME;
        myGamePaddle.setFitWidth(myStandardPaddleSize*EXTRA_LONG_PADDLE_SIZE);
    }

    private void useBouncerDropperPowerUp() {
        myAvailablePowerUps[BALL_DROPPER_POWERUP_NUMBER] = myAvailablePowerUps[BALL_DROPPER_POWERUP_NUMBER] - 1;
        resetMyBouncerDrop();
        root.getChildren().addAll(myBouncerDrop);
        inBallDropperMode = true;
    }

    private void useSelectAndDestroyPowerUp() {
        myAvailablePowerUps[SELECT_AND_DESTROY_POWERUP_NUMBER] =
                myAvailablePowerUps[SELECT_AND_DESTROY_POWERUP_NUMBER] - 1;
        inDestroyMode = true;
        root.getScene().setOnMouseClicked(e -> handleMouseClick(e.getX(), e.getY()));
    }

    private void useExtraPaddlePowerUp() {
        myAvailablePowerUps[EXTRA_PADDLE_POWERUP_NUMBER] = myAvailablePowerUps[EXTRA_PADDLE_POWERUP_NUMBER] - 1;
        myExtraPaddleTimeRemaining = POWERUP_LIFETIME;
        myExtraPaddle.setX(myGamePaddle.getX() + root.getScene().getWidth()/2);
        myExtraPaddle.setMyXSpeed(myGamePaddle.getMyXSpeed());
        if (!root.getChildren().contains(myExtraPaddle)){
            root.getChildren().add(myExtraPaddle);
        }
    }

    private void updateBouncerDropBouncers(double elapsedTime) {
        Set<BouncerDropBouncer> bouncersToBeRemoved = new HashSet<>();
        Set<GenericBrick> bricksToBeRemoved = new HashSet<>();
        for (BouncerDropBouncer bouncer : myBouncerDrop){
            bricksToBeRemoved.addAll(bouncer.handleBouncerDropCollisions(elapsedTime, myBrickManager));
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

    /**
     * Handles animation of power-ups
     * @param elapsedTime
     */
    public void updatePowerUpStatus(double elapsedTime){
        updateExtraPaddleStatus(elapsedTime);
        updateLongPaddlePowerUpStatus(elapsedTime);
    }

    private void updateLongPaddlePowerUpStatus(double elapsedTime) {
        if (myGamePaddle.getLayoutBounds().getWidth() != myStandardPaddleSize && myLongPaddleTimeRemaining <= 0){
            myGamePaddle.setFitWidth((myStandardPaddleSize));
        }
        myLongPaddleTimeRemaining -= elapsedTime;
    }

    private void updateExtraPaddleStatus(double elapsedTime) {
        if (root.getChildren().contains(myExtraPaddle) && myExtraPaddleTimeRemaining <= 0){
            root.getChildren().remove(myExtraPaddle);
        }
        myExtraPaddle.updatePaddlePosition(elapsedTime, root.getScene());
        myExtraPaddle.setMyXSpeed(myGamePaddle.getMyXSpeed());
        if (root.getChildren().contains(myExtraPaddle) && myExtraPaddleTimeRemaining > 0) {
            myGameBouncer.handlePaddleCollisions(myExtraPaddle);
        }
        myExtraPaddleTimeRemaining -= elapsedTime;
    }

    /**
     * Handles the animation for BouncerDropper power-up
     * Needs to be separate because it is in a time-stopped game state
     * @param elapsedTime
     */
    public void handleBallDropperMode(double elapsedTime) {
        if (isInBallDropperMode()){
            if (!getMyBouncerDrop().isEmpty()){
                updateBouncerDropBouncers(elapsedTime);
            }
            else {
                setInBallDropperMode(false);
            }

        }
    }

    /**
     * Causes extra paddle to respond to arrow keys
     * @param code
     */
    public void handleKeyInput(KeyCode code) {
        if (code == KeyCode.RIGHT) {
            myExtraPaddle.increaseVelocity();
        }
        else if (code == KeyCode.LEFT) {
            myExtraPaddle.decreaseVelocity();
        }
    }

    /**
     * Makes PowerUpManager recognize when the Bouncer and Paddle get reset
     * @param myBouncer
     * @param myPaddle
     */
    public void resetPowerUpManager(Bouncer myBouncer, Paddle myPaddle) {
        myGameBouncer = myBouncer;
        myGamePaddle = myPaddle;
    }

    /**
     * Getter for whether game should be in time-stopped destroy mode
     * @return inDestroyMode
     */
    public boolean isInDestroyMode() {
        return inDestroyMode;
    }

    /**
     * Getter for whether game should be in time-stopped BouncerDropper mode
     * @return inBallDropperMode
     */
    public boolean isInBallDropperMode() {
        return inBallDropperMode;
    }

    /**
     * Setter for whether game should be in time-stopped BouncerDropper mode
     * @param inBallDropperMode
     */
    public void setInBallDropperMode(boolean inBallDropperMode) {
        this.inBallDropperMode = inBallDropperMode;
    }

    /**
     * Gets the Bouncers for the bouncer drop animation
     * @return myBouncerDrop
     */
    public ArrayList<BouncerDropBouncer> getMyBouncerDrop() {
        return myBouncerDrop;
    }
}
