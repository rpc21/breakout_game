package example;

import javafx.stage.Stage;

public class StageManager {

    public static final String TITLE = "Ryan Culhane Game";

    private MainScreen mainScreen;
    private PauseScreen pauseScreen;
    private CheatKeyMode cheatKeyMode;
    private TutorialMode tutorialMode;
    private Stage stage;
    private GenericScreen currentScreen;

    public MainScreen getMainScreen() {
        return mainScreen;
    }

    public StageManager(Stage stage) {
        this.mainScreen = new MainScreen(this);
        this.pauseScreen = new PauseScreen(this);
        this.cheatKeyMode = new CheatKeyMode(this);
        this.tutorialMode = new TutorialMode(this);
        this.stage = stage;
        this.currentScreen = new GenericScreen();
        stage.setScene(currentScreen.getMyScene());
    }

    public void switchScene(GenericScreen newScene){
        stage.setScene(newScene.getMyScene());
        stage.show();
        stage.setTitle(TITLE);
        currentScreen = newScene;
    }

    public GenericScreen getCurrentScreen() {
        return currentScreen;
    }

    public Stage getStage(){
        return stage;
    }
}
