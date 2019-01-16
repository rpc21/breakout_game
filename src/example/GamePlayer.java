package example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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
    private Rectangle welcomeScreenBackground;
    private Button playGameButton;
    private Button playTutorialButton;
    private Button cheatKeysButton;

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
//        myWelcomeScreen.setFitHeight(myScene.getHeight());
//        myWelcomeScreen.setFitWidth(myScene.getWidth());
        welcomeScreenBackground.setHeight(myScene.getHeight());
        welcomeScreenBackground.setWidth(myScene.getWidth());
//        trialButton.setLayoutX(SIZE/2.0 - trialButton.getWidth()/2.0);
//        System.out.println(trialButton.getWidth());
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

//        https://docs.oracle.com/javafx/2/layout/builtin_layouts.htm
        welcomeScreenBackground = new Rectangle(width, height);
        welcomeScreenBackground.setFill(new LinearGradient(0,0,0,1, true, CycleMethod.NO_CYCLE,
                new Stop[]{
                        new Stop(0,Color.web("#4977A3")),
                        new Stop(0.5, Color.web("#B0C6DA")),
                        new Stop(1,Color.web("#9CB6CF")),}));
        welcomeScreenBackground.setStroke(Color.web("#D0E6FA"));


        playGameButton = new Button("Play Game");
        playGameButton.setPrefWidth(100.0D);
        playTutorialButton = new Button("Play Tutorial");
        playTutorialButton.setPrefWidth(100.0D);
        cheatKeysButton = new Button("Cheat Keys");
        cheatKeysButton.setPrefWidth(100.0D);


        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(playGameButton, playTutorialButton, cheatKeysButton);
//        vBox.setAlignment(Pos.CENTER);
        vBox.setPrefWidth(100.0D);
        vBox.setLayoutX(width/2 - 40.0D);
        vBox.setLayoutY(height / 1.5);

        root.getChildren().add(welcomeScreenBackground);
        root.getChildren().add(vBox);
//        root.getChildren().add(hBox);
//        root.getChildren().add(trialButton);



        return scene;

    }
}
