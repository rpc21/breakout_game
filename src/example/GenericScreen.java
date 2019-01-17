package example;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public abstract class GenericScreen {

    protected final String TITLE = "Ryan Culhane Game";
    protected final int SIZE = 500;
    protected final Paint BACKGROUND = Color.AZURE;
    protected final String BOUNCER_IMAGE = "ball.gif";
    protected final int BOUNCER_SPEED = 40;
    protected final String PADDLE_IMAGE = "paddle.gif";

    protected Scene myScene;

    public Scene getMyScene() {
        setupScreen(SIZE,SIZE,BACKGROUND);
        return myScene;
    }

    protected void setupScreen(int width, int height, Paint background) {
        var root = new Group();
        var scene = new Scene(root,width,height,background);
        myScene = scene;
    }

    protected void step(double elapsedTime){}
}
