package example;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
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

    private Group myGroupRoot;
    private Scene myScene;
    //    private ImageView myBouncer;
    private List<Bouncer> myBouncers;

    public Scene getMyScene() {
        return myScene;
    }

    //    private Rectangle myPaddle;
    private Rectangle myGrower;
    private ImageView myPaddle;



    public TutorialMode(StageManager stageManager){
        super(stageManager);
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    }

    @Override
    protected void setUpScene (int width, int height, Paint background) {
        // create one top level collection to organize the things in the scene
        var root = new Group();
        // create a place to see the shapes
        var scene = new Scene(root, width, height, background);
        // make some shapes and set their properties
        var image = new Image(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));
//        myBouncer = new ImageView(image);
        var paddle = new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
        myPaddle = new ImageView(paddle);
        // x and y represent the top left corner, so center it
        myBouncers = new ArrayList<>();
        for(int i = 0; i<10; i++){
            Bouncer bouncer = new Bouncer(image, new GameDifficulty(GameDifficulty.BEGINNING_MODE));
            myBouncers.add(bouncer);
            root.getChildren().add(bouncer);
        }
        myPaddle.setX(SIZE/2);
        myPaddle.setY(SIZE-50);
        root.getChildren().add(myPaddle);

        myScene = scene;
    }

//    @Override
//    protected void step (double elapsedTime) {
//        // update attributes
//        for(Bouncer bouncer : myBouncers) {
//            bouncer.updateBouncer(elapsedTime, myScene);
//
//        }
//    }

    @Override
    protected void handleKeyInput(KeyCode code) {
        super.handleKeyInput(code);
    }
}
