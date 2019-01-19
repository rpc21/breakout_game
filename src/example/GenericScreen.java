package example;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class GenericScreen {

    protected final String TITLE = "Ryan Culhane Game";
    public static final int SIZE = 500;
    public static final Paint BACKGROUND = Color.AZURE;
    protected final String BOUNCER_IMAGE = "ball.gif";
    protected final int BOUNCER_SPEED = 40;
    protected final String PADDLE_IMAGE = "paddle.gif";

    protected StageManager myStageManager;
    protected Stage myStage;
    protected Scene myScene;

    public GenericScreen(){
        setUpScene(SIZE,SIZE,BACKGROUND);
        this.myStage = null;
    }

    public GenericScreen(StageManager stageManager){
        this.myStageManager = stageManager;
        this.myStage = myStageManager.getStage();
        setUpScene(SIZE,SIZE,BACKGROUND);

    }

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

}
