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
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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

    private Stage stage;
    private Scene myScene;
    private ImageView myWelcomeScreen;
    private Rectangle welcomeScreenBackground;
    private Button playGameButton;
    private Button playTutorialButton;
    private Button cheatKeysButton;
    private ArrayList<Button> myButtons;
    private ImageView myPaddle;
    private Text welcomeText;
    private TutorialMode tutorialMode;
//    private GamePlayMode gamePlayMode;
    private CheatKeyMode cheatKeyMode;

    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start (Stage stage) {
        // attach scene to the stage and display the Start Page
        tutorialMode = new TutorialMode();
        cheatKeyMode = new CheatKeyMode();

        myScene = setupStartPage(SIZE, SIZE, BACKGROUND);


        stage.setScene(myScene);
        System.out.println(myScene);
        stage.setTitle(TITLE);
        stage.show();



        playTutorialButton.setOnAction(e -> {
            myScene = tutorialMode.getMyScene();
            stage.setScene(myScene);
            stage.show();
            System.out.println(myScene);
        });

        cheatKeysButton.setOnAction(e -> {
            myScene = cheatKeyMode.getMyScene();
            stage.setScene(myScene);
            stage.show();
            System.out.println(myScene);
        });




        //attach "game loop" to timeline to play it
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();


    }

    private void step(double secondDelay) {
        welcomeScreenBackground.setHeight(myScene.getHeight());
        welcomeScreenBackground.setWidth(myScene.getWidth());
        System.out.println(myScene);

        System.out.println(tutorialMode.getMyScene());
        if (myScene != null && tutorialMode.getMyScene() != null && myScene == tutorialMode.getMyScene()){
            tutorialMode.step(secondDelay);
        }
    }

    private Scene setupStartPage(int width, int height, Paint background) {
//        var root = new Group();
        StackPane root = new StackPane();
        var scene = new Scene(root, width, height, background);


//        https://docs.oracle.com/javafx/2/layout/builtin_layouts.htm
        welcomeScreenBackground = new Rectangle(width, height);
        welcomeScreenBackground.setFill(new LinearGradient(0,0,0,1, true, CycleMethod.NO_CYCLE,
                new Stop[]{
                        new Stop(0,Color.web("#4977A3")),
                        new Stop(0.5, Color.web("#B0C6DA")),
                        new Stop(1,Color.web("#9CB6CF")),}));
        welcomeScreenBackground.setStroke(Color.web("#D0E6FA"));

        var paddle = new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
        myPaddle = new ImageView(paddle);


        welcomeText = new Text("Welcome to Breakout");
        welcomeText.setFont(Font.font("Palatino", FontWeight.BOLD, 40.0D));
        welcomeText.setTextAlignment(TextAlignment.CENTER);

        myButtons = new ArrayList<>();

        playGameButton = new Button("Play Game");
        playGameButton.setPrefWidth(100.0D);
        playGameButton.setOnMouseClicked(e -> handleButtonPressed(playGameButton));
        myButtons.add(playGameButton);


        playTutorialButton = new Button("Play Tutorial");
        playTutorialButton.setPrefWidth(100.0D);
        playTutorialButton.setOnMouseClicked(e -> handleButtonPressed(playGameButton));
        myButtons.add(playTutorialButton);


        cheatKeysButton = new Button("Cheat Keys");
        cheatKeysButton.setPrefWidth(100.0D);
        cheatKeysButton.setOnMouseClicked(e -> handleButtonPressed(playGameButton));
        myButtons.add(cheatKeysButton);



        VBox vBox = new VBox(SIZE/40.0);
        vBox.getChildren().addAll(new Text(""),new Text(""), new Text(""));
        vBox.getChildren().add(myPaddle);
        vBox.getChildren().add(new Text(""));
        vBox.getChildren().add(welcomeText);
        vBox.getChildren().add(new Text(""));
        vBox.getChildren().addAll(playGameButton, playTutorialButton, cheatKeysButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(new Text(""));

        root.getChildren().add(welcomeScreenBackground);
        root.getChildren().add(vBox);



        return scene;

    }

    private void handleButtonPressed(Button pressedButton) {
        if (pressedButton.equals(playGameButton)){
            myScene = loadLevelOne(SIZE,SIZE,BACKGROUND);
        } else if (pressedButton.equals(playTutorialButton)){
            myScene = loadTutorialMode(SIZE,SIZE,BACKGROUND);
        } else if (pressedButton.equals(cheatKeysButton)){
            myScene = loadCheatKeyScreen(SIZE,SIZE,BACKGROUND);
        }
    }

    private Scene loadCheatKeyScreen(int width, int height, Paint background) {
        var root = new Group();
        var scene = new Scene(root, width, height, background);
        myScene = scene;

        return scene;

    }

    private Scene loadTutorialMode(int width, int height, Paint background) {
        var root = new Group();
        var scene = new Scene(root, width, height, background);
        myScene = scene;
        return scene;

    }

    private Scene loadLevelOne(int width, int height, Paint background) {
        var root = new Group();
        var scene = new Scene(root, width, height, background);
        myScene = scene;

        return scene;

    }


}
