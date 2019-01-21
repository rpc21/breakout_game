package example;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

/**
 * Defines the basic functionality for any screen displayed to the user in the application
 * Sub-classes: CheatKeyMode, GameLevel, GenericScreen, MainScreen, PauseScreen, TutorialMode
 */
public class GenericScreen {

    public static final int SIZE = 500;
    public static final Paint BACKGROUND = Color.AZURE;
    protected final String PADDLE_IMAGE = "paddle.gif";

    protected StageManager myStageManager;
    protected Stage myStage;
    protected Scene myScene;

    /**
     * GenericScreen default constructor
     */
    public GenericScreen(){
        setUpScene(SIZE,SIZE,BACKGROUND);
        this.myStage = null;
    }

    /**
     * GenericScreen constructor
     * Sets up the scene and sets the stage and the StageManager
     * @param stageManager
     */
    public GenericScreen(StageManager stageManager){
        this.myStageManager = stageManager;
        this.myStage = myStageManager.getStage();
        setUpScene(SIZE,SIZE,BACKGROUND);

    }

    /**
     * Getter for myScene
     * @return myScene
     */
    public Scene getMyScene() {
        return this.myScene;
    }

    protected void setUpScene(int width, int height, Paint background) {
        var root = new Group();
        var scene = new Scene(root,width,height,background);
        myScene = scene;
    }

    protected void handleKeyInput(KeyCode code) {
        if (code == KeyCode.BACK_SPACE){
            System.out.println(myStageManager+"is the stageManager on line 47");
            myStageManager.switchScene(myStageManager.getMainScreen());
        }
    }

    protected void step(double elapsedTime){}

    protected void centerHBoxText(HBox hBox, double height, Scene scene) {
        hBox.relocate(scene.getWidth()/2 - hBox.getBoundsInParent().getWidth()/2,
                height);
    }
}
