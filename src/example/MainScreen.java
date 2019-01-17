package example;

import javafx.geometry.Pos;
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

import java.util.ArrayList;

public class MainScreen {
    private final int SIZE = 500;
    public static final String PADDLE_IMAGE = "paddle.gif";
    public static final Paint BACKGROUND = Color.AZURE;

    private Rectangle welcomeScreenBackground;
    private Button playGameButton;
    private Button playTutorialButton;
    private Button cheatKeysButton;
    private ArrayList<Button> myButtons;
    private ImageView myPaddle;
    private Text welcomeText;
    private Scene myScene;


    public Scene getMyScene() {
        return setupStartPage(SIZE,SIZE,BACKGROUND);
    }

    private Scene setupStartPage(int width, int height, Paint background) {
        StackPane root = new StackPane();
        var scene = new Scene(root, width, height, background);


//        https://docs.oracle.com/javafx/2/layout/builtin_layouts.htm
        welcomeScreenBackground = new Rectangle(width, height);
        welcomeScreenBackground.setFill(new LinearGradient(0,0,0,1, true, CycleMethod.NO_CYCLE,
                new Stop[]{
                        new Stop(0, Color.web("#4977A3")),
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
//        playGameButton.setOnMouseClicked(e -> handleButtonPressed(playGameButton));
        myButtons.add(playGameButton);


        playTutorialButton = new Button("Play Tutorial");
        playTutorialButton.setPrefWidth(100.0D);
//        playTutorialButton.setOnMouseClicked(e -> handleButtonPressed(playGameButton));
        myButtons.add(playTutorialButton);


        cheatKeysButton = new Button("Cheat Keys");
        cheatKeysButton.setPrefWidth(100.0D);
//        cheatKeysButton.setOnMouseClicked(e -> handleButtonPressed(playGameButton));
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

//    private void handleButtonPressed(Button pressedButton) {
//        if (pressedButton.equals(playGameButton)){
//            myScene = loadLevelOne(SIZE,SIZE,BACKGROUND);
//        } else if (pressedButton.equals(playTutorialButton)){
//            myScene = loadTutorialMode(SIZE,SIZE,BACKGROUND);
//        } else if (pressedButton.equals(cheatKeysButton)){
//            myScene = loadCheatKeyScreen(SIZE,SIZE,BACKGROUND);
//        }
//    }
}
