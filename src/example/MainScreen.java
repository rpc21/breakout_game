package example;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;

public class MainScreen extends GenericScreen{

    public static final double SIZE_TO_VBOX_PADDING_RATIO = 40.0;
    private Rectangle welcomeScreenBackground;
    private Button playGameButton;
    private Button playTutorialButton;
    private Button cheatKeysButton;
    private ArrayList<Button> myButtons;
    private ImageView myPaddle;
    private Text welcomeText;
    private VBox myVBox;




    public MainScreen(StageManager stageManager){
        myStageManager = stageManager;
        this.myStage = stageManager.getStage();
        setUpScene(this.SIZE,this.SIZE,this.BACKGROUND);
    }

    @Override
    protected void setUpScene(int width, int height, Paint background) {
        StackPane root = new StackPane();
        var scene = new Scene(root, width, height, background);

        initializeWelcomeScreenBackground();
        initializePaddle();
        initializeWelcomeText();

        createButtons();

        addSceneElementsToVBox();

        root.getChildren().add(welcomeScreenBackground);
        root.getChildren().add(myVBox);

        myScene = scene;

    }

    private void addSceneElementsToVBox() {
        myVBox = new VBox(SIZE / SIZE_TO_VBOX_PADDING_RATIO);
        myVBox.getChildren().addAll(new Text(""),new Text(""), new Text(""));
        myVBox.getChildren().add(myPaddle);
        myVBox.getChildren().add(new Text(""));
        myVBox.getChildren().add(welcomeText);
        myVBox.getChildren().add(new Text(""));
        myVBox.getChildren().addAll(myButtons);
        myVBox.setAlignment(Pos.CENTER);
        myVBox.getChildren().add(new Text(""));
    }

    private void initializeWelcomeText() {
        welcomeText = new Text("Welcome to Breakout");
        welcomeText.setFont(Font.font("Palatino", FontWeight.BOLD, 40.0D));
        welcomeText.setTextAlignment(TextAlignment.CENTER);
    }

    private void initializePaddle() {
        var paddle = new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
        myPaddle = new ImageView(paddle);
    }

    private void initializeWelcomeScreenBackground() {
        //        https://docs.oracle.com/javafx/2/layout/builtin_layouts.htm
        welcomeScreenBackground = new Rectangle(SIZE, SIZE);
        welcomeScreenBackground.setFill(new LinearGradient(0,0,0,1, true, CycleMethod.NO_CYCLE,
                new Stop[]{
                        new Stop(0, Color.web("#4977A3")),
                        new Stop(0.5, Color.web("#B0C6DA")),
                        new Stop(1,Color.web("#9CB6CF")),}));
        welcomeScreenBackground.setStroke(Color.web("#D0E6FA"));
    }

    private void createButtons() {
        myButtons = new ArrayList<>();
        playGameButton = new Button("Play Game");
        playGameButton.setOnAction(e -> {
            myStageManager.switchScene(new PauseScreen(myStageManager));
        });
        myButtons.add(playGameButton);


        playTutorialButton = new Button("Play Tutorial");
        playTutorialButton.setOnAction(e -> {
            myStageManager.switchScene(new TutorialMode(myStageManager));
        });
        myButtons.add(playTutorialButton);


        cheatKeysButton = new Button("Cheat Keys");
        cheatKeysButton.setOnAction(e -> {
            myStageManager.switchScene(new CheatKeyMode(myStageManager));
        });
        myButtons.add(cheatKeysButton);
    }



}
