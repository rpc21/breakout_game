package example;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

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
    private Paddle myGamePaddle;
    private BrickManager myBrickManager;

    public PowerUpManager(Group root, Paddle gamePaddle, BrickManager brickManager){
        this.root = root;
        myDisplay = new HBox(10.0);
        root.getChildren().add(myDisplay);
        initializePowerUpArray();
        myGamePaddle = gamePaddle;
        myBrickManager = brickManager;
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
            bouncer.setY(0);
            bouncer.setX(i * root.getScene().getWidth() / BOUNCERS_TO_CREATE_FOR_BOUNCER_DROP);
            bouncer.setMyYSpeed(250);
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
            myExtraPaddle = new Paddle(root.getScene(), myGamePaddle);
            myExtraPaddle.setX(myGamePaddle.getX() + root.getScene().getWidth()/2);
            root.getChildren().add(myExtraPaddle);
        }
        else if(powerUpNumber == SELECT_AND_DESTROY_POWERUP_NUMBER && myAvailablePowerUps[SELECT_AND_DESTROY_POWERUP_NUMBER] > 0){
            myAvailablePowerUps[SELECT_AND_DESTROY_POWERUP_NUMBER] =
                    myAvailablePowerUps[SELECT_AND_DESTROY_POWERUP_NUMBER] - 1;

        }
        else if (powerUpNumber == BALL_DROPPER_POWERUP_NUMBER && myAvailablePowerUps[BALL_DROPPER_POWERUP_NUMBER] > 0){
            myAvailablePowerUps[BALL_DROPPER_POWERUP_NUMBER] = myAvailablePowerUps[BALL_DROPPER_POWERUP_NUMBER] - 1;

        }
        else if (powerUpNumber == LONG_PADDLE_POWERUP_NUMBER && myAvailablePowerUps[LONG_PADDLE_POWERUP_NUMBER] > 0){
            myAvailablePowerUps[LONG_PADDLE_POWERUP_NUMBER] = myAvailablePowerUps[LONG_PADDLE_POWERUP_NUMBER] - 1;
            myLongPaddleTimeRemaining = POWERUP_LIFETIME;
            myGamePaddle.setFitWidth(myGamePaddle.getFitWidth()*EXTRA_LONG_PADDLE_SIZE);
        }
    }







}
