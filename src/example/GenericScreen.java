package example;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class GenericScreen {

    protected final String TITLE = "Ryan Culhane Game";
    protected final int SIZE = 500;
    protected final Paint BACKGROUND = Color.AZURE;
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

    public GenericScreen(Stage stage){
        setUpScene(SIZE,SIZE,BACKGROUND);
//        this.myStageManager = stageManager;
        this.myStage = stage;

    }

    public Scene getMyScene() {
        return this.myScene;
    }

    protected void setUpScene(int width, int height, Paint background) {
        var root = new Group();
        var scene = new Scene(root,width,height,background);
        myScene = scene;
    }

    protected void step(double elapsedTime){}
}
