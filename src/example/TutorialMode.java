package example;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        myStageManager = stageManager;
        this.myStage = stageManager.getStage();
        myScene = setupGame(SIZE,SIZE,BACKGROUND);
    }


    private Scene setupGame (int width, int height, Paint background) {
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
//        myBouncer.setX(width / 2 - myBouncer.getBoundsInLocal().getWidth() / 2);
//        myBouncer.setY(height / 2 - myBouncer.getBoundsInLocal().getHeight() / 2);
        myBouncers = new ArrayList<>();
        for(int i = 0; i<10; i++){
            Bouncer bouncer = new Bouncer(image);
//            bouncer.setX((int) (Math.random() * scene.getWidth()));
//            bouncer.setY((int) (Math.random() * scene.getHeight()));
            myBouncers.add(bouncer);
            root.getChildren().add(bouncer);
        }
//        myPaddle = new Rectangle(width / 2 - 25, height / 2 - 100, MOVER_SIZE, MOVER_SIZE);
//        myPaddle.setFill(MOVER_COLOR);
//        myGrower = new Rectangle(width / 2 - 25, height / 2 + 50, GROWER_SIZE, GROWER_SIZE);
//        myGrower.setFill(GROWER_COLOR);
        // order added to the group is the order in which they are drawn
//        root.getChildren().add(myBouncers);
        myPaddle.setX(SIZE/2);
        myPaddle.setY(SIZE-50);
//        root.getChildren().add(myPaddle);
//        root.getChildren().add(myGrower);
        root.getChildren().add(myPaddle);
        // respond to input, the e gives a name to the event that happened so you can get the info
//        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
//        scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        return scene;
    }

    public void step (double elapsedTime) {
        // update attributes
        boolean highlightGrower = false;
        for(Bouncer bouncer : myBouncers) {
            bouncer.updateBouncer(elapsedTime, myScene);
//            if (myGrower.getBoundsInParent().intersects(bouncer.getBoundsInParent())) {
//                highlightGrower = true;
//            }
        }
//        if(highlightGrower){
//            myGrower.setFill(HIGHLIGHT);
//        }
//        else{
//            myGrower.setFill(GROWER_COLOR);
//        }
//        myPaddle.setRotate(myPaddle.getRotate() - 1);

        // check for collisions
        // with shapes, can check precisely
//        var intersect = Shape.intersect(myPaddle, myGrower);
//        if (intersect.getBoundsInLocal().getWidth() != -1) {
//            myPaddle.setFill(HIGHLIGHT);
//        }
//        else {
//            myPaddle.setFill(MOVER_COLOR);
//        }
        // with images can only check bounding box
//        if (myGrower.getBoundsInParent().intersects(myBouncer.getBoundsInParent())) {
//            myGrower.setFill(HIGHLIGHT);
//        }
//        else {
//            myGrower.setFill(GROWER_COLOR);
//        }
    }
}
