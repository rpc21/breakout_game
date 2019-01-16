package example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class GamePlayer extends Application{
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


    private Scene myScene;
    private ImageView myWelcomeScreen;

    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start (Stage stage) {
        // attach scene to the stage and display the Start Page
        myScene = setupStartPage(SIZE, SIZE, BACKGROUND);
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();

        //attach "game loop" to timeline to play it
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();


    }

    private void step(double secondDelay) {
        myWelcomeScreen.setFitHeight(myScene.getHeight());
        myWelcomeScreen.setFitWidth(myScene.getWidth());
    }

    private Scene setupStartPage(int width, int height, Paint background) {
        var root = new Group();
        var scene = new Scene(root, width, height, background);
        var backgroundImage = new Image(this.getClass().getClassLoader().getResourceAsStream(WELCOME_PAGE_BACKGROUND));

        myWelcomeScreen = new ImageView(backgroundImage);

        myWelcomeScreen.setFitHeight(scene.getHeight());
        myWelcomeScreen.setFitWidth(scene.getWidth());
        myWelcomeScreen.setX(0.0D);
        myWelcomeScreen.setY(0.0D);

        root.getChildren().add(myWelcomeScreen);
        return scene;

    }
}
