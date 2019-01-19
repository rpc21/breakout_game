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
    private GameLevel gameLevel;



    public StageManager(Stage stage) {
        this.mainScreen = new MainScreen(this);
        this.gameLevel = new GameLevel(this,3);
        this.pauseScreen = new PauseScreen(this);
        this.cheatKeyMode = new CheatKeyMode(this);
        this.tutorialMode = new TutorialMode(this);
        this.stage = stage;
        this.currentScreen = new GenericScreen();
        stage.setScene(currentScreen.getMyScene());
    }

    public void switchScene(GenericScreen newScene){
        stage.setScene(newScene.getMyScene());
        stage.setTitle(TITLE);
        stage.show();
        currentScreen = newScene;
        if (newScene instanceof GameLevel){
            this.gameLevel = (GameLevel) newScene;
        }
    }

    public MainScreen getMainScreen() {
        return mainScreen;
    }

    public PauseScreen getPauseScreen() {
        return pauseScreen;
    }

    public CheatKeyMode getCheatKeyMode() {
        return cheatKeyMode;
    }

    public TutorialMode getTutorialMode() {
        return tutorialMode;
    }

    public GenericScreen getCurrentScreen() {
        return currentScreen;
    }

    public Stage getStage(){
        return stage;
    }

    public GameLevel getGameLevel() {
        return gameLevel;
    }
}
